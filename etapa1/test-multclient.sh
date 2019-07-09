echo "Teste Multiplos clientes"
sleep 1

mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&

mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&

mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"

sleep 60