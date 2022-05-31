#include <SoftwareSerial.h>

SoftwareSerial mySerial(2,3);

int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;
int robotX = 1;

char currentCommand = 'n';
unsigned long startTime = millis();

void setup() {
  for(int i=0; i<=13; i++){
    if (!(i==2||i==3)) {
     pinMode(i, OUTPUT); 
    }
  }
  pinMode(A0, INPUT);
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
  analogWrite(M1S, pwm);
  if(links){
    digitalWrite(M1R, HIGH);
  } else {
    digitalWrite(M1R, LOW);
  }
}

void duw(){
  if(millis() - startTime <= 450){
    Motor2(180, false);
  }else if(millis() - startTime <= 900){
    Motor2(180, true);
  }else{
    currentCommand = 'n';
    Motor2(0, true);
    mySerial.write('e');
  } 
}

void gaan(int schap){
  bool richting = (robotX - schap) > 0;
  int runtime = (robotX - schap) * 590;

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
      if(c == 'p' || c == '1' || c == '2'|| c == '3'|| c == '4'|| c == '5' || c == '6'|| c == '7'|| c=='c'){            // lijst van geverifieerde commandos
        mySerial.println(c);

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

  // execute commands for y as

}
