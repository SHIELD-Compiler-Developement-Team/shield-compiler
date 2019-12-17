public class Settings {
	
	public static String path = "";
		
	public static ProjectConfigaration pconf = null;	
	
	public static void getPath() {
		StreamFileReader sfr = new StreamFileReader("bpath.shieldsettings");
		path = sfr.singleRead()+"\\";
	}
	
}