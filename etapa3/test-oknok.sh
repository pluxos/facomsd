echo "TEST Ok"
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="1 src\test\resources\test-ok.txt"
sleep 5

echo "TEST NOk"
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="2 src\test\resources\test-nok.txt"
sleep 5

echo "Fim do teste OK e NOK"
sleep 60
