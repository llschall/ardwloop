#include <Arduino.h>

#include "ardwloop_buffer.h"
#include "ardwloop_core.h"
#include "ardwloop_init.h"

void inject_arduino_h() {
  fct_inject(&impl_log, &impl_delay, &impl_write_low, &impl_write_high,
             &impl_pin_out, &impl_serial_begin, &impl_available, &impl_read,
             &impl_write);
}

void ignore_log(const char *msg) {
  // do nothing
}

void (*LOG)(const char *msg) = &ignore_log;

void inject_log(void (*p)(const char *)) {
  LOG = p;
  impl_log("= logger ready =");
}

void impl_log(const char *msg) { (*LOG)(msg); }

void impl_delay(unsigned long ms) { delay(ms); }

void impl_write_low(int i) { digitalWrite(i, LOW); }

void impl_write_high(int i) { digitalWrite(i, HIGH); }

void impl_pin_out(int pin) { pinMode(pin, OUTPUT); }

void impl_serial_begin(int baud) {
  Serial.begin(baud);
  Serial.setTimeout(20000);
}

int impl_available() { return Serial.available(); }

int impl_read(char *buffer, const int n) { return Serial.readBytes(buffer, n); }

int impl_write(char c) { return Serial.write(c); }
