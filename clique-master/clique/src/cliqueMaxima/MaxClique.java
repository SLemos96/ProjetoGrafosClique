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
		// Cria matriz de adjacência com base no grafo do arquivo
		iniciarMatriz("graph.txt");
		// Guarda os graus de cada vértice
		iniciarGraus();
		
		System.out.println("O relogio está iniciando sua contagem:" + "0ms\n");
		dadosRelatorio.add("O relogio está iniciando sua contagem:" + "0ms\n");
		
		// Inicio da solução
		// Itera sobre matrizAdjacencia, inicia na clique de maior tamanho
		for (int i = matrizAdjacencia.length; i >= 1; i--, fimLaco = 0, inicioLaco = System.currentTimeMillis())
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
				dadosRelatorio.add("Não existe uma clique de tamanho " + i);
				fimLaco = System.currentTimeMillis() - inicioLaco;
				System.out.println("O laço executou em " + fimLaco + "ms\n");
				dadosRelatorio.add("O laço executou em " + fimLaco + "ms\n");
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
	 * Encontra o número de vértices que possuem o "grau" recebido como parâmetro
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
	 * Verifica se existe uma clique completa com o "tamanho" recebido como parâmetro
	 * @throws IOException 
	 */
	public boolean verificaSubClique(int tamanho) throws IOException 
	{
		/* "vertices" guarda os vértices de grau maior ou igual a tamanho
		   exemplo: uma busca por uma clique de tamanho 4 não inclui vértices de grau 1, 
		   pois é impossível que esses vértices façam parte da clique */
		System.out.println("Procurando por uma clique de tamanho " + tamanho + "...");
		dadosRelatorio.add("Procurando por uma clique de tamanho " + tamanho + "...");
		inicioLaco = System.currentTimeMillis();
		int vertices[] = new int[encontraGrauMaior(tamanho-1)];
		int cont = 0;
		// Usando "vertices", ou seja, vértices de grau maior ou igual a tamanho
		for (int i = 0; i < graus.length; i++)
		{
			if (graus[i] >= tamanho-1)
			{
				vertices[cont] = i;
				cont++;
			}
		}
		
		// Chamada de método usado para verificar todas as adjacências entre os vértices de v[]
		// Usa força bruta
		int[] res = new int[tamanho];
		return verificaAdjacencias(vertices, res, 0, 0, tamanho); // combination(v, tamanho);
	}
	
	/**
	 * Verifica se um dado grafo tem uma clique completa do "tamanho" recebido como parâmetro
	 * @param vertices Vértices do grafo
	 * @param tamanho O tamanho da clique completa que estamos buscando
	 * @return True se o grafo contém uma clique completa de "tamanho" recebido por parâmetro
	 * @throws IOException 
	 */
	private boolean verificaClique(int[] vertices, int tamanho) throws IOException
	{
		boolean falhou = false;
		for (int i = 0; i < vertices.length-1; i++) // O ultimo vértice nunca precisa ser verificado
		{
			for (int j = i+1; j < vertices.length; j++)
			{
				// Impressão para debugging
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
			
			System.out.println("O laço executou em " + fimLaco + "ms\n");
			dadosRelatorio.add("O laço executou em " + fimLaco + "ms\n");
			
			fimTotal = System.currentTimeMillis() - inicioTotal;
			System.out.println("O programa executou em " + fimTotal + "ms\n");
			dadosRelatorio.add("O programa executou em " + fimTotal + "ms\n");
			
			//o +1 representa a soma pq a contagem no vetor começa em 0 e no grafo começa em 1
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
	 * Método recursivo que verifica todas as adjacências dos vértices de entrada
	 * @param vertices Vetor de vértices
	 * @param res
	 * @param indiceAtual
	 * @param nivel
	 * @param tamanho
	 * @return True if a clique of size r is found
	 * @throws IOException 
	 */
	private boolean verificaAdjacencias(int[] vertices, int[] res, int indiceAtual, int nivel, int tamanho) throws IOException {
		// caso base da recursão
		if (nivel == tamanho)
        {
			// Verificando se é uma clique
        	if(verificaClique(res, tamanho))
        	{
				// Sim, é uma clique
        		return true;
        	}
        	// Não é uma clique
        	return false;
        }
        for (int i = indiceAtual; i < vertices.length; i++) //laço de verificação
        {
            res[nivel] = vertices[i]; //preenche recursivamente o vetor res com os vertices até chegar no tamanho;
            if (verificaAdjacencias(vertices, res, i+1, nivel+1, tamanho))
            {
    			return true;
            }
            // Para evitar a impressão duplicada
            if (i < vertices.length-1 && vertices[i] == vertices[i+1])
            {
                i++;
            }
        }
        // Clique de tamanho r não foi encontrada
        return false;
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
}