/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemasDistribuidos.utils;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author luizw
 *
 */
public class Filas {

    private static Queue<DatagramPacket> comandos = new LinkedList<>();
    private static Queue<DatagramPacket> fila = new LinkedList<>();
    private static Queue<DatagramPacket> processaFila = new LinkedList<>();

    public static void setComandoFila(DatagramPacket packet) {
        comandos.offer(packet);
    }

    public static DatagramPacket getComandoFila() {
        return comandos.poll();
    }

    public static void setFila(DatagramPacket packet) {
        fila.offer(packet);
    }

    public static DatagramPacket getFila() {
        return fila.poll();
    }

    public static void setProcessaFila(DatagramPacket packet) {
        processaFila.offer(packet);
    }

    public static DatagramPacket getProcessaFila() {
        return processaFila.poll();
    }

}
