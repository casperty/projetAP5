package views;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Model;
import models.OpenAFG;
import models.OpenSVG;


/**
 * 
 * @author Francois Lamothe Guillaume Leccoq Alexandre Ravaux
 *
 */
public class OpenFile {
	private ArrayList<String>listLine=new ArrayList<String>();
	/**
	 * Gestion de l'ouverture du dessin. On ouvrer un JFileChooser, un filtre d'extension est appos� (afg ou svg pas d'autres extensions possible) 
	 * puis on regarde l'extension du fichier choisi : si svg on a une procedure donn�e et si c'est en afg c'est une autre proc�dure.
	 * @param m
	 * @param model
	 */
	public OpenFile(MainFrame m, Model model){
		 JFileChooser chooser = new JFileChooser();
		 //On ne peut ouvrir que des fichiers AFG ou SVG
		 FileNameExtensionFilter filter = new FileNameExtensionFilter("AFG & SVG Images", "afg", "svg");//on ne veut pouvoir ouvrir que des fichiers AFG ou SVG
		 chooser.setFileFilter(filter);
		 int returnVal = chooser.showOpenDialog(chooser);
		 /*DEBUG : pour voir si le nom du fichier est bien celui qu'on voulait
		 if(returnVal == JFileChooser.APPROVE_OPTION) {
		       System.out.println("File chosen: " +
		       chooser.getSelectedFile().getName());
		 } */
		 if (returnVal != JFileChooser.APPROVE_OPTION) return; 
	     File selectedFile = chooser.getSelectedFile();
	     System.out.println(selectedFile);
	     /* DEBUG : TESTE LE TYPE DE FICHIER 
	     if(chooser.getSelectedFile().getName().endsWith(".svg")) System.out.println("C'est un fichier au format SVG");
	     if(chooser.getSelectedFile().getName().endsWith(".afg")) System.out.println("C'est un fichier au format AFG");*/
	     /**
	      * Recuperation du contenu du fichier dans une ArrayList
	      */
	     try{
	    	 /*
	    	  * Ouverture d'un flux BufferedReader sur le fichier selectionn�
	    	  * On utilise un try{}catch{} pour �viter les problemes de lectures et les NullPointerException
	    	  */
	    	 BufferedReader buff = new BufferedReader(new FileReader(selectedFile));
	    	 try {
	    		 String line;
	    		 /* LECTURE LIGNE PAR LIGNE */
	    		 while ((line = buff.readLine()) != null) {//fin de lecture quand on retourne la valeur null
	    			 listLine.add(line);
	    			 /* DEBUG : pour voir si toutes les lignes ont bien ete enregistrees
						System.out.println(listLine.get(i));*/
	    		 }
	    	 }finally {
	    		 //dans tous les cas, on ferme nos flux
	    		 buff.close();
	    	 }
	     }catch (IOException ioe) {
	    	 // erreur de fermeture des flux
	    	 JOptionPane.showMessageDialog(chooser, "Nous sommes desole, mais une erreur est apparue lors de l'ouverture du fichier :\n" + ioe);
	    	 return;
	     }
	     /* ON EFFACE LE CANVAS ACTUEL */
	     model.getFormes().clear();
	     m.repaint();
	     /**
	      * CREER NOTRE DESSIN A PARTIR D'UN SVG
	      * On va relire l'ArrayList cr�� et appliquer un m�thode de lecture sp�cifique au format SVG pour ouvrir le logiciel
	      * m�thode (malheureusement) applicable uniquement au SVG cr�� par AFG
	      */
	     if(chooser.getSelectedFile().getName().endsWith(".svg")){
	    	 new OpenSVG(model, listLine);
	     /**
	      * CREER NOTRE DESSIN A PARTIR D'UN AFG
	      * 
	      */
	     //on peut se permettre de mettre else car seuls deux formats de fichiers peuvent etre ouverts (afg et svg)
	     }else if(chooser.getSelectedFile().getName().endsWith(".afg")){
	    	 new OpenAFG(listLine,model);
	     }
        
	}	
}
