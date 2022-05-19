#include <Servo.h>

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;

Servo s1;
int s1Pos = 0;

void setup() {
  for(int i=0; i<=13; i++){
    pinMode(i, OUTPUT);
  }
  pinMode(A0, INPUT);
  Serial.begin(115200);
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
  Serial.println("duw");
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
  Serial.println("Calibrating...");
  s1.write(90);
  unsigned long tijd = millis();
  while (millis() - tijd <= 500){
  Motor2(120, false);
  }
  tijd = millis();
  while (millis() - tijd <= 1000){
  Motor2(60, false);
  }
  Motor2(0,true);
  s1.write(0);
  return;
}

void loop() {
  if (Serial.available() > 0){
    int t = Serial.read();
    if(t == '0'){
      Motor2(120,false);
      delay(250);
      Motor2(100,true);  
    } else if(t == '1'){
      Motor2(255, true);
      delay(250);
      Motor2(100, true);
    } else if (t == 'p'){
      duw();
    } else if (t == '3') {
      calib();
    } else if (t == 'd') {
      delay(250);
    }
  }
}
