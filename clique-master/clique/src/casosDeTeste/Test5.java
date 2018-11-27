package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

import java.io.IOException;

public class Test5 
{
	    
	@Test
	public void test() throws IOException 
	{
		/* 25 Vértices e apenas 1 aresta
		 * 
		 * Resultado esperado:
		 * Clique de tamanho 2
		 * 
		 * OK
		 * 
		 */
		
		MaxClique clique = new MaxClique("testes/graphTest5.txt");
		
		for (int i = clique.matrizAdjacencia.length; i >= 1; i--)
		{	
			if (clique.encontraGrauMaior(i-1) >= i)
			{
				// Check for a complete clique of size i
				if (clique.verificaSubClique(i))
				{
					// Clique encontrada
					return;
				}
			}
		}
		
		fail();
	}
}