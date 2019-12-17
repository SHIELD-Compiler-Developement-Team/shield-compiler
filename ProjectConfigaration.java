import java.util.*;

public class ProjectConfigaration
{
	private Vector<String> conf;
	
	private String pconf;
	
	private String pname;
	
	private String proot;
	
	private String jsrc;
	
	private String jsrct;
	
	private String jbin;
	
	private String jbint;
	
	private String packroot;
	
	private Vector<String> subpack;
	
	private Vector<String> jfiles;
	
	private String executable;
	
	public ProjectConfigaration(String conf) {
		pconf=conf;
		StreamFileReader srd = new StreamFileReader(conf);
		this.conf = srd.read();
		this.subpack = new Vector<String>(1,1);
		this.jfiles = new Vector<String>(1,1);
		parse();
	}
	
	public String getName() {
		return pname;
	}
	
	public String getProjectRoot() {
		return ShieldSwitch.oneEGH(proot, '\\');
	}
	
	public String getJSource() {
		return ShieldSwitch.oneEGH(jsrc, '\\');
	}
	
	public String getJBin() {
		return ShieldSwitch.oneEGH(jbin, '\\');
	}
	
	public String getPRoot() {
		return ShieldSwitch.oneEGH(packroot, '\\');
	}
	
	public Vector<String> getSubPackages() {
		return subpack;
	}
	
	public Vector<String> getJavaFiles() {
		return jfiles;
	}
	
	public String getExecutable() {
		return executable;
	}
	
	public void newJavaClass(String st) {
		jfiles.addElement(st);
	}
	
	public void newSubPackage(String st) {
		subpack.addElement(st);
	}
	
	public void parse() {
		String[] arr = new String[conf.size()];
		for(int i = 0; i < arr.length; i++)
			arr[i] = conf.elementAt(i);
		for(int i = 0; i < arr.length; i++)
		{
			String t = arr[i];
			if(t.startsWith("PROJECT_JAVA_SRC_PATH")) jsrct = t.substring(t.indexOf(":")+1);
			if(t.startsWith("PROJECT_JAVA_BIN_PATH")) jbint = t.substring(t.indexOf(":")+1);
		}
		for(int i = 0; i < arr.length; i++)
			arr[i] = arr[i].replace("$DEF_PATH", Settings.path);
		for(int i = 0; i < arr.length; i++) {
			String t = arr[i];
			if(t.startsWith("PROJECT_NAME")) pname = t.substring(t.indexOf(":")+1);
			if(t.startsWith("PROJECT_PATH")) proot = t.substring(t.indexOf(":")+1);
			if(t.startsWith("PROJECT_JAVA_SRC_PATH")) jsrc = t.substring(t.indexOf(":")+1);
			if(t.startsWith("PROJECT_JAVA_BIN_PATH")) jbin = t.substring(t.indexOf(":")+1);
			if(t.startsWith("PROJECT_JAVA_TRPACK")) packroot = t.substring(t.indexOf(":")+1);
			if(t.equals("<spackage>")) {
				Vector<String> sp = new Vector<String>(1,1);
				int j = 0;
				for(j = i+1; j < arr.length; j++) {
					if(arr[j].equals("</spackage>")) break;
					sp.addElement(arr[j]);
				}
				subpack = sp;
				i = j;
			}
			if(t.equals("<jfiles>")) {
				Vector<String> sp = new Vector<String>(1,1);
				int j = 0;
				for(j = i+1; j < arr.length; j++) {
					if(arr[j].equals("</jfiles>")) break;
					sp.addElement(arr[j]);
				}
				jfiles = sp;
				i = j;
			}
			if(t.startsWith("EXECUTABLE")) executable = t.substring(t.indexOf(":"));
		}
	}
	
	public void rewrite() {
		Vector<String> nconf = new Vector<String>(1,1);
		String tpath = "$DEF_PATH";
		nconf.addElement("PROJECT_NAME:"+pname);
		nconf.addElement("PROJECT_PATH:"+tpath+"\\"+pname+"\\");
		nconf.addElement("PROJECT_JAVA_SRC_PATH:"+(jsrct+"\\"));
		nconf.addElement("PROJECT_JAVA_BIN_PATH:"+(jbint+"\\"));
		nconf.addElement("PROJECT_JAVA_TRPACK:"+packroot);
		nconf.addElement("<spackage>");
		if(subpack!=null) for(int i =0; i < subpack.size(); i++) nconf.addElement(subpack.elementAt(i));
		nconf.addElement("</spackage>");
		nconf.addElement("<jfiles>");
		if(jfiles!=null) for(int i =0; i < jfiles.size(); i++) nconf.addElement(jfiles.elementAt(i));
		nconf.addElement("</jfiles>");
		nconf.addElement("EXECUTABLE:"+executable);
		StreamFileWriter sfw = new StreamFileWriter(pconf, nconf);
		sfw.write();
	}
}