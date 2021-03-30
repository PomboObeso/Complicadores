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
			if(c == '=') {
				return new Token(EnumTokens.OP_ATR,"=");
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
			}
		}
		return null;
	}
}
