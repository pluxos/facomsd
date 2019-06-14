#!/usr/bin/env bash

ports=('12345' '12346' '12347' '12348')
NUM_CLIENTS=$2
NUM_SERVERS=$1

rm -rf Server/logs/
mvn clean install

mkdir Server/logs
mkdir Server/logs/Server0

cp testFiles/counter.log Server/logs/Server0/counter.log
cp testFiles/snap0.log Server/logs/Server0/snap0.log

cd Server

gnome-terminal -- bash -c "mvn exec:java -Dexec.args=\"logs/Server0/ localhost ${ports[0]}\""

for (( i = 1; i < NUM_SERVERS; ++i )); do
    mkdir logs/Server${i}
    sleep 20
    echo "Iniciando Servidor " ${i}
    gnome-terminal -- bash -c "mvn exec:java -Dexec.args=\"logs/Server${i}/ localhost ${ports[i]} localhost ${ports[i-1]}\""
done

echo "Iniciando cliente de testes"

cd ../Client
for (( i = 0; i < NUM_CLIENTS; ++i )); do
    echo "Iniciando Cliente " ${i}
    sleep 10
    gnome-terminal -- bash -c "mvn exec:java -Dexec.args=\"localhost ${ports[i]} teste src/test/resources/stress${i+1}.txt\""
done