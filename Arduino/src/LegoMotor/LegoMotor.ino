#include <Servo.h>

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;
int p = 0;

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
  Motor2(125, true);
  }
  tijd = millis();
  while(millis() - tijd <= 575){
  Motor2(125, false);
  }
  Motor2(0, true);
}

void Gaan(int schap){
  for(int i=0; i<schap; i++){
    unsigned long tijd = millis();
    while (millis() - tijd <= 640){
      Motor1(100,false);
      Motor1(0,false);
    }
  }
}
void terug(int schap){
  for(int i=0; i<schap; i++){
    unsigned long tijd = millis();
    while(millis() - tijd <= 640){
      Motor1(100, true);
      Motor1(0, true);
    }
  }
}

void loop() {
  if (Serial.available() > 0){
    int t = Serial.read();
    if(t == '0'){
      terug(p);
      p = 0;
    } else if(t == '1'){
      if(p>1){
        terug(p-1);
      } else if (p<1) {
        Gaan(1);
      }
      p = 1;
    } else if (t == '2'){
      if(p>2){
        terug(p-2);
      } else if(p<2){
        if(p==1){
          Gaan(1);
        } else {
          Gaan(2);
        }
      }
      p = 2;
    } else if(t == '3'){
      if(p>3){
        terug(p-3);
      } else if (p<3){
        if (p==2){
          Gaan(1);
        } else if (p==1){
          Gaan(2);
        } else {
          Gaan(3);
        }
      }
      p = 3;
    } else if(t == '4'){
      if(p==0){
        Gaan(4);
      } else if (p==1){
        Gaan(3);
      } else if (p==2){
        Gaan(2);
      } else if (p==3){
        Gaan(1);
      }
      p = 4;
    } else if (t == '5'){
      Motor1(200,false);
      delay(100);
      Motor1(0,false);
    } else if (t=='6'){
      Motor1(200,true);
      delay(100);
      Motor1(0,true);
    } else if (t=='p'){
      duw();
    }
  }
}
