package lexico;

public class Lexicon {
	ReaderTxtInput data;
	
	public Lexicon(String archive) {
		data = new ReaderTxtInput(archive);
	}
	
	public Token nextToken() {
		Token next = null;
		spaceAndComment();
		data.confirm();
		
		next = endOfAnalyse();
		if(next == null) {
			data.reset();
		}else {
			data.confirm();
			return next;
		}
		
		next = reservedWord();
		if(next == null) {
			data.reset();
		}else {
			data.confirm();
			return next;
		}
		
		next = identifier();
		if(next == null) {
			data.reset();
		}else {
			data.confirm();
			return next;
		}
		
		next = numbers();
		if(next == null) {
			data.reset();
		}else {
			data.confirm();
			return next;
		}
		
		next = OpArit();
		if(next == null) {
			data.reset();
		}else {
			data.confirm();
			return next;
		}
		
		next = opRelacional();
		if(next == null) {
			data.reset();
		}else {
			data.confirm();
			return next;
		}
		
		next = delimitador();
		if(next == null) {
			data.reset();
		}else {
			data.confirm();
			return next;
		}
		
		next = string();
		if(next == null) {
			data.reset();
		}else {
			data.confirm();
			return next;
		}
		
		System.err.println("Lexicon Error!");
		System.err.println(data.toString());
		
		return null;
	}
	
	private Token OpArit() { //check
		int readChar = data.readNextChar();
		char c = (char)readChar;
		
		if(c == '*') {
			return new Token(EnumTokens.OP_MULT,"*");
		}else if(c == '/') {
			return new Token(EnumTokens.OP_DIV,"/");
		}else if(c == '+') {
			return new Token(EnumTokens.OP_ADI,"+");
		}else if(c == '-') {
			return new Token(EnumTokens.OP_SUB, "-");
		}else {
			return null;
		}
	}
	private Token delimitador() { //check
		int readChar = data.readNextChar();
		char c = (char)readChar;
		if(c == ',') {
			return new Token(EnumTokens.DELIM,",");
		}else if(c == '(') {
			return new Token(EnumTokens.AB_PAR, "(");
		}else if(c == ')') {
			return new Token(EnumTokens.FC_PAR,")");
		}else if(c == '[') {
			return new Token(EnumTokens.AB_COL,"[");
		}else if(c == ']') {
			return new Token(EnumTokens.FC_COL,"]");
		}else if(c == ';'){
			return new Token(EnumTokens.TERMINAL,";");
		}else {
			return null;
		}
	}
	private Token opRelacional() { //check
		int readChar = data.readNextChar();
		char c = (char)readChar;
	
		if(c == '<') {
			c = (char)data.readNextChar();
			if(c == '=') {
				return new Token(EnumTokens.OP_MENORIG,"<=");
			}else {
				data.back();
				return new Token(EnumTokens.OP_MENOR,"<");
			}
		}else if(c == '>') {
			c = (char)data.readNextChar();
			if(c == '=') {
				return new Token(EnumTokens.OP_MAIORIG,">=");
			}else {
				data.back();
				return new Token(EnumTokens.OP_MAIOR,">");
			}
		}else if(c == '!') {
			c = (char)data.readNextChar();
			if(c == '=') {
				return new Token(EnumTokens.OP_RELDIFER,"!=");
			}else {
				data.back();
				return new Token(EnumTokens.OP_NEG,"!");
			}
		}else if(c == '=') {
			c = (char)data.readNextChar();
			if(c == '=') {
				return new Token(EnumTokens.OP_RELIGUAL,"==");
			}else {
				data.back();
				return new Token(EnumTokens.OP_ATR,"=");
			}
		}else {
			return null;
		}
	}
	private Token numbers() { //check
		int state = 1;
		while(true) {
			char c = (char) data.readNextChar();
			if(state == 1) {
				if(Character.isDigit(c)) {
					state = 2;
				}else {
					return null;
				}
			}else if(state == 2) {
				if(c == '.') {
					c = (char) data.readNextChar();
					if(Character.isDigit(c)) {
						state = 3;
					}else {
						return null;
					}
				}else if(!Character.isDigit(c)) {
					data.back();
					return new Token(EnumTokens.CTE_INT,data.getLexeme());
				}
			}else if(state == 3) {
				if(!Character.isDigit(c)) {
					data.back();
					return new Token(EnumTokens.CTE_FLT,data.getLexeme());
				}
			}
		}
	}
	private Token identifier() { //check
		int state = 1;
		while(true) {
			char c = (char) data.readNextChar();
			if(state == 1) {
				if(Character.isLowerCase(c)) {
					state = 2;
				}else {
					return null;
				}
			}else if(state == 2) {
				if(!Character.isLetterOrDigit(c)) {
					data.back();
					return new Token(EnumTokens.ID,data.getLexeme());
				}
			}
		}
	}
	private Token string() { //check
		int state = 1;
		while(true) {
			char c = (char) data.readNextChar();
			if(state == 1) {
				if(c == '\'') {
					state = 2;
				}else {
					return null;
				}
			}else if(state == 2) {
				if(c == '\n') {
					return null;
				}
				if (c=='\'') {
					return new Token(EnumTokens.CTE_CDP,data.getLexeme());
				}else if(c == '\\') {
					state = 3;
				}
			}else if(state == 3) {
				if(c == '\n') {
					return null;
				}else {
					state = 3;
				}
			}
		}
	}
	private void spaceAndComment() { //check
		int state = 1;
		while(true) {
			char ch = (char) data.readNextChar();
			if(state == 1) {
				if(Character.isWhitespace(ch)|| ch == ' ') {
					state = 2;
				}else if(ch == '#') {
					state = 3;
				}else {
					data.back();
					return;
				}
			} else if (state == 2) {
				if(ch == '#') {
					state = 3;
				}else if(!(Character.isWhitespace(ch)|| ch == ' ')) {
					data.back();
					return;
				}
			}else if (state == 3) {
				if(ch == '\n') {
					return;
				}
			}
		}
	}
	private Token reservedWord() { //check
		while(true) {
			char ch = (char) data.readNextChar();
			if(!Character.isLetter(ch)) {
				data.back();
				String word = data.getLexeme();
				if(word.equals("E")) {
					return new Token(EnumTokens.PR_E,word);
				}else if(word.equals("Ou")) {
					return new Token(EnumTokens.PR_OU,word);
				}else if(word.equals("Funcao")) {
					return new Token(EnumTokens.PR_FUNCAO,word);
				}else if(word.equals("Devolve")) {
					return new Token(EnumTokens.PR_DEVOLVE,word);
				}else if(word.equals("Se")) {
					return new Token(EnumTokens.PR_SE,word);
				}else if(word.equals("Porem")) {
					return new Token(EnumTokens.PR_POREM,word);
				}else if(word.equals("Enquanto")) {
					return new Token(EnumTokens.PR_ENQUANTO,word);
				}else if(word.equals("Repita")) {
					return new Token(EnumTokens.PR_REPITA,word);
				}else if(word.equals("Inteiro")) {
					return new Token(EnumTokens.PR_INTEIRO,word);
				}else if(word.equals("Flutuante")) {
					return new Token(EnumTokens.PR_FLUTUANTE,word);
				}else if(word.equals("ConjuntoDePalavras")) {
					return new Token(EnumTokens.PR_CONJUNTODEPALAVRAS,word);
				}else if(word.equals("Caracter")) {
					return new Token(EnumTokens.PR_CARACTER,word);
				}else if(word.equals("Booleano")) {
					return new Token(EnumTokens.PR_BOOLEANO,word);
				}else if(word.equals("Entrada")) {
					return new Token(EnumTokens.PR_ENTRADA,word);
				}else if(word.equals("Imprimir")) {
					return new Token(EnumTokens.PR_IMPRIMIR,word);
				}else if(word.equals("ImprimirNl")) {
					return new Token(EnumTokens.PR_IMPRIMIRNL,word);
				}else if(word.equals("Verdade")) {
					return new Token(EnumTokens.PR_VERDADE,word);
				}else if(word.equals("Mentira")) {
					return new Token(EnumTokens.PR_MENTIRA,word);
				}else if(word.equals("Nada")) {
					return new Token(EnumTokens.PR_NADA,word);
				}else if(word.equals("INICIO")) {
					return new Token(EnumTokens.PR_INICIO,word);
				}else if(word.equals("FIM")) {
					return new Token(EnumTokens.PR_FIM,word);
				}else if(word.equals("Vazio")) {
					return new Token(EnumTokens.PR_VAZIO,word);
				}else if(word.equals("Principal")) {
					return new Token(EnumTokens.PR_PRINCIPAL,word);
				}else {
					return null;
				}
			}
		}
	}
	private Token endOfAnalyse() { //check
		int charRead = data.readNextChar();
		if(charRead == -1) {
			return new Token(EnumTokens.Fim,"End of File");
		}else {
			return null;
		}
	}
}
