echo "Teste Multiplos clientes"
sleep 1

echo "3 Clientes em cada Servidor"
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="1 src\test\resources\test-op1.txt"&
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="1 src\test\resources\test-op1.txt"&
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="1 src\test\resources\test-op1.txt"&

mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="2 src\test\resources\test-op2.txt"&
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="2 src\test\resources\test-op2.txt"&
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="2 src\test\resources\test-op2.txt"&

mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="3 src\test\resources\test-op3.txt"&
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="3 src\test\resources\test-op3.txt"&
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="3 src\test\resources\test-op3.txt"

echo "Fim dos testes"
sleep 60