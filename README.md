# KBS_Robotica

### GUI application to control an Arduino-based endpoint

```diff
- WARNING -

This repository is a work in progress, and while it can be used to make certain changes, it's still
constantly evolving. Please be aware that the codebase can drastically change at any time.
```

## Compiling

Compiling is done using Maven.
The default tasks are: 'clean compile package shade:shade' and will generate 'target/KBS.jar'

`$ mvn`

## Running

After compiling the generated jar can be executed using:

`$ java -jar KBS.jar`
