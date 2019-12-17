public class Main
{
	public static void main(String[] args) {
		Settings.getPath();
		Global.startProgram();
		while(Global.isRunning()) {
			System.out.print("\n "+Settings.path + " >> ");
			String com = Global.getInput();
			ShieldSwitch.switchProcess(com);
		}
	}
}