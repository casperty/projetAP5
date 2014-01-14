package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import models.Coord;
import models.Model;

public class ToolButton extends JPanel implements MouseListener,Observer {
	
	private Coord sz;
	private Coord c;
	private Image img;
	private boolean mouseOver=false;
	private Model model;
	private int id;
	private String label;
	
	
	public ToolButton(Model model,int id,Coord c,Coord sz,Image img,String label){
		this.model=model;
		model.addObserver(this);
		this.setId(id);
		this.c=c;
		this.sz=sz;
		this.img=img;
		setBounds(c.getX(), c.getY(), sz.getX(), sz.getY());
		setToolTipText(label);
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g){
		g.setColor(getBackground());
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
		model.setTool(getId());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(model.getCurTool()==this.getId()){
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}else{
			if(getBorder()!=null){
				this.setBorder(null);
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
		
}
