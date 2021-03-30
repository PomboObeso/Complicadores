package lexico;

public class Lexicon {
	ReaderTxtInput data;
	
	public Lexicon(String archive) {
		data = new ReaderTxtInput(archive);
	}
	
	public Token nextToken() {
		int caracterRead = -1;
		
		while((caracterRead = data.readNextChar()) != -1) {
			char c = (char)caracterRead;
			if(c == ' ' ||c == '\n') {
				continue;
			}
			//Operadores Simples
			if(c == ',') {
				return new Token(EnumTokens.DELIM,",");
			}else if(c == '*') {
				return new Token(EnumTokens.OP_MULT,"*");
			}else if(c == '/') {
				return new Token(EnumTokens.OP_DIV,"/");
			}else if(c == '+') {
				return new Token(EnumTokens.OP_ADI,"+");
			}else if(c == '-') {
				return new Token(EnumTokens.OP_SUB, "-");
			}else if(c == '(') {
				return new Token(EnumTokens.AB_PAR, "(");
			}else if(c == ')') {
				return new Token(EnumTokens.FC_PAR,")");
			}else if(c == ';') {
				return new Token(EnumTokens.TERMINAL,";");
			}else if(c == '<') {
				c = (char)data.readNextChar();
				if(c == '=') {
					return new Token(EnumTokens.OP_MENORIG,"<=");
				}else {
					return new Token(EnumTokens.OP_MENOR,"<");
				}
			}else if(c == '>') {
				c = (char)data.readNextChar();
				if(c == '=') {
					return new Token(EnumTokens.OP_MAIORIG,">=");
				}else {
					return new Token(EnumTokens.OP_MAIOR,">");
				}
			}
			
		}
		return null;
	}
}
