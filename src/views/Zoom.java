package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Model;

public class Zoom extends JPanel implements MouseListener {
	
	private int zoom;
	private JTextField text;
	private DrawArea drawArea;
	private RoundButton plus,moins;
	
	public Zoom(DrawArea drawArea){
		this.drawArea=drawArea;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		moins = new RoundButton(new ImageIcon("res/btnm.png").getImage());
		moins.addMouseListener(this);
		add(moins);
		
		//TODO modif zoom depuis textfield ?
		text = new JTextField("100%");
		text.setEditable(false);
		text.setBackground(Color.WHITE);
		add(text);
		
		plus = new RoundButton(new ImageIcon("res/btnp.png").getImage());
		plus.addMouseListener(this);
		add(plus);
	}
	
	public void setZoom(int zoom){
		this.zoom=zoom;
		text.setText(String.valueOf(this.zoom)+"%");
	}
	
	
	public class RoundButton extends JPanel{
		
		private Image img;
		
		public RoundButton(Image img){
			Dimension dim = new Dimension(20,20);
			this.setMinimumSize(dim);
			this.setMaximumSize(dim);
			this.setPreferredSize(dim);
			this.img=img;
			repaint();
		}
		
		public void paintComponent(Graphics g){
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		}
		
	}

	public void mousePressed(MouseEvent e) {
		if(e.getSource()==moins){
			drawArea.setZoom(drawArea.getZoom()-0.1f);
		}else{
			drawArea.setZoom(drawArea.getZoom()+0.1f);
		}
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
