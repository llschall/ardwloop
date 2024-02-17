#include <stdio.h>
#include "ardwloop.h"

int entry_check(int i) {
  printf("\n# ardwloop_jni.h entry_check# %d\n", i);
  return i * 2;
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
