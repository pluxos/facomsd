#!/bin/bash

is_bootstrap=$1
replica_port=$2
N=$3
M=$4
id=$5
replica_ip=$6
bootstrap_address=$7
ring_ip=$8

if [ "${is_bootstrap}" != "1" ]; then
    concoord replica -o my_dict.MyDict -b ${bootstrap_address}':'${replica_port} -a 0.0.0.0 -p ${replica_port} 1> concoord.logs 2> concoord.logs.err &
else
	concoord replica -o my_dict.MyDict -a 0.0.0.0 -p ${replica_port} 1> concoord.logs 2> concoord.logs.err &
fi

if [ "${ring_ip}" != "" ]; then
    python -u __init__.py -m ${M} -n ${N} --id ${id} --ip-replica "${replica_ip}" --ring ${ring_ip}
else
    python -u __init__.py -m ${M} -n ${N} --id ${id} --ip-replica "${replica_ip}"
fi

