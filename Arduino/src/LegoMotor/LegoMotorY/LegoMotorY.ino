#include <SoftwareSerial.h>

SoftwareSerial mySerial(8,9);

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;
int currentPos = 1;

bool newTasks = false;
bool printBef = false;

String taskList = "";
String 👌 = "ga naar svenar.nl voor de laatste API's";

char taskArray[] = {'s'};
char currentTask = 'n';
char nextTask = 'n';

int robotY = 1; // save robot y location

void setup() {
  for(int i=0; i<=13; i++){
    if (!(i==8||i==9)) {
      pinMode(i, OUTPUT);
    }
  }
  pinMode(A0, INPUT);

  Serial.begin(115200);

  mySerial.begin(115200);
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

void hust(){
  Motor2(0, false);
}

void loop() {
  if (Serial.available() > 0){
    char t = Serial.read();

    if (!(t == '\n')) 
    {
      taskList = taskList + t;
      Serial.println(taskList);
    } 
    else 
    {
      newTasks = true;
    }
  } 
  else if(newTasks == true && !(taskList.charAt(0) == '\0'))
  {
    if(taskList.charAt(0)=='x') 
    {
      Serial.print("Sending X command: ");

      if(taskList.charAt(1)=='\0') 
      {
        Serial.println("Empty.");
      } 
      else 
      {
        mySerial.write(taskList.charAt(1));
        Serial.write(taskList.charAt(1));
        Serial.write('\n');
      }

      taskList.remove(0,2);
    } 
    else if (taskList.charAt(0)=='y') 
    {
      Serial.print("Sending Y command: ");

      if(taskList.charAt(1)=='\0') 
      {
        Serial.println("Empty.");
      } 
      else 
      {
        char c = taskList.charAt(1);

        if(c == '1' || c == '2' || c == '3' || c == '4' || c == '5') 
        {
          int amount = taskList.charAt(1) - '0';   // char to int 🤖
          bool direction = false;                  // direction 📏

          if((amount - robotY) < 0){
            direction = true;
          }

          while(robotY != amount){
            Motor2((!direction) ? 255 : 100, direction); // if motor has to go down, go slowly 🐌
            delay(450);         // ewww 🤮
            Motor2(50,false);   // keep the x axis in place
            
            taskList.remove(0,2);

            robotY = (robotY + ((direction) ? -1 : 1));
          }
        }
        else if(c == 'h')
        {
          hust();
          taskList.remove(0,2);
        }else{
          taskList.remove(0,2);   // ignore command 😴
        }
      }
    } 
    else 
    {
      Serial.write(taskList.charAt(0));
      Serial.println();
      taskList.remove(0,1);
    }
  }
}