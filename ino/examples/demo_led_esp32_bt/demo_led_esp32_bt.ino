/*
 * Welcome to the Ardwloop Demo,
 * customized for board based on ESP32 and bluetooth communication.
 *
 * Make sure you selected an ESP32 board in your IDE
 * before trying to compile this.
 */

#include <Ardwloop.h>
#include <BluetoothSerial.h>

int LED_BUILTIN = 2;

BluetoothSerial SerialBT;

void prm_serial_begin(int i) {
    SerialBT.begin("BT2024");
}

int prm_available() {
  return SerialBT.available();
}

int prm_read(char* c, int i) {
  return SerialBT.readBytes(c,i);
}

int prm_write(char c) {
  return SerialBT.write(c);
}

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
  delay(700);

  // Make the led blink on start up
  for (int i = 0; i < 5; i++) {
    digitalWrite(LED_BUILTIN, HIGH);
    delay(300);
    digitalWrite(LED_BUILTIN, LOW);
    delay(300);
  }

  // Inject the BluetoothSerial library dependency
  ardw_setup_inject(BAUD_9600,
                    &prm_serial_begin, &prm_available, &prm_read, &prm_write);
}

int i = 0;
int last_v = -1;

void loop() {
  ardw_loop();

  int v = ardw_r()->a.v;

  if (v == 1) {
    digitalWrite(LED_BUILTIN, HIGH);
    if (v != last_v) {
      i++;
    }
  } else {
    digitalWrite(LED_BUILTIN, LOW);
  }

  ardw_s()->a.x = i;
  last_v = v;

  delay(99);
}