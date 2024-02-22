#include <ardwloop.h>

void setup() {

  pinMode(LED_BUILTIN, OUTPUT);

  ardw_start(999, 9, 9999, 99, 9);
  ardw_setup();
}

void loop() {
  ardw_loop();

  int v = ardw_r()->a.v;

  if (v == 1) {
    digitalWrite(LED_BUILTIN, HIGH);
  } else {
    digitalWrite(LED_BUILTIN, LOW);
  }

  delay(99);
}
