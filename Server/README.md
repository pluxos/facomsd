

# Distributed System Homework (Server side)

#### Status

[![Build Status](https://travis-ci.org/MarcusAdriano/DS-Server.svg?branch=master)](https://travis-ci.org/MarcusAdriano/DS-Server)

Simple server implementation for Distributed System homework.

#### Getting Started

###### System Requirements

- Java JDK 8
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)

Clone it locally:

```bash
$ git clone https://github.com/MarcusAdriano/DS-Server.git
```


Build with maven:

```bash
$ mvn clean install
```

Running jar:

```bash
$ cd target
$ java -jar  ds-server-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

Running all unit tests:

```bash
$ mvn test
```

Configuring hostname and port (inside folder with jar, create a file server.config) like this:
```bash
host.name=localhost
host.port=5454
```

> Recommended IDE: IntelliJ IDEA Professional

#### Evolution of the Project :chart_with_upwards_trend:

All new changes, improvements, future works will be controlled by a simple [Kanban](https://github.com/MarcusAdriano/DS-Server/projects).
   
