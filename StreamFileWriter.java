

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class StreamFileWriter {
	
	private String fname;
	private Vector<String> inp;
	
	public StreamFileWriter(String st, Vector<String> data) {
		fname = ShieldSwitch.oneEGH(st, '\\');
		inp = data;
	}
	
	public void write() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fname)));
			for (int i = 0; i < inp.size(); i++) {
				bw.write(inp.elementAt(i));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
