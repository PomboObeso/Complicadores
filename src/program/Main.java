package program;

import lexico.Lexicon;
import lexico.Token;

public class Main {

	public static void main(String[] args) {
		String path = "/home/beelzebub/eclipse-workspace/compilador_tipo/input.txt";
		Lexicon lex = new Lexicon(path);
		Token t = null;
		
		while((t = lex.nextToken())!= null) {
			System.out.print(t);
		}
	}

}
