import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;
public class Bomb {
    int x;
    int y;
    int size;
    Image sprite;
    
    public Bomb (int x, int y, int size, File f) throws Exception{
        this.x = x;
        this.y = y;
        this.size = size;
        sprite = ImageIO.read(f);
    }

    public void draw(Graphics g, Insets ins, Component it) {
        g.drawImage(sprite, x + ins.left, y + ins.top, size, size, it);
    }

    public void explode(int map[][]) throws Exception
    {
    	/*
    	 * kaboom!
    	 */
    	
    	
    }
    public void draw(Graphics g, Insets ins, Component it, String f) throws IOException {
    	Image sprite2 = ImageIO.read(new File(f));
        g.drawImage(sprite2, x + ins.left, y + ins.top, size, size, it);
    }
    public void draw2(Graphics g, Insets ins, Component it, String f,char d) throws IOException {
    	Image sprite2 = ImageIO.read(new File(f));
    	if(d=='n'){
        g.drawImage(sprite2, (x*size + ins.left)-(size/2), y*size + ins.top, size, size, it);
    	}
    	if(d=='s'){
            g.drawImage(sprite2, (x*size + ins.left)+(size/2), y*size + ins.top, size, size, it);
        	}
    	if(d=='y'){
            g.drawImage(sprite2, x*size + ins.left, (y*size + ins.top)-(size/2), size, size, it);
        	}
    	
    }
    public void drawxy(Graphics g, Insets ins, Component it,int a, int b) throws IOException {
    	Image img= ImageIO.read(new File("Sprites/Flame/Flame_f01.png"));
    	g.drawImage(img, a+ ins.left, b + ins.top, size, size, it);
    }
    public void clear(Graphics g, Insets ins, Component it) throws Exception{
    	Image img = ImageIO.read(new File("Sprites/Blocks/BackgroundTile.png"));
    	g.drawImage(img, x+ ins.left, y + ins.top, size, size, it);
    }
    public void clear(Graphics g, Insets ins, Component it, int a, int b) throws Exception{
    	Image img = ImageIO.read(new File("Sprites/Blocks/BackgroundTile.png"));
    	g.drawImage(img, a+ ins.left, b + ins.top, size, size, it);
    }
    public void clear(Graphics g, Insets ins, Component it, char d) throws Exception{
    	Image img = ImageIO.read(new File("Sprites/Blocks/BackgroundTile.png"));
    	if(d=='n')
    		g.drawImage(img, x*size + ins.left, (y*size + ins.top)+(size/2), size, size, it);
    	if(d=='s')
    		g.drawImage(img, x*size + ins.left, (y*size + ins.top)-(size/2), size, size, it);
    }
    
    
}
