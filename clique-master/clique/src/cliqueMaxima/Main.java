package cliqueMaxima;

import java.io.IOException;

public class Main 
{
	public static void main(String[] args) throws IOException {
		
		RandomGraph novo = new RandomGraph();
		novo.gerarRandom();
		System.out.printf("Iniciando a busca pela clique m�xima\n");
		teste();
	}

	public static void teste() throws IOException { //colar a parte de gera��o do grafo aleat�rio aqui
		new MaxClique();
		
	}
}
