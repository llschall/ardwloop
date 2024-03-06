#ifndef ardwloop_core_h
#define ardwloop_core_h

//////////////////////////////////
// MODEL
//////////

struct D {
  int v, w, x, y, z;
};
struct V {
  D a, b, c, d, e, f, g, h, i;
};

void reboot();

void wr(char);
char rd();

void fct_inject(void (*prm_log)(const char *), void (*prm_delay)(unsigned long),
                void (*prm_write_low)(int), void (*prm_write_high)(int),
                void (*prm_pin_out)(int), void (*prm_serial_begin)(int),
                int (*prm_available)(), int (*prm_read)(char *, int),
                int (*prm_write)(char));


void func_delay(unsigned long ms);
int func_read(char *arr, int n);
int func_available();

void core_setup();
void core_loop();

void core_post(bool (*p)());

char core_prg();
int core_rc();
int core_sc();
int core_delay_read();
int core_delay_post();
V *core_s();
V *core_p();
V *core_r();

void initJ();
void reset();
void send_s();
void send_p();

void log(const char *, int, int);
void log(const char *, int, char);
void log(const char *, char, int);
void log(const char *, char, char);
void log(const char *);

#endif