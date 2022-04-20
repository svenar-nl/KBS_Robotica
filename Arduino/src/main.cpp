#include <Arduino.h>

char buf[80];

int readline(int readch, char *buffer, int len) {
  static int pos = 0;
  int rpos;

  if (readch > 0) {
    switch (readch) {
      case '\r': // Ignore CR
        break;
      case '\n': // Return on new-line
        rpos = pos;
        pos = 0;  // Reset position index ready for next time
        return rpos;
      default:
        if (pos < len - 1) {
          buffer[pos++] = readch;
          buffer[pos] = 0;
        }
    }
  }
  return 0;
}

void setup() {
  Serial.begin(115200);
  pinMode(LED_BUILTIN, OUTPUT);
}

void loop() {
  if (readline(Serial.read(), buf, 80) > 0) {
    switch (buf[0]) {
      case '0':
        digitalWrite(LED_BUILTIN, LOW);
        Serial.println("ACK");
        break;

      case '1':
        digitalWrite(LED_BUILTIN, HIGH);
        Serial.println("ACK");
        break;

      default:
        Serial.println("NACK");
        break;
    }
  }
}