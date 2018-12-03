package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

import java.io.IOException;

public class Test1 
{
	    
	@Test
	public void test() throws IOException 
	{
		/* Grafo de Entrada:
		 * O----O
		 * |    |
		 * |    |
		 * O----O
		 * Resultado esperado:
		 * Clique de tamanho 2
		 * 
		 * OK
		 */
		
		MaxClique clique = new MaxClique("testes/graphTest1.txt");
		
		for (int i = clique.matrizAdjacencia.length; i >= 1; i--)
		{	
			if (clique.encontraMaiorGrau(i-1) >= i-1)
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