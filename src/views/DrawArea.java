package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import models.ColorModel;
import models.Coord;
import models.Forme;
import models.ImgObject;
import models.Line;
import models.Model;
import models.Oval;


public class DrawArea extends JPanel implements MouseListener, MouseMotionListener, KeyListener,Observer,MouseWheelListener {
	
	private Coord mse = new Coord(0,0);
	private Coord sz;
	private Model model;
	private float zoom=1f;
	
	//test
	private JFrame mainFrame;
	
	private InfoPanel infoPanel;
	
	public DrawArea(Model model,JFrame mf,Coord sz){
		this.model=model;
		model.addObserver(this);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(sz.getX(), sz.getY()));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addMouseWheelListener(this);
		setFocusable(true);
		
		mainFrame=mf;
		this.sz=sz;
	}
	
	public void resize(Coord sz){
		this.sz=sz;
		this.setSize(sz.getX(), sz.getY());
		this.setPreferredSize(new Dimension(sz.getX(), sz.getY()));
		mainFrame.pack();
		repaint();
	}
	
	public Shape getShape(Forme f){
		if(f.getClass() == Oval.class){
			Ellipse2D.Double c = new Ellipse2D.Double();
			Oval oval = (Oval)f;
			c.width=oval.getSize().getX()*zoom;
			c.height=oval.getSize().getY()*zoom;
			c.x=oval.getPos().getX()*zoom;
			c.y=oval.getPos().getY()*zoom;
			return c;
		}
		int nbPoint=0;
		nbPoint=f.getPoints().size();
		switch(nbPoint){
		case 1:
			break;
		default:
			Polygon poly = new Polygon();
			for(Coord c : f.getPoints()){
				poly.addPoint((int)(c.getX()*zoom), (int)(c.getY()*zoom));
			}
			return poly;
		}
		return null;
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		
		ArrayList<Forme> resize= new ArrayList<Forme>();
		for(Forme f : model.getFormes()){
			g2d.setStroke(new BasicStroke(1));
			ColorModel m = f.getColor();
			g2d.setColor(new Color(m.getR(),m.getG(),m.getB(),m.getA()));
			
			Shape s = getShape(f);
			if(s==null) continue;
			
			if(f.getClass() == ImgObject.class){
				try {
					ImgObject o = (ImgObject)f;
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(o.getData()));
					g2d.drawImage(img, f.getPos().getX(), f.getPos().getY(),f.getSz().getX(),f.getSz().getY(), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				g2d.draw(s);
				if(f.isFill()){
					g2d.fill(s);
				}
			}
			
			if(f.isResize()){
				resize.add(f);
			}
			if(f.isSelect()){
				float dash[] = { 5.0f };
				g2d.setColor(Color.BLACK);
			    g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
			        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
			    g2d.draw(s);
			}
		}
		
		//resize
		for(Forme f : resize){
			drawRectBounds(g2d,f);
		}
		
	}
	
	public void drawRectBounds(Graphics2D g2d,Forme f){
		if(f instanceof Line){
			int x=(int) (f.getPoints().get(0).getX()*zoom);
			int y=(int) (f.getPoints().get(0).getY()*zoom);
			int x1=(int) (f.getPoints().get(1).getX()*zoom);
			int y1=(int) (f.getPoints().get(1).getY()*zoom);
			g2d.fillRect(x-3, y-3, 6, 6);
			g2d.fillRect(x1-3, y1-3, 6, 6);
			return;
		}
		Polygon poly = new Polygon();
		ArrayList<Coord> pts = (ArrayList<Coord>) f.getRectBounds();
		for(Coord c : pts){
			c.mul(zoom);
			poly.addPoint(c.getX(), c.getY());
		}
		
		float dash[] = { 5.0f };
		g2d.setColor(Color.BLACK);
	    g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
	        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
	    g2d.draw(poly);
	    
	    for(Coord c : pts){
	    	g2d.fillRect(c.getX()-3, c.getY()-3, 6, 6);
	    }
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		mse.setX(e.getX());
		mse.setY(e.getY());
		if(infoPanel!=null)infoPanel.setMse(mse);
		Coord mse2 = new Coord(mse);
		mse2.div(zoom);
		model.mouseDragged(mse2);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mse.setX(e.getX());
		mse.setY(e.getY());
		if(infoPanel!=null)infoPanel.setMse(mse);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Coord mse2 = new Coord(mse);
		mse2.div(zoom);
		model.mousePressed(mse2);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Coord mse2 = new Coord(mse);
		mse2.div(zoom);
		model.mouseReleased(mse2);		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_DELETE){
			model.delselects();
		}else if(e.getKeyCode()==KeyEvent.VK_SHIFT){
			model.setShift(true);
		}else if(e.getKeyCode()==KeyEvent.VK_CONTROL){
			model.setCtrl(true);
		}else if(e.getKeyCode()==KeyEvent.VK_C){
			if(model.isCtrl())model.copy();
		}else if(e.getKeyCode()==KeyEvent.VK_V){
			if(model.isCtrl())model.paste();
		}
	}
	
	
	
	public float getZoom(){
		return zoom;
	}
	
	public void setZoom(float z){
		zoom=Math.round(z*100);
		zoom/=100;
		zoom=(zoom>2f)?2f:((zoom<0.1f)?0.1f:zoom);
		this.setSize(new Dimension((int)(sz.getX()*zoom),(int)(sz.getY()*zoom)));
		this.setPreferredSize(new Dimension((int)(sz.getX()*zoom),(int)(sz.getY()*zoom)));
		mainFrame.pack();
		infoPanel.setZoom(zoom);
		repaint();
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SHIFT){
			model.setShift(false);
		}else if(e.getKeyCode()==KeyEvent.VK_ENTER){
			model.enter();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(model.getAreaSz()!=this.sz){
			this.sz=model.getAreaSz();
			resize(sz);
		}
		repaint();
	}
	
	public void setInfoPanel(InfoPanel info){
		this.infoPanel=info;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		setZoom(getZoom()+((-e.getWheelRotation())*0.1f));
	}
}
