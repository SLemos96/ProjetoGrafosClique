package cliqueSolver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MaxClique 
{
	private String nomeDoArquivo; // Nome do arquivo de entrada
	private Scanner scanner; // File Scanner
	public int matrizAdjacencia[][]; 	 // Matriz de adjacencia
	public int graus[]; 	 // Vetor de graus
	
	/**
	 * Construtor MaxClique
	 * Automaticamente le grafo do arquivo de entrada
	 */
	public MaxClique()
	{
		long inicio = System.currentTimeMillis();
		long fim = 0;
		// Cria matriz de adjacência com base no grafo do arquivo
		iniciarMatriz("graph.txt");
		// Guarda os graus de cada vértice
		iniciarGraus();
		
		// Inicio da solução
		// Itera sobre matrizAdjacencia, inicia na clique de maior tamanho
		for (int i = matrizAdjacencia.length; i >= 1; i--, fim = 0, inicio = System.currentTimeMillis())
		{	
			// exemplo: Em um grafo de 5 nós, senão contém 5 nós de grau 4, siga para o próximo grau inferior.
			if (encontraGrauMaior(i-1) >= i)
			{
				// Procura uma clique completa de tamanho i
				if (verificaSubClique(i))
				{
					// Após encontrar, para de procurar
					return;
				}
				System.out.print("Não existe uma clique de tamanho " + i + "...\n");
				fim = System.currentTimeMillis() - inicio;
				System.out.println("O laço executou em " + fim + "ms\n");
			}
		}
	}
	
	/**
	 * CliqueSolver Constructor -- Testing Edition
	 * Test constructor, test cases graph everything manually
	 */
	public MaxClique(String graphInputFile)
	{
		// Get the graph in the form of an adjacency matrix
		iniciarMatriz(graphInputFile);
		// Get the degrees for each vertex
		iniciarGraus();
		
	}
	
	/**
	 * Set a 2D array that matches the input file
	 */
	@SuppressWarnings("resource")
	private void iniciarMatriz(String graphInputFile)
	{
		// Grab the input file
		// File needs to be in the folder just above where the code is located
		nomeDoArquivo = graphInputFile;
		
		try
		{
			scanner = new Scanner(new FileReader(nomeDoArquivo)).useDelimiter("\n");
		
			String row[] = scanner.next().split("\\W+");
				
			matrizAdjacencia = new int[row.length][row.length];
			
			int index = 0;
			
			for(int i = 0; i< row.length; i++)
				matrizAdjacencia[index][i] = Integer.parseInt(row[i]);

			while(scanner.hasNext())
			{
				index++;
				row = scanner.next().split("\\W+");
				for(int i = 0; i< row.length; i++)
					matrizAdjacencia[index][i] = Integer.parseInt(row[i]);
			}
			
			scanner.close();
		}
		
		catch (FileNotFoundException e)
		{
			// File does not exist, shut the program down with an error
			System.out.println("Oops! It looks like we couldn't find your input file.");
			System.out.println("Please read the documentation for more information.");
		}
	}
	
	/**
	 * Set an array of the degrees for each vertex
	 */
	private void iniciarGraus()
	{
		graus = new int[matrizAdjacencia.length];
		int deg = 0;
		for (int i = 0; i < matrizAdjacencia.length; i++) 
		{
			deg = 0;
			for (int j = 0; j < matrizAdjacencia.length; j++)
			{
				deg += matrizAdjacencia[i][j];
			}
			graus[i] = deg;
		}
	}
	
	/**
	 * Finds the number of vertices with the input degree or higher
	 * @param deg The degree
	 * @return The number of the vertices with the input degree or higher
	 */
	public int encontraGrauMaior(int deg)
	{
		int num = 0;
		for (int i = 0; i < matrizAdjacencia.length; i++)
		{
			if(graus[i] >= deg)
			{
				num++;
			}
		}
		return num;
	}
	
	/**
	 * Prints the input matrix
	 * Used for debugging
	 */
	@SuppressWarnings("unused")
	private void imprimeMatriz()
	{
		//print what's in Matrix
		for (int i = 0; i < matrizAdjacencia.length; i++)
		{
			for(int j = 0; j < matrizAdjacencia.length; j++)
			{
				System.out.print(matrizAdjacencia[i][j]);
			}
			System.out.print('\n');
		}
	}
	
	/**
	 * Check if there is a complete clique with the input size
	 * @param tamanho The number of nodes in the clique
	 * @return True if there is a complete clique with the input size
	 */
	public boolean verificaSubClique(int tamanho) 
	{
		/* Nodes used to store the nodes that fit the degree requirements
		   i.e. if we're looking for a clique of size 4, don't include nodes that only have 
		   a degree of 1 because there's no way it could be in the complete clique */
		System.out.println("Checking for clique of size " + tamanho + "...");
		int nodes[] = new int[encontraGrauMaior(tamanho-1)];
		int count = 0;
		// Get the previously discussed nodes that fit the degree requirements
		for (int i = 0; i < graus.length; i++)
		{
			if (graus[i] >= tamanho-1)
			{
				nodes[count] = i;
				count++;
			}
		}
		
		// Helper method called, used to check all of the combinations of the nodes array
		// Uses brute force, but cuts down on the number of nodes that need to be checked
		int[] res = new int[tamanho];
		return verificaAdjacencias(nodes, res, 0, 0, tamanho);//combination(nodes, tamanho);
	}
	
	/**
	 * Check if the given graph has a complete clique of size "size"
	 * @param nodes The nodes in the graph
	 * @param size The size of the complete clique we're looking for
	 * @return True if the graph contains a complete clique of size "size"
	 */
	private boolean verificaClique(int[] nodes, int size)
	{
		boolean failed = false;
		for (int i = 0; i < nodes.length-1; i++) // the last node NEVER needs to be checked
		{
			for (int j = i+1; j < nodes.length; j++)
			{
				// Debug print statements.
				/*System.out.println("nodes.length = " + nodes.length);
				System.out.println("i = " + i + ", j = " + j);
				System.out.println("nodes[i] = " + nodes[i]);
				System.out.println("nodes[j] = " + nodes[j]);
				System.out.println("matrix[nodes[i]][nodes[j]] = " + matrix[nodes[i]][nodes[j]]);*/
				if (matrizAdjacencia[nodes[i]][nodes[j]] == 0)
				{
					failed = true;
				}
			}
			if (failed)
			{
				break;
			}
		}
		
		if (failed)
		{
			failed = false;
		}
		else
		{
			// Found the largest clique
			System.out.println("The largest clique is of size " + size + ".");
			System.out.print("Nodes included: " + (nodes[0]+1));
			for(int i = 1; i < nodes.length; i++)
			{
				System.out.print(", " + (nodes[i]+1));
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Recursive helper method that checks for all of the combinations of the input nodes
	 * @param arr The array of nodes
	 * @param res
	 * @param indiceAtual
	 * @param nivel
	 * @param r
	 * @return True if a clique of size r is found
	 */
	private boolean verificaAdjacencias(int[] arr, int[] res, int indiceAtual, int nivel, int r) {
		// Check if combo found
		if (nivel == r)
        {
			// Check if the combination is a clique
        	if(verificaClique(res, r))
        	{
				// Success! Let's get out of here
        		return true;
        	}
        	// Fail, keep on chugging
        	return false;
        }
		// Combo not found, keep chugging
        for (int i = indiceAtual; i < arr.length; i++) 
        {
            res[nivel] = arr[i];
            if (verificaAdjacencias(arr, res, i+1, nivel+1, r))
            {
    			return true;
            }
            // Avoid printing duplicates
            if (i < arr.length-1 && arr[i] == arr[i+1])
            {
                i++;
            }
        }
        // Clique of size r was never found, get out of here
        return false;
    }
}