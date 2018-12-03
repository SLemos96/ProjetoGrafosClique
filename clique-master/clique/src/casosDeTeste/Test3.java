package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

import java.io.IOException;

public class Test3 
{
	    
	@Test
	public void test() throws IOException 
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
			if (clique.encontraMaiorGrau(i-1) >= i-1)
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