package program;

import exceptions.FTExceptions;
import lexico.Lexicon;
import lexico.Token;

public class Main {

	public static void main(String[] args) {
		Lexicon lex = new Lexicon(args[0]);
		Token tk = null;
		try {
			do {
				tk = lex.nextToken();
				if(tk != null) {
					System.out.println(tk);
				}
			}while(tk.lexema != "EOF");
		}catch(FTExceptions ft) {
			System.out.println("Erro Lexico:"+ft.getMessage());
		}catch(Exception ex) {
			System.out.println("Erro comum");
		}
	}

}
