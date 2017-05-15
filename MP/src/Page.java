import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;


public class Page extends JFrame{

	private Page frame;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		Page k = new Page();
		
		k.setVisible(true);
	}

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
			    img.getScaledInstance(186, 100, Image.SCALE_AREA_AVERAGING);
		Image img2 =ImageIO.read(new File("Sprites/GUI/About2.png"));
		Image resizedImage2 = 
			    img2.getScaledInstance(186, 100, Image.SCALE_AREA_AVERAGING);
		btnNewButton.setIcon(new ImageIcon(resizedImage));
		btnNewButton.setBounds(10, 126, 186, 73);
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setIcon(new ImageIcon(resizedImage2));
		btnNewButton_1.setBounds(238, 126, 186, 73);
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setContentAreaFilled(false);
		getContentPane().add(btnNewButton_1);
	}

	/**
	 * Initialize the contents of the frame.
	 */

	
	private void initialize() {
		
	}
}
