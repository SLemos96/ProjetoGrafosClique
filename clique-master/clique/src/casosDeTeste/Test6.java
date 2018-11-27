package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

import java.io.IOException;

public class Test6 
{
	    
	@Test
	public void test() throws IOException 
	{
		/* Grafo de entrada:
		 * K_10
		 * Resultado esperado:
		 * Clique de tamanho 10
		 * 
		 * OK
		 */
		
		MaxClique clique = new MaxClique("testes/graphTest6.txt");
		
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