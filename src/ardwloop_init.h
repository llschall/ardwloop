#ifndef ardwloop_init
#define ardwloop_init

void inject_arduino_h();

void impl_delay(unsigned long ms);

void impl_write_low(int);

int impl_available();

int impl_read(int);

int impl_write(char);

#endif