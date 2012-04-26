import java.io.DataInputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import CommandExecutor.SysCommandExecutor;

public class SysCommandExecutorTest extends TestCase{

	@Test
	public void testCommandExecutor() throws Exception {
		String commandLine = "pwd";
		SysCommandExecutor cmdExecutor = new SysCommandExecutor();
		int exitStatus = cmdExecutor.runCommand(commandLine);

		String cmdError = cmdExecutor.getCommandError();
		String cmdOutput = cmdExecutor.getCommandOutput();

	}
	
	@Test
	public void testBasicCommandExample() throws Exception {

			try {
			String ls_str;

			Process ls_proc = Runtime.getRuntime().exec("ls -l");

			// get its output (your input) stream

			DataInputStream ls_in = new DataInputStream(
			ls_proc.getInputStream());

			try {
			while ((ls_str = ls_in.readLine()) != null) {
			System.out.println(ls_str);
			}
			} catch (IOException e) {
			//System.exit(0);
			}
			} catch (IOException e1) {
			System.err.println(e1);
			//System.exit(1);
			}

			//System.exit(0);
			 
		
	}
}
