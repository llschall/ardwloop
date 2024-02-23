/*
Welcome to the ardwloop demo !

More setup instructions are available in
https://github.com/llschall/ardwloop-demo
*/

#include <ardwloop.h>

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
