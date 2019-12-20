/*
 * Part of Rubidium Projects
 * General Public Licence v3.0, 2019
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

import java.io.File;

/**
* A class used to switch the input to process
* programs
*
*/
public class ShieldSwitch
{
	
	// Don't let anyone instantiate this class.
	private ShieldSwitch() {}
	
	// Actual process called by other classes
	public static void switchProcess(String st) 
	{
		/*
		 exit     - Terminate
		 dir / ls - Directory Listing
		 newp     - New Project
		 newf     - New File
		 loadp    - Load Project
		 compile  - Compiling the project
		*/
		if(st.equals("exit") || st.equals("end")) {
			Global.endProgram();
		}
		else if(st.startsWith("dir")) dirSwitch(st);
		else if(st.startsWith("ls")) lsSwitch(st);
		else if(st.startsWith("cd")) cdSwitch(st);
		else if(st.startsWith("newp")) NewProjectModule.commandLineNewProject(st);
		else if(st.startsWith("newf")) NewProjectModule.newFile(st);
		else if(st.startsWith("loadp")) loadProject(st);
		else if(st.startsWith("compile")) ShieldCompiler.compile(st);
	}
	
	// Analagous to "cd" for change directory
	private static void cdSwitch(String st) {
		// "cd" -> .. to previous directory
		if(st.contains("..")) {
			int nplaces = zeroED(st, '\\') + 2;
			String tpath = Settings.path;
			for(int i = 0; i < nplaces; i++) {
				int idxo = tpath.lastIndexOf('\\');
				if(idxo == -1) break;
				tpath = tpath.substring(0, idxo);
			}
			if(!tpath.endsWith("\\")) tpath+="\\";
			Settings.path = tpath;
			return;
		}
		
		// Forward Directory Move
		int indexd = st.indexOf(' ');
		String args = st.substring(indexd+1);
		File file = new File(Settings.path+args);
		if(!file.isDirectory()) {
			System.out.println("Invalid Directory Specification!!");
			return;
		}
		Settings.path = Settings.path+args+"\\";
	}		
	
	// Analagous to "dir" for directory listing
	private static void dirSwitch(String st) {
		System.out.println();
		
		// General File Listing
		if(st.equals("dir")) {
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				System.out.println(f[i].getName());
			}
			return;
		}
		
		// File Listing of a specific extension
		int indexd = st.indexOf(' ');
		String args = st.substring(indexd+1);
		if(args.equals("*")) {
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				System.out.println(f[i].getName());
			}
			return;
		}
		if(args.startsWith("*")) {
			String extension = args.substring(args.indexOf('.')+1);
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				if(f[i].getName().endsWith(extension))
					System.out.println(f[i].getName());
			}
			return;
		}
		
		// File Listing of a subfolder
		String tpath = Settings.path + "/" + args;
		File ndir= new File(tpath);
		if(!ndir.isDirectory() || !ndir.exists()) {
			System.out.println("Invalid Directory Specification!!");
			return;
		}
		File[] f = listFiles(tpath);
		for(int i = 0; i < f.length; i++) {
			System.out.println(f[i].getName());
		}
	}	
	
	// Analagous to "ls" for directory listing
	private static void lsSwitch(String st) {
		System.out.println();
		if(st.equals("ls")) {
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				System.out.println(f[i].getName());
			}
			return;
		}
		int indexd = st.indexOf(' ');
		String args = st.substring(indexd+1);
		if(args.equals("*")) {
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				System.out.println(f[i].getName());
			}
			return;
		}
		if(args.startsWith("*")) {
			String extension = args.substring(args.indexOf('.')+1);
			File[] f = listFiles(Settings.path);
			for(int i = 0; i < f.length; i++) {
				if(f[i].getName().endsWith(extension))
					System.out.println(f[i].getName());
			}
			return;
		}
		String tpath = Settings.path + "/" + args;
		File ndir= new File(tpath);
		if(!ndir.isDirectory() || !ndir.exists()) {
			System.out.println("Invalid Directory Specification!!");
			return;
		}
		File[] f = listFiles(tpath);
		for(int i = 0; i < f.length; i++) {
			System.out.println(f[i].getName());
		}
	}
	
	// Load Project to Memory
	private static void loadProject(String st) {
		String pname = st.substring(st.indexOf(" ")+1)+".shieldproject";
		String lp = (new ProjectConfigaration(pname)).getProjectRoot();
		if(!(new File(lp)).isDirectory()) {
			System.out.println("Project "+(new ProjectConfigaration(pname)).getName()+" not found !!");
			return;
		}
		Settings.pconf = new ProjectConfigaration(pname);
	}
	
	// Occurance of 
	public static int zeroED(String string, char c) 
	{
		int cd = 0;
		for(int i = 0; i < string.length(); i++) if(string.charAt(i) == c) cd++;
		return cd;
	}
	
	// Replace Multiple Simultaneous Occurances
	public static String oneEGH(String st, char ch) {
		String r=" ";
		for(int i = 0; i < st.length(); i++) {
			char tobe = st.charAt(i);
			char last = r.charAt(r.length()-1);
			if(tobe == last) continue;
			r+=tobe;
		}
		r=r.trim();
		return r;
	}
	
	// Internal Method to list files in a given path
	public static File[] listFiles(String path) {
		return (new File(path).listFiles());
	}
}