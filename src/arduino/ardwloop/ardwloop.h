#ifndef ardwloop_h
#define ardwloop_h

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
void receive_r();
void send_s();
void send_p();
void initJ();
void reset();
void wr_i(int);
void wr_int(int);
void wr(char);
char rd();

void setup_serial();
void loop_serial();

void ardw_begin(int reboot, int read, int post, int j, int before_k);

char ardw_prg();

V* ardw_s();

V* ardw_p();

V* ardw_r();

void ardw_post(bool (*p)());

#endif