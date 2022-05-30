#include <ArduinoRobotMotorBoard.h>
#include <EasyTransfer2.h>
#include <LineFollow.h>
#include <Multiplexer.h>

#include <Servo.h>
#include <SoftwareSerial.h>

SoftwareSerial mySerial(8,9);

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;

Servo s1;
int s1Pos = 0;

void setup() {
  for(int i=0; i<=13; i++){
    if (!(i==8||i==9)) {
      pinMode(i, OUTPUT);
    }
  }
  pinMode(A0, INPUT);
  mySerial.begin(57600);
  s1.attach(13);
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

void duw(){
  mySerial.println("duw");
  unsigned long tijd = millis();
  while (millis() - tijd <= 800){
  Motor2(255, false);
  }
  tijd = millis();
  while(millis() - tijd <= 925){
  Motor2(255, true);
  }
  Motor2(0, true);
}

void calib() {
  unsigned long tijd = millis();
  while (millis() - tijd <= 3000) {
    Motor2(60,true);
  }
  Motor2(0,false);
}

void loop() {
  if (mySerial.available() > 0){
    char t = mySerial.read();
    mySerial.write(t);
    if(t == '0'){
      Motor2(255,true);
      delay(250);
      Motor2(50,false);  
    } else if(t == '1'){
      Motor2(255, false);
      delay(250);
      Motor2(50, false);
    } else if (t == 'p'){
      duw();
    } else if (t == '3') {
      calib();
    } else if (t == 'd') {
      delay(250);
    }
  }
}
