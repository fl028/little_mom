# little_mom

Assignment project Advanced concepts programming DHBW CAS SoSe24

## Introduction

This project contains a Java implementation simulation of a Message Oriented Middleware (**MOM**) with a Message Broker. The problem definition corresponds to the producer-consumer pattern, with several producers and consumers. 

## Setup

The project is implemented with a Visual Studio Code Development Container. For more information look here: [Dev Container](https://code.visualstudio.com/docs/devcontainers/containers)

### Check Java Version

- `java -version`

## Build and Package with Maven

To build the JAR file and generate JavaDocs, use Maven:

- Build the JAR file: `cd little_mom && mvn clean package`
- Generate the JavaDocs: `cd little_mom && mvn javadoc:javadoc`

The resulting JAR file and JavaDocs will be placed in the repo.

## Run jar

- `java -jar little_mom.jar`

## View docs

- [JavaDocs](apidocs/index.html)
