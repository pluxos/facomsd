package com.sd.etapa2.test;

import java.io.IOException;

import com.sd.etapa2.chord.Chord;

public class IniciaChord {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Chord chord = new Chord(3, 5050);
		chord.iniciaChord();
	}
}