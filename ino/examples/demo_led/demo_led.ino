/*
 * Welcome to the Ardwloop Demo !
 *
 * Featuring Ardwloop 0.2.4
 *
 * More setup instructions are available in
 * https://llschall.github.io/ardwloop/demo
 *
 */

#include <Ardwloop.h>

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);

  // Here the baud value should be set to the same value as on the Java side
  ardw_setup(BAUD_19200);
}

int i = 0;
int last_v = -1;

void loop() {
  ardw_loop();

  int v = ardw_r()->a.v;

  if (v == 1) {
    digitalWrite(LED_BUILTIN, HIGH);
    if(v != last_v) {
      i++;
    }
  } else {
    digitalWrite(LED_BUILTIN, LOW);
  }

  ardw_s()->a.x = i;
  last_v = v;

  delay(99);
}