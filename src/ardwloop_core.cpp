
#include "ardwloop_core.h"
#include "ardwloop_buffer.h"
#include "ardwloop_read.h"
#include "ardwloop_utils.h"

#include <stdio.h>

int Rc = -1;
int Sc = -1;

struct V R, S, P;

const char END = '/';

const char H[] = {'v', 'w', 'x', 'y', 'z', END};
const char K[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', END};
const int Kc = 9;

struct D *Rv[] = {&R.a, &R.b, &R.c, &R.d, &R.e, &R.f, &R.g, &R.h, &R.i};
struct D *Sv[] = {&S.a, &S.b, &S.c, &S.d, &S.e, &S.f, &S.g, &S.h, &S.i};
struct D *Pv[] = {&P.a};

int S_I = -1;
int P_I = -1;

char PRG = 'A';

int RESET_PIN = -1;

int DELAY_REBOOT = -1;
int DELAY_POST = -1;
int DELAY_J = -1;
int DELAY_BEFORE_K = -1;

void (*fct_log)(const char *);
void (*fct_delay)(unsigned long);
void (*fct_write_low)(int);
void (*fct_write_high)(int);
void (*fct_pin_out)(int);
void (*fct_serial_begin)(int);
int (*fct_available)();
int (*fct_read)(char *, int);
int (*fct_write)(char);

bool (*POST_IMPL)();

void fct_inject(void (*prm_log)(const char *), void (*prm_delay)(unsigned long),
                void (*prm_write_low)(int), void (*prm_write_high)(int),
                void (*prm_pin_out)(int), void (*prm_serial_begin)(int),
                int (*prm_available)(), int (*prm_read)(char *, int),
                int (*prm_write)(char), bool (*prm_post)()
                ) {
  fct_log = prm_log;
  fct_delay = prm_delay;
  fct_write_low = prm_write_low;
  fct_write_high = prm_write_high;
  fct_pin_out = prm_pin_out, fct_serial_begin = prm_serial_begin,
  fct_available = prm_available;
  fct_read = prm_read;
  fct_write = prm_write;
  POST_IMPL = prm_post;
}

void func_delay(unsigned long ms) {(*fct_delay)(ms);}
int func_available() { return (*fct_available)(); }
int func_read(char *arr, int n) { return (*fct_read)(arr, n); }

char core_prg() { return PRG; }
int core_reset_pin() {return RESET_PIN; }
int core_rc() { return Rc; }
int core_sc() { return Sc; }
int core_delay_read() { return get_delay_read(); }
int core_delay_post() { return DELAY_POST; }

void reboot() {
  func_delay(DELAY_REBOOT);
  (*fct_write_low)(2);
}

void wr(char c) { (*fct_write)(c); }

void reset() {

  log("### Reset ###");
  func_delay(DELAY_BEFORE_K);
  // Wait for 'K'

  for (char c = rd(); c != 'K'; c = rd()) {
    printf("RD %c\n", c);
  }

  while (func_available()) {
    impl_read(1);
  }

  reset_bfn();

  log("# Got K #");

  // Send 'K'
  wr('K');

  if ('C' != rd()) {
    log("# Program error #");
  }
  PRG = rd();

  if ('C' != rd()) {
    log("# Program error #");
  }
  Rc = map_c(rd());
  if ('C' != rd()) {
    log("# Program error #");
  }

  Sc = map_c(rd());
  if ('C' != rd()) {
    log("# Program error #");
  }

  char c = rd();
  int s = -1;

  while (c != 'C') {
    int i = map_c(c);
    if (s == -1)
      s = 0;
    s = 10 * s + i;
    c = rd();
  }

  c = rd();
  int p = -1;

  while (c != 'C') {
    int i = map_c(c);
    if (p == -1)
      p = 0;
    p = 10 * p + i;
    c = rd();
  }

  set_delay_read(s);
  DELAY_POST = p;

  char m[16];
  snprintf(m, 16, "%c %d %d", PRG, s, DELAY_POST);
  log(m);
  func_delay(999);
} //()

void wr_int(int v) {

  bool pos = v >= 0;
  int t = pos ? v : -v;
  int p = 1;

  while (p <= t)
    p *= 10;

  while (p >= 10) {
    p /= 10;
    int d = t / p;
    wr(map_i(d));
    t -= d * p;
  }

  wr(pos ? '+' : '-');
}

void wr_i(int i) {

  if (i >= 100) {
    int d = (i / 100);
    wr(map_i(d));
    d = d * 100;
    i = (i - d);
  } else {
    wr(map_i(0));
  }
  if (i >= 10) {
    int d = (i / 10);
    wr(map_i(d));
    d = d * 10;
    i = (i - d);
  } else {
    wr(map_i(0));
  }
  wr(map_i(i));
}

void init_j() {

  log("### Init J ###");
  while (func_available() > 0) {
    impl_read(1);
  }

  while (true) {
    wr('J');
    func_delay(DELAY_J);
    int n = func_available();
    if (n > 0)
      impl_read(1);
    char c = buffer(0);
    if (c == 'J') {
      log("### Got J ###");
      return;
    }
  }
}

//////////////////////////////////
// SERIAL

void send_s() {
  wr('S');

  int i = S_I;
  S_I++;
  if (S_I == 1000)
    S_I = 0;
  wr_i(i);

  for (int i = 0; i < Sc; i++) {
    
    int h_i = 0;
    char h = H[0];

    while(h!=END) {
      
      struct D *d = Sv[i];

      int v;

      switch (h_i) {
      case 0:
        v = d->v;
        break;
      case 1:
        v = d->w;
        break;
      case 2:
        v = d->x;
        break;
      case 3:
        v = d->y;
        break;
      case 4:
        v = d->z;
        break;
      }

      if(v!=0) {
        wr(K[i]);
        wr(h);
        wr_int(v);
      }

      h_i++;
      h=H[h_i];
    }
  }
  wr('T');
}

void send_p() {
  wr('P');

  int i = P_I;
  P_I++;
  if (P_I == 1000) {
    P_I = 0;
  }
  wr_i(i);
  
  int h_i = 0;
  char h = H[0];

  while(h!=END) {

    struct D *d = Pv[0];
    int v;

    switch (h_i) {
    case 0:
      v = d->v;
      break;
    case 1:
      v = d->w;
      break;
    case 2:
      v = d->x;
      break;
    case 3:
      v = d->y;
      break;
    case 4:
      v = d->z;
      break;
    }

    if(v!=0) {
      wr(K[0]);
      wr(h);
      wr_int(v);
    }

    h_i++;
    h=H[h_i];
  }
  wr('T');
}

V *core_s() { return &S; }
V *core_r() { return &R; }
V *core_p() { return &P; }

void core_setup(long baud) {
  DELAY_REBOOT = 999;
  DELAY_J = 99;
  DELAY_BEFORE_K = 2000;

  log("# core_setup--");

  S_I = 0;
  P_I = 0;

  (*fct_pin_out)(2);
  (*fct_write_high)(2);
  (*fct_serial_begin)(baud);

  init_j();
  reset();
  send_s();
  receive_r(END, Rc, H, Kc, K, Rv);
  log("# --core_setup");
}

void core_loop() {
  send_s();

  int i = 0;
  bool post = true;
  while (func_available() == 0) {
    if (post) {
      if (i == core_delay_post()) {
        post = (*POST_IMPL)();
        send_p();
        i = 0;
      } else {
        i++;
        func_delay(1);
      }
    } else {
      func_delay(get_delay_read());
    }
  }
  receive_r(END, Rc, H, Kc, K, Rv);
}

void core_post(bool (*p)()) { POST_IMPL = p; }

void log(const char *msg, int i0, int i1) {
  char m[16];
  snprintf(m, 16, msg, i0, i1);
  log(m);
}
void log(const char *msg, int i, char c) {
  char m[16];
  snprintf(m, 16, msg, i, c);
  log(m);
}
void log(const char *msg, char c, int i) {
  char m[16];
  snprintf(m, 16, msg, c, i);
  log(m);
}
void log(const char *msg, char c0, char c1) {
  char m[16];
  snprintf(m, 16, msg, c0, c1);
  log(m);
}

void log(const char *msg) { (*fct_log)(msg); }