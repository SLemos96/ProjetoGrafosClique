package casosDeTeste;

import org.junit.Test;

import cliqueMaxima.*;

import static org.junit.Assert.*;

public class Test5 
{
	    
	@Test
	public void test() 
	{
		/* Input graph:
		 * O----O----O
		 *      | \  |
		 *      |  \ |
		 *      O----O----O
		 *           | \/ |
		 *           | /\ |
		 *           O----O
		 * 
		 * Expected Result:
		 * Clique of Size 4
		 * 
		 * Current Status:
		 * PASS
		 */
		
		MaxClique clique = new MaxClique("testInputs/graphTest5.txt");
		
		// Begin solving the problem
		// Iterate over the matrix, start at the highest clique size
		for (int i = clique.matrizAdjacencia.length; i >= 1; i--)
		{	
			if (clique.encontraGrauMaior(i-1) >= i)
			{
				// Check for a complete clique of size i
				if (clique.verificaSubClique(i))
				{
					// Clique found
					// Application architecture does not allow for checking the clique nodes
					// Really should be refactored
					return;
				}
			}
		}
		
		fail();
	}
}