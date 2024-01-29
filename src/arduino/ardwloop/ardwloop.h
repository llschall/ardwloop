#ifndef ardwloop_h
#define ardwloop_h

#include "ardwloop_core.h"

void ardw_begin(int reboot, int read, int post, int j, int before_k);
char ardw_prg();

void ardw_setup();
void ardw_loop();
void ardw_post(bool (*p)());

V *ardw_s();
V *ardw_p();
V *ardw_r();

void reboot();
void receive_r();
void send_s();
void send_p();
void initJ();
void reset();
void wr_i(int);
void wr_int(int);

#endif