import java.net.*;
import java.io.*;

public class MyConnection {
	
    Socket s;
    String name, sMessage, gMessage;
    PrintWriter out;
    BufferedReader in;
    boolean tag = false;
	
    public MyConnection (Socket s, String name) {
        this.s = s;
		this.name = name;
        try {
            InputStream is = s.getInputStream();
            InputStreamReader isw = new InputStreamReader(is);
            in = new BufferedReader(isw);
            OutputStream os = s.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            out = new PrintWriter(osw);
		}
		catch(Exception e) {
	            e.printStackTrace();
		}	
    }

    public boolean sendMessage(String sMessage) {
        try {
            this.sMessage = sMessage;
            out.println(sMessage);
            out.flush();
            tag = true;
		}
		catch(Exception e) {
	            System.out.println("Server: Error* " + e);
	            e.printStackTrace();			
		}
		return tag;
    }
	
    public String getMessage() {
        try {	
            gMessage = in.readLine();
		}
		catch (Exception e) {
			System.out.println("Server: Error " + e);
			e.printStackTrace();
		}	
		return gMessage;
    }	
}