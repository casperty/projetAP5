package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import models.ColorModel;
import models.Coord;
import models.Forme;
import models.Model;
import models.Oval;
import models.Rectangle;


public class DrawArea extends JPanel implements MouseListener, MouseMotionListener, KeyListener,Observer {
	
	private Coord mse = new Coord(0,0);
	private Model model;
	
	public DrawArea(Model model){
		this.model=model;
		model.addObserver(this);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(500, 500));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		setFocusable(true);
	}
	
	public Shape getShape(Forme f){
		if(f.getClass() == Oval.class){
			Ellipse2D.Double c = new Ellipse2D.Double();
			Oval oval = (Oval)f;
			c.width=oval.getSize().getX();
			c.height=oval.getSize().getY();
			c.x=oval.getPos().getX();
			c.y=oval.getPos().getY();
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
				poly.addPoint(c.getX(), c.getY());
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
		
		for(Forme f : model.getFormes()){
			g2d.setStroke(new BasicStroke(1));
			ColorModel m = f.getColor();
			g2d.setColor(new Color(m.getR(),m.getG(),m.getB()));
			Shape s = getShape(f);
			g2d.draw(s);
			if(f.isFill()){
				g2d.fill(s);
			}
			if(f.isSelect()){
				float dash[] = { 5.0f };
				g2d.setColor(Color.BLACK);
			    g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
			        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
			    g2d.draw(s);
			}
		}
		
		if(MainFrame.DEBUG){
			g2d.setColor(Color.BLACK);
			g2d.drawString(mse.toString(), mse.getX(), mse.getY());
		}
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		mse.setX(e.getX());
		mse.setY(e.getY());
		model.mouseDragged(mse);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mse.setX(e.getX());
		mse.setY(e.getY());
		model.mouseMoved(mse);
		repaint();
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
		model.mousePressed(mse);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		model.mouseReleased(mse);		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_DELETE){
			model.uimsg("del");
		}else if(e.getKeyCode()==KeyEvent.VK_SHIFT){
			model.setShift(true);
		}
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

}
