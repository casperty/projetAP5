package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import views.PathChooser;
public class MenuListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Quit")){
			System.exit(0);
        }
		if(e.getActionCommand().equals("Open")){
			PathChooser pc = new PathChooser();
		}
		
	}
	

}
