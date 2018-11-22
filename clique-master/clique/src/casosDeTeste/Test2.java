package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

public class Test2 
{
	    
	@Test
	public void test() 
	{
		/* Grafo de entrada:
		 * O (Único nó)
		 * Expected Result:
		 * Clique de tamanho 1
		 * 
		 * OK
		 */
		
		MaxClique clique = new MaxClique("testes/graphTest2.txt");
		
		for (int i = clique.matrizAdjacencia.length; i >= 1; i--)
		{	
			if (clique.encontraGrauMaior(i-1) >= i-1)
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