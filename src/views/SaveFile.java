package views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SaveFile {
	private JFileChooser fileDialog;
	private File fileName,selectedFile; 
	public SaveFile(){
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
	         if (selectedFile.exists()) {  // Le fichier existe déjà devons nous nous ecraser le fichier existant ?
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
	    }catch(Exception e){ //Echec de l'ouverture du fihier
	    	JOptionPane.showMessageDialog(fileDialog, this,"Nous sommes désolé mais une erreur s'est produite.", option);
	    }
	    try {
	    		System.out.println("Entré dans la méthode d'enregistrement");
	            out.println("AFG file");
	            /*
	            	Contenu du fichier >> out.println(...);
	            */
	            out.close();
	            if (out.checkError())
	               throw new IOException("Output error.");
	            fileName = selectedFile;
	            //this.setTitle("AFG : " + fileName.getName());
	         }
	         catch (Exception e) { //Echec de l'enregistrement des données
	            JOptionPane.showMessageDialog(fileDialog, this,
	               "Nous sommes désolé mais une erreur s'est produite.\n" + e, option);
	         }   

	}
}
