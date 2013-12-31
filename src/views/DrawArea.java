package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.ColorModel;
import models.Coord;
import models.Forme;
import models.Line;
import models.Model;
import models.Oval;
import models.Rectangle;

public class DrawArea extends JPanel implements MouseListener, MouseMotionListener, KeyListener,Observer,MouseWheelListener {
	
	private Coord mse = new Coord(0,0);
	private Coord sz;
	private Model model;
	private float zoom=1f;
	private Oval o;
	private Rectangle r;
	private Line l;
	
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
			g2d.setColor(new Color(m.getR(),m.getG(),m.getB()));
			Shape s = getShape(f);
			if(s==null) continue;
			g2d.draw(s);
			if(f.isFill()){
				g2d.fill(s);
			}
			if(f.isResize()){
//				drawRectBounds(g2d,f);
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
		//uniquement avec le bouton gauche de la souris
		if (SwingUtilities.isLeftMouseButton(e)){
			Coord mse2 = new Coord(mse);
			mse2.div(zoom);
			model.mousePressed(mse2);
		}
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
		}else if(e.getKeyCode()==KeyEvent.VK_C){
			if(e.isControlDown()){
				System.out.println("CTRL+C");
				try{
				int i=0;
				while(!model.getFormes().get(i).isSelect()){
					i++;
				}
				Coord sz= new Coord(model.getFormes().get(i).getSz().getX(),model.getFormes().get(i).getSz().getY());
				Coord pos = new Coord(model.getFormes().get(i).getPos().getX()+10, model.getFormes().get(i).getPos().getY()+10);
				ColorModel c = new ColorModel(model.getFormes().get(i).getColor().getR(),model.getFormes().get(i).getColor().getG(),model.getFormes().get(i).getColor().getB(),255);
				boolean fill=model.getFormes().get(i).isFill();
				if(model.getFormes().get(i) instanceof Oval){
					o=new Oval(sz, c, fill);
					o.setDeep(model.getFormes().get(model.getFormes().size()-1).getDeep()+1);
					o.setPos(pos);
				}else if(model.getFormes().get(i) instanceof Rectangle){
					r= new Rectangle(pos, sz, c, fill);
					r.setDeep(model.getFormes().get(model.getFormes().size()-1).getDeep()+1);
				}else if(model.getFormes().get(i) instanceof Line){
					l=new Line(pos, sz, c, fill, 0);
				}else{
					System.out.println("wut ?");
				}
				}catch (Exception ie){
					System.out.println("Pas de formes séléctionnées");
					JOptionPane.showMessageDialog(mainFrame, "Any form has been selected.", "Can't copy !", JOptionPane.INFORMATION_MESSAGE);
				};
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_V){
			if(e.isControlDown()){
				System.out.println("CTRL+V");
				int i=0;
				while(!model.getFormes().get(i).isSelect()){
					i++;
				}
				if(model.getFormes().get(i) instanceof Oval){
					model.unSelectAll();
					model.getFormes().add(o);
				}else if(model.getFormes().get(i) instanceof Rectangle){
					model.unSelectAll();
					model.getFormes().add(r);
				}else if(model.getFormes().get(i) instanceof Line){
					model.unSelectAll();
					model.getFormes().add(l);	
				}
				repaint();
			}
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
		}		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
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
