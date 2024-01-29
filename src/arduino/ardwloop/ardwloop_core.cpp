#include <Arduino.h>
#include "ardwloop_core.h"
#include "ardwloop_buffer.h"

struct V R, S, P;

char H[] = {'v', 'w', 'x', 'y', 'z'};
char K[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};

struct D *Rv[] = {&R.a, &R.b, &R.c, &R.d, &R.e, &R.f, &R.g, &R.h, &R.i};
struct D *Sv[] = {&S.a, &S.b, &S.c, &S.d, &S.e, &S.f, &S.g, &S.h, &S.i};
struct D *Pv[] = {&P.a};

int S_I = -1;
int P_I = -1;

char PRG = 'A';

int DELAY_REBOOT = -1;
int DELAY_READ = -1;
int DELAY_POST = -1;
int DELAY_J = -1;
int DELAY_BEFORE_K = -1;

int bfI = 0;
int bfN = 0;

char ardw_prg()
{
  return PRG;
}

void reboot()
{
  delay(DELAY_REBOOT);
  digitalWrite(2, LOW);
}

char rd()
{

  if (bfI < bfN)
  {
    char c = (char)Bf[bfI];
    if (c == 'Z')
      reboot();
    if (c == 'N')
    {
      wr('N');
    }
    else
    {
      bfI++;
      return c;
    }
  }

  int i = Serial.available();
  while (i == 0)
  {
    delay(DELAY_READ);
    i = Serial.available();
  }

  if (i > bfS)
    i = bfS;

  bfN = Serial.readBytes(Bf, i);
  bfI = 0;

  return rd();
}

void wr(char c)
{
  Serial.write(c);
}
