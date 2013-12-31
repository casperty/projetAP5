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
import views.OpenFile;
import views.SaveFile;
/**
 * 
 * @author Franï¿½ois Lamothe Guillaume Leccoq Alexandre Ravaux
 * Classe listener pour le menu
 *
 */
public class MenuListener implements ActionListener{
	
	MainFrame m;
	Model model;
	ArrayList<Forme> archiveUndo=new ArrayList<Forme>();
	
	public MenuListener(MainFrame m, Model model){
		this.model=model;
		this.m=m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New")){
			
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
			try{
				archiveUndo.add(model.getFormes().get(model.getFormes().size()-1));
	           	model.getFormes().remove(model.getFormes().size()-1);
	           	m.repaint();
			}catch(Exception ie){//s'il n'y a plus d'element à enlever
				JOptionPane.showMessageDialog(m, "Action impossible à effectuer ", "Message interne", JOptionPane.WARNING_MESSAGE);
			}
		}
		if(e.getActionCommand().equals("Redo")){
			try{
				model.getFormes().add(archiveUndo.get(archiveUndo.size()-1));
				archiveUndo.remove(archiveUndo.size()-1);//pas vraiment obligatoire
				m.repaint();
			}catch(Exception ie){//s'il n'y a plus d'element à ajouter
				JOptionPane.showMessageDialog(m, "Action impossible à effectuer ", "Message interne", JOptionPane.WARNING_MESSAGE);
			}
		}
		/* A VERIFIER */
		if(e.getActionCommand().equals("Clear all")){
			/*for(int j=0;j<3;j++){
				for(int i=0; i<model.getFormes().size();i++){
	            	model.getFormes().remove(i);
	            }
			}*/
			model.getFormes().clear();
            m.repaint();

			
		}
		if(e.getActionCommand().equals("About")){
			JOptionPane.showMessageDialog(m, "AFG is a vectors graphics editor.", "About", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}
	

}
