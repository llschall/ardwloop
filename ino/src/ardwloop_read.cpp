
#include "ardwloop_read.h"

#include "ardwloop_buffer.h"
#include "ardwloop_core.h"
#include "ardwloop_utils.h"

#include <stdio.h>

int bfI = 0;
int bfN = 0;

int DELAY_READ = 99;

void set_delay_read(int ms) {
    DELAY_READ = ms;
}

int get_delay_read() {
    return DELAY_READ;
}


void reset_bfn() { bfN = 0;}

int impl_read(const int n) {

  char arr[n];
  int r = func_read(arr, n);
  for (int i = 0; i < r; i++) {
    buffer_set(i, arr[i]);
  }
  return r;
}

char rd() {
  if (bfI < bfN) {
    char c = buffer(bfI);
    if (c == 'Z')
      reboot();
    if (c == 'N') {
      wr('N');
    } else {
      bfI++;
      return c;
    }
  }

  log("available()");
  int i = func_available();
  while (i == 0) {
    func_delay(DELAY_READ);
    i = func_available();
  }

  int bfS = buffer_size();

  if (i > bfS)
    i = bfS;

  bfN = impl_read(i);
  bfI = 0;

  return rd();
}

int read_v() {

  int v = 0;
  while (true) {
    char c = rd();
      if (c == 'Y') {
         reset();
         return 0;
      } // if

      if (c == '+') {
        return v;
      } // if
      if (c == '-') {
        v *= -1;
        return v;
      } // if

      int i = map_c(c);
      v = 10 * v + i;
   } // while

}

void receive_r(const char END, int Rc, const char *H, const int Kc, const char *K, struct D **Rv) {

  char r = rd();

  if (r != 'R') {
    log("Expected R but got %c %c\n", r, r);
  }

  for (int i = 0; i < Rc; i++) {
    char k = K[i];

    char h = H[0];
    int h_i = 0;
    while(h!=END) {
      
      char c = rd();
      if (c == 'Y') {
        reset();
        return;
      } // if

      if (c != k) {
        log("== ERR %c != %c", c, k);
        reset();
        return;
      } // if

      c = rd();
      if (c == 'Y') {
        reset();
        return;
      } // if

      if (c != h) {
        log("== ERR %c != %c", c, h);
        reset();
        return;
      } // if

      int v = read_v();

      struct D *d = Rv[i];

      switch (h_i) {
      case 0:
        d->v = v;
        break;
      case 1:
        d->w = v;
        break;
      case 2:
        d->x = v;
        break;
      case 3:
        d->y = v;
        break;
      case 4:
        d->z = v;
        break;
      } // switch

      h_i++;
      h=H[h_i];
    }   // while h
  }  // for i

  char t = rd();

  if (t != 'T') {
    log("Expected T but got %c %c\n", t, t);
  }

} // receive()
