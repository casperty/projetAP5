package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.ColorModel;
import models.Coord;
import models.Model;

/**
* 
* @author François Lamothe Guillaume Lecocq Alexandre Ravaux
* Classe pour la construction de la fenetre "Choix de la couleur"
*
*/


public class ColorChooser extends JDialog implements Observer, KeyListener{
	
	private Model model;
	private JTextField rField,gField,bField,aField,hField;
	private final Coord sz=new Coord(350,210);
	
	public ColorChooser(Model model){
		this.model=model;
		model.addObserver(this);
		
		this.setTitle("Color");
		this.setIconImage(new ImageIcon("res/colorwheel.png").getImage());
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
		
		JLabel couleur = new JLabel("Couleur: ");
		couleur.setBounds(250, 15, 75, 22);
		panel.add(couleur);
		
		
		//R
		JLabel rLabel = new JLabel("R: ");
		rLabel.setBounds(250, 40, 20, 22);
		panel.add(rLabel);
		
		rField = new IntegerField();
		rField.setBounds(270, 40, 75, 22);
		rField.addKeyListener(this);
		panel.add(rField);
		
		
		//G
		JLabel gLabel = new JLabel("G: ");
		gLabel.setBounds(250, 70, 20, 22);
		panel.add(gLabel);
		
		gField = new IntegerField();
		gField.setBounds(270, 70, 75, 22);
		gField.addKeyListener(this);
		panel.add(gField);
		
		//B
		JLabel bLabel = new JLabel("B: ");
		bLabel.setBounds(250, 100, 20, 22);
		panel.add(bLabel);
		
		bField = new IntegerField();
		bField.setBounds(270, 100, 75, 22);
		bField.addKeyListener(this);
		panel.add(bField);
		
		//A
		JLabel aLabel = new JLabel("A: ");
		aLabel.setBounds(250, 130, 20, 22);
		panel.add(aLabel);
		
		aField = new IntegerField();
		aField.setBounds(270, 130, 75, 22);
		aField.addKeyListener(this);
		panel.add(aField);
		
		//hexa
		JLabel hLabel = new JLabel("H: ");
		hLabel.setBounds(250, 160, 20, 22);
		panel.add(hLabel);
		
		hField = new HexaField();
		hField.setBounds(270, 160, 75, 22);
		hField.addKeyListener(this);
		panel.add(hField);
		
		
		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
		update(model,null);
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
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
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
	
	public class ColorWheel extends JPanel implements MouseListener,MouseMotionListener,Observer {
		
		private Image img;
		private Model model;
		private Shape wheel;
		private Rectangle cursor;
		private Coord sz=new Coord(0,0);
		private Point mse=new Point();
		private boolean msePressed=false;
		
		
		
		public ColorWheel(Model model){
			this.model=model;
			model.addObserver(this);
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			cursor = new Rectangle(0, 0, 0, 0);
		}
		
		public void paintComponent(Graphics g){
			long cur=System.currentTimeMillis();
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				    RenderingHints.VALUE_ANTIALIAS_ON);
			if(img==null){
				getImg();
			}
			g.drawImage(img,1, 1, this.getWidth()-5, this.getHeight()-5,null);
			
			Ellipse2D.Double contours = new Ellipse2D.Double(1, 1, getWidth()-5, getHeight()-5);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(2));
			g2d.draw(contours);
			wheel=contours;
			g2d.setStroke(new BasicStroke(1));
			g2d.drawRect(cursor.x, cursor.y, cursor.width, cursor.height);
		}
		
		public void getImg(){
			int w = getWidth();
			int h = getHeight();
			sz.setX(w);
			sz.setY(h);
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
			
			//met le curseur au centre
			cursor.setBounds((w/2)-3, (h/2)-3, 6, 6);
		}
		
		public Color getCursorColor(){
			int x = cursor.x+cursor.width/2;
			int y = cursor.y+cursor.height/2;
			int w = sz.getX();
			int h = sz.getY();
			x-=w/2;
			y-=h/2;
			
			float angle = (float) (Math.atan2(y, x) * 180/Math.PI);
			if(y<0){
				angle+=360;
			}
			float distance = (float) Math.sqrt((x*x)+(y*y));
			float s = (distance<((w<h)?(w/2):h/2))?(distance / ((w<h)?(w/2):h/2)):0;
			Color c = (s!=0)?Color.getHSBColor(angle/360, s, 1):this.getBackground();
			return c;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
		public void moveCursorToMouse(){
			cursor.x=mse.x-3;
			cursor.y=mse.y-3;
			Color c = getCursorColor();
			model.setCurColor(new ColorModel(c.getRed(),c.getGreen(), c.getBlue(), c.getAlpha()));
		}

		@Override
		public void mousePressed(MouseEvent e) {
			mse = new Point(e.getX(), e.getY());
			if(wheel.contains(mse.x,mse.y)){
				moveCursorToMouse();
				repaint();
			}
			msePressed=true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mse = new Point(e.getX(), e.getY());
			if(wheel.contains(mse.x,mse.y)){
				moveCursorToMouse();
				repaint();
			}			
			msePressed=false;
		}

		
		
		
		@Override
		public void mouseDragged(MouseEvent e) {
			mse = new Point(e.getX(), e.getY());
			if(wheel.contains(mse.x,mse.y)){
				moveCursorToMouse();
				repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void update(Observable arg0, Object arg1) {
			if(!msePressed){
				ColorModel m = model.getCurColor();
				Color c = new Color(m.getR(), m.getG(), m.getB(),255);
				Coord coord = getCursorCoord(c);
				cursor.x=coord.getX()-3;
				cursor.y=coord.getY()-3;
				repaint();
			}
		}
		
		public Coord getCursorCoord(Color c){
			float angle = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null)[0];
			float dist = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null)[1];
			
			angle*=2*Math.PI;
			System.out.println(dist);
			dist*=((float)getWidth())/2;
			System.out.println(dist);
			float cos = (float) Math.cos(angle);
			float sin = (float) Math.sin(angle);
			
			float x = ((float)getWidth())/2;
			float y = ((float)getHeight())/2;
			
			
			return new Coord((int)(x+(dist*cos)),(int)(y+(dist*sin)));
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("update");
		rField.setText(String.valueOf(model.getCurColor().getR()));
		gField.setText(String.valueOf(model.getCurColor().getG()));
		bField.setText(String.valueOf(model.getCurColor().getB()));
		aField.setText(String.valueOf(model.getCurColor().getA()));
		hField.setText(String.valueOf(model.getCurColor().getHexa()));
	}
	
	public JTextField getrField() {
		return rField;
	}

	public JTextField getgField() {
		return gField;
	}

	public JTextField getbField() {
		return bField;
	}

	public JTextField getaField() {
		return aField;
	}

	public JTextField gethField() {
		return hField;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)return;
		ColorModel m = new ColorModel(0, 0, 0, 0);
		m.setR(Integer.parseInt(((rField.getText().length()>0)?rField.getText():"0")));
		System.out.println(m.getR());	
		m.setG(Integer.parseInt(((gField.getText().length()>0)?gField.getText():"0")));
		m.setB(Integer.parseInt(((bField.getText().length()>0)?bField.getText():"0")));
		m.setA(Integer.parseInt(((aField.getText().length()>0)?aField.getText():"0")));
		if(hField.isFocusOwner())m.setHexa(hField.getText());
		model.setCurColor(m);
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
