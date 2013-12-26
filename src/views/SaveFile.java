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

import models.Forme;
import models.Model;
/**
 * 
 * @author Fran�ois Lamothe Guillaume Leccoq Alexandre Ravaux
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
	         if (selectedFile.exists()) {  // Le fichier existe déjà, devons nous ecraser le fichier existant ?
	            int response = JOptionPane.showConfirmDialog( fileDialog,
	                  "Le fichier \"" + selectedFile.getName()
	                  + "\" existe déjà.\nVoulez-vous le remplacez ?", 
	                  "Remplacer",
	                  JOptionPane.YES_NO_OPTION, 
	                  JOptionPane.WARNING_MESSAGE );
	            if (response != JOptionPane.YES_OPTION) return;  // Annuler l'enregistrement
	         }
	    PrintWriter out = null;
	    try{
	    	FileWriter stream=new FileWriter(selectedFile);
	    	out=new PrintWriter(stream);
	    }catch(Exception e){ // Echec de l'ouverture du fihier
	    	JOptionPane.showMessageDialog(fileDialog, this,"Nous sommes desole mais une erreur s'est produite.", option);
	    }
	    try {
	    		//entete du fichier
	    		out.println("AFG file");
	            out.println("Path : "+selectedFile);
	            out.println("Date of creation : "+recupDatActu());
	            // Contenu du fichier (liste des formes + leurs attributs)
	            for(int i=0; i<model.getFormes().size();i++){
	              	out.println(model.getFormes().get(i).toString());
	            }
	            out.close();
	            if (out.checkError())
	               throw new IOException("Output error.");
	            fileName = selectedFile;
	            //this.setTitle("AFG : " + fileName.getName());
	         }
	         catch (Exception e) { // Echec de l'enregistrement des donnees
	            JOptionPane.showMessageDialog(fileDialog, this,
	               "Nous sommes desole mais une erreur s'est produite.\n" + e, option);
	         }   

	}
	/**
	 * retourne la date actuelle (permet d'enregistrer la date de creation du fichier
	 * @return curDate
	 */
	public String recupDatActu(){
		Date temp = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String curDate = dateFormat.format(temp);
		return curDate;
	}
}
