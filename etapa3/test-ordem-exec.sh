eacho "Test Ordem Excecucao"
sleep 1
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.TestClientOrdemExc" -Dexec.args="1" 
echo "Fim dos testes"
sleep 20