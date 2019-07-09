echo "Teste Multiplos clientes"
sleep 1

mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&

mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&

mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"&
mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt"

sleep 60