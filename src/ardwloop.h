#ifndef ardwloop_h
#define ardwloop_h

#include "ardwloop_core.h"

void ardw_start(int reboot, int read, int post, int j, int before_k);

char ardw_prg();

void ardw_setup();
void ardw_loop();
void ardw_post(bool (*p)());

V *core_s();
V *ardw_p();
V *ardw_r();


#endif