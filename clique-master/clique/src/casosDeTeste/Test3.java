package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

public class Test3 
{
	    
	@Test
	public void test() 
	{
		/* Grafo de entrada:
		 * O O (Dois grafos desconectados)
		 * Resultado esperado
		 * Clique de tamanho 1
		 * 
		 * OK
		 * 
		 */
		
		MaxClique clique = new MaxClique("testes/graphTest3.txt");
		
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