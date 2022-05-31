#include <SoftwareSerial.h>

SoftwareSerial mySerial(8,9);

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;
int currentPos = 1;
bool newTasks = false;
bool printBef = false;
String taskList = "1123";
char taskArray[] = {'s'};
char currentTask = 'n';
char nextTask = 'n';


void setup() {
  for(int i=0; i<=13; i++){
    if (!(i==8||i==9)) {
      pinMode(i, OUTPUT);
    }
  }
  pinMode(A0, INPUT);
  Serial.begin(115200);
  mySerial.begin(38400);
  Serial.println();
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
    if (!(t == '\n')) {
    taskList = taskList + t;
    Serial.println(taskList);
  } else {
    newTasks = true;
  }
  } else if(newTasks == true && !(taskList.charAt(0) == '\0')){
    if(taskList.charAt(0)=='x') {
      Serial.print("Sending X command: ");
      if(taskList.charAt(1)=='\0') {
        Serial.println("Empty.");
      } else {
        mySerial.write(taskList.charAt(1));
        Serial.write(taskList.charAt(1));
        Serial.write('\n');
      }
      taskList.remove(0,2);
    } else if (taskList.charAt(0)=='y') {
      Serial.print("Sending Y command: ");
      if(taskList.charAt(1)=='\0') {
        Serial.println("Empty.");
      } else {
        switch(taskList.charAt(1)) {
          case '1':
            Serial.println("Y going up 1");
            Motor2(255,false);
            delay(450);
            Motor2(50,false);
            break;

          case '0':
            Serial.println("Y going down 1");
            Motor2(60,true);
            delay(370);
            Motor2(50,false);
        }
      }
     taskList.remove(0,2);
    } else {
      Serial.write(taskList.charAt(0));
      Serial.println();
      taskList.remove(0,1);
    }
}}
