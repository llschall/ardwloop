#include <Arduino.h>

#include "ardwloop_init.h"
#include "ardwloop_core.h"
#include "ardwloop_buffer.h"

void inject_arduino_h() {
    fct_init(
        &impl_delay,
        &impl_write_low,
        &impl_available,
        &impl_read,
        &impl_write);
}

void impl_delay(unsigned long ms) {
    delay(ms);
}

void impl_write_low(int i) {
    digitalWrite(i, LOW);
}

int impl_available() {
    return Serial.available();
}

int impl_read(int n) {
    char arr[n];
    int r =  Serial.readBytes(arr, n);
    for(int i = 0; i < r; i++) {
        buffer_set(i, arr[i]);
    }
    return r;
}

int impl_write(char c) {
    return Serial.write(c);
}
