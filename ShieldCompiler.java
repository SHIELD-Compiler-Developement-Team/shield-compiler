import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ShieldCompiler {
	
	public static void compile(String st) {
		String cstatement = "javac -d "+getCleanClasspath()+" ";
		String[] jfs = clean();
		for(int i = 0; i < jfs.length; i++)
			cstatement+="\""+jfs[i]+"\" ";
		cstatement = cstatement.trim();
		String pdirs = new String(getClean(Settings.pconf.getProjectRoot()+"\\"));
		File cscript = new File(pdirs+"compile.bat");
		Vector<String> vec = new Vector<String>(1,1);
		vec.addElement("@echo off");
		vec.addElement("echo Compiling ... ");
		vec.addElement(cstatement);
		vec.addElement("echo+");
		vec.addElement("echo Compiling Done.");
		vec.addElement("echo Done > compile.lock");
		vec.addElement("pause > nul");
		StreamFileWriter sfw = new StreamFileWriter(cscript.getAbsolutePath(), vec);
		sfw.write();
		try {
			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "compile.bat");
			File dir = new File(pdirs);
			pb.directory(dir);
			Process p = pb.start();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		//lockProcess(pdirs+"compile.lock", "Waiting for compilation ...");
		//script.delete();
	}
	
	public static String[] clean() {
		String[] r = new String[Settings.pconf.getJavaFiles().size()];
		for(int i = 0; i < r.length; i++)
			r[i]=ShieldSwitch.oneEGH(Settings.pconf.getJavaFiles().elementAt(i), '\\');
		return r;
	}
	
	public static void lockProcess(String fname, String msg) {
		File f = (new File(fname));
		System.out.println(msg);
		while(f.exists()) {}
		f.delete();
	}
	
	public static String getClean(String st) {
		return ShieldSwitch.oneEGH(st, '\\');
	}
	
	public static String getCleanClasspath() {
		String r = "";
		String p = ShieldSwitch.oneEGH(Settings.pconf.getJBin(), '\\');
		p = replaceLast(p, '\\');
		for(int i = p.lastIndexOf("\\")+1; i < p.length(); i++)
			r+=p.charAt(i);
		return r;
	}
	
	private static String replaceLast(String st, char ch) {
		String r = "";
		if(st.charAt(st.length() - 1) != ch) return st;
		for(int i =0; i < st.length()-1; i++)
			r+=st.charAt(i);
		return r;
	}
	
}