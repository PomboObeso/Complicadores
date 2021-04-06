package lexico;

public class Token {
	
	public EnumTokens categoria;
	public String lexema;
	public int line, columm;

	public Token() {
		
	}
	
	public Token(EnumTokens categoria,String lexema, int line, int columm) {
		this.categoria = categoria;
		this.lexema = lexema;
		this.line = line;
		this.columm = columm;
	}
	
	
	
	
	@Override	
	public String toString() {
		String format = " [%04d,%04d] (%04d,%20s) {%s} ";
		return String.format(format, line-1,columm,categoria.ordinal(),categoria.toString(),lexema);
	}
	
	
}
