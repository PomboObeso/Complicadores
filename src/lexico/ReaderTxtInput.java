package lexico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReaderTxtInput {
	
	private static final int SIZE = 20;
	int[] bufferedRead;
	int pointer;
	int currentBuffer;
	int initLex;
	private String lexeme;
	
	InputStream is;
	
	public ReaderTxtInput(String archive) {
		try {
			is = new FileInputStream(new File(archive));
			startBuffer();
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		}
	}
	
	private void startBuffer() {
		currentBuffer = 2;
		initLex = 0;
		lexeme = "";
		bufferedRead = new int[SIZE * 2];
		pointer = 0;
		reloadBuffer1();
	}
	
	private void incrementPointer() {
		pointer++;
		if(pointer == SIZE) {
			reloadBuffer2();
		} else if(pointer == SIZE * 2) {
			reloadBuffer1();
			pointer = 0;
		}
	}
	
	private void reloadBuffer1() {
		if(currentBuffer == 2) {

			currentBuffer = 1;
		
			for(int i = 0; i < SIZE; i++) {
				try {
					bufferedRead[i] = is.read();
					if(bufferedRead[i] == -1) {
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void reloadBuffer2() {
		if(currentBuffer == 1) {
			
			currentBuffer = 2;
		
			for(int i = SIZE; i < SIZE * 2; i++) {
				try {
					bufferedRead[i] = is.read();
					if(bufferedRead[i] == -1) {
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private int readCaracterOfBuffer() {
		int ret = bufferedRead[pointer];
		//System.out.println(this);
		incrementPointer();
		return ret;
	}
	public int readNextChar() {
		int c = readCaracterOfBuffer();
		lexeme += (char) c;
		return c;
	}
	
	public void back() {
		pointer--;
		lexeme = lexeme.substring(0, lexeme.length() - 1);
		if(pointer < 0) {
			pointer = (SIZE * 2) - 1;			
		}
	}
	
	public void reset() {
		pointer = initLex;
		lexeme = "";
	}
	
	public void confirm() {
		initLex = pointer;
		lexeme = "";
	}
	
	public String getLexeme() {
		return lexeme;
	}
	
	@Override
	
	public String toString() {
		String tks = "Buffer:[";
		for(int i : bufferedRead) {
			char c = (char) i;
			if(Character.isWhitespace(c)) {
				tks += ' ';
			}else {
				tks += (char) i;
			}
		}
		tks += "]\n";
		tks += "         ";
		for(int i = 0; i < SIZE * 2; i++) {
			if(i == initLex && i == pointer) {
				tks += "#";
			}else if(i == initLex) {
				tks += "^";
			}else if(i == pointer) {
				tks += "*";
			}else {
				tks += " ";
			}
		}
		return tks;
	}
}
