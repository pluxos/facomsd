package com.sd.projeto1.dao;

import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.FileUtils;
import com.sd.projeto1.util.Utils;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MapaDao implements Serializable {

    private static final long serialVersionUID = 1L;
    public static Map<BigInteger, String> mapa = new HashMap();


    public static void salvar(Mapa mapValue) {
        BigInteger key = new BigInteger(String.valueOf(generateKey()));

        if (mapa.containsKey(mapValue.getChave())) {
            System.out.println("Mensagem com essa chave já adicionada");
        }
        FileUtils.writeFile(String.valueOf(Utils.CREATE), key, mapValue.getTexto());
        mapa.put(key, mapValue.getTexto());
    }


    public static void editar(Mapa mapValue) {
        BigInteger key = new BigInteger(String.valueOf(mapValue.getChave()));

        if (!mapa.containsKey(mapValue.getChave())) {
            System.out.println("Chave não encontrada");
        }
        FileUtils.writeFile(String.valueOf(Utils.UPDATE), key, mapValue.getTexto());
        mapa.put(key, mapValue.getTexto());
    }

    public static void excluir(Mapa mapValue) {
        BigInteger key = new BigInteger(String.valueOf(mapValue.getChave()));
        FileUtils.writeFile(String.valueOf(Utils.DELETE), key, "");
        mapa.remove(key);
    }

    public static String buscar(Mapa mapa1) {
        BigInteger chave = new BigInteger(String.valueOf(mapa1.getChave()));
        return mapa.get(chave);
    }

    public static void imprimeCRUD(Mapa mapa1) {

        System.out.println("\n===============================");
        System.out.println("Chave: " + mapa1.getChave());
        System.out.println("Texto: " + mapa1.getTexto());
        System.out.println("Tipo de Operaçao: " + Utils.retornaTipoOperacao(mapa1.getTipoOperacaoId()));
        System.out.println("Data: " + mapa1.getData());
        System.out.println("Tamanho da fila: " + mapa.size());
        System.out.println("===============================");
    }

    public static void imprimeMapa() {
        for (Map.Entry<BigInteger, String> map : mapa.entrySet()) {

            System.out.println("\n=============================");
            System.out.println("Chave: " + map.getKey());
            System.out.println("Texto: " + map.getValue());
            System.out.println("===============================");
        }
    }

    public Map<BigInteger, String> getMapa() {
        return mapa;
    }

    public static String buscarTodos() {
        String toString = "";
        for (Map.Entry<BigInteger, String> entry : mapa.entrySet()) {
            toString = toString.concat("(" + entry.getKey().toString() + "," + entry.getValue().toString() + ")");
        }
        return toString;
    }


    private static int generateKey() {
        return mapa.size();
    }

}
