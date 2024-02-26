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
    void (*prm_write_high)(int),
    void (*prm_pin_out)(int),
    void (*prm_serial_begin)(int),
    int (*prm_available)(),
    int (*prm_read)(char*, int),
    int (*prm_write)(char));

void core_begin(int read, int post);
int core_delay_post();

void core_setup();
void core_loop();

void core_post(bool (*p)());
char core_prg();

V *core_s();
V *core_p();
V *core_r();

void initJ();
void reset();
void send_s();
void send_p();
void receive_r();