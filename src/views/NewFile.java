package views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.ColorModel;
import models.Coord;
import models.Model;
/**
 * Fenetre de creation d'un nouveau dessin.
 * @author François Lamothe Guillaume Leccoq Alexandre Ravaux
 *
 */
public class NewFile extends JDialog implements ActionListener, KeyListener{
	private JTextField width, height;
	private JButton confirm, cancel;
	private int w=0,h=0;
	private Model m;
	/**
	 * Fenetre pour creer un nouveau dessin
	 * @param model
	 */
	public NewFile(Model model){
		this.setTitle("New draw");
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300,150));
		panel.setLayout(null);
		this.m=model;
		
		JLabel title = new JLabel("Taille d'image");
		title.setBounds(10, 10, 80, 22);
		panel.add(title);
		JLabel hLabel = new JLabel("Height :");
		hLabel.setBounds(10, 30, 50, 22);
		panel.add(hLabel);

		height = new IntegerField();
		height.setBounds(60, 30, 95, 22);
		height.addKeyListener(this);
		//rField.addKeyListener(this);
		panel.add(height);
		
		JLabel wLabel = new JLabel("Width :");
		wLabel.setBounds(10, 60, 50, 22);
		panel.add(wLabel);
		
		width = new IntegerField();
		width.setBounds(60, 60, 95, 22);
		width.addKeyListener(this);
		//rField.addKeyListener(this);
		panel.add(width);
		confirm=new JButton("Confirm");
		confirm.setBounds(30, 100, 90, 22);
		confirm.addActionListener(this);
		cancel=new JButton("Cancel");
		cancel.setBounds(150, 100, 90, 22);
		cancel.addActionListener(this);
		panel.add(confirm);
		panel.add(cancel);
		
		this.setContentPane(panel);
		this.pack();
		this.setVisible(true);
		
	}
	/**
	 * gestion écoute du clavier
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		try{
		w=Integer.parseInt(width.getText());
		h=Integer.parseInt(height.getText());
		if(w!=0 && h!=0){
			System.out.println(w+","+h);
		}
		}catch(Exception e1){
			
		}
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Listener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == confirm || e.getActionCommand() == confirm.getText()) {
			Coord sz = new Coord(w,h);
			m.newArea(sz);
			setVisible(false);
		}else if(e.getSource() == cancel || e.getActionCommand() == cancel.getText()) {
			setVisible(false);
		}
		
	}
}
