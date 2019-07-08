## Atomix-Raft: Distributed Systems

This simple project illustrate usage of [Atomix](https://atomix.io/): A reactive Java framework for building fault-tolerant distributed systems.

What you can see here?

* Raft with 3 nodes;
* Interactive client, using a Distributed Map.

#### Requirements

* Java 1.8+
* Apache Maven

## Getting started

First of all, clone it:

```bash
git clone https://github.com/MarcusAdriano/Atomix-Raft
```

Second and obvious, compile it:

```bash
mvn clean compile
```

#### Server usage

You need start 3 nodes's server:
* Open 3 terminals screen

For each terminal, start a node instance:

First instance: 
```bash
 mvn exec:java -Dexec.mainClass="br.ufu.ds.server.Server" -Dexec.args="1 localhost:8001"
```

Second instance:
```bash
 mvn exec:java -Dexec.mainClass="br.ufu.ds.server.Server" -Dexec.args="2 localhost:8002"
```

Third instance:
```bash
 mvn exec:java -Dexec.mainClass="br.ufu.ds.server.Server" -Dexec.args="3 localhost:8003"
```

#### Client usage

For using automatic client, create a file, *test_example*, with this content on root's project folder:

```text
create 1 Marcus
create 2 Lucas
create 3 Isadora
update 1 MarcusAdriano
read 1
delete 3
update 2 LucasT
delete 1
read 2
exit
```

And now, run client:

```bash
mvn exec:java -Dexec.mainClass="br.ufu.ds.client.Client" -Dexec.args="test_example"
```

> When use a file as input of the client, the output is in test_output file.

For run client without file, ignore -Dexec.args flag:

```bash
mvn exec:java -Dexec.mainClass="br.ufu.ds.client.Client"
```

#### Available commands for client

* create key value
* read key
* update key value
* delete key value


