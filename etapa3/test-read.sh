echo "Test READ"
sleep 2

echo "READ no Server 1"
sleep 2
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="1 src\test\resources\test-read.txt"
sleep 10

echo "READ no Server 2"
sleep 2
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="2 src\test\resources\test-read.txt"
sleep 10

echo "READ no Server 3"
sleep 2
mvn exec:java -Dexec.mainClass="br.ufu.sd.main.ClientTest" -Dexec.args="3 src\test\resources\test-read.txt"
sleep 10

echo "Fim dos testes"
sleep 60
