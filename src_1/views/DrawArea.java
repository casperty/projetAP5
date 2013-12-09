package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import models.Circle;
import models.Forme;


public class DrawArea extends JPanel {
	
	private JPanel area;
	private ArrayList<AdvShape> formes;
	
	public DrawArea(){
		formes = new ArrayList<AdvShape>();
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(500, 500));
	}
	
	public boolean addForme(Forme f){
		if(f.getClass() == Circle.class){
			Ellipse2D.Double c = new Ellipse2D.Double();
			Circle circle = (Circle)f;
			c.width=circle.getRadius()*2;
			c.height=circle.getRadius()*2;
			c.x=circle.getPos().getX();
			c.y=circle.getPos().getY();
			formes.add(new AdvShape(c,f));
		}
		int npPoint;
		return false;
	}
	
	public void remove(Forme f){
		
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

}
