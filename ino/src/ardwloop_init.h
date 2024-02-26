#ifndef ardwloop_init
#define ardwloop_init

void inject_arduino_h();

void impl_delay(unsigned long ms);

void impl_write_low(int i);

void impl_write_high(int i);

void impl_pin_out(int pin);

void impl_serial_begin(int baud);

int impl_available();

int impl_read(char *buffer, const int n);

int impl_write(char c);

#endif