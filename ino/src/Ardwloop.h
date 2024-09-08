/*
 * Adrwloop.h - Entry point of the ardwloop API for the Arduino side
 * See https://llschall.github.io/ardwloop
 *
 * Version 0.1.7
 */

#ifndef ardwloop_h
#define ardwloop_h

#include "ardwloop_core.h"

void ardw_setup();

char ardw_prg();

void ardw_loop();
void ardw_post(bool (*p)());

V *ardw_s();
V *ardw_p();
V *ardw_r();

void ardw_log(void (*)(const char *));

#endif