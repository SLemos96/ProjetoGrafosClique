package testing;

import cliqueSolver.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class Test4 
{
	    
	@Test
	public void test() 
	{
		/* Input graph:
		 * K_5
		 * Expected Result:
		 * Clique of size 5
		 * 
		 * Current Status:
		 * PASS
		 */
		
		CliqueSolver clique = new CliqueSolver("testInputs/graphTest4.txt");
		
		// Begin solving the problem
		// Iterate over the matrix, start at the highest clique size
		for (int i = clique.matrix.length; i >= 1; i--)
		{	
			if (clique.numOfDegreeOrHigher(i-1) >= i-1)
			{
				// Check for a complete clique of size i
				if (clique.checkClique(i))
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