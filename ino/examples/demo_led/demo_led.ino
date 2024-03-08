/*
 * Welcome to the Ardwloop Demo !
 *
 * Featuring Ardwloop 0.1.3
 *
 * More setup instructions are available in
 * https://github.com/llschall/ardwloop-demo
 *
 */


#include <Ardwloop.h>

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);

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
