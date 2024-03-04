
#include "ardwloop_read.h"

#include "ardwloop_buffer.h"
#include "ardwloop_core.h"
#include "ardwloop_utils.h"

#include <stdio.h>

int impl_read0(const int n) {

  log("IMPL READ");

  char arr[n];
  int r = func_read(arr, n);
  for (int i = 0; i < r; i++) {
    buffer_set(i, arr[i]);
  }
  return r;
}

void receive_r(int Rc, char *H, char *K, struct D **Rv) {

  char r = rd();

  if (r != 'R') {
    printf("Expected R but got %c\n", r);
  }

  for (int i = 0; i < Rc; i++) {
    log("for RC");

    char k = K[i];

    for (unsigned long j = 0; j < 5; j++) {

      char m[16];
      snprintf(m, 16, "for long %d 5", j);
      log(m);

      char h = H[j];

      char c = rd();
      if (c == 'Y') {
        reset();
        return;
      } // if

      if (c != k) {
        reset();
        return;
      } // if

      c = rd();
      if (c == 'Y') {
        reset();
        return;
      } // if

      if (c != h) {
        reset();
        return;
      } // if

      int v = 0;

      while (true) {
        c = rd();
        if (c == 'Y') {
          reset();
          return;
        } // if

        if (c == '+') {
          log("break +");
          break;
        } // if
        if (c == '-') {
          log("break -");
          v *= -1;
          break;
        } // if

        int i = map_c(c);
        v = 10 * v + i;
      } // while

      struct D *d = Rv[i];

      switch (j) {
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
    }   // for j
  }     // for i

  log("lost receive");

} // receive()
