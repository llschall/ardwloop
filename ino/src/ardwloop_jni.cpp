#include "ardwloop_core.h"

int entry_check(int i) {
  log("\n# ardwloop_jni.h entry_check#");
  return i * 2;
}

void entry_init() {
  core_init(0);
}

void entry_setup(long baud) {
  log("\n# ardwloop_jni.h entry_setup#\n");
  core_setup(baud);
}

void entry_loop() {
  log("\n# ardwloop_jni.h entry_loop#\n");
  core_loop();
}

void entry_reset() {
  log("# ardwloop_jni.h entry_reset#");
  reset();
}

char entry_prg() { return core_prg(); }
int entry_reset_pin() { return core_reset_pin(); }
int entry_rc() { return core_rc(); }
int entry_sc() { return core_sc(); }
int entry_delay_read() { return core_delay_read(); }
int entry_delay_post() { return core_delay_post(); }

V *entry_s() { return core_s(); }
void entry_str(char* str, int size) { core_str(str, size); }
int entry_arr(int i) { return core_arr(i); }
V *entry_r() { return core_r(); }
