package lexico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReaderTxtInput {
	InputStream is;
	
	public ReaderTxtInput(String archive) {
		try {
			is = new FileInputStream(new File(archive));
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		}
	}
	
	public int readNextChar() {
		int ret;
		try {
			ret = is.read();
			System.out.println((char)ret);
			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
}
