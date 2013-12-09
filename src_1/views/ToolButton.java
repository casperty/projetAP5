package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import models.Coord;

public class ToolButton extends JPanel implements MouseListener {
	
	private Coord sz;
	private Coord c;
	private Image img;
	private boolean mouseOver=false;
	
	
	public ToolButton(Coord c,Coord sz,Image img){
		this.c=c;
		this.sz=sz;
		this.img=img;
		setBounds(c.getX(), c.getY(), sz.getX(), sz.getY());
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if(img!=null){
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),null);
		}
		if(mouseOver){
			g.setColor(new Color(0,255,0,100));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOver=true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseOver=false;	
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
		
		
}
