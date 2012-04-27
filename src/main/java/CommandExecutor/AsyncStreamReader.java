package CommandExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class AsyncStreamReader extends Thread
{
	private StringBuffer fBuffer = null;
	private InputStream fInputStream = null;
	private ILogDevice fLogDevice = null;
	
	private String fNewLine = null;
	
	public AsyncStreamReader(InputStream inputStream, StringBuffer buffer, ILogDevice logDevice)
	{
		fInputStream = inputStream;
		fBuffer = buffer;
		fLogDevice = logDevice;
		
		fNewLine = System.getProperty("line.separator");
	}	
	
	public String getBuffer() {		
		return fBuffer.toString();
	}
	
	public void run()
	{
		try {
			readCommandOutput();
		} catch (Exception ex) {
			//ex.printStackTrace(); //DEBUG
		}
	}
	
	private void readCommandOutput() throws IOException
	{		
		BufferedReader bufOut = new BufferedReader(new InputStreamReader(fInputStream));		
		String line = null;
		while (((line = bufOut.readLine()) != null))
		{
			fBuffer.append(line + fNewLine);
			printToDisplayDevice(line);
		}		
		bufOut.close();
	}
	
	private void printToDisplayDevice(String line)
	{
		if( fLogDevice != null )
			fLogDevice.log(line);
	}
}