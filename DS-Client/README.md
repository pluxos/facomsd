# Distributed System Homework (Client side)

#### Status and Tests

[![Build Status](https://travis-ci.org/MarcusAdriano/DS-Client.svg?branch=master)](https://travis-ci.org/MarcusAdriano/DS-Client)
[![codecov](https://codecov.io/gh/MarcusAdriano/DS-Client/branch/master/graph/badge.svg)](https://codecov.io/gh/MarcusAdriano/DS-Client)

Simple client implementation for Distributed System homework.

#### Getting Started

###### System Requirements

- Java JDK 8
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)

Clone it locally:

```bash
$ git clone https://github.com/MarcusAdriano/DS-Client.git
```


Build with maven:

```bash
$ mvn clean install
```

Running jar:

```bash
$ cd target
$ java -jar  ds-client-1.0-SNAPSHOT-jar-with-dependencies.jar 
```

Running all unit tests:

```bash
$ mvn test
```

> Recommended IDE: IntelliJ IDEA Professional

#### Problem Requirements

A client can connect to a server, the server is database server, that can save
a value by a key. The key is a BigInteger and valeu anything. When client
connect with server, 2 threads are working: 

>Requirement marked with (:thumbsup:) is complete. 

1. Thread 1: interpret user interaction and to send correct commands for server.
Commands are:
    1. :thumbsup: CREATE: prompt a key and value;
    2. :thumbsup: READ: prompt a key;
    3. :thumbsup: UPDATE: prompt a key and value (update when a key already exists);
    4. :thumbsup: DELETE: prompt a key.
    
2. :thumbsup: Thread 2: receive responses from server and show to user;

3. :thumbsup: When user prompt exit, stop all threads, but Thread 2 wait for 5 seconds before shut down complete.

4. :thumbsup: Unit tests: [![codecov](https://codecov.io/gh/MarcusAdriano/DS-Client/branch/master/graph/badge.svg)](https://codecov.io/gh/MarcusAdriano/DS-Client)

#### Evolution of the Project :chart_with_upwards_trend:

All new changes, improvements, future works will be controlled by a simple [Kanban](https://github.com/MarcusAdriano/DS-Client/projects).

#### Contributors

[Marcus Adriano](https://github.com/MarcusAdriano)
   
