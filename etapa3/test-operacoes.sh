echo "Teste CREATE"
sleep 2
echo "Inserindo chaves(2 a 6) no server 1"
sleep 2
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="1 src\test\resources\test-op1.txt" 
sleep 5
echo "Inserindo chaves(7 a 11) no server 2"
sleep 2
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="2 src\test\resources\test-op2.txt" 
sleep 5
echo "Inserindo chaves(12 a 16) no server 3"
sleep 2
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="3 src\test\resources\test-op3.txt" 
sleep 5

echo "Fim dos testes"
sleep 60