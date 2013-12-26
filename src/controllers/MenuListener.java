package controllers;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Model;
import views.DrawArea;
import views.ExportFile;
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
	Model m;
	public MenuListener(Model m){
		this.m=m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New")){
			
		}
		if(e.getActionCommand().equals("Open")){
			new PathChooser();
		}
		if(e.getActionCommand().equals("Save")){
			new SaveFile(this.m);
		}
		if(e.getActionCommand().equals("Export")){
			new ExportFile(this.m);
		}
		if(e.getActionCommand().equals("Quit")){
			/* confirmer la fermeture */
			int confirm = JOptionPane.showConfirmDialog( null,
	                  "Quitter AFG ?", 
	                  "Confirmer la fermeture de AFG",
	                  JOptionPane.YES_NO_OPTION, 
	                  JOptionPane.QUESTION_MESSAGE );
	            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        }
		if(e.getActionCommand().equals("Clear")){
			System.out.println("Clear");
		}

		
	}
	

}
