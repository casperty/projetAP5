package views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import models.ExportAFG;
import models.Forme;
import models.Model;
/**
 * 
 * @author Fran&ccedilois Lamothe Guillaume Leccoq Alexandre Ravaux
 * Classe qui gere la sauvegarde du fichier
 *
 */
public class SaveFile {
	private JFileChooser fileDialog;
	private File fileName,selectedFile; 
	private Model model;
	/**
	 * Fenetre pour sauvegarder le fichier, 
	 * se lance quand on clique sur Ctrl+S ou bien File > Save
	 */
	public SaveFile(Model model){
		this.model=model;
		if(fileDialog==null){
			fileDialog = new JFileChooser();
			File selectedFile;
		} 
		if(fileName==null){
			selectedFile = new File("Untitled.afg");
		}else{
			selectedFile = new File(fileName.getName());
		}
		fileDialog.setSelectedFile(selectedFile); 
	    fileDialog.setDialogTitle("Save the file");
	    int option = fileDialog.showSaveDialog(fileDialog);
		if (option != JFileChooser.APPROVE_OPTION) return;  // Annuler ou fermeture de la fenetre.
	         selectedFile = fileDialog.getSelectedFile();
	         String path=""+selectedFile;
	         if (!path.endsWith(".afg")){  // On a oublié de mettre l'extension ? Pas grave le logiciel s'en charge :D
		          path+=".afg";
		          selectedFile.delete();
		          selectedFile = new File(path);
		     }
	         if (selectedFile.exists()) {  // Le fichier existe déjà, devons nous ecraser le fichier existant ?
	            int response = JOptionPane.showConfirmDialog( fileDialog,
	                  "Le fichier \"" + selectedFile.getName()
	                  + "\" existe déjà.\nVoulez-vous le remplacez ?", 
	                  "Remplacer",
	                  JOptionPane.YES_NO_OPTION, 
	                  JOptionPane.WARNING_MESSAGE );
	            if (response != JOptionPane.YES_OPTION) return;  // Annuler l'enregistrement
	         }
	    try{
	    	new ExportAFG(model, new FileWriter(selectedFile));
	    }catch(Exception e){ // Echec de l'ouverture du fihier
	    	JOptionPane.showMessageDialog(fileDialog, this,"Nous sommes desole mais une erreur s'est produite.", option);
	    }
	    
	}

}
