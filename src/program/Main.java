package program;

import exceptions.FTExceptions;
import lexico.Token;
import sintatico.Sintatico;

public class Main {

	public static void main(String[] args) {
		//Lexicon lex = new Lexicon("/home/beelzebub/eclipse-workspace/compilador_tipo/src/program/input.txt");
		Token tk = null;
		try {
			String path = "/home/beelzebub/eclipse-workspace/compilador_tipo/src/program/input.txt";
			Sintatico sint = new Sintatico(path);
					
		}catch(FTExceptions ft) {
			System.out.println("Erro Lexico:"+ft.getMessage());
		}catch(Exception ex) {
			System.out.println("Erro comum");
		}
	}

}
