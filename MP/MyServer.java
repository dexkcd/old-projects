import java.net.*;
import java.util.*;

class NewThread implements Runnable {
	
    Socket s;
    Vector<MyConnection> array;
    MyConnection mc;
    
    NewThread(Socket s, Vector<MyConnection> array, MyConnection mc) {
		this.s = s;
		this.array = array;
		this.mc = mc;
    }
   
    public void run() {
		while (true) {
	            String str = mc.getMessage();
				//System.out.println(str);
				String[] code = str.split(" ");
				if(code[0].equals("MOVE:") || code[0].equals("BOMB:")) {
					for (MyConnection conn : array) {
						conn.sendMessage(str);
					}
				} else {
					for (MyConnection conn : array) {
						conn.sendMessage(mc.name + ": " + str );
					}
				}
		}	
    }
}

public class MyServer {

    public static void main(String args[]) {
		
        try {
            System.out.println("Starting server...");
            ServerSocket ssocket = new ServerSocket(8888);
            System.out.println("Waiting for connections...");
            Vector<MyConnection> array = new Vector<MyConnection>();
            int CNumber = 1;
            while (true) {
                Socket s = ssocket.accept();		
                String name = "Client" + CNumber;
                MyConnection mc = new MyConnection(s, name);
                System.out.println(name + " has connected.");
                array.add(mc);
				for (MyConnection conn : array) {
					conn.sendMessage("Server: " + mc.name + " has connected"); 
					conn.sendMessage(mc.name);
				}
				NewThread newthread = new NewThread(s, array, mc);
				new Thread(newthread).start();
				CNumber++;
            }
	}	
	catch (Exception e) {
            System.out.println("Server: Error... " + e);
            e.printStackTrace();
	}
}
	
}




