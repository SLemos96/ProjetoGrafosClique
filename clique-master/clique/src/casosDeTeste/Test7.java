package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

import java.io.IOException;

public class Test7 
{
	    
	@Test
	public void test() throws IOException 
	{
		/* Grafo de entrada:
		 * K_15
		 * Resultado esperado:
		 * Clique de tamanho 15
		 * 
		 * OK
		 */
		
		MaxClique clique = new MaxClique("testes/graphTest7.txt");
		
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