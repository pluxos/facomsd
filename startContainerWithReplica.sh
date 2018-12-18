#!/usr/bin/env bash

stop=$1

replica_port=14000

echo 'Choose the number of nodes in then ring:';
read N;

echo 'Choose the number of bits for the key:';
read M;


echo 'Choose the number of replicas:';
read replica_number;

name_base='node';

echo 'Do you want to remove the containers if they already exists? [Y/n]'
read remove;

if [ "$remove" == 'n' ]; then
    remove=false;
else
    remove=true;
fi

function stopContainers {
    jobs_legacy=`jobs | wc -l`
    echo "Call stop containers"
    for i in `seq 1 ${replica_number}`
    do
        name="${name_base}_${N}_${j}_${i}"
        if [ ! -z "$(docker ps -aq -f name=${name})" ]; then
            if [ ! "$(docker ps -aq -f status=exited -f name=${name})" ]; then
                echo "stopping Container" ${name}
                docker stop ${name} > /dev/null &
            fi
        fi
    done;
    echo "stop containers called!"

    echo "Waiting containers stop!"

    while true
    do

        stops=`jobs | wc -l`

        if [ "$stops" -le "$jobs_legacy" ]; then
            break;
        fi

    done;
    echo "all containers stopped!"
}

function stopRemoveAndStartContainer {
    node1=false;
    if [ ! -z "$(docker ps -aq -f name=${name})" ]; then
        node1=true;
        if [ ! "$(docker ps -aq -f status=exited -f name=${name})" ]; then
            echo "stopping Container" ${name}
            docker stop ${name} > /dev/null ;
        fi

        if [ "$remove" = true ]; then
            node1=false
            echo "I'm removing Container" ${name}
            docker rm ${name} > /dev/null
        fi
    fi

    if [ ! "$stop" == "true" ]; then
        if [ "$node1" = false ]; then
            docker run -d --name=${name} \
                -v `pwd`/server:/workspace/server \
                -v `pwd`/config.py:/workspace/config.py \
                -v `pwd`/grpcDefinitions/:/workspace/grpcDefinitions \
                -v `pwd`/client:/workspace/client \
                facomsd-server:0.1 \
                tail -f /dev/null > /dev/null
        else
            docker start ${name}
        fi
    fi
    ring_ip=`docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' ${name}`
    replica_ips+=${ring_ip}':'${replica_port}

    if [ "${i}" == "1" ]; then
        bootstrap=${ring_ip}
    fi
}

function startContainers {

    for i in `seq 1 ${replica_number}`
    do
        name="${name_base}_${N}_${j}_${i}";

        stopRemoveAndStartContainer;

        if [ "$i" != "${replica_number}" ]; then
            replica_ips+=', ';
        fi

    done;

}

function startServers {

    for i in `seq 1 ${replica_number}`
    do
        name="${name_base}_${N}_${j}_${i}";

        if [ "${i}" == "1" ]; then
            is_bootstrap=1
        else
            is_bootstrap=0
        fi

        echo "docker exec ${name} /bin/bash ./startServer.sh ${is_bootstrap} ${replica_port} ${N} ${M} ${j} '${replica_ips}' ${name} ${bootstrap} ${ring} > output_${name} 2>&1 &"
        docker exec ${name} /bin/bash ./startServer.sh ${is_bootstrap} ${replica_port} ${N} ${M} ${j} "${replica_ips}" ${name} ${bootstrap} ${ring} > output_${name} 2>&1 &

    done;

}

echo "Call stop all to fast process!"
for j in `seq 1 ${N}`
do
    for i in `seq 1 ${replica_number}`
        do
            name="${name_base}_${N}_${j}_${i}"
            if [ ! -z "$(docker ps -aq -f name=${name})" ]; then
                if [ ! "$(docker ps -aq -f status=exited -f name=${name})" ]; then
                    docker stop ${name} > /dev/null &
                fi
            fi
    done;
done;
echo "Call stop all done!"

ring='';
for j in `seq 1 ${N}`;
do
    stopContainers;
    replica_ips='';
    bootstrap='';

    if [ "$stop" != "stop" ]; then
        startContainers;

        startServers;
    fi

    if [ "${j}" == "1" ]; then
        ring=${bootstrap};
    fi

    echo 'Cluster IPs number' ${j} 'are' ${replica_ips};
done;