import java.io.*;
import java.util.Vector;

public class NewProjectModule {
	
	public static void commandLineNewProject(String st) 
	{
		if(st.indexOf(' ') == -1) {
			System.out.println("\nNew eXtensible SHIELD Project Wizard");
			System.out.print("Project Name : ");
			String pname = Global.getInput();
			System.out.print("Source Root              [Java] : "+pname+"\\");
			String jsrc = Global.getInput();
			System.out.print("Binaries Root            [Java] : "+pname+"\\");
			String jbin = Global.getInput();
			System.out.print("Common Root Package Name [Java] : ");
			String proot = Global.getInput();
			newProject(pname, jsrc, jbin, proot);
		} else {
			String args = "";
			for(int i = st.indexOf(" ")+1; i < st.length(); i++) {
				args+=st.charAt(i);
			}
			System.out.println("\nNew eXtensible SHIELD Project Wizard");
			System.out.println("Project Name : "+args);
			System.out.print("Source Root              [Java] : "+args+"\\");
			String jsrc = Global.getInput();
			System.out.print("Binaries Root            [Java] : "+args+"\\");
			String jbin = Global.getInput();
			System.out.print("Common Root Package Name [Java] : ");
			String proot = Global.getInput();
			newProject(args, jsrc, jbin, proot);
		}
	}
	
	public static void newFile(String st)
	{
		String fname="";
		if(st.indexOf(" ") == -1) {
			System.out.println("\nNew Source File");
			System.out.print("Name: ");
			fname = Global.getInput();
		} else {
			for(int i = st.indexOf(" ")+1; i < st.length(); i++) {
				fname+=st.charAt(i);
			}
		}
		String cf = fname;
		fname+=".java";
		System.out.print("Package : "+Settings.pconf.getPRoot()+".");
		String pack = Global.getInput();
		String cp = pack;
		if(pack.equals("")) {
			pack = Settings.pconf.getPRoot();
			pack = pack.replace(".", "\\");
			(new File(Settings.pconf.getJSource()+"\\"+pack)).mkdirs();
		}
		else {
			Settings.pconf.newSubPackage(pack);
			pack = Settings.pconf.getPRoot()+"."+pack;
			pack = pack.replace(".", "\\");
			(new File(Settings.pconf.getJSource()+"\\"+pack)).mkdirs();
		}
		try {
			(new File(Settings.pconf.getJSource()+"\\"+pack+"\\"+fname)).createNewFile();
			NewFileManager nfm = new NewFileManager(cf, Settings.pconf.getJSource()+"\\"+pack, Settings.pconf.getPRoot()+"."+cp);
			nfm.create();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		// TODO Notepad++ Open Mark
		Settings.pconf.newJavaClass(Settings.pconf.getJSource()+"\\"+pack+"\\"+fname);
	}
	
	public static void newProject(String pname, String jsrc, String jbin, String proot) 
	{
		String tpath = Settings.path;
		tpath+=pname+"\\";
		(new File(tpath)).mkdir();
		(new File(tpath+jsrc)).mkdir();
		(new File(tpath+jbin)).mkdir();
		String proot2 = proot.replace(".", "\\");
		(new File(tpath+jsrc+"\\"+proot2)).mkdirs();
		tpath = "$DEF_PATH\\"+pname+"\\";
		Vector<String> vec = new Vector<String>(1,1);
		vec.addElement("PROJECT_NAME:"+pname);
		vec.addElement("PROJECT_PATH:"+tpath);
		vec.addElement("PROJECT_JAVA_SRC_PATH:"+(tpath+jsrc+"\\"));
		vec.addElement("PROJECT_JAVA_BIN_PATH:"+(tpath+jbin+"\\"));
		vec.addElement("PROJECT_JAVA_TRPACK:"+proot);
		StreamFileWriter srw = new StreamFileWriter(pname+".shieldproject", vec);
		srw.write();
	}
}