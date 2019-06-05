#!/usr/bin/env bash
rm -rf Server/logs/
mvn clean install
echo "iniciando Servidores!"

ports=('12345' '12346' '12347' '12348')

cd Server
mkdir logs
mkdir logs/Server0

gnome-terminal -- bash -c "mvn exec:java -Dexec.args=\"logs/Server0/ ${ports[0]}\""

for (( i = 1; i < 3; ++i )); do
    mkdir logs/Server${i}
    sleep 10
    echo "Iniciando Servidor " ${i}
    gnome-terminal -- bash -c "mvn exec:java -Dexec.args=\"logs/Server${i}/  ${ports[i]} localhost ${port
	s[i-1]}\""
done
