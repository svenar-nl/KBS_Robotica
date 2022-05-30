#include <SoftwareSerial.h>

SoftwareSerial mySerial(8,9);

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;
bool XControl = false;

void setup() {
  for(int i=0; i<=13; i++){
    if (!(i==8||i==9)) {
      pinMode(i, OUTPUT);
    }
  }
  pinMode(A0, INPUT);
  Serial.begin(115200);
  mySerial.begin(38400);
}

void Motor2(int pwm, boolean links){
  analogWrite(M2R, pwm);
  if(links){
    digitalWrite(M2S, HIGH);
  } else {
    digitalWrite(M2S, LOW);
  }
}



void Motor1(int pwm, boolean links){
  analogWrite(M1R, pwm);
  if(links){
    digitalWrite(M1S, HIGH);
  } else {
    digitalWrite(M1S, LOW);
  }
}

void calib() {
  unsigned long tijd = millis();
  while (millis() - tijd <= 3000) {
    Motor2(80,true);
  }
  Motor2(0,false);
}

void loop() {
  if (Serial.available() > 0){
    char t = Serial.read();
    if(t == '?') {
      Serial.println("");
    }
    if(t == '0'){
      Motor2(255,true);
      delay(250);
      Motor2(50,false);  
    } else if(t == '1'){
      Motor2(255, false);
      delay(250);
      Motor2(50, false);
    } else if (t == '3') {
      calib();
    } else if (t == 'd') {
      delay(250);
    } else if (t == 'x') {
      Serial.println();
      Serial.println("Beginning X axis control.");
      XControl = true;
      while (XControl == true) {
         if (Serial.available() > 0){
           char t = Serial.read();
           if (t == 'x') {
            XControl = false;
            Serial.println("Ending X axis control.");
           } else if (t == '1') {
            Serial.println("Sending X to vak 1.");
           }
         }
      }
      
    }
    Serial.write(t);
  }
}
