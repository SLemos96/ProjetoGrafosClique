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
	 * Construtor MaxClique - Com um arquivo de entrada
	 * Utiliza automaticamente o grafo do arquivo de entrada
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
	 * Construtor MaxClique - Versão para testes
	 * Versão do construtor que recebe o grafo como parâmetro de entrada
	 */
	public MaxClique(String graphInputFile)
	{
		// Cria matriz de adjacência com base no grafo recebido
		iniciarMatriz(graphInputFile);
		// Guarda os graus de cada vértice
		iniciarGraus();
		
	}
	
	/**
	 * Cria uma matriz com base no grafo do arquivo de entrada, essa matriz vai armazenar as adjacencias posteriormente
	 */
	@SuppressWarnings("resource")
	private void iniciarMatriz(String graphInputFile)
	{
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
			// Não foi possível abrir o arquivo. Parar execução do programa e imprimir erro 
			System.out.println("Não foi possível abrir o arquivo de entrada.");
			System.out.println("Por favor, tente novamente.");
		}
	}
	
	/**
	 * Cria um vetor para armazenar os graus de cada vértice
	 */
	private void iniciarGraus()
	{
		graus = new int[matrizAdjacencia.length];
		int gr = 0;
		for (int i = 0; i < matrizAdjacencia.length; i++) 
		{
			gr = 0;
			for (int j = 0; j < matrizAdjacencia.length; j++)
			{
				gr += matrizAdjacencia[i][j];
			}
			graus[i] = gr;
		}
	}
	
	/**
	 * Encontra o número de vértices que possuem o grau recebido como parâmetro
	 */
	public int encontraGrauMaior(int grau)
	{
		int vertices = 0;
		for (int i = 0; i < matrizAdjacencia.length; i++)
		{
			if(graus[i] >= grau)
			{
				vertices++;
			}
		}
		return vertices;
	}
	
	/**
	 * Método para impressão de matriz
	 * Usado para debugging
	 */
	@SuppressWarnings("unused")
	private void imprimeMatriz()
	{
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
	 * Verifica se existe uma clique completa com o tamanho recebido como parâmetro
	 */
	public boolean verificaSubClique(int tamanho) 
	{
		/* nodes guarda os vértices de grau maior ou igual a tamanho
		   exemplo: uma busca por uma clique de tamanho 4 não inclui vértices de grau 1, 
		   pois é impossível que esses vértices façam parte da clique */
		System.out.println("Procurando por uma clique de tamanho " + tamanho + "...");
		int nodes[] = new int[encontraGrauMaior(tamanho-1)];
		int count = 0;
		// Usando nodes, ou seja, vértices de grau maior ou igual a tamanho
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
            // Para evitar a impressão duplicada
            if (i < arr.length-1 && arr[i] == arr[i+1])
            {
                i++;
            }
        }
        // Clique de tamanho r não foi encontrada
        return false;
    }
}