package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

public class Test1 
{
	    
	@Test
	public void test() 
	{
		/* Grafo de Entrada:
		 * O----O
		 * |    |
		 * |    |
		 * O----O
		 * Resultado esperado:
		 * Clique de tamanho 1
		 * 
		 * OK
		 */
		
		MaxClique clique = new MaxClique("testes/graphTest1.txt");
		
		for (int i = clique.matrizAdjacencia.length; i >= 1; i--)
		{	
			if (clique.encontraGrauMaior(i-1) >= i-1)
			{
				// Verificando se existe uma clique completa de tamanho i
				if (clique.verificaSubClique(i))
				{
					//Clique encontrada
					return;
				}
			}
		}
		fail();
		
	}
}