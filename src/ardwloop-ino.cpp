#include <Arduino.h>
#include <stdio.h>
#include "ardwloop-ino.h"
#include "ardwloop-core.h"
#include "ardwloop-core.cpp"
#include "ardwloop-buffer.h"
#include "ardwloop-utils.h"

//////////////////////////////////

bool ignore()
{
  return false;
}

bool (*POST_IMPL)() = &ignore;

V *ardw_s()
{
  return &S;
}

V *ardw_p()
{
  return &P;
}

V *ardw_r()
{
  return &R;
}

void ardw_post(bool (*p)())
{
  POST_IMPL = p;
}

void ardw_begin(int reboot, int read, int post, int j, int before_k)
{
  DELAY_REBOOT = reboot;
  DELAY_READ = read;
  DELAY_POST = post;
  DELAY_J = j;
  DELAY_BEFORE_K = before_k;
}

//////////////////////////////////
/// MISC


void ardw_setup()
{

  S_I = 0;
  P_I = 0;

  pinMode(2, OUTPUT);
  digitalWrite(2, HIGH);

  Serial.begin(BAUD);
  Serial.setTimeout(20000);

  initJ();
  reset();
}

void ardw_loop()
{
  send_s();

  int i = 0;
  int p = 0;
  bool post = true;
  while (Serial.available() == 0)
  {
    if (post)
    {
      if (i == DELAY_POST)
      {
        post = (*POST_IMPL)();
        send_p();
        i = 0;
      }
      else
      {
        i++;
        delay(1);
      }
    }
    else
    {
      p++;
      if (p > 999)
      {
        send_p();
        p = 0;
      }
      delay(9);
    }
  }
  receive_r();
}
