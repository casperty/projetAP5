package views;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import models.Model;

public class ColorPicker extends JFrame{
	
	private Model m;
	private JPanel panel;
	
	public ColorPicker(Model m){
		this.m=m;
		 
		try {
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("RGBSelector");
//		this.setPreferredSize(new Dimension(500,500));
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		//panel.add(new ViewRect(m));
		//panel.add(new ViewTextField(m));
		panel.add(new ViewSliders(m));
		panel.add(new ViewColor(m));
		
		m.setR(33);
		m.setV(33);
		m.setB(34);
		
		this.setContentPane(panel);
	}
	
	public static void main(String[] args){
		Model m = new Model();
		ColorPicker f = new ColorPicker(m);
		f.pack();
		f.setVisible(true);
	}
	
}

