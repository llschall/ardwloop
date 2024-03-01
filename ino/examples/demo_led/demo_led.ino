/*
Welcome to the Ardwloop demo !
Version 0.1.1

More setup instructions are available in
https://github.com/llschall/ardwloop-demo
*/

#include <Ardwloop.h>

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);

  ardw_start(9, 9999);
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
