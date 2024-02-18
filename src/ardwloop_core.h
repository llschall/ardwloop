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

void ardw_begin(int reboot, int read, int post, int j, int before_k);

void ardw_setup();

void ardw_loop();

V *ardw_s();

V *ardw_r();

char ardw_prg();