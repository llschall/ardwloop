#include "Ardwloop.h"
#include "ardwloop_buffer.h"
#include "ardwloop_core.h"
#include "ardwloop_init.h"
#include "ardwloop_utils.h"
#include <stdio.h>

/////////////////////////////////

void ardw_setup(long baud) {
  inject_arduino_h();
  inject_serial();
  core_setup(baud);
}

void ardw_setup_inject(long baud, void (*prm_serial_begin)(int), int (*prm_available)(),
                       int (*prm_read)(char *, int), int (*prm_write)(char)) {
  inject_arduino_h();
  fct_inject_serial(prm_serial_begin, prm_available, prm_read, prm_write);
  core_setup(baud);
}

void ardw_loop() { core_loop(); }

/////////////////////////////////

V *ardw_s() { return core_s(); }
void ardw_str(char* str) { core_str(str); }
V *ardw_r() { return core_r(); }
V *ardw_p() { return core_p(); }

void ardw_post(bool (*p)()) { core_post(p); }
char ardw_prg() { return core_prg(); }

void ardw_log(void (*p)(const char *)) {
  inject_log(p);
}