package cliqueMaxima;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MaxClique 
{
	private String nomeDoArquivo; // Nome do arquivo de entrada
	private Scanner scanner; // File Scanner
	public int matrizAdjacencia[][]; 	 // Matriz de adjacencia
	public int graus[]; 	 // Vetor de graus
	long fim = 0;
	long inicio = System.currentTimeMillis();
	
	/**
	 * Construtor MaxClique - Com um arquivo de entrada
	 * Utiliza automaticamente o grafo do arquivo de entrada
	 */
	public MaxClique()
	{
		// Cria matriz de adjac�ncia com base no grafo do arquivo
		iniciarMatriz("graph.txt");
		// Guarda os graus de cada v�rtice
		iniciarGraus();
		
		// Inicio da solu��o
		// Itera sobre matrizAdjacencia, inicia na clique de maior tamanho
		for (int i = matrizAdjacencia.length; i >= 1; i--, fim = 0, inicio = System.currentTimeMillis())
		{	
			// exemplo: Em um grafo de 5 n�s, sen�o cont�m 5 n�s de grau 4, siga para o pr�ximo grau inferior.
			if (encontraGrauMaior(i-1) >= i)
			{
				// Procura uma clique completa de tamanho i
				if (verificaSubClique(i))
				{
					// Ap�s encontrar, para de procurar
					return;
				}
				System.out.print("N�o existe uma clique de tamanho " + i + "...\n");
				fim = System.currentTimeMillis() - inicio;
				System.out.println("O la�o executou em " + fim + "ms\n");
			}
		}
	}
	
	/**
	 * Construtor MaxClique - Vers�o para testes
	 * Vers�o do construtor que recebe o grafo como par�metro de entrada
	 */
	public MaxClique(String graphInputFile)
	{
		// Cria matriz de adjac�ncia com base no grafo recebido
		iniciarMatriz(graphInputFile);
		// Guarda os graus de cada v�rtice
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
			// N�o foi poss�vel abrir o arquivo. Parar execu��o do programa e imprimir erro 
			System.out.println("N�o foi poss�vel abrir o arquivo de entrada.");
			System.out.println("Por favor, tente novamente.");
		}
	}
	
	/**
	 * Cria um vetor para armazenar os graus de cada v�rtice
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
	 * Encontra o n�mero de v�rtices que possuem o "grau" recebido como par�metro
	 */
	public int encontraGrauMaior(int grau)
	{
		int n_vertices = 0;
		for (int i = 0; i < matrizAdjacencia.length; i++)
		{
			if(graus[i] >= grau)
			{
				n_vertices++;
			}
		}
		return n_vertices;
	}
		
	/**
	 * Verifica se existe uma clique completa com o "tamanho" recebido como par�metro
	 */
	public boolean verificaSubClique(int tamanho) 
	{
		/* "vertices" guarda os v�rtices de grau maior ou igual a tamanho
		   exemplo: uma busca por uma clique de tamanho 4 n�o inclui v�rtices de grau 1, 
		   pois � imposs�vel que esses v�rtices fa�am parte da clique */
		System.out.println("Procurando por uma clique de tamanho " + tamanho + "...");
		inicio = System.currentTimeMillis();
		int vertices[] = new int[encontraGrauMaior(tamanho-1)];
		int cont = 0;
		// Usando "vertices", ou seja, v�rtices de grau maior ou igual a tamanho
		for (int i = 0; i < graus.length; i++)
		{
			if (graus[i] >= tamanho-1)
			{
				vertices[cont] = i;
				cont++;
			}
		}
		
		// Chamada de m�todo usado para verificar todas as adjac�ncias entre os v�rtices de v[]
		// Usa for�a bruta
		int[] res = new int[tamanho];
		return verificaAdjacencias(vertices, res, 0, 0, tamanho); // combination(v, tamanho);
	}
	
	/**
	 * Verifica se um dado grafo tem uma clique completa do "tamanho" recebido como par�metro
	 * @param vertices V�rtices do grafo
	 * @param tamanho O tamanho da clique completa que estamos buscando
	 * @return True se o grafo cont�m uma clique completa de "tamanho" recebido por par�metro
	 */
	private boolean verificaClique(int[] vertices, int tamanho)
	{
		boolean falhou = false;
		for (int i = 0; i < vertices.length-1; i++) // O ultimo v�rtice nunca precisa ser verificado
		{
			for (int j = i+1; j < vertices.length; j++)
			{
				// Impress�o para debugging
				/*System.out.println("vertices.length = " + vertices.length);
				System.out.println("i = " + i + ", j = " + j);
				System.out.println("vertices[i] = " + vertices[i]);
				System.out.println("vertices[j] = " + vertices[j]);
				System.out.println("matrizAdjacencia[vertices[i]][vertices[j]] = " + matrizAdjacencia[vertices[i]][vertices[j]]);*/
				if (matrizAdjacencia[vertices[i]][vertices[j]] == 0)
				{
					falhou = true;
				}
			}
			if (falhou)
			{
				break;
			}
		}
		
		if (falhou)
		{
			falhou = false;
		}
		else
		{
			// Encontrou a maior clique
			fim = System.currentTimeMillis() - inicio;
			System.out.println("A clique maxima eh de tamanho " + tamanho + ".");
			System.out.println("O la�o executou em " + fim + "ms\n");
			System.out.print("Vertices incluidos: " + (vertices[0]+1));
			for(int i = 1; i < vertices.length; i++)
			{
				System.out.print(", " + (vertices[i]+1));
			}
			return true;
		}
		return false;
	}
	
	/**
	 * M�todo recursivo que verifica todas as adjac�ncias dos v�rtices de entrada
	 * @param vertices Vetor de v�rtices
	 * @param res
	 * @param indiceAtual
	 * @param nivel
	 * @param r
	 * @return True if a clique of size r is found
	 */
	private boolean verificaAdjacencias(int[] vertices, int[] res, int indiceAtual, int nivel, int r) {
		// Check if combo found
		if (nivel == r)
        {
			// Verificando se � uma clique
        	if(verificaClique(res, r))
        	{
				// Sim, � uma clique
        		return true;
        	}
        	// N�o � uma clique
        	return false;
        }
        for (int i = indiceAtual; i < vertices.length; i++) 
        {
            res[nivel] = vertices[i];
            if (verificaAdjacencias(vertices, res, i+1, nivel+1, r))
            {
    			return true;
            }
            // Para evitar a impress�o duplicada
            if (i < vertices.length-1 && vertices[i] == vertices[i+1])
            {
                i++;
            }
        }
        // Clique de tamanho r n�o foi encontrada
        return false;
    }
	
	/**
	 * M�todo para impress�o de matriz
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
}