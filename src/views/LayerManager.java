package views;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

import models.Model;

public class LayerManager extends JDialog implements Observer{

	private final int myWidth=200;
	private Model model;
	
	
	public LayerManager(Model model	){
		this.model=model;
		model.addObserver(this);
		this.setTitle("test");
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width/2+600/2)+50, (dim.height/2));
		this.setPreferredSize(new Dimension(myWidth,300));
		this.setResizable(false);
		
		JPanel cont = new JPanel();
		cont.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
