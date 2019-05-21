#!/bin/bash

cp log ../Server/src/main/java/server/

cd ../Client
mvn clean package

cd ../Server
mvn clean package

cd ../output

java -jar Server-1.0-SNAPSHOT.jar &

executeClients(){
	java -jar Client-1.0-SNAPSHOT.jar < in
}

# 5 executions
for i in {1..5}
do
	executeClients & # Put the function in background
done

wait 
echo "All done"
