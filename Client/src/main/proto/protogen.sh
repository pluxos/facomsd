#!/bin/bash
OUTPUT_DIR=$PWD
FILE_NAME="br"
JAVA_SOURCE="src/main/java"
PROJS_DIR=("/home/marcus/code/Java/dsserver/" "/home/marcus/code/Java/dsclient/")

echo "Generating protobuf's Java file..."
protoc --java_out=$OUTPUT_DIR messages.proto

index=0
while [ "x${PROJS_DIR[index]}" != "x" ]
do
  DIR=${PROJS_DIR[index]}$JAVA_SOURCE
  cp -r -v $FILE_NAME $DIR
  index=$(( index + 1 ))
done
