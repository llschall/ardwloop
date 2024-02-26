#include "Ardwloop.h"
#include "ardwloop_buffer.h"
#include "ardwloop_core.h"
#include "ardwloop_init.h"
#include "ardwloop_utils.h"
#include <stdio.h>

/////////////////////////////////

void ardw_start(int read, int post) {
  inject_arduino_h();
  core_begin(read, post);
  core_setup();
}

/////////////////////////////////

V *ardw_s() { return core_s(); }
V *ardw_p() { return core_p(); }
V *ardw_r() { return core_r(); }

void ardw_post(bool (*p)()) { core_post(p); }
void ardw_loop() { core_loop(); }
char ardw_prg() { return core_prg(); }