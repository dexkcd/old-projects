import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class MyConnection {

	/**
	 * @param args
	 */
	
	Socket s;
	String name = "";
	String status ="";
	public MyConnection(Socket s)
	{
		this.s = s;
	}
	boolean sendMessage(String msg)
	{
		try{
	//	System.out.println("Sending message...");
		OutputStream os = s.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		PrintWriter out = new PrintWriter(osw);
		out.println(msg);
		out.flush();
	//	System.out.println("Message Sent");
		return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	String getMessage() throws Exception
	{
		InputStream is = s.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader in = new BufferedReader(isr);
		String msg = in.readLine();
	//	System.out.println("Message Received!");
		return msg;
	}
	

}
