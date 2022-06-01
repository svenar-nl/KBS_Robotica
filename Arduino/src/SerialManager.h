#pragma once

#include <Arduino.h>

class SerialManager {
    public:
        SerialManager();
        void begin(long baudrate);
        void update();
        bool isAvailable();
        String getData();
        void write(String data);
        
    private:
        int readline(int readch, char *buffer, int len);

        bool available;
        int bufferSize = 256;
        char buffer[256];
        String data;
};