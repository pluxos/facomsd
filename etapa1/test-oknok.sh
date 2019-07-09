echo "TEST Ok"
mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-ok.txt"
sleep 5

echo "TEST NOk"
mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-nok.txt"
sleep 5

sleep 60
