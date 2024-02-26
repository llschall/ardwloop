/*
 * Adrwloop.h - Entry point of the ardwloop API for the Arduino side
 * See https://github.com/llschall/ardwloop
 *
 * Version 0.0.8
 */

#ifndef ardwloop_h
#define ardwloop_h

#include "ardwloop_core.h"

void ardw_start(int read, int post);

char ardw_prg();

void ardw_loop();
void ardw_post(bool (*p)());

V *ardw_s();
V *ardw_p();
V *ardw_r();

#endif