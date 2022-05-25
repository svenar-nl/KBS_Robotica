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
  analogWrite(M1S, pwm);
  if(links){
    digitalWrite(M1R, HIGH);
  } else {
    digitalWrite(M1R, LOW);
  }
}

void duw(){
  Serial.println("duw");
  unsigned long tijd = millis();
  while (millis() - tijd <= 450){
  Motor2(125, false);
  }
  tijd = millis();
  while(millis() - tijd <= 575){
  Motor2(125, true);
  }
  Motor2(0, true);
}

void Gaan(int schap){
  for(int i=0; i<schap; i++){
    unsigned long tijd = millis();
    while (millis() - tijd <= 150){
      Motor1(50,false);
      Motor1(0,false);
    }
  }
  duw();
  for(int i=0; i<schap; i++){
  unsigned long tijd = millis();
    while (millis() - tijd <= 150){
      Motor1(50,true);
      Motor1(0,true);
    }
   }
}

void loop() {
  if (Serial.available() > 0){
    int t = Serial.read();
    if(t == '0'){
      Gaan(0);
    } else if(t == '1'){
      Gaan(1);
    } else if (t == '2'){
      Gaan(2);
    } else if(t == '3'){
      Gaan(3);
    } else if(t == '4'){
      Gaan(4);
    } else if (t == '5'){
      Motor1(255,false);
      delay(100);
      Motor1(0,false);
    } else if (t=='6'){
      Motor1(255,true);
      delay(100);
      Motor1(0,true);
    }
  }
}
