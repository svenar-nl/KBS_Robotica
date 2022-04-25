#include <Arduino.h>
#include "SerialManager.h"

SerialManager serialManager = SerialManager();

bool handleSerialInNumber(int number);
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
    int num = serialManager.getData().toInt();
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

bool handleSerialInNumber(int number) {

  return false;
}

bool handleSerialInString(String text) {
  text.toLowerCase();
  
  if (text == "ping") {
    return true;
  }

  return false;
}