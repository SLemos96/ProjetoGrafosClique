package cliqueMaxima;

import java.io.IOException;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) throws IOException {
		
		RandomGraph novo = new RandomGraph();
		novo.gerarRandom();
		System.out.printf("Iniciando a busca pela clique m�xima\n");
		teste();
	}

	public static void teste() { //colar a parte de gera��o do grafo aleat�rio aqui
		new MaxClique();
		
	}
}
