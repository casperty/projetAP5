package views;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Model;
import models.ExportSVG;

/**
 * 
 * @author Francois Lamothe Guillaume Leccoq Alexandre Ravaux
 * Classe qui gere la sauvegarde du fichier
 *
 */
public class ExportSVGFile {
	private JFileChooser fileDialog;
	private File fileName,selectedFile; 
	/**
	 * Fenetre pour sauvegarder le fichier, 
	 * Se lance quand on clique sur Ctrl+E ou bien File > Export > Export to SVG
	 * Appelé dans MenuListener
	 */
	public ExportSVGFile(Model model){
		if(fileDialog==null){
			fileDialog = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter( "SVG file", "svg");
			fileDialog.setFileFilter(filter);
		} 
		if(fileName==null){
			selectedFile = new File("Untitled.svg");
		}else{
			selectedFile = new File(fileName.getName());
		}
		fileDialog.setSelectedFile(selectedFile); 
	    fileDialog.setDialogTitle("Export the file in SVG");
	    int option = fileDialog.showSaveDialog(fileDialog);
		if (option != JFileChooser.APPROVE_OPTION) return;  // Annuler ou fermeture de la fenetre.
	         selectedFile = fileDialog.getSelectedFile();
	         String path=""+selectedFile;
	         if (!path.endsWith(".svg")){  // On a oublié de mettre l'extension ? Pas grave le logiciel s'en charge :D
		          path+=".svg";
		          selectedFile.delete();
		          selectedFile = new File(path);
		     }
	         if (selectedFile.exists()) {  // Le fichier existe deja , devons nous ecraser le fichier existant ?
	            int response = JOptionPane.showConfirmDialog( fileDialog,
	                  "Le fichier \"" + selectedFile.getName()
	                  + "\" existe deja .\nVoulez-vous le remplacez ?", 
	                  "Remplacer",
	                  JOptionPane.YES_NO_OPTION, 
	                  JOptionPane.WARNING_MESSAGE );
	            if (response != JOptionPane.YES_OPTION) return;  // Annuler l'enregistrement
	         }
	    PrintWriter out = null;
	    try{
	    	FileWriter stream=new FileWriter(selectedFile);
	    	out=new PrintWriter(stream);
	    }catch(Exception e){ // Echec de l'ouverture du fichier
	    	JOptionPane.showMessageDialog(fileDialog, this,"Nous sommes desole mais une erreur s'est produite.", option);
	    }
	    /* ECRITURE DU FICHIER SVG */
	    try {
	    		new ExportSVG(model, out);
	    }catch (Exception e) { // Echec de l'enregistrement des donnees
	    	JOptionPane.showMessageDialog(fileDialog, this, "Nous sommes desole mais une erreur s'est produite.\n" + e, option);
	    }   
	}

	
}
