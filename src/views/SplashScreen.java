package views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 
 * @author Fran&ccedilois Lamothe Guillaume Leccoq Alexandre Ravaux
 *
 */
public class SplashScreen extends JFrame{
	
	private BufferedImage img;
	private JPanel panel;
	/**
	 * Splashscreen du logiciel
	 * @param sec temps d'affichage
	 */
	public SplashScreen(int sec){
		try {
			img = ImageIO.read(new File("res/splashscreen.png"));
			int width = img.getWidth();
			int height = img.getHeight();
			Dimension sz=new Dimension(width,height);
			this.setSize(sz);
			this.setPreferredSize(sz);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation((dim.width/2)-(width/2), (dim.height/2)-(height/2));
			this.setUndecorated(true);
			panel = new ImgPanel(img);
			this.setContentPane(panel);
			this.pack();
			this.setVisible(true);
			long st = System.currentTimeMillis();
			while(System.currentTimeMillis()-st<sec*1000){
				Thread.sleep(100);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public class ImgPanel extends JPanel{
		
		private Image img;
		/**
		 * Pour dessiner l'image du splashscreen
		 * @param img
		 */
		public ImgPanel(Image img){
			this.img=img;
			repaint();
		}
		/**
		 * Dessine l'image
		 */
		public void paintComponent(Graphics g){
			g.drawImage(img, 0, 0, null);
		}
	}

}
