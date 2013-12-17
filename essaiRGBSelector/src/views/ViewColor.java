package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import models.Model;

public class ViewColor extends JPanel implements Observer {

	private Model m;
	private int r,v,b;
	private JPanel rp,rv,rb;
	
	public ViewColor(Model m){
		this.m=m;
		m.addObserver(this);
		this.setBorder(BorderFactory.createTitledBorder("Color"));
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
		int width = this.getWidth()-30;
		int height = this.getHeight()-30;
		
		int x=0;//(this.getWidth()/2)-(width/2);
		int y=0;//(this.getHeight()/2)-(height/2);
		Color c;
		c=new Color(r,v,b);
		//100 = 360
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(c);
		g2d.fillRect(15,20,width,height);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		this.r=m.getR();
		this.v=m.getV();
		this.b=m.getB();
		repaint();
	}
	
}
