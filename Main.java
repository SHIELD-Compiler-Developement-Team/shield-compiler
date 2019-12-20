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
 
 /**
  * Basic Start-Point for Program
  *
 **/
public class Main
{
	public static void main(String[] args) {
		// Get Path
		Settings.getPath();
		
		// Global Init Method
		Global.startProgram();
		
		// Program Loop
		while(Global.isRunning()) {
			System.out.print("\n "+Settings.path + " >> ");
			String com = Global.getInput();
			ShieldSwitch.switchProcess(com);
		}
	}
}