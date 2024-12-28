/*
 * Adrwloop.h - Entry point of the ardwloop API for the Arduino side
 * See https://llschall.github.io/ardwloop
 *
 * Version 0.2.9
 */

#ifndef ardwloop_h
#define ardwloop_h

#include "ardwloop_core.h"

const long BAUD_300 = 300;
const long BAUD_1200 = 1200;
const long BAUD_4800 = 4800;
const long BAUD_9600 = 9600;
const long BAUD_19200 = 19200;
const long BAUD_38400 = 38400;
const long BAUD_57600 = 57600;
const long BAUD_115200 = 115200;

void ardw_setup(long);

void ardw_setup_inject(long baud, void (*prm_serial_begin)(int), int (*prm_available)(),
                       int (*prm_read)(char *, int), int (*prm_write)(char));

char ardw_prg();

void ardw_loop();
void ardw_post(bool (*p)());

V *ardw_s();
V *ardw_p();
V *ardw_r();

void ardw_log(void (*)(const char *));

#endif