package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import models.Coord;
import models.Forme;
import models.Model;
import models.Oval;
import models.Rectangle;


public class DrawArea extends JPanel implements MouseListener, MouseMotionListener {
	
	private JPanel area;
	private ArrayList<AdvShape> formes;
	private Coord mse = new Coord(0,0);
	private Model model;
	
	public DrawArea(Model model){
		this.model=model;
		formes = new ArrayList<AdvShape>();
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(500, 500));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public boolean addForme(Forme f){
		if(f.getClass() == Oval.class){
			Ellipse2D.Double c = new Ellipse2D.Double();
			Oval oval = (Oval)f;
			c.width=oval.getSize().getX();
			c.height=oval.getSize().getY();
			c.x=oval.getPos().getX();
			c.y=oval.getPos().getY();
			formes.add(new AdvShape(c,f));
			return true;
		}
		int nbPoint=0;
		switch(nbPoint){
		case 1:
			break;
//		case 2:
//			break;
//		case 3:
//			break;
//		case 4:
//			Rectangle2D.Double rec = new Rectangle2D.Double();
//			Rectangle rect = (Rectangle)f;
//			rec.x=rect.
//			break;
		default:
			Polygon poly = new Polygon();
			for(Coord c : f.getPoints()){
				poly.addPoint(c.getX(), c.getY());
			}
			formes.add(new AdvShape(poly,f));
			break;
		}
		return false;
	}
	
	public void setFormes(List<Forme> list){
		formes.clear();
		for(Forme f : list){
			addForme(f);
		}
		repaint();
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		
		for(AdvShape s : formes){
			g2d.setColor(s.getColor());
			g2d.draw(s.getShape());
			if(s.getForme().isFill()){
				g2d.fill(s.getShape());
			}
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
		// TODO Auto-generated method stub
		
	}

}
