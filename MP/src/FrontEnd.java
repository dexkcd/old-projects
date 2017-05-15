import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.EventListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;




public class FrontEnd extends JFrame implements KeyListener{	
	private JFrame frame;
	static Page k;
	static FrontEnd gui;
	static JButton btnNewButton;
	static JButton btnNewButton_1;
	/*
	 * character variable
	 */
	
	static Music background;
	static Music Win, Lose;
	Thread north;
	Thread south;
	Thread east;
	Thread west;
	Character nacu;
	Character mario;
	SaintPeter pedro;
	static Thread del = new Thread();
	static Thread a;
	static Thread b;
	static Thread c;
	int port;
	String address;
	boolean canMoveMario= true;
	boolean canMoveNacu = true;
	static char nacuDir='x';
	static char marioDir='x';
	private final int map[][]=
		{
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,2,1,0,1,0,1,0,1},
			{1,0,0,2,2,2,2,2,0,0,1},
			{1,0,1,0,1,2,1,2,1,0,1},
			{1,0,0,2,0,2,0,2,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,2,2,2,0,2,0,0,1},
			{1,0,1,0,1,2,1,0,1,0,1},
			{1,0,0,0,0,2,0,2,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1}
		};
	
	int blockSize = 60;
	Canvas canvas = new Canvas();
	Graphics g = getGraphics();
	
	/*
	 * connection variables
	 */
	
	static Socket s;
	static String name;
	static MyConnection mc1;
	public void draw(Graphics g,int x, int y, Color c) {
		g.setColor(c);
        g.fillRect(x,y,1,1);
    }
	public FrontEnd(String address, int port) throws UnknownHostException, IOException, AWTException
	{
		super();
		try {
			nacu = new Character(60, 60, blockSize, new File("Sprites/Creep/Front/Creep_F_f00.png"));
			mario = new Character(540, 540, blockSize, new File("Sprites/Creep2/Front/Creep_F_f00.png"));
		} catch (Exception e) {
			// do nothing
			e.printStackTrace();
		}
		addKeyListener(this);
	
		System.out.println("yeah!");
	//	Socket s = new Socket("192.168.1.103", 8080);
	//	Socket s = new Socket("192.168.254.104", 8888);
	//	Socket s = new Socket("169.254.198.34", 8888);
		this.port= port;
		this.address = address;
		Socket s = new Socket(address, port);
		
		String name = "Client";
		System.out.println("yeah");
		mc1 = new MyConnection(s, name);
		String sname = mc1.getMessage();
		mc1.name = sname;
		try {

			ThreadTwo thread2 = new ThreadTwo(s, mc1);
			new Thread(thread2).start();
		}	
		catch (Exception e) {
			System.out.println("Client: Error... " + e);
			e.printStackTrace();
		}
		
		
	a = new Thread();
	b = new  Thread();
	
	pedro = new SaintPeter();
	c= new Thread(pedro);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	public void paint(Graphics g)
	{
		
		for(int i=0;i<11;i++)
		{
			for(int j=0;j<11;j++)
			{
				if(map[i][j]==0 || map[i][j]==2){
					try{
						Image img1 = ImageIO.read(new File("Sprites/Blocks/BackgroundTile.png"));
						g.drawImage(img1, j*blockSize+getInsets().left, i*blockSize+getInsets().top, blockSize, blockSize, this);
					}
					catch(Exception e){}
				}
				if(map[i][j]==2)
				{
					try{
						Image img1 = ImageIO.read(new File("Sprites/Blocks/ExplodableLaptop.png"));
						g.drawImage(img1, j*blockSize+getInsets().left, i*blockSize+getInsets().top, blockSize, blockSize, this);
					}
					catch(Exception e){}
				}
				else if(map[i][j]==1)
				{
					try{
						Image img1 = ImageIO.read(new File("Sprites/Blocks/SolidBlock.png"));
						g.drawImage(img1, j*blockSize+getInsets().left, i*blockSize+getInsets().top, blockSize, blockSize, this);
					}
						catch(Exception e){}
				}
			}
		}
		nacu.draw(getGraphics(), getInsets(),this);
		mario.draw(getGraphics(), getInsets(),this);
		
	}

public class ThreadTwo implements Runnable {
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
			String[] code = str.split("\\s+");
			try{
				if (code[0].equals("MOVE:")) {	// "MOVE: <CLIENT#> <DIRECTION>"
					/* commands for nacu */
					if (code[1].equals("1") && code[2].equals("n")) {
						nacuMove('n');
					} else if (code[1].equals("1") && code[2].equals("s")) {
						nacuMove('s');
					} else if (code[1].equals("1") && code[2].equals("w")) {
						nacuMove('w');
					} else if (code[1].equals("1") && code[2].equals("e")) {
						nacuMove('e');
					}	
					/* commands for mario */
					else if (code[1].equals("2") && code[2].equals("n")){
						marioMove('n');
					} else if (code[1].equals("2") && code[2].equals("s")) {
						marioMove('s');
					} else if (code[1].equals("2") && code[2].equals("w")) {
						marioMove('w');
					} else if (code[1].equals("2") && code[2].equals("e")) {
						marioMove('e');
					}
		
				} else if (code[0].equals("BOMB:")) { // "BOMB: <CLIENT#>"
					if (code[1].equals("1")) {
						nacuBomb();
					} else if (code[1].equals("2")) {
						marioBomb();
					}
				}
					
			}
			catch (Exception e) {}
		}
	}

}

	
	 public void keyTyped(KeyEvent e) {
	    }

	    public void keyPressed(KeyEvent e){
	    	
	    	try {
	    	
	    	if(canMoveNacu){
	    		/* nacu's movements */
		        if ((e.getKeyCode() == KeyEvent.VK_UP && map[nacu.y/blockSize-1][nacu.x/blockSize] == 0)){
					mc1.sendMessage("MOVE: 1 n");
				} else if ((e.getKeyCode() == KeyEvent.VK_DOWN && map[nacu.y/blockSize+1][nacu.x/blockSize] == 0)){
			        mc1.sendMessage("MOVE: 1 s");
		        } else if ((e.getKeyCode() == KeyEvent.VK_LEFT && map[nacu.y/blockSize][nacu.x/blockSize-1] == 0)){
		        	mc1.sendMessage("MOVE: 1 w");
		        } else if ((e.getKeyCode() == KeyEvent.VK_RIGHT && map[nacu.y/blockSize][nacu.x/blockSize+1] == 0)){
					mc1.sendMessage("MOVE: 1 e");
		        }
		    //    canMoveNacu = false;
	    	}
	    	if(canMoveMario){
	        	/* mario's movements */
		        if ((e.getKeyCode() == KeyEvent.VK_W && map[mario.y/blockSize-1][mario.x/blockSize] == 0)){
		        	mc1.sendMessage("MOVE: 2 n");
		        } else if ((e.getKeyCode() == KeyEvent.VK_S && map[mario.y/blockSize+1][mario.x/blockSize] == 0)) {	
		        	mc1.sendMessage("MOVE: 2 s");
		        } else if ((e.getKeyCode() == KeyEvent.VK_A && map[mario.y/blockSize][mario.x/blockSize-1] == 0)) {
		        	mc1.sendMessage("MOVE: 2 w");
		        } else if ((e.getKeyCode() == KeyEvent.VK_D && map[mario.y/blockSize][mario.x/blockSize+1] == 0)) {
		        	mc1.sendMessage("MOVE: 2 e");
		        }
		     //   canMoveMario = false;
	    	}
	       
	        /*
	         *  bomb listeners
	         */
	        if(e.getKeyCode()== KeyEvent.VK_SPACE) {
	        	mc1.sendMessage("BOMB: 1");
	        } else if(e.getKeyCode()== KeyEvent.VK_ENTER) {
	        	mc1.sendMessage("BOMB: 2");
	        }
	        
	    	}
	    	catch(Exception ex){}
	       	
	    	
	    }

	    public void keyReleased(KeyEvent e) {
	    }
	    
	    /*
	     * burning animation
	     */
	    
	    public int charIsHere(int x, int y)
	    {
	    	x = x*60;
	    	y = y*60;
	    	if(mario.x==x && mario.y==y)
	    	{
	    		return 2;
	    	}
	    	else if(nacu.x==x && nacu.y==y)
	    	{
	    		return 1;
	    	}
	    	else
	    	{
	    		return 0;
	    	}
	    }
	    public class Burner implements Runnable
	    {

			
			Image img;
			Bomb b;
			char dir;
			int x,y;
			public Burner( char dir, Bomb b ) throws Exception
			{
				this.b = b;
				this.dir = dir;
				this.x = b.x/60;
				this.y = b.y/60;
				img= ImageIO.read(new File("Sprites/Flame/Flame_f00.png"));
			}
			@Override
			public void run() {
				try {
					Thread.sleep(333);
					for(int i=1;i<=3;i++)
					{
						for(int j=1;j<=3;j++)
						{
							for(int k=0;k<50;k++){
							b.draw(getGraphics(), getInsets(), frame, "Sprites/Bomb/Bomb_f0"+i+".png");
							}
							Thread.sleep(333);
						}
					}
					b.clear(getGraphics(), getInsets(), frame);
					b.drawxy(getGraphics(), getInsets(), frame, x*60, (y)*60);
					map[b.y/60][b.x/60] = 0;
					if(dir=='n')
					{
						for(int i=0;map[y-i][x]==0;i++) //burn baby  burn!
						{
							System.out.println(map[x][y-i]);
							Thread.sleep(100);
							if(map[(y-i)-1][x]==2)
							{
								map[(y-i)-1][x] =0;
								b.drawxy(getGraphics(), getInsets(), frame, x*60, (y-i)*60);
								b.clear(getGraphics(), getInsets(), frame, x*60, (y-i-1)*60);
								b.drawxy(getGraphics(), getInsets(), frame, x*60, ((y-i)-1)*60);
								break;
							}
							b.drawxy(getGraphics(), getInsets(), frame, x*60, (y-i)*60);
							if(charIsHere(x, y-i)==1)
							{
								nacu.isAlive = false;
							}
							else if(charIsHere(x, y-i)==2)
							{
								mario.isAlive = false;
							}
						}
						Thread.sleep(1000);
						for(int i=0;map[y-i][x]==0;i++) //erase
						{
							System.out.println(map[x][y-i]);
							Thread.sleep(100);
							b.clear(getGraphics(), getInsets(), frame, x*60, (y-i)*60);
						}
					}
					if(dir=='s')
					{
						for(int i=0;map[y+i][x]==0;i++)
						{
							Thread.sleep(100);	
							System.out.println(map[x][y+i]);
							if(map[y+i+1][x]==2)
							{
								map[y+i+1][x] =0;
								b.drawxy(getGraphics(), getInsets(), frame, x*60, (y+i)*60);
								b.clear(getGraphics(), getInsets(), frame, x*60, (y+i+1)*60);
								b.drawxy(getGraphics(), getInsets(), frame, x*60, (y+i+1)*60);
								break;
							}
							
							b.drawxy(getGraphics(), getInsets(), frame, x*60, (y+i)*60);
							if(charIsHere(x, y+i)==1)
							{
								nacu.isAlive = false;
							}
							else if(charIsHere(x, y+i)==2)
							{
								mario.isAlive = false;
							}
						}
						Thread.sleep(1000);
						for(int i=0;map[y+i][x]==0;i++)
						{
							Thread.sleep(100);
							System.out.println(map[x][y+i]);
							b.clear(getGraphics(), getInsets(), frame, x*60, (y+i)*60);
						}
					}
					if(dir=='e')
					{
						
						for(int i=0;map[y][x+i]==0;i++)
						{
							Thread.sleep(100);
							System.out.println(x+" "+y);
							if(map[y][x+i+1]==2)
							{
								map[y][x+i+1]=0;
								b.drawxy(getGraphics(), getInsets(), frame, (x+i)*60, y*60);
								b.clear(getGraphics(), getInsets(), frame, (x+i+1)*60, y*60);
								b.drawxy(getGraphics(), getInsets(), frame, (x+i+1)*60, (y)*60);
								break;
							}
							
							b.drawxy(getGraphics(), getInsets(), frame, (x+i)*60, y*60);
							
							if(charIsHere(x+i, y)==1)
							{
								nacu.isAlive = false;
							}
							else if(charIsHere(x+i, y)==2)
							{
								mario.isAlive = false;
							}
							
						}
						Thread.sleep(1000);
						for(int i=0;map[y][x+i]==0;i++)
						{
							Thread.sleep(100);
							System.out.println(x+" "+y);
							b.clear(getGraphics(), getInsets(), frame, (x+i)*60, y*60);
							
						}
					}
					if(dir=='w')
					{
						Thread.sleep(100);
						for(int i=0;map[y][x-i]==0;i++)
						{
							System.out.println(x+" "+y);
							if(map[y][x-i-1]==2)
							{
								map[y][x-i-1]=0;
								b.drawxy(getGraphics(), getInsets(), frame, (x-i)*60, y*60);
								b.clear(getGraphics(), getInsets(), frame, (x-i-1)*60, y*60);
								b.drawxy(getGraphics(), getInsets(), frame, (x-i-1)*60, (y)*60);
								break;
							}
							b.drawxy(getGraphics(), getInsets(), frame, (x-i)*60, y*60);
							if(charIsHere(x-i, y)==1)
							{
								nacu.isAlive = false;
							}
							else if(charIsHere(x-i, y)==2)
							{
								mario.isAlive = false;
							}
						}
						Thread.sleep(1000);
						for(int i=0;map[y][x-i]==0;i++)
						{
							System.out.println(x+" "+y);
							b.clear(getGraphics(), getInsets(), frame, (x-i)*60, y*60);
						}
					}
				}
			catch( Exception e){}
		}
	    	
	    }
	    
	    public class SaintPeter implements Runnable
	    {

			@Override
			public void run() {
				while(true)
				{
					
					if(mario.isAlive==false)
					{
						System.out.println(mario.isAlive);
						Lose.play();
						JOptionPane.showMessageDialog(frame, "Mario is Dead :(");
						
						break;
					}
					if(nacu.isAlive==false)
					{
						System.out.println(nacu.isAlive);
						Lose.play();
						JOptionPane.showMessageDialog(frame, "Nacu is Dead :(");
						
						break;
					}
					
				}
				background.stop();
				gui.setVisible(false);
				k.setVisible(true);
				try {
					gui.finalize();
					gui = new FrontEnd(address,port);
					mario.isAlive= true;
					nacu.isAlive = true;
					c.start();
					a.start();
					b.start();
				} catch ( IOException | AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gui.setVisible(false);
				gui.setSize(660,660);
				gui.setResizable(false);
				BufferedImage image;
				try {
					image = ImageIO.read(new File("Sprites/GUI/Credits.jpg"));
					JLabel picLabel = new JLabel(new ImageIcon(image));
					JOptionPane.showMessageDialog(null, picLabel, "Credits", JOptionPane.PLAIN_MESSAGE, null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	    	
	    }
	    /*
	     * bomb of the characters
	     */
	    
	    
	    //bomb of Mario
	    public void marioBomb() throws Exception {
	    	System.out.println("now");
        	int k,l;
    		k = mario.x;
    		l = mario.y;
        	Bomb b = new Bomb(mario.x, mario.y, 60, new File("Sprites/Bomb/Bomb_f01.png"));
        	map[mario.y/60][mario.x/60] = 3;
        	b.draw(getGraphics(), getInsets(), this);
        	Burner n = new Burner('n', b);
        	Burner s = new Burner('s', b);
        	Burner w = new Burner('w', b);
        	Burner eas = new Burner('e', b);
        	
        	north =new Thread(n);
        	south =new Thread(s);
        	west = new Thread(w);
        	east= new Thread(eas);
        	
        	north.start();
        	south.start();
        	west.start();
        	east.start();
	    }
	    //bomb of Nacu
	    public void nacuBomb() throws Exception {
	    	System.out.println("now");
        	int k=0,l=0;
        	System.out.println(mc1.name);
    		k = nacu.x;
    		l = nacu.y;
        	Bomb b = new Bomb(k, l, 60, new File("Sprites/Bomb/Bomb_f01.png"));
        	map[nacu.y/60][nacu.x/60] = 3;
        	b.draw(getGraphics(), getInsets(), this);
        	Burner n = new Burner('n', b);
        	Burner s = new Burner('s', b);
        	Burner w = new Burner('w', b);
        	Burner eas = new Burner('e', b);
        	
        	north =new Thread(n);
        	south =new Thread(s);
        	west = new Thread(w);
        	east= new Thread(eas);
        	
        	north.start();
        	south.start();
        	west.start();
        	east.start();
	    }
	    
	    /*
	     * movements of the characters
	     */
	    
	    
	    //movements of Mario
	    public synchronized void marioMove(char dir) throws Exception
	    {
	    	canMoveMario = false;
	    	if (dir=='n'){
	        	int x1 = mario.x;
	        	int y2 = mario.y; 
	        	mario.clear(getGraphics(), getInsets(),this);
	        	mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Back/Creep_B_f01.png");
		       
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Back/Creep_B_f02.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Back/Creep_B_f03.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Back/Creep_B_f04.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Back/Creep_B_f05.png");
		       
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Back/Creep_B_f00.png");
		        
	        } else if (dir=='s') {
	        	int x1 = mario.x;
	        	int y2 = mario.y;
	        	mario.clear(getGraphics(), getInsets(),this);
	        	mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y+=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Front/Creep_F_f01.png");
		       
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y+=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Front/Creep_F_f02.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y+=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Front/Creep_F_f03.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.y+=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Front/Creep_F_f04.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y+=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Front/Creep_F_f05.png");
		       
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.y+=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Front/Creep_F_f00.png");
		       
		        
	        } else if (dir=='w') {
	            
	            int x1 = mario.x;
	        	int y2 = mario.y;
	        	mario.clear(getGraphics(), getInsets(),this);
	        	mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.x-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f01.png");
		       
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.x-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f02.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.x-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f03.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.x-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f04.png");
		        
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.x-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f05.png");
		       
		        b.sleep(100);
		        mario.clear(getGraphics(), getInsets(),this);
		        mario.clear(getGraphics(), getInsets(),this,x1,y2);
		        mario.x-=(blockSize/6);
		        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f00.png");
	          
	        } else if (dir=='e') {
		            
		            int x1 = mario.x;
		        	int y2 = mario.y;
		        	mario.clear(getGraphics(), getInsets(),this);
		        	mario.clear(getGraphics(), getInsets(),this,x1,y2);
			        mario.x+=(blockSize/6);
			        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f01.png");
			       
			        b.sleep(100);
			        mario.clear(getGraphics(), getInsets(),this);
			        mario.clear(getGraphics(), getInsets(),this,x1,y2);
			        mario.x+=(blockSize/6);
			        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f02.png");
			        
			        b.sleep(100);
			        mario.clear(getGraphics(), getInsets(),this);
			        mario.clear(getGraphics(), getInsets(),this,x1,y2);
			        mario.x+=(blockSize/6);
			        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f03.png");
			        
			        b.sleep(100);
			        mario.clear(getGraphics(), getInsets(),this);
			        mario.clear(getGraphics(), getInsets(),this,x1,y2);
			        mario.x+=(blockSize/6);
			        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f04.png");
			        
			        b.sleep(100);
			        mario.clear(getGraphics(), getInsets(),this);
			        mario.clear(getGraphics(), getInsets(),this,x1,y2);
			        mario.x+=(blockSize/6);
			        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f05.png");
			       
			        b.sleep(100);
			        mario.clear(getGraphics(), getInsets(),this);
			        mario.clear(getGraphics(), getInsets(),this,x1,y2);
			        mario.x+=(blockSize/6);
			        mario.draw(getGraphics(), getInsets(),this,"Sprites/Creep2/Side/Creep_S_f00.png");
		           
	        }
	    	canMoveMario = true;
	    }
	    //movements of Nacu
	    public synchronized void nacuMove(char dir) throws Exception
	    {
	    	canMoveNacu = false;
	    	if (dir=='n'){ //north
	        	int x1 = nacu.x;
	        	int y2 = nacu.y;
	        	nacu.clear(getGraphics(), getInsets(),this);
	        	nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Back/Creep_B_f01.png");
		       
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Back/Creep_B_f02.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Back/Creep_B_f03.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.y-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Back/Creep_B_f04.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Back/Creep_B_f05.png");
		       
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Back/Creep_B_f00.png");
		       
		        
	        } else if (dir=='s') { //south
	        	int x1 = nacu.x;
	        	int y2 = nacu.y;
	        	nacu.clear(getGraphics(), getInsets(),this);
	        	nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y+=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Front/Creep_F_f01.png");
		       
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y+=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Front/Creep_F_f02.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y+=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Front/Creep_F_f03.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.y+=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Front/Creep_F_f04.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y+=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Front/Creep_F_f05.png");
		       
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.y+=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Front/Creep_F_f00.png");
		       
		        
	        } else if (dir=='w') { //move west
	            
	            int x1 = nacu.x;
	        	int y2 = nacu.y;
	        	nacu.clear(getGraphics(), getInsets(),this);
	        	nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.x-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f01.png");
		       
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.x-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f02.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.x-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f03.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.x-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f04.png");
		        
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.x-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f05.png");
		       
		        a.sleep(100);
		        nacu.clear(getGraphics(), getInsets(),this);
		        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
		        nacu.x-=(blockSize/6);
		        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f00.png");
	          
	        } else if (dir=='e') { //move east
		            
		            int x1 = nacu.x;
		        	int y2 = nacu.y;
		        	nacu.clear(getGraphics(), getInsets(),this);
		        	nacu.clear(getGraphics(), getInsets(),this,x1,y2);
			        nacu.x+=(blockSize/6);
			        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f01.png");
			       
			        a.sleep(100);
			        nacu.clear(getGraphics(), getInsets(),this);
			        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
			        nacu.x+=(blockSize/6);
			        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f02.png");
			        
			        a.sleep(100);
			        nacu.clear(getGraphics(), getInsets(),this);
			        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
			        nacu.x+=(blockSize/6);
			        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f03.png");
			        
			        a.sleep(100);
			        nacu.clear(getGraphics(), getInsets(),this);
			        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
			        nacu.x+=(blockSize/6);
			        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f04.png");
			        
			        a.sleep(100);
			        nacu.clear(getGraphics(), getInsets(),this);
			        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
			        nacu.x+=(blockSize/6);
			        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f05.png");
			       
			        a.sleep(100);
			        nacu.clear(getGraphics(), getInsets(),this);
			        nacu.clear(getGraphics(), getInsets(),this,x1,y2);
			        nacu.x+=(blockSize/6);
			        nacu.draw(getGraphics(), getInsets(),this,"Sprites/Creep/Side/Creep_S_f00.png");
		           
	        }
	    	canMoveNacu = true;
	    }
	    public static void main(String[] args) throws Exception {
			
			Robot rob = new Robot();
		//	rob.keyPress(KeyEvent.VK_W);	
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter IP address:");
			String s = br.readLine();
			System.out.print("Enter port number:");
			String s2 = br.readLine();
			gui = new FrontEnd(s,Integer.parseInt(s2));
			gui.setVisible(false);
			gui.setSize(660,660);
			gui.setResizable(false);
			a.start();
			b.start();
			c.start();
			k = new Page();
    		k.setResizable(false);
    		k.setVisible(true);
			
		}
	    
	    
	    public static class Page extends JFrame implements ActionListener{

	    	private Page frame;


	    	/**
	    	 * Launch the application.
	    	 */
	    	

	    	/**
	    	 * Create the application.
	    	 */
	    	
	    	public static class ImagePanel extends JComponent {
	    	    private Image image;
	    	    public ImagePanel() throws IOException {
	    	        this.image = ImageIO.read(new File("Sprites/GUI/Background.png"));
	    	    }
	    	    @Override
	    	    protected void paintComponent(Graphics g) {
	    	    	g.drawImage(image, getInsets().left, getInsets().top, 450, 300, this);
	    	    }
	    	}
	    	public Page() throws Exception {
	    		super();
	    		setBounds(100, 100, 450, 300);
	    		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		setContentPane(new ImagePanel());
	    		getContentPane().setLayout(null);
	    		
	    		btnNewButton = new JButton("New button");
	    		Image img =ImageIO.read(new File("Sprites/GUI/NewGame2.png"));
	    		Image resizedImage = 
	    			    img.getScaledInstance(186, 150, Image.SCALE_AREA_AVERAGING);
	    		Image img2 =ImageIO.read(new File("Sprites/GUI/About2.png"));
	    		Image resizedImage2 = 
	    			    img2.getScaledInstance(186, 150, Image.SCALE_AREA_AVERAGING);
	    		btnNewButton.setIcon(new ImageIcon(resizedImage));
	    		btnNewButton.setBounds(10, 206, 186, 43);
	    		btnNewButton.setOpaque(false);
	    		btnNewButton.setContentAreaFilled(false);
	    		getContentPane().add(btnNewButton);
	    		btnNewButton_1 = new JButton("New button");
	    		btnNewButton_1.setIcon(new ImageIcon(resizedImage2));
	    		btnNewButton_1.setBounds(238, 206, 186, 43);
	    		btnNewButton_1.setOpaque(false);
	    		btnNewButton_1.setContentAreaFilled(false);
	    		btnNewButton.setActionCommand("start");
	    		btnNewButton.addActionListener(this);
	    		btnNewButton_1.setActionCommand("about");
	    		btnNewButton_1.addActionListener(this);
	    		getContentPane().add(btnNewButton_1);
	    	}
	    	
	    	public void repaint(Graphics g)
	    	{
	    		
	    	}
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("start"))
				{
					try {
						background = new Music(new File("Music.wav"));
						Win = new Music(new File("Win.wav"));
						Lose = new Music(new File("Lose.wav"));
						background.play();
					} catch(IOException error) {}
					System.out.println("Go!!!!");
					gui.setVisible(true);
					k.setVisible(false);
				}
				else if(e.getActionCommand().equals("about"))
				{
					BufferedImage image;
					try {
						image = ImageIO.read(new File("Sprites/GUI/AboutPage.jpg"));
						JLabel picLabel = new JLabel(new ImageIcon(image));
						JOptionPane.showMessageDialog(null, picLabel, "About", JOptionPane.PLAIN_MESSAGE, null);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
	    }
}
