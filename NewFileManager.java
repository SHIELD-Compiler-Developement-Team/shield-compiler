import java.util.Vector;

public class NewFileManager {
	
	private String fn;
	
	private String p;
	
	private String pc;
	
	public NewFileManager(String fname, String path, String pck) {
		fn = fname;
		p = path;
		pc = pck;
	}
	
	public void create() {
		String[] contents = {
			"package "+pc+";",
			"",
			"public class "+fn+" { ",
			"	",
			"}"
		};
		Vector<String> vcon = new Vector<String>(1,1);
		for(int i = 0; i < contents.length; i++)
			vcon.addElement(contents[i]);
		String temp = p+"\\"+fn+".java";
		StreamFileWriter sfw = new StreamFileWriter(ShieldSwitch.oneEGH(temp, '\\'), vcon);
		sfw.write();
	}
}