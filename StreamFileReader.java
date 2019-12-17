

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class StreamFileReader {

	private String fname;
	
	public StreamFileReader(String st) {
		fname = st;
	}
	
	public Vector<String> read() {
		Vector<String> vec = new Vector<String>(1,1);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(fname)));
			while(true) {
				String st = br.readLine();
				if(st == null) break;
				vec.addElement(st);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vec;
	}
	
	public String singleRead() {
		String s = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(fname)));
			s = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
}
