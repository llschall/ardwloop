#include <stdio.h>
#include "ardwloop.h"
#include "ardwloop_init.h"
#include "ardwloop_core.h"
#include "ardwloop_buffer.h"
#include "ardwloop_utils.h"

/////////////////////////////////

void ardw_start(int reboot, int read, int post, int j, int before_k) {
  inject_arduino_h();
  ardw_begin(reboot,read,post,j,before_k);
}

/////////////////////////////////

V* ardw_s()
{
  return core_s();
}

V* ardw_p()
{
  return core_p();
}

V* ardw_r()
{
  return core_r();
}

void ardw_post(bool (*p)())
{
  core_post(p);
}

//////////////////////////////////
/// MISC


void ardw_setup()
{
  core_setup();
}

void ardw_loop()
{
  core_loop();
}
