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

public class ViewCircle extends JPanel implements Observer {

	private Model m;
	private int r,v,b;
	private JPanel rp,rv,rb;
	
	public ViewCircle(Model m){
		this.m=m;
		m.addObserver(this);
		this.setBorder(BorderFactory.createTitledBorder("ViewRect"));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);  
		
		int width = this.getWidth()/2;
		int height = this.getHeight()/2;
		
		int x=(this.getWidth()/2)-(width/2);
		int y=(this.getHeight()/2)-(height/2);
		//100 = 360
		
		
		double rA = 3.6*r;
		double rS = 0;
		double vA = 3.6*v;
		double vS= rS+rA;
		double bA = 3.6*b;
		double bS= vS+vA;
		
		System.out.println(rA+" "+rS+" "+vA+" "+vS+" "+bA+" "+bS+" "+(rA+bA+vA));
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(r!=0){
//			g.setColor(Color.RED);
//			g.fillArc(x, y, width, height, rS, rA);
			g2d.setColor(Color.RED);
			g2d.fill(new Arc2D.Double(x,y,width,height,rS,rA,Arc2D.PIE));
		}
		if(v!=0){
//			g.setColor(Color.GREEN);
			g2d.setColor(Color.GREEN);
//			g.fillArc(x, y, width, height, vS, vA);
			g2d.fill(new Arc2D.Double(x,y,width,height,vS,vA,Arc2D.PIE));
		}
		if(b!=0){
			g2d.setColor(Color.BLUE);
//			g.fillArc(x, y, width, height, bS, bA);
			g2d.fill(new Arc2D.Double(x,y,width,height,bS,bA,Arc2D.PIE));
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		this.r=m.getR();
		this.v=m.getV();
		this.b=m.getB();
		repaint();
	}
	
}
