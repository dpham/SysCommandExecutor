import java.io.DataInputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import CommandExecutor.LogDevice;
import CommandExecutor.SysCommandExecutor;

public class SysCommandExecutorTest extends TestCase{
	static final String commandLine = "diff pom.xml README.md";
//	static final String commandLine = "pwd";

	@Test
	public void testCommandExecutor() throws Exception {

		SysCommandExecutor cmdExecutor = new SysCommandExecutor();
		cmdExecutor.setOutputLogDevice(new LogDevice());
		cmdExecutor.setErrorLogDevice(new LogDevice());
		cmdExecutor.runCommand(commandLine);
		
		assertEquals("", cmdExecutor.getCommandError());
		assertEquals("", cmdExecutor.getCommandOutput());

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testBasicCommandExample() throws Exception {

			try {
			String ls_str;

			Process ls_proc = Runtime.getRuntime().exec(commandLine);

			DataInputStream ls_in = new DataInputStream(
			ls_proc.getInputStream());

			try {
			while ((ls_str = ls_in.readLine()) != null) {
			System.out.println(ls_str);
			}
			} catch (IOException e) {
			}
			} catch (IOException e1) {
			System.err.println(e1);
			}

			 
		
	}
}
