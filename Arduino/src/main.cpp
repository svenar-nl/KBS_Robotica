#include <Arduino.h>
#include "SerialManager.h"

SerialManager serialManager = SerialManager();

bool handleSerialInNumber(long number);
bool handleSerialInString(String text);

String lastTextCommand = "";
int lastNumberCommand = -1;

void setup() {
  serialManager.begin(115200);
  pinMode(LED_BUILTIN, OUTPUT);
}

void loop() {
  serialManager.update();

  if (serialManager.isAvailable()) {
    long num = serialManager.getData().toInt();
    String text = serialManager.getData();

    if (isDigit(text.charAt(0))) {
      Serial.println(handleSerialInNumber(num) ? "ACK" : "NACK");
      lastNumberCommand = num;
    } else {
      Serial.println(handleSerialInString(text) ? "ACK" : "NACK");
      lastTextCommand = text;
    }
  }
}

bool handleSerialInNumber(long number) {

  if (lastTextCommand == "flash") {
    Serial.println(String(number));
    digitalWrite(LED_BUILTIN, HIGH);
    delay(number);
    digitalWrite(LED_BUILTIN, LOW);
    return true;
  }

  return false;
}

bool handleSerialInString(String text) {
  text.toLowerCase();
  
  if (text == "ping") {
    return true;
  }

  if (text == "flash") {
    Serial.println("wait@number");
    return true;
  }

  if (text == "owo") {
    Serial.println("uwu");
    return false;
  }

  return false;
}