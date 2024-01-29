#ifndef ardwloop_core_h
#define ardwloop_core_h

//////////////////////////////////
// MODEL
//////////

struct D
{
  int v, w, x, y, z;
};
struct V
{
  D a, b, c, d, e, f, g, h, i;
};

void reboot();

void wr(char);
char rd();

#endif