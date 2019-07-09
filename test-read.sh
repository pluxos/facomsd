echo "Test READ"
sleep 2

echo "READ das chaves 0 a 16"
sleep 2
mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-read.txt"
sleep 60
