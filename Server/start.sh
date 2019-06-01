#!/usr/bin/env bash
echo "iniciando Servidores!"

ports=('12345' '12346' '12347' '12348')

for (( i = 0; i < 2; ++i )); do
    echo "Iniciando Servidor " ${i}
    gnome-terminal -- bash -c "mvn exec:java -Dexec.args=\"${ports[i]}\""
done
