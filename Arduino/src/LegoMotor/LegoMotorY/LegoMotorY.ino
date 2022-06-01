#include <SoftwareSerial.h>

SoftwareSerial mySerial(8,9);

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;
int currentPos = 1;

int distance;
long duration;

const int trigPin = 2;
const int echoPin = 3;

bool newTasks = false;
bool printBef = false;

String taskList = "1123";

char taskArray[] = {'s'};
char currentTask = 'n';
char nextTask = 'n';


int robotY = 1; // save robot y location

void setup() {
  for(int i=0; i<=13; i++){
    if (!(i==8||i==9||i==2||i==3)) {
      pinMode(i, OUTPUT);
    }
  }
  pinMode(A0, INPUT);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);

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

int current = 0;

void loop() {
  if (getDistance() < current + 2 && getDistance() > current - 2) {
    Serial.println(getDistance());
    current = getDistance;
  }
  
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
        taskList.remove(0,1);
      } 
      else 
      {
        mySerial.write(taskList.charAt(1));
        Serial.write(taskList.charAt(1));
        Serial.write('\n');
        taskList.remove(0,2);
      }
    } 
    else if (taskList.charAt(0)=='y') 
    {
      Serial.print("Sending Y command: ");
      char c = taskList.charAt(1);
      if(taskList.charAt(1)=='\0') {
        Serial.println("Empty.");
      } else {
        Serial.write(taskList.charAt(1));
        Serial.println();
      }

      if (c == '1') {
        goToHeight(4);
      } else if (c == '2') {
        goToHeight(8);
      } else if (c == '3') {
        goToHeight(15);
      } else if (c == '4') {
        goToHeight(20);
      } else if (c == '5') {
        goToHeight(25);
      }
      
      taskList.remove(0,2);

    } else if (taskList.charAt(0)=='d') {
      delay(250);
      taskList.remove(0,1);
    }
    else 
    {
      Serial.write(taskList.charAt(0));
      Serial.println();
      taskList.remove(0,1);
    }
  }
}

//Floor 1: 3-6
//Floor 2: 9-10
//Floor 3: 14-16
//Floor 4: 20-21
//Floor 5: 24-25


int getDistance() {
  // Clear/prep the trigPin.
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  // Pulse.
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  // Read & calc.
  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;
  return distance;
}

void goToHeight(int low) {
  int endAv;
  while (true) {
    if (getAverage() > low) {
      Motor2(80,true);
    } else if (getAverage() == low) {
      Motor2(60,false);
    } else {
      Motor2(255,false);
    }
    endAv = getAverage();
    Serial.println(endAv);
    if (checkAvDist(low,low)) { break; }
  }
  Serial.println("Done!");
  Serial.print("With: ");
  Serial.print(getAverage());
  Motor2(80,false);
}

int getAverage() {
  int average = 0;
  for (int i = 0; i < 10; i++) {
    average += getDistance();
  }
  average = average / 10;
  return average;
}

bool checkAvDist(int low, int high) {
  int e = getAverage();
  if (e >= low && e <= high) { return true; } else { return false; }
}
