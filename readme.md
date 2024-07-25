# little_mom

Assignment project Advanced concepts programming DHBW CAS SoSe24

## Introduction

This project contains a Java implementation simulation of a Message Oriented Middleware (**MOM**) with a Message Broker. The problem definition corresponds to the producer-consumer pattern, with several producers and consumers. 

## Setup

The project is implemented with a Visual Studio Code Development Container. For more information look here: [Dev Container](https://code.visualstudio.com/docs/devcontainers/containers)

### Check Java Version

- `java -version`

## TODO (Generate Jar)

### Generate project
- `mvn archetype:generate -DgroupId=de.dhbw.cas -DartifactId=little_mom -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false`

### Generate Jar
- `mvn clean package`

### Run Jar
- `java -jar target/little_mom-1.0.jar`


