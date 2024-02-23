/*
  adrwloop.h - Entry point of the ardwloop API for the Arduino side
  See https://github.com/llschall/ardwloop

  Version 0.0.7
*/

#ifndef ardwloop_h
#define ardwloop_h

#include "ardwloop_core.h"

void ardw_start(int reboot, int read, int post, int j, int before_k);

char ardw_prg();

void ardw_setup();
void ardw_loop();
void ardw_post(bool (*p)());

V *ardw_s();
V *ardw_p();
V *ardw_r();


#endif