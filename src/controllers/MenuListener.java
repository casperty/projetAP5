package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import models.Model;
import views.DrawArea;
import views.MainFrame;
import views.PathChooser;
import views.SaveFile;
/**
 * 
 * @author Fran�ois Lamothe Guillaume Leccoq Alexandre Ravaux
 * Classe listener pour le menu
 *
 */
public class MenuListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New")){
			
		}
		if(e.getActionCommand().equals("Open")){
			new PathChooser();
		}
		if(e.getActionCommand().equals("Save")){
			new SaveFile();
		}
		if(e.getActionCommand().equals("Quit")){
			System.exit(0);
        }
		if(e.getActionCommand().equals("Clear")){
			System.out.println("Clear");
		}

		
	}
	

}
