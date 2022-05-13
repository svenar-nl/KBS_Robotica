int M2R = 6;
int M2S = 7;
int M1R = 4;
int M1S = 5;


void setup() {
  for(int i=0; i<=13; i++){
    pinMode(i, OUTPUT);
  }
  pinMode(A0, INPUT);
  Serial.begin(9600);
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
  Motor2(255, false);
  delay(675);
  Motor2(255, true);
  delay(800);
  Motor2(0, true);
}

void loop() {
  if (Serial.available() > 0){
    int t = Serial.read();
    if(t == '1'){
      duw();  
    }
  }
}
