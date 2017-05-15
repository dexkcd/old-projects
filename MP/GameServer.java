import java.awt.EventQueue;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class GameServer{
	static ArrayList<Waiter> connections = new ArrayList<Waiter>();
	static ArrayList<MyConnection> gmList = new ArrayList<MyConnection>();
	static boolean go = true;
	static int clnum=0;
	public static class Refresher implements Runnable{

		@Override
		public void run() {
			
			while(true)
			{
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(MyConnection k : gmList)
				{
					GroupMessage("cList new "+k.name+" "+k.status);
				}
			}
			
		}
		
	}
	private synchronized static void GroupMessage(String s)
	{
		for(MyConnection c: gmList)
		{
			System.out.println(c.name+" "+s);
			c.sendMessage(s);
		}
	}
	private static class WaiterMaker implements Runnable
	{
		ServerSocket ssocket;
		public WaiterMaker(ServerSocket ssocket){
			this.ssocket =ssocket;
		}
		@Override
		public void run() {
			while(true)
			{
				if(go)
				{
					connections.add(new Waiter(ssocket));
					new Thread(connections.get(connections.size()-1)).start();
					go = false;
				}
				
			}
		}
		
	}
	private static class Waiter implements Runnable
	{
		ServerSocket ssocket;
		public Waiter(ServerSocket ssocket)
		{
			this.ssocket = ssocket;
		}
		@Override
		public void run() {
			try {
				Socket s = ssocket.accept();
				go = true;
				System.out.println("S: Somebody Connected! ^_^");
				System.out.println("S: IP is " + s.getInetAddress());
				System.out.println("Server: Waiting for message...");
				MyConnection mc = new MyConnection(s);
				mc.name = "Client"+clnum;
				clnum+=1;
				gmList.add(mc); //adds the connection to the list
				Protocol p = new Protocol(mc);
				p.start();
				
			} catch (Exception e) {
				System.out.println("Disconnected!");
			}
			
		}
		
	}
	public static class Protocol
	{
		MyConnection c;
		public Protocol(MyConnection c)
		{
			this.c = c;
		}
		public void start() throws Exception
		{
			
			while(true)
			{
				String msg= c.getMessage();
				msg = msg.trim();
				System.out.println("Client Says: "+msg);
				GroupMessage(msg);
				
			}
		}
		
	}
	public static void main(String args[])
	{
		try{
			System.out.println("Server: Starting server...");
			System.out.println("Server: Waiting for Connections...");
			ServerSocket ssocket = new ServerSocket(8888);
			WaiterMaker maker = new WaiterMaker(ssocket);
			Refresher r = new Refresher();
			new Thread(r).start();
			new Thread(maker).start();
		}
		catch(Exception e){
			System.out.println("@_@ dafuq happened? "+e+" Happened");
			e.printStackTrace();
		}	
	}

}