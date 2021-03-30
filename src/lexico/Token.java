package lexico;

public class Token {
	
	public EnumTokens identifiers;
	public String lexema;
	
	public Token(EnumTokens identifiers, String lexema) {
		this.identifiers = identifiers;
		this.lexema = lexema;
	} 
	
	@Override
	
	public String toString() {
		return "["+identifiers+","+lexema+"]";
	}
	
	
}
