#include "SerialManager.h"

SerialManager::SerialManager() {

}

void SerialManager::begin(long baudrate) {
    Serial.begin(baudrate);
}

void SerialManager::update() {
    int pos = readline(Serial.read(), this->buffer, this->bufferSize);
    this->available = pos > 0;
    this->data = String(this->buffer).substring(0, pos);
}

bool SerialManager::isAvailable() {
    return this->available;
}

String SerialManager::getData() {
    return this->data;
}

void SerialManager::write(String data) {
    Serial.println(data);
}

int SerialManager::readline(int readch, char *buffer, int len) {
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