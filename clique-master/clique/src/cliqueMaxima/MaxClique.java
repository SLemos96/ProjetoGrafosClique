package cliqueMaxima;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaxClique 
{
	private String nomeDoArquivo; // Nome do arquivo de entrada
	private Scanner scanner; // File Scanner
	public int matrizAdjacencia[][]; 	 // Matriz de adjacencia
	public int graus[]; 	 // Vetor de graus
	long fimTotal = 0;
	long inicioTotal = System.currentTimeMillis();
	long fimLaco = 0;
	long inicioLaco = System.currentTimeMillis();
	List<String> dadosRelatorio = new ArrayList<>();
	EscreveLog registro = new EscreveLog();
	
	/**
	 * Construtor MaxClique - Com um arquivo de entrada
	 * Utiliza automaticamente o grafo do arquivo de entrada
	 * @throws IOException 
	 */
	public MaxClique() throws IOException
	{
		// Cria matriz de adjac�ncia com base no grafo do arquivo
		iniciarMatriz("graph.txt");
		// Guarda os graus de cada v�rtice
		iniciarGraus();
		
		System.out.println("O relogio est� iniciando sua contagem:" + "0ms\n");
		dadosRelatorio.add("O relogio est� iniciando sua contagem:" + "0ms\n");
		
		// Inicio da solu��o
		// Itera sobre matrizAdjacencia, inicia na clique de maior tamanho
		for (int i = matrizAdjacencia.length; i >= 1; i--, fimLaco = 0, inicioLaco = System.currentTimeMillis())
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
				dadosRelatorio.add("N�o existe uma clique de tamanho " + i);
				fimLaco = System.currentTimeMillis() - inicioLaco;
				System.out.println("O la�o executou em " + fimLaco + "ms\n");
				dadosRelatorio.add("O la�o executou em " + fimLaco + "ms\n");
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
		
			String linhas[] = scanner.next().split("\\W+");
				
			matrizAdjacencia = new int[linhas.length][linhas.length];
			
			int index = 0;
			
			for(int i = 0; i< linhas.length; i++)
				matrizAdjacencia[index][i] = Integer.parseInt(linhas[i]);

			while(scanner.hasNext())
			{
				index++;
				linhas = scanner.next().split("\\W+");
				for(int i = 0; i< linhas.length; i++)
					matrizAdjacencia[index][i] = Integer.parseInt(linhas[i]);
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
	 * @throws IOException 
	 */
	public boolean verificaSubClique(int tamanho) throws IOException 
	{
		/* "vertices" guarda os v�rtices de grau maior ou igual a tamanho
		   exemplo: uma busca por uma clique de tamanho 4 n�o inclui v�rtices de grau 1, 
		   pois � imposs�vel que esses v�rtices fa�am parte da clique */
		System.out.println("Procurando por uma clique de tamanho " + tamanho + "...");
		dadosRelatorio.add("Procurando por uma clique de tamanho " + tamanho + "...");
		inicioLaco = System.currentTimeMillis();
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
	 * @throws IOException 
	 */
	private boolean verificaClique(int[] vertices, int tamanho) throws IOException
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
			fimLaco = System.currentTimeMillis() - inicioLaco;
			System.out.println("A clique maxima eh de tamanho " + tamanho + ".");
			dadosRelatorio.add("A clique maxima eh de tamanho " + tamanho + ".");
			
			System.out.println("O la�o executou em " + fimLaco + "ms\n");
			dadosRelatorio.add("O la�o executou em " + fimLaco + "ms\n");
			
			fimTotal = System.currentTimeMillis() - inicioTotal;
			System.out.println("O programa executou em " + fimTotal + "ms\n");
			dadosRelatorio.add("O programa executou em " + fimTotal + "ms\n");
			
			//o +1 representa a soma pq a contagem no vetor come�a em 0 e no grafo come�a em 1
			System.out.print("Vertices incluidos: " + (vertices[0]+1));
			String auxiliar = "Vertices incluidos: " + (vertices[0]+1);
			for(int i = 1; i < vertices.length; i++)
			{
				System.out.print(", " + (vertices[i]+1));
				auxiliar += ", " + (vertices[i]+1);
				//dadosRelatorio.add(", " + (vertices[i]+1));
			}
			dadosRelatorio.add(auxiliar);
			registro.geraLog(dadosRelatorio);
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
	 * @param tamanho
	 * @return True if a clique of size r is found
	 * @throws IOException 
	 */
	private boolean verificaAdjacencias(int[] vertices, int[] res, int indiceAtual, int nivel, int tamanho) throws IOException {
		// caso base da recurs�o
		if (nivel == tamanho)
        {
			// Verificando se � uma clique
        	if(verificaClique(res, tamanho))
        	{
				// Sim, � uma clique
        		return true;
        	}
        	// N�o � uma clique
        	return false;
        }
        for (int i = indiceAtual; i < vertices.length; i++) //la�o de verifica��o
        {
            res[nivel] = vertices[i]; //preenche recursivamente o vetor res com os vertices at� chegar no tamanho;
            if (verificaAdjacencias(vertices, res, i+1, nivel+1, tamanho))
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