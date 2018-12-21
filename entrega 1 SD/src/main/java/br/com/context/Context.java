package br.com.context;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.enums.Operation;
import br.com.operations.Creat;
import br.com.operations.Delete;
import br.com.operations.Read;
import br.com.operations.Update;
import io.atomix.copycat.server.Commit;

public class Context {

    private Map<BigInteger, Object> context;

    public Context() {
        this.context = new LinkedHashMap<BigInteger, Object>();
    }

    public void put(Commit<Creat> commit) {
        BigInteger key = commit.operation().key;
        String valor = commit.operation().valor;
        context.putIfAbsent(key, valor);
    }

    public void update(Commit<Update> commit) {
        BigInteger key = commit.operation().key;
        String valor = commit.operation().valor;
        if (context.get(key) != null) {
            context.put(key, valor);
        }
    }

    public void remove(Commit<Delete> commit) {
        BigInteger key = commit.operation().key;
        context.remove(key);
    }

    public String get(Commit<Read> commit) {
        BigInteger key = commit.operation().key;
        if (context.get(key) != null) {
            return "(" + key + "," + context.get(key) + ")";
        } else {
            return "Chave nao encontrada no contexto.";
        }
    }

    public void load(Path snapshotPath, Path logPath) throws IOException {
        Files.lines(snapshotPath).forEach((line) -> load(line));
        Files.lines(logPath).forEach((line) -> load(line));
    }

    public void load(String line) {
        List<String> list = Arrays.asList(line.split(";"));

        if (Operation.INSERT.name().equals(list.get(0))) {
            context.put(new BigInteger(list.get(1)), list.get(2));
        } else if (Operation.UPDATE.name().equals(list.get(0))) {
            context.put(new BigInteger(list.get(1)), list.get(2));
        } else if (Operation.DELETE.name().equals(list.get(0))) {
            context.remove(new BigInteger(list.get(1)));
        }
    }


    public Map<BigInteger, Object> get() {
        return this.context;
    }

}
