package lexico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReaderTxtInput {
	
	private static final int SIZE = 5;
	int[] bufferedRead;
	int pointer;
	
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
	private void reloadBuffer2() {
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
	
	private int readCaracterOfBuffer() {
		int ret = bufferedRead[pointer];
		incrementPointer();
		return ret;
	}
	public int readNextChar() {
		int c = readCaracterOfBuffer();
		System.out.println((char)c);
		return c;
	}
	
	public void back() {
		pointer--;
		if(pointer < 0) {
			pointer = (SIZE * 2) - 1;
			
		}
	}
}
