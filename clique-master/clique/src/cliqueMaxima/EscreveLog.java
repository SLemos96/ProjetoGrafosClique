package cliqueMaxima;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EscreveLog {
	public void geraLog(List<String> lista) throws IOException {
		// variáveis de apoio para escrever um arquivo txt
		FileWriter arq = new FileWriter("log.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		
		for(String i : lista) {
			gravarArq.printf(i);
			gravarArq.printf("\n");
		}
		
		arq.close();
	}
}
