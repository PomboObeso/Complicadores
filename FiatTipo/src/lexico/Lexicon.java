package lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Lexicon {
	TabelaHashDePalavras hash = new TabelaHashDePalavras();
	private char[] content;
	private int state;
	private int position;
	private int line,columm;
	private String lineTxt = " ";
	private BufferedReader reader;
	
	Token token;
	
	public Lexicon(String archive) {
		try {
			line = 1;
			columm = 1;
			reader = new BufferedReader(new FileReader(new File(archive)));
			nextLine();
			content = lineTxt.toCharArray();
			position = 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Token nextToken() {
		char currentChar;
		String lex = "";
		state = 0;
		while(true) {
			if(isEOF()) {
				if(nextLine()) {
					content = lineTxt.toCharArray();
				}else {
					return new Token(EnumTokens.EOF,"EOF",line,columm);
				}
			}
			currentChar = nextChar();
			
			switch(state) {
			case 0:
				if(isCharLower(currentChar)) {
					lex+=currentChar;
					state = 1;
				}else if(isDigit(currentChar)) {
					lex+=currentChar;
					state = 3; 
				}else if(isSpaceWhite(currentChar)) {
					state = 0;
				}else if(isOperator(currentChar)) {
					lex+=currentChar;
					state = 7;
				}else if(currentChar == '\'') {
					lex+=currentChar;
					state = 8;
				}else if(isCharUpper(currentChar)) {
					lex += currentChar;
					state = 11;
				}else if(currentChar == '#') {
					state = 13;
				}else if(currentChar == '+') {
					lex+=currentChar;
					columm++;
					return new Token(EnumTokens.OP_ADI,lex,line,columm);
				}else if(currentChar == '-') {
					lex+=currentChar;
					columm++;
					return new Token(EnumTokens.OP_SUB,lex,line,columm);
				}else if(currentChar == '*') {
					lex+=currentChar;
					columm++;
					return new Token(EnumTokens.OP_MULT,lex,line,columm);
				}else if(currentChar == '/') {
					lex+=currentChar;
					columm++;
					return new Token(EnumTokens.OP_DIV,lex,line,columm);
				}else if(currentChar == '%') {
					lex+=currentChar;
					columm++;
					return new Token(EnumTokens.OP_RES,lex,line,columm);
				}else if(currentChar == '(') {
					lex += currentChar;
					columm++;
					return new Token(EnumTokens.AB_PAR,lex,line,columm);
				}else if(currentChar == ')') {
					lex += currentChar;
					columm++;
					return new Token(EnumTokens.FC_PAR,lex,line,columm);
				}else if(currentChar == '[') {
					lex += currentChar;
					columm++;
					return new Token(EnumTokens.AB_COL,lex,line,columm);
				}else if(currentChar == ']') {
					lex += currentChar;
					columm++;
					return new Token(EnumTokens.FC_COL,lex,line,columm);
				}else if(currentChar == ';') {
					lex+=currentChar;
					columm++;
					return new Token(EnumTokens.TERMINAL,lex,line,columm);
				}else if(currentChar == ',') {
					lex+=currentChar;
					columm++;
					return new Token(EnumTokens.DELIM,lex,line,columm);
				}else if(currentChar == '~') {
					lex += currentChar;
					columm++;
					return new Token(EnumTokens.OP_NEGUN,lex,line,columm);
				}else if(currentChar == '&') {
					lex += currentChar;
					columm++;
					return new Token(EnumTokens.OP_CONC,lex,line,columm);
				}else {
					new Token(EnumTokens.ER_SYM,lex,line,columm);
				}
				break;
			case 1:
				 if(isCharLower(currentChar) || isCharUpper(currentChar) || isDigit(currentChar)) {
					 lex+= currentChar;
				 }else if(isSpaceWhite(currentChar) || isOperator(currentChar) || !Character.isLetterOrDigit(currentChar)) {
					 back();
					 state = 2;
				 }else {
					 columm++;
					 new Token(EnumTokens.ER_ID,lex,line,columm);
				 }
				 break;
			case 2:
				back();
				columm++;
				return new Token(EnumTokens.ID,lex,line,columm);
			case 3:
				if(isDigit(currentChar)) {
					lex += currentChar;
				} else if(currentChar == '.') {
					lex += currentChar;
					state = 4;
					columm++;
				} else if(!Character.isLetterOrDigit(currentChar) || isSpaceWhite(currentChar) || isOperator(currentChar)) {
					back();
					state = 5;
				} else {
					columm++;
					new Token(EnumTokens.ER_NUM,lex,line,columm);
				}
				break;
			case 4:
                if(isDigit(currentChar)) {
                    lex += currentChar;
                } else if(currentChar == '.') {
                    columm++;
                    new Token(EnumTokens.ER_NUM, lex,line,columm);
                } else if(!Character.isLetterOrDigit(currentChar) || isSpaceWhite(currentChar) || isOperator(currentChar)) {
                    back();
                    state = 6;
                } else {
                    columm++;
                    new Token(EnumTokens.ER_NUM, lex,line,columm);
                }
                break;
			case 5:
				back();
				columm++;
				return new Token(EnumTokens.CTE_INT,lex,line,columm); 
			case 6:
				back();
				columm++;
				return new Token(EnumTokens.CTE_FLT,lex,line,columm);
			case 7:
				back(); //Volta duas vezes para verficar as tuplas dos operadores relacionais
				back();
				currentChar = nextChar();
				if(currentChar == '>') {
					currentChar = nextChar();
					if(currentChar == '=') {
						lex += currentChar;
						columm++;
						return new Token(EnumTokens.OP_MAIORIG,lex,line,columm);
					}else {
						back();
						columm++;
						return new Token(EnumTokens.OP_MAIOR,lex,line,columm);
					}
				}else if(currentChar == '<') {
					currentChar = nextChar();
					if(currentChar == '=') {
						lex += currentChar;
						columm++;
						return new Token(EnumTokens.OP_MENORIG,lex,line,columm);
					}else {
						back();
						columm++;
						return new Token(EnumTokens.OP_MENOR,lex,line,columm);
					}
				}else if(currentChar == '=') {
					currentChar = nextChar();
					if(currentChar == '=') {
						lex+=currentChar;
						columm++;
						return new Token(EnumTokens.OP_RELIGUAL,lex,line,columm);
					}else {
						back();
						columm++;
						return new Token(EnumTokens.OP_ATR,lex,line,columm);
					}
				}else if(currentChar == '!') {
					currentChar = nextChar();
					if(currentChar == '=') {
						lex+=currentChar;
						columm++;
						return new Token(EnumTokens.OP_RELDIFER,lex,line,columm);
					}else {
						back();
						columm++;
						return new Token(EnumTokens.OP_NEG,lex,line,columm);
					}
				}else {
					columm++;
					new Token(EnumTokens.ER_SYM,lex,line,columm);
				}
			case 8:
				if(currentChar >= (char)32 && currentChar <= (char) 126) {
					lex+=currentChar;
					currentChar = nextChar();
					if(currentChar == '\'') {
						lex += currentChar;
						state = 9;
					}else {
						back();
						state = 10;
					}
				}else {
					columm++;
					new Token(EnumTokens.ER_CHR,lex,line,columm);
				}
				break;
			case 9:
				back();
				columm++;
				return new Token(EnumTokens.CTE_CHR,lex,line,columm);
			case 10:
				if(currentChar >= (char)32 && currentChar <= (char) 126) {
					lex += currentChar;
					if(currentChar == '\'') {
						columm++;
						return new Token(EnumTokens.CTE_CDP,lex,line,columm);
					}
				}else {
					columm++;
					System.out.println("Debug Last: "+ lex);
					new Token(EnumTokens.ER_CHR,lex,line,columm);
				}
				break;
			case 11:
                if(isCharLower(currentChar)) {
                    lex += currentChar;
                } else if(!isCharLower(currentChar) || isSpaceWhite(currentChar)) {
                    back();
                    state = 12;
                }
                break;
			case 12:
				back();
				if(hash.palavraReservada.get(lex) != null) {
					columm++;
					return new Token(hash.palavraReservada.get(lex),lex,line,columm);
				}else {
					columm++;
					return new Token(EnumTokens.ER_PR,lex,line,columm); 
				}
			case 13:
				if(isSpaceWhite(currentChar)) {
					state = 0;
				}
			}
		}
	}
	
	private boolean isDigit(char ch) {
		return Character.isDigit(ch);
	}
	private boolean isCharUpper(char ch) {
		return Character.isUpperCase(ch);
	}
    private boolean isCharLower(char ch) {
        return Character.isLowerCase(ch);
    }
    private boolean isOperator(char ch) {
        return ch == '>' || ch == '<' || ch == '=' || ch == '!';
    }
    private boolean isSpaceWhite(char ch) {
        return Character.isWhitespace(ch) || ch == '\t' || ch == '\n' || ch == '\r' || ch == '\f' || ch == '\0' || ch == '\b';
    }
    private char nextChar() {
        return content[position++];
    }
    private boolean isEOF() {
        return position == content.length;
    }
    private void back() {
        position--;
    }
    
    //Metodo auxiliar para passar linha
    
    public void printCodeLine(String content) {
        String format = "%4d  %s";
        System.out.println(String.format(format, line, content));
    }
    
    private boolean nextLine() {
        String contentTemp = " ";
        try {
            contentTemp = reader.readLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(contentTemp != null) {
            lineTxt = contentTemp;
            printCodeLine(lineTxt);
            lineTxt += " ";
            line++;
            position = 0;
            columm = 0;

            return true;
        }
        return false;
    }
}
