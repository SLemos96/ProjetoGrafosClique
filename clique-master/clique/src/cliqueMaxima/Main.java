package cliqueMaxima;

import java.io.IOException;

public class Main 
{
	public static void main(String[] args) throws IOException {
		
		GrafoAleatorio novo = new GrafoAleatorio();
		novo.gerarRandom();
		System.out.printf("Iniciando a busca pela clique máxima\n");
		buscaCliqueMaxima();
	}

	public static void buscaCliqueMaxima() throws IOException { //colar a parte de geração do grafo aleatório aqui
		new MaxClique();
		
	}
}
