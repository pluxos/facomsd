echo "Teste OPERACOES"
sleep 2
echo "Inserindo chaves(2 a 16) "
sleep 2
mvn exec:java -Dexec.mainClass="com.sd.etapa2.cliente.ClientTest" -Dexec.args="src\test\resources\test-op.txt" 

sleep 60