#!/usr/bin/env bash

stop=$1

echo 'Choose the number of nodes in then ring:';
read N;

echo 'Choose the number of bits for the key:';
read M;

name_base='node';


echo 'Do you want to remove the containers if they already exists? [Y/n]'
read remove;

if [ "$remove" == 'n' ]; then
    remove=false;
else
    remove=true;
fi

name="${name_base}_${N}_1"
node1=false;
if [ ! -z "$(docker ps -aq -f name=${name})" ]; then
    node1=true;
    echo "I'm here"
    if [ ! "$(docker ps -aq -f status=exited -f name=${name})" ]; then
        echo "stopping Container" ${name}
        docker stop ${name};
    fi

    if [ "$remove" = true ]; then
        node1=false
        echo "I'm removing Container" ${name}
        docker rm ${name}
    fi
fi

if [ ! "$stop" == "true" ]; then
    if [ "$node1" = false ]; then
        # run your container
        docker run -d --name=${name} \
            -v `pwd`/server:/workspace/server \
            -v `pwd`/config.py:/workspace/config.py \
            -v `pwd`/grpcDefinitions/:/workspace/grpcDefinitions \
            -v `pwd`/client:/workspace/client \
            facomsd-server:0.1 \
            python3 -u __init__.py ${M} ${N} 1
    else
        docker start ${name}
    fi
fi

ring_ip=`docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' ${name}`
echo ${name} ' ip is ' ${ring_ip}

for i in `seq 2 ${N}`
do
    name="${name_base}_${N}_${i}"
    node=false;
    if [ ! -z "$(docker ps -aq -f name=${name})" ]; then
        node=true;
        echo "I'm here"
        if [ ! "$(docker ps -aq -f status=exited -f name=${name})" ]; then
            echo "stopping Container" ${name}
            docker stop ${name};
        fi

        if [ "$remove" = true ]; then
            node=false
            echo "I'm removing Container" ${name}
            docker rm ${name}
        fi
    fi

    echo $name

    if [ ! "$stop" == "true" ]; then
        if [ "$node" = false ]; then
            # run your container
            docker run -d --name=${name} \
                -v `pwd`/server:/workspace/server \
                -v `pwd`/config.py:/workspace/config.py \
                -v `pwd`/grpcDefinitions/:/workspace/grpcDefinitions \
                -v `pwd`/client:/workspace/client \
                facomsd-server:0.1 \
                python3 -u __init__.py ${ring_ip} ${M} ${N} ${i}
        else
            docker start ${name}
        fi
    fi
    sleep 0.5
done;

#
#
#
