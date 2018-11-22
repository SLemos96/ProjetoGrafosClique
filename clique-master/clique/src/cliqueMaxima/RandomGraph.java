package cliqueMaxima;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class RandomGraph {

	public void gerarRandom() throws IOException {
		// Ler argumentos da linha de comandos
	   	Scanner ler = new Scanner(System.in);
		int n   = 30;//Integer.parseInt(200);
		int e   = 350;//Integer.parseInt(args[1]);
		int limiteArestas = 0;

		// Novo gerador de numeros aleatorios
		Random r = new Random();
		
		System.out.printf("Informe o número de vértices:\n");
		n = ler.nextInt();
		int matriz[][] = new int[n][n]; // matriz de adjacencia
		do {
			System.out.printf("Agora informe o número de arestas:\n");
			e = ler.nextInt();
			limiteArestas = (n*(n-1)/2);
			if(e > limiteArestas) {
				System.out.printf("O número de arestas é superior ao limite do grafo, insira um número menor que:"+ limiteArestas +"\n");
			}
		} while (e > limiteArestas);
		
		//variáveis de apoio para escrever um arquivo txt
		FileWriter arq = new FileWriter("graph.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		

		// Criar lista de adjacencias (usando conjuntos)
		ArrayList<Set<Integer>> adj = new ArrayList<Set<Integer>>();
		for (int i=0; i<=n; i++) adj.add(new HashSet<Integer>());

		// Criar spanning tree para garantir conectividade
		boolean s[] = new boolean[n+1];
		s[1] = true;
		int a, b, reale = 0;	
		for (int i=1; i<n; i++) {
		    do {a = r.nextInt(n)+1;} while (s[a]);
		    do {b = r.nextInt(n)+1;} while (!s[b]);
		    s[a] = true;
		    adj.get(a).add(b);
		    adj.get(b).add(a);
		    reale++;
		}

		// Criar restantes arestas
		while (reale < e) {	    
		    do {
			a = r.nextInt(n) + 1;
			b = r.nextInt(n) + 1;
		    } while (a==b || adj.get(a).contains(b));
		    adj.get(a).add(b);
		    adj.get(b).add(a);
		    reale++;
		}
		
		
		//preenchendo matriz com zeros
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				matriz[i][j] = 0;
			}
		}
		
		//preenchendo a matriz de adjacencia
		for (int i=1; i<=n; i++)
		    for (int j : adj.get(i)) {
		    	matriz[i-1][j-1] = 1;
		    	matriz[j-1][i-1] = 1;
		    }
		
		//imprimindo em arquivo a matriz de adjacencia
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				 //System.out.print(matriz[i][j] + " ");
				 gravarArq.printf(matriz[i][j] + " ");
			}
			gravarArq.printf("\n");
			//System.out.println();
		}
		arq.close();
	}
}
