package views;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

import models.ColorModel;
import models.Coord;
import models.Model;

public class ColorChooser extends JDialog{
	
	private Model model;
	private final Coord sz=new Coord(250,210);
	
	public ColorChooser(Model model){
		this.model=model;
		
		this.setTitle("Color");
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width/2+600/2)+50, (dim.height/2-600/2));
//		this.setPreferredSize(new Dimension(sz.getX(),sz.getY()));
		this.setResizable(false);
		
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(sz.getX(),sz.getY()));
		panel.setLayout(null);
		ColorViewer c = new ColorViewer(model);
		c.setBounds(10, 10, 32, 32);
		panel.add(c);
		ColorWheel c1 = new ColorWheel(model);
		c1.setBounds(45, 5, 200, 200);
		panel.add(c1);
		
		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
	}
	
	public class ColorViewer extends JPanel implements Observer{
		
		private Model model;
		private Color c;
		
		public ColorViewer(Model model){
			this.model=model;
			model.addObserver(this);
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		
		public void paintComponent(Graphics g){
			g.setColor(c);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		@Override
		public void update(Observable arg0, Object arg1) {
			ColorModel cm = model.getCurColor();
			c=new Color(cm.getR(),cm.getG(),cm.getB(),cm.getA());
			repaint();
		}
		
	}
	
	public class ColorWheel extends JPanel implements MouseListener,MouseMotionListener {
		
		private Image img;
		private Model model;
		private Robot robot;
		private Shape wheel;
		
		
		public ColorWheel(Model model){
			this.model=model;
			try {
				this.robot=new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}
		
		public void paintComponent(Graphics g){
			if(img==null){
				getImg();
			}
			g.drawImage(img,1, 1, this.getWidth()-5, this.getHeight()-5,null);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				    RenderingHints.VALUE_ANTIALIAS_ON);
			Ellipse2D.Double contours = new Ellipse2D.Double(1, 1, getWidth()-5, getHeight()-5);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(2));
			g2d.draw(contours);
			wheel=contours;
		}
		
		public void getImg(){
			int w = getWidth();
			int h = getHeight();
			img = createImage(w, h);
			Graphics g = img.getGraphics();
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				    RenderingHints.VALUE_ANTIALIAS_ON);
			for(int y=-(h/2);y<(h/2);y++){
				for(int x=-(w/2);x<(w/2);x++){
					
					float angle = (float) (Math.atan2(y, x) * 180/Math.PI);
					if(y<0){
						angle+=360;
					}
					float distance = (float) Math.sqrt((x*x)+(y*y));
					
					float s = (distance<((w<h)?(w/2):h/2))?(distance / ((w<h)?(w/2):h/2)):0;
					
					Color c = (s!=0)?Color.getHSBColor(angle/360, s, 1):this.getBackground();
					g2d.setColor(c);
					g2d.fillOval(x+(w/2), y+(h/2), 1, 1);
				}
			}
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Point mse = new Point(e.getX(), e.getY());
			if(wheel.contains(mse.x,mse.y)){
				Color c = robot.getPixelColor(e.getXOnScreen(), e.getYOnScreen());
				model.setCurColor(new ColorModel(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point mse = new Point(e.getX(), e.getY());
			System.out.println(mse);
			if(wheel.contains(mse.x,mse.y)){
				Color c = robot.getPixelColor(e.getXOnScreen(), e.getYOnScreen());
				model.setCurColor(new ColorModel(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}

}
