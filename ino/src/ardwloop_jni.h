#ifndef ardwloop_jni
#define ardwloop_jni

#include "ardwloop_core.h"

int entry_check(int i);
void entry_begin(int reboot, int read, int post, int beforeK, int j);

void entry_setup(long);
void entry_loop();

void entry_reset();

char entry_prg();

V *entry_s();

V *entry_r();

char entry_prg();
int entry_rc();
int entry_sc();
int entry_delay_read();
int entry_delay_post();

#endif