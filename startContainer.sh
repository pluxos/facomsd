

docker run --rm \
    -v `pwd`/server:/workspace/server \
    -v `pwd`/config.py:/workspace/config.py \
    -v `pwd`/grpcDefinitions/:/workspace/grpcDefinitions \
    -v `pwd`/client:/workspace/client \
    -e M=10 \
    -e N=10 \
    facomsd-server:0.1
