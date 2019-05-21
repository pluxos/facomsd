#!/bin/bash

# Creating a default log
cp log ../Server/src/main/java/server/
# Build the client
cd ../Client
mvn clean package

# Build the server
cd ../Server
mvn clean package

# Execute one server
cd ../output
java -jar Server-1.0-SNAPSHOT.jar &

# Execute five clients
executeClients(){
	java -jar Client-1.0-SNAPSHOT.jar < in
}

# 5 executions
for i in {1..5}
do
	executeClients & # Put the function in background
done

# Block the terminal until all threads finished
wait 
echo "All done"
