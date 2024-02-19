#include <stdio.h>
#include "ardwloop_core.h"

int entry_check(int i) {
  printf("\n# ardwloop_jni.h entry_check# %d\n", i);
  return i * 2;
}

void entry_begin(int reboot, int read, int post, int beforeK, int j) {
  ardw_begin(reboot, read, post, beforeK, j);
}

void entry_setup() {
  printf("\n# ardwloop_jni.h entry_setup#\n");
  ardw_setup();
}

void entry_loop() {
  printf("\n# ardwloop_jni.h entry_loop#\n");
  ardw_loop();
}

void entry_reset() {
  //back_print(1, "# ardwloop_jni.h entry_reset#");
  //reset();
}

char entry_prg() {
  return ardw_prg();
}

V* entry_s() {
  return ardw_s();
}

V* entry_r() {
  return ardw_r();
}
