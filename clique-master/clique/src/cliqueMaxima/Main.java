package cliqueMaxima;

import java.io.IOException;

public class Main 
{
	public static void main(String[] args) throws IOException {
		
		RandomGraph novo = new RandomGraph();
		novo.gerarRandom();
		System.out.printf("Iniciando a busca pela clique máxima\n");
		teste();
	}

	public static void teste() throws IOException { //colar a parte de geração do grafo aleatório aqui
		new MaxClique();
		
	}
}
