package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import views.DrawArea;
import views.PathChooser;
public class MenuListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New")){
			
		}
		if(e.getActionCommand().equals("Open")){
			PathChooser pc = new PathChooser();
		}
		if(e.getActionCommand().equals("Quit")){
			System.exit(0);
        }

		
	}
	

}
