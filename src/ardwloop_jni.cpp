#include "ardwloop_core.h"
#include <stdio.h>

int entry_check(int i) {
  printf("\n# ardwloop_jni.h entry_check# %d\n", i);
  return i * 2;
}

void entry_begin(int reboot, int read, int post, int beforeK, int j) {
  core_begin(read, post);
}

void entry_setup() {
  printf("\n# ardwloop_jni.h entry_setup#\n");
  core_setup();
}

void entry_loop() {
  printf("\n# ardwloop_jni.h entry_loop#\n");
  core_loop();
}

void entry_reset() {
  log("# ardwloop_jni.h entry_reset#");
  reset();
}

char entry_prg() { return core_prg(); }
int entry_rc() { return core_rc(); }
int entry_sc() { return core_sc(); }

V *entry_s() { return core_s(); }
V *entry_r() { return core_r(); }
