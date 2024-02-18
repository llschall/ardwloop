#include "ardwloop_core.h"
#include "ardwloop_buffer.h"
#include "ardwloop_utils.h"

int Rc = -1;
int Sc = -1;

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


void reset()
{

  delay(DELAY_BEFORE_K);
  // Wait for 'K'

  for (char c = rd(); c != 'K'; c = rd())
  {
    printf("RD %c\n", c);
  }

  while (Serial.available())
  {
    Serial.readBytes(Bf, 1);
  }

  bfN = 0;

  // Send 'K'
  wr('K');

  if ('C' != rd())
  {
    // msg("Program error !");
  }

  PRG = rd();
  Rc = map_c(rd());
  Sc = map_c(rd());

  printf("RESET PRG %c\n", PRG);
} //()


void wr_int(int v)
{

  bool pos = v >= 0;
  int t = pos ? v : -v;
  int p = 1;

  while (p <= t)
    p *= 10;

  while (p >= 10)
  {
    p /= 10;
    int d = t / p;
    wr(map_i(d));
    t -= d * p;
  }

  wr(pos ? '+' : '-');
}

void wr_i(int i)
{

  if (i >= 100)
  {
    int d = (i / 100);
    wr(map_i(d));
    d = d * 100;
    i = (i - d);
  }
  else
  {
    wr(map_i(0));
  }
  if (i >= 10)
  {
    int d = (i / 10);
    wr(map_i(d));
    d = d * 10;
    i = (i - d);
  }
  else
  {
    wr(map_i(0));
  }
  wr(map_i(i));
}

void initJ()
{

  while (Serial.available() > 0)
  {
    Serial.readBytes(Bf, 1);
  }

  while (true)
  {
    wr('J');
    delay(DELAY_J);
    int n = Serial.available();
    if (n > 0)
      Serial.readBytes(Bf, 1);
    char c = Bf[0];
    if (c == 'J')
    {
      return;
    }
  }
}

//////////////////////////////////
// SERIAL

void receive_r()
{

  char r = rd();

  if (r != 'R')
  {
    printf("Expected R but got %c\n", r);
  }

  for (int i = 0; i < Rc; i++)
  {

    char k = K[i];

    for (unsigned long j = 0; j < sizeof(H); j++)
    {

      char h = H[j];

      char c = rd();
      if (c == 'Y')
      {
        reset();
        return;
      } // if

      if (c != k)
      {
        reset();
        return;
      } // if

      c = rd();
      if (c == 'Y')
      {
        reset();
        return;
      } // if

      if (c != h)
      {
        reset();
        return;
      } // if

      int v = 0;

      while (true)
      {
        c = rd();
        if (c == 'Y')
        {
          reset();
          return;
        } // if

        if (c == '+')
        {
          break;
        } // if
        if (c == '-')
        {
          v *= -1;
          break;
        } // if

        int i = map_c(c);
        v = 10 * v + i;
      } // while

      struct D *d = Rv[i];

      switch (j)
      {
      case 0:
        d->v = v;
        break;
      case 1:
        d->w = v;
        break;
      case 2:
        d->x = v;
        break;
      case 3:
        d->y = v;
        break;
      case 4:
        d->z = v;
        break;
      } // switch
    }   // for j
  }     // for i
} // receive()

void send_s()
{
  wr('S');

  int i = S_I;
  S_I++;
  if (S_I == 1000)
    S_I = 0;
  wr_i(i);

  for (int i = 0; i < Sc; i++)
  {
    for (unsigned int j = 0; j < sizeof(H); j++)
    {
      wr(K[i]);
      wr(H[j]);

      struct D *d = Sv[i];

      int v;

      switch (j)
      {
      case 0:
        v = d->v;
        break;
      case 1:
        v = d->w;
        break;
      case 2:
        v = d->x;
        break;
      case 3:
        v = d->y;
        break;
      case 4:
        v = d->z;
        break;
      }
      wr_int(v);
    }
  }
}

void send_p()
{
  wr('P');

  int i = P_I;
  P_I++;
  if (P_I == 1000)
    P_I = 0;
  wr_i(i);

  for (unsigned int j = 0; j < sizeof(H); j++)
  {
    wr(K[0]);
    wr(H[j]);

    struct D *d = Pv[0];

    int v;

    switch (j)
    {
    case 0:
      v = d->v;
      break;
    case 1:
      v = d->w;
      break;
    case 2:
      v = d->x;
      break;
    case 3:
      v = d->y;
      break;
    case 4:
      v = d->z;
      break;
    }
    wr_int(v);
  }
}
