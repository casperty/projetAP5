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
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Forme;
import models.Line;
import models.Model;
import models.Rectangle;
/**
 * 
 * @author Fran�ois Lamothe Guillaume Leccoq Alexandre Ravaux
 * Classe qui gere la sauvegarde du fichier
 *
 */
public class ExportSVGFile {
	private JFileChooser fileDialog;
	private File fileName,selectedFile; 
	private Model model;
	private String color, r, g, b;
	/**
	 * Fenetre pour sauvegarder le fichier, 
	 * se lance quand on clique sur Ctrl+S ou bien File > Save
	 */
	public ExportSVGFile(Model model){
		this.model=model;
		if(fileDialog==null){
			fileDialog = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter( "SVG file", "svg");
			fileDialog.setFileFilter(filter);
			//File selectedFile;
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
	         if (selectedFile.exists()) {  // Le fichier existe déjà, devons nous ecraser le fichier existant ?
	            int response = JOptionPane.showConfirmDialog( fileDialog,
	                  "Le fichier \"" + selectedFile.getName()
	                  + "\" existe deja�.\nVoulez-vous le remplacez ?", 
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
	    /* ECRITURE DU FICHIER SVG */
	    try {
	    		/* ENTETE XML */
	    		out.println("<?xml version=\"1.0\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20010904//EN\" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd_\">");
	    		//commentaire XML
	    		out.println("<!-- Created with AFG -->");
	    		//svg
	    		out.println("<svg width=\"500px\" height=\"500px\" viewBox=\"0 0 500 500\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
	    		/* ECRITURE DES FORMES A PARTIR DE LA LISTE DES FORMES */
	            for(int i=0; i<model.getFormes().size();i++){
	            	/* LIGNE */
		            if(model.getFormes().get(i) instanceof Line ){
		            	/* DESSIN DE LA LIGNE */
		            	out.println("<line x1=\""+model.getFormes().get(i).getPoints().get(0).getX()+"\" y1=\""+model.getFormes().get(i).getPoints().get(0).getY()+"\" x2=\""+model.getFormes().get(i).getPoints().get(1).getX()+"\" y2=\""+model.getFormes().get(i).getPoints().get(1).getY()+"\" style=\" stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" />");
		            }
		            /* RECTANGLE */
		            if(model.getFormes().get(i) instanceof Rectangle){
		            	int width, height;
		            	/* Calcul distance entre deux points : calcul cartesien */
		            	int x=model.getFormes().get(i).getPoints().get(1).getX()-model.getFormes().get(i).getPoints().get(0).getX();
		            	int y=model.getFormes().get(i).getPoints().get(1).getY()-model.getFormes().get(i).getPoints().get(0).getY();
		            	width=(int) Math.sqrt((int)Math.pow((double)x,2.0)+(int)Math.pow((double)y,2.0));
		            	x=model.getFormes().get(i).getPoints().get(2).getX()-model.getFormes().get(i).getPoints().get(1).getX();
		            	y=model.getFormes().get(i).getPoints().get(2).getY()-model.getFormes().get(i).getPoints().get(1).getY();
		            	height=(int) Math.sqrt((int)Math.pow((double)x,2.0)+(int)Math.pow((double)y,2.0));
		            	/* DESSIN DU RECTANGLE */
		            	out.println("<rect x=\""+model.getFormes().get(i).getPos().getX()+"\" y=\""+model.getFormes().get(i).getPos().getY()+"\" width=\""+width+"\" height=\""+height+"\" style=\" fill:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" />");
		            }
		            else{
		            	out.println("<!-- not implemented -->");
		            }  
		            /* DEBUG */
		            out.println("<!--"+ model.getFormes().get(i)+ "-->");
		            /*
	            	out.println("<!--"+ model.getFormes().get(i).getColor()+ "-->");
	            	out.println("<!--"+model.getFormes().get(i).getPoints().get(1).getX()+","+model.getFormes().get(i).getPoints().get(1).getY()+"-->");
		           	out.println("<!--"+ model.getFormes().get(i).getPoints()+ "-->");
		            out.println("<!--"+ model.getFormes().get(i).getPoints().get(0)+ "-->");
		            out.println("<!--"+ model.getFormes().get(i).getPoints().get(1)+ "-->");*/
		            
	            }
	            out.println("</svg>");
	            // fin de l'ecriture, fermeture du stream
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
	
}
