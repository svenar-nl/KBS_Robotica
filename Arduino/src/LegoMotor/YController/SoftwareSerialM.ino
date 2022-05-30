#include <SoftwareSerial.h>

SoftwareSerial mySerial(8,9);

void setup() {
  Serial.begin(57600);
  while (!Serial) {
    ;
  }
  
  mySerial.begin(57600);
  mySerial.println("Hello there! Pls respond");
}

void loop() {
  // put your main code here, to run repeatedly:
  if (mySerial.available()) {
    Serial.write(mySerial.read());
  }
  if (Serial.available()) {
    mySerial.write(Serial.read());
  }
}
