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

void fct_init(
      void (*prm_delay)(unsigned long),
      void (*prm_write_low)(int),
      int (*prm_available)(),
      int (*prm_read)(int),
      int (*prm_write)(char));

void ardw_begin(int reboot, int read, int post, int j, int before_k);

void ardw_setup();

void ardw_loop();

V *ardw_s();

V *ardw_r();

char ardw_prg();

V *core_s();
V *core_p();
V *core_r();

void initJ();
void reset();
void send_s();
void send_p();
void receive_r();
int core_delay_post();
void core_zero();