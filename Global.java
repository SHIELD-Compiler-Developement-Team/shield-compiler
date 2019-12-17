import java.util.Scanner;

public class Global {
	
	private static boolean state = false;
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void startProgram() {
		state = true;
	}
	
	public static boolean isRunning() {
		return state;
	}
	
	public static void endProgram() {
		state = false;
		postTerminiationProcess();
	}
	
	public static String getInput() {
		return sc.nextLine();
	}
	
	public static void postTerminiationProcess() {
		sc.close();
		if(Settings.pconf != null) Settings.pconf.rewrite();
	}
}