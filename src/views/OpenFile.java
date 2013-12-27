package views;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Coord;
import models.Model;

/**
 * 
 * @author François Lamothe Guillaume Lecocq Alexandre Ravaux
 * 
 *
 */
public class OpenFile {
	ArrayList<String>listLine=new ArrayList<String>();
	public OpenFile(MainFrame m, Model model){
		 JFileChooser chooser = new JFileChooser();
		 //ne peut ouvrir que des fichiers AFG ou SVG
		 FileNameExtensionFilter filter = new FileNameExtensionFilter("AFG & SVG Images", "afg", "svg");
		 chooser.setFileFilter(filter);
		 //Component parent = null;
		 int returnVal = chooser.showOpenDialog(chooser);
		 //DEBUG : pour voir si le nom du fichier est bien celui qu'on voulait
		 if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("File chosen: " +
		       chooser.getSelectedFile().getName());
		 }  
		 if (returnVal != JFileChooser.APPROVE_OPTION) return; 
	     File selectedFile = chooser.getSelectedFile();
	     System.out.println(selectedFile);
	     /* DEBUG : TESTE LE TYPE DE FICHIER */
	     if(chooser.getSelectedFile().getName().endsWith(".svg")) System.out.println("C'est un fichier au format SVG");
	     if(chooser.getSelectedFile().getName().endsWith(".afg")) System.out.println("C'est un fichier au format AFG");
	     /* recupere le contenu du fichier dans un ArrayList */
	     try{
	    	 // Création du flux bufférisé sur un FileReader, immédiatement suivi par un 
	    	 // try/finally, ce qui permet de ne fermer le flux QUE s'il le reader
	    	 // est correctement instancié (évite les NullPointerException)
	    	 BufferedReader buff = new BufferedReader(new FileReader(selectedFile));
	    	 int i=0;
	        try {
		       	String line;
		       	// Lecture du fichier ligne par ligne. Cette boucle se termine
		       	// quand la méthode retourne la valeur null.
		       	while ((line = buff.readLine()) != null) {
		       		listLine.add(line);
		       		/* DEBUG : pour voir si toutes les lignes ont bien ete enregistrees*/
		       		System.out.println(listLine.get(i));
		       		i++;
		       	}
	        }finally {
		        	// dans tous les cas, on ferme nos flux
		        	buff.close();
	        }
	     }catch (IOException ioe) {
	        	// erreur de fermeture des flux
	        	JOptionPane.showMessageDialog(chooser, "Nous sommes desole, mais une erreur est apparue lors de l'ouverture du fichier :\n" + ioe);
	        	return;
	     }
	     /* CREER NOTRE DESSIN A PARTIR D'UN SVG */
	     if(chooser.getSelectedFile().getName().endsWith(".svg")){
	        for(int i=0;i<listLine.size();i++){
	        	System.out.println(listLine.get(i));
	        	/* Repere la balise <line> pour creer la ligne */
	        	if(listLine.get(i).startsWith("<line")){
	        		System.out.println("une ligne !");
	        		//while()
	        	}
	        }
	     //sinon c'est un fichier AFG	
	     }else{
	       	System.out.println("Not yet implemented");
	     }
        
	}
}
