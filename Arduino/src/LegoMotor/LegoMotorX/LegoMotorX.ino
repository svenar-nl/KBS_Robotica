#include <Servo.h>

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;
int robotX = 1;

Servo s1;
char currentCommand = 'n';
unsigned long startTime = millis();

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
  if(millis() - startTime <= 450){
    Motor2(100, true);
  }else if(millis() - startTime <= 900){
    Motor2(100, false);
  }else{
    currentCommand = 'n';
    Motor2(0, true);
  } 
}

void gaan(int schap){
  bool richting = (robotX - schap) > 0;
  int runtime = (robotX - schap) * 620;

  // make positive
  if(!richting){
    runtime = runtime * -1;
  }

  if(millis() - startTime <= runtime){
    Motor1(200, richting);
  }else{
    currentCommand = 'n';
    Motor1(0, false);
    robotX = schap;
  }
}

void loop() {
  // check for commands
  if (Serial.available()){
    if(currentCommand == 'n'){ // als er op dit moment geen commando gegeven word
      char c = Serial.read();
      if(c == 'p' || c == '1' || c == '2'|| c == '3'|| c == '4'|| c == '5' || c == '6'|| c == '7'){            // lijst van geverifieerde commandos
        Serial.println(c);

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
      gaan(1);  //TODO char to int
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
    break;
    case '7':
      Motor1(200, false);
      delay(1000);
      Motor1(0, false);
      currentCommand = 'n';
    break;
    default:
      break;
  }

  // execute commands for y as

}
