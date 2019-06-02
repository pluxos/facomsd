#!/usr/bin/env bash
echo "iniciando Servidores!"

ports=('12345' '12346' '12347' '12348')

cd Server
mkdir logs
mkdir logs/Server0

mvn exec:java -Dexec.args="logs/Server1/ ${ports[0]}" -e

for (( i = 1; i < 10; ++i )); do
    mkdir logs/Server${i}
    sleep 30
    echo "Iniciando Servidor " ${i}
    gnome-terminal -- bash -c "mvn exec:java -Dexec.args=\"logs/Server${i}/  ${ports[i]} localhost ${ports[i-1]}\""
done
