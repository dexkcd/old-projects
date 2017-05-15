import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.lang.*;

class ThreadOne implements Runnable {
	Socket s;
	MyConnection mc;
	ThreadOne(Socket s, MyConnection mc) {
		this.s = s;
		this.mc = mc;
	}
   
	public void run() {
		boolean quit = false;
		while (quit == false) {
			String command = JOptionPane.showInputDialog("Enter Message: ");
			mc.sendMessage(command);
		}
   }
}

class ThreadTwo implements Runnable {
	Socket s;
	MyConnection mc;
	ThreadTwo(Socket s, MyConnection mc) {
		this.s = s;
		this.mc = mc;
	}
   
   public void run() {
		while (true) {
			String str = mc.getMessage();
			System.out.println(str);
		}
   }
}

public class MyClient {
	
	public static void main(String args[]) {
		
		try {
			
			Socket s = new Socket("192.168.254.104", 8888);
			System.out.println("Client is connecting to server...");
			System.out.println("Client connected!");
			String name = "Client";
			MyConnection mc = new MyConnection(s, name);
			ThreadOne thread1 = new ThreadOne(s, mc);
			ThreadTwo thread2 = new ThreadTwo(s, mc);
			new Thread(thread1).start();
			new Thread(thread2).start();
		}	
		catch (Exception e) {
			System.out.println("Client: Error... " + e);
			e.printStackTrace();
		}
	
	}
	
}