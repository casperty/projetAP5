package controllers;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Model;
import models.Forme;
import views.DrawArea;
import views.ExportSVGFile;
import views.MainFrame;
import views.NewFile;
import views.OpenFile;
import views.SaveFile;
/**
 * 
 * @author Franï¿½ois Lamothe Guillaume Leccoq Alexandre Ravaux
 * Classe listener pour le menu
 *
 */
public class MenuListener implements ActionListener{
	
	private MainFrame m;
	private Model model;
	
	/**
	 * Le MenuListener gere toutes les actions qui sont liés au menu
	 * @param m
	 * @param model
	 */
	public MenuListener(MainFrame m, Model model){
		this.model=model;
		this.m=m;
	}
	/**
	 * Choix des actions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New")){
			new NewFile(this.model);
			
		}
		if(e.getActionCommand().equals("Open")){
			new OpenFile(this.m,this.model);
		}
		if(e.getActionCommand().equals("Save")){
			new SaveFile(this.model);
		}
		if(e.getActionCommand().equals("Export to SVG")){
			new ExportSVGFile(this.model);
		}
		if(e.getActionCommand().equals("Export to JPG")){
			//new ExportJPGFile(this.m);
			System.out.println("Export to JPG");
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
		/* A AMELIORER */
		if(e.getActionCommand().equals("Undo")){
			model.undo();
		}
		if(e.getActionCommand().equals("Redo")){
			model.redo();
		}
		/* A VERIFIER */
		if(e.getActionCommand().equals("Clear all")){
			model.getFormes().clear();
            m.repaint();
		}
		if(e.getActionCommand().equals("About")){
			JOptionPane.showMessageDialog(m, "AFG is a vectors graphics editor.", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}
	

}
