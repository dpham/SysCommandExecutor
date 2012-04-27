package CommandExecutor;

import java.io.IOException;
import java.io.InputStream;
 
/**
 * Usage of following class can go as ...
 * <P><PRE><CODE>
 * 		SysCommandExecutor cmdExecutor = new SysCommandExecutor();
 * 		cmdExecutor.setOutputLogDevice(new LogDevice());
 * 		cmdExecutor.setErrorLogDevice(new LogDevice());
 * 		int exitStatus = cmdExecutor.runCommand(commandLine);
 * </CODE></PRE></P>
 * 
 * OR
 * 
 * <P><PRE><CODE>
 * 		SysCommandExecutor cmdExecutor = new SysCommandExecutor(); 		
 * 		int exitStatus = cmdExecutor.runCommand(commandLine);
 * 
 * 		String cmdError = cmdExecutor.getCommandError();
 * 		String cmdOutput = cmdExecutor.getCommandOutput(); 
 * </CODE></PRE></P> 
 */
public class SysCommandExecutor
{	
	private ILogDevice fOuputLogDevice = null;
	private ILogDevice fErrorLogDevice = null;
	
	private StringBuffer fCmdOutput = null;
	private StringBuffer fCmdError = null;
	private AsyncStreamReader fCmdOutputThread = null;
	private AsyncStreamReader fCmdErrorThread = null;	
	
	public void setOutputLogDevice(ILogDevice logDevice)
	{
		fOuputLogDevice = logDevice;
	}
	
	public void setErrorLogDevice(ILogDevice logDevice)
	{
		fErrorLogDevice = logDevice;
	}
	
	public String getCommandOutput() {		
		return fCmdOutput.toString();
	}
	
	public String getCommandError() {
		return fCmdError.toString();
	}
	
	public int runCommand(String commandLine) throws Exception
	{
		/* run command */
		Process process = runCommandHelper(commandLine);
		
		/* start output and error read threads */
		startOutputAndErrorReadThreads(process.getInputStream(), process.getErrorStream());
	    
		/* wait for command execution to terminate */
		int exitStatus = -1;
		try {
			exitStatus = process.waitFor();
					
		} catch (Throwable ex) {
			throw new Exception(ex.getMessage());
			
		} 
		
		return exitStatus;
	}	
	
	private Process runCommandHelper(String commandLine) throws IOException
	{
		return Runtime.getRuntime().exec(commandLine);
	}
	
	private void startOutputAndErrorReadThreads(InputStream processOut, InputStream processErr)
	{
		fCmdOutput = new StringBuffer();
		fCmdOutputThread = new AsyncStreamReader(processOut, fCmdOutput, fOuputLogDevice);		
		fCmdOutputThread.start();
		
		fCmdError = new StringBuffer();
		fCmdErrorThread = new AsyncStreamReader(processErr, fCmdError, fErrorLogDevice);
		fCmdErrorThread.start();
	}
}
