#!/usr/bin/env bash

rm -rf Server/logs/
mvn clean install
echo "iniciando Servidores!"

ports=('12345' '12346' '12347' '12348')

cd Server
mkdir logs
mkdir logs/Server0

xfce4-terminal -e "mvn exec:java -Dexec.args=\"logs/Server0/ localhost ${ports[0]}\""
cd ../Client
xfce4-terminal -e "mvn exec:java -Dexec.args=\"localhost ${ports[0]}\""

for (( i = 1; i < 4; ++i )); do
    cd ../Server
    mkdir logs/Server${i}
    sleep 20
    echo "Iniciando Servidor " ${i}
    xfce4-terminal -e "mvn exec:java -Dexec.args=\"logs/Server${i}/ localhost ${ports[i]} localhost ${ports[i-1]}\""
    echo "Iniciando Cliente " ${i}
    cd ../Client
    xfce4-terminal -e "mvn exec:java -Dexec.args=\"localhost ${ports[i]}\""
done
