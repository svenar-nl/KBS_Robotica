#include <Arduino.h>
#line 1 "c:\\Stuff\\School\\java1\\projects\\src\\KBS_Robotica\\Arduino\\src\\LegoMotor\\LegoMotorX\\LegoMotorX.ino"
#include <SoftwareSerial.h>

SoftwareSerial mySerial(2,3);

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;
int robotX = 1;

char currentCommand = 'n';
unsigned long startTime = millis();

#line 14 "c:\\Stuff\\School\\java1\\projects\\src\\KBS_Robotica\\Arduino\\src\\LegoMotor\\LegoMotorX\\LegoMotorX.ino"
void setup();
#line 27 "c:\\Stuff\\School\\java1\\projects\\src\\KBS_Robotica\\Arduino\\src\\LegoMotor\\LegoMotorX\\LegoMotorX.ino"
void Motor2(int pwm, boolean links);
#line 36 "c:\\Stuff\\School\\java1\\projects\\src\\KBS_Robotica\\Arduino\\src\\LegoMotor\\LegoMotorX\\LegoMotorX.ino"
void Motor1(int pwm, boolean links);
#line 45 "c:\\Stuff\\School\\java1\\projects\\src\\KBS_Robotica\\Arduino\\src\\LegoMotor\\LegoMotorX\\LegoMotorX.ino"
void duw();
#line 58 "c:\\Stuff\\School\\java1\\projects\\src\\KBS_Robotica\\Arduino\\src\\LegoMotor\\LegoMotorX\\LegoMotorX.ino"
void gaan(int schap);
#line 77 "c:\\Stuff\\School\\java1\\projects\\src\\KBS_Robotica\\Arduino\\src\\LegoMotor\\LegoMotorX\\LegoMotorX.ino"
void loop();
#line 14 "c:\\Stuff\\School\\java1\\projects\\src\\KBS_Robotica\\Arduino\\src\\LegoMotor\\LegoMotorX\\LegoMotorX.ino"
void setup() {
  for(int i=0; i<=13; i++){
    if (!(i==2||i==3)) {
     pinMode(i, OUTPUT); 
    }
  }
  pinMode(A0, INPUT);

  Serial.begin(9600);

  mySerial.begin(9600);
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
  if(millis() - startTime <= 450){
    Motor2(180, true);
  }else if(millis() - startTime <= 900){
    Motor2(180, false);
  }else{
    currentCommand = 'n';
    Motor2(0, true);
    mySerial.write('e');
    delay(500);
  } 
}

void gaan(int schap){
  bool richting = (robotX - schap) > 0;
  int runtime = (robotX - schap) * 500;

  // make positive
  if(!richting){
    runtime = runtime * -1;
  }

  if(millis() - startTime <= runtime){
    Motor1(200, richting);
  }else{
    currentCommand = 'n';
    mySerial.write('e');
    Motor1(0, false);
    robotX = schap;
  }
}

void loop() {
  // Check for mySerial commands. (Override)
  if (mySerial.available()){
    if(currentCommand == 'n'){ // als er op dit moment geen commando gegeven word
      char c = mySerial.read();

      Serial.write(c);

      if(c == 'p' || c == '1' || c == '2'|| c == '3'|| c == '4'|| c == '5' || c == '6'|| c == '7'|| c=='c'){            // lijst van geverifieerde commandos
        currentCommand = c;

        startTime = millis();
      }
    }
  }

  // execute commands for x as
  switch (currentCommand)
  {
    case 'p':
      duw();
    break;
    case '1':
      gaan(1);
    break;
    case '2':
      gaan(2);
    break;
    case '3':
      gaan(3);
    break;
    case '4':
      gaan(4);
    break;
    case '5':
      gaan(5);
    break;
    case '6':
      Motor1(200, true);
      delay(1000);
      Motor1(0, false);
      currentCommand = 'n';
      mySerial.write('e');
    break;
    case '7':
      Motor1(200, false);
      delay(1000);
      Motor1(0, false);
      currentCommand = 'n';
      mySerial.write('e');
    break;
    case 'c':
      Motor1(100, true);
      delay(3000);
      currentCommand = 'n';
      mySerial.write('e');
      Motor1(0, false);
    break;
    default:
      break;
  }
}
