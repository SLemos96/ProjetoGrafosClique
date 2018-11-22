package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

public class Test5 
{
	    
	@Test
	public void test() 
	{
		/* Grafo de entrada:
		 * O----O----O
		 *      | \  |
		 *      |  \ |
		 *      O----O----O
		 *           | \/ |
		 *           | /\ |
		 *           O----O
		 * 
		 * Resultado esperado:
		 * Clique de tamanho 4
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