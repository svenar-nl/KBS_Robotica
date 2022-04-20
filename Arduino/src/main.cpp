#include <Arduino.h>
#include "SerialManager.h"

SerialManager serialManager = SerialManager();

void setup() {
  serialManager.begin(115200);
  pinMode(LED_BUILTIN, OUTPUT);
}

void loop() {
  serialManager.update();

  if (serialManager.isAvailable()) {
    int num = serialManager.getData().toInt();
    String text = serialManager.getData();

    if (isDigit(text.charAt(0))) {
      if (num == 0) {
        digitalWrite(LED_BUILTIN, LOW);
        Serial.println("ACK");
      } else if (num == 1) {
        digitalWrite(LED_BUILTIN, HIGH);
        Serial.println("ACK");
      } else {
        Serial.println("NACK");
      }
    } else {
      Serial.println("NACK");
    }
  }
}