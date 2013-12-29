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
import models.Oval;
import models.Polygon;
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
	private String color, r, g, b, polygon;
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
	    		out.println("<?xml version=\"1.0\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1 Basic//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11-basic.dtd\">");
	    		//commentaire XML
	    		out.println("<!-- Created with AFG -->");
	    		out.println("<!-- SVG 1.1 Basic (W3C standard) -->");
	    		//svg
	    		out.println("<svg width=\"500px\" height=\"500px\" viewBox=\"0 0 500 500\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
	    		/* ECRITURE DES FORMES A PARTIR DE LA LISTE DES FORMES */
	            for(int i=0; i<model.getFormes().size();i++){
	            	/* LINE */
		            if(model.getFormes().get(i) instanceof Line ){
		            	/* DESSIN DE LA LIGNE */
		            	out.println("<line x1=\""+model.getFormes().get(i).getPoints().get(0).getX()+"\" y1=\""+model.getFormes().get(i).getPoints().get(0).getY()+"\" x2=\""+model.getFormes().get(i).getPoints().get(1).getX()+"\" y2=\""+model.getFormes().get(i).getPoints().get(1).getY()+"\" style=\" stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" />");
		            }
		            /* RECTANGLE */
		            if(model.getFormes().get(i) instanceof Rectangle){
		            	int x_rect, y_rect, width, height, x_temp, y_temp;
		            	/* Calcul distance entre deux points : calcul cartesien */
		            	x_temp=model.getFormes().get(i).getPoints().get(1).getX()-model.getFormes().get(i).getPoints().get(0).getX();
		            	y_temp=model.getFormes().get(i).getPoints().get(1).getY()-model.getFormes().get(i).getPoints().get(0).getY();
		            	width=(int) Math.sqrt((int)Math.pow((double)x_temp,2.0)+(int)Math.pow((double)y_temp,2.0));
		            	x_temp=model.getFormes().get(i).getPoints().get(2).getX()-model.getFormes().get(i).getPoints().get(1).getX();
		            	y_temp=model.getFormes().get(i).getPoints().get(2).getY()-model.getFormes().get(i).getPoints().get(1).getY();
		            	height=(int) Math.sqrt((int)Math.pow((double)x_temp,2.0)+(int)Math.pow((double)y_temp,2.0));
		            	/* On recupere les coordonnees du coin gauche de rectangle */
		            	if(model.getFormes().get(i).getPoints().get(0).getX()>model.getFormes().get(i).getPoints().get(1).getX()){
		            		x_rect=model.getFormes().get(i).getPoints().get(1).getX();
		            		if(model.getFormes().get(i).getPoints().get(1).getY()>model.getFormes().get(i).getPoints().get(2).getY()){
		            			y_rect=model.getFormes().get(i).getPoints().get(2).getY();
		            			
		            		}else{
		            			y_rect=model.getFormes().get(i).getPoints().get(1).getY();
		            		}
		            	}else{
		            		x_rect=model.getFormes().get(i).getPoints().get(0).getX();
		            		if(model.getFormes().get(i).getPoints().get(1).getY()>model.getFormes().get(i).getPoints().get(3).getY()){
		            			y_rect=model.getFormes().get(i).getPoints().get(3).getY();
		            			
		            		}else{
		            			y_rect=model.getFormes().get(i).getPoints().get(0).getY();
		            		}
		            	}
		            	/* DESSIN DU RECTANGLE */
		            	if(model.getFormes().get(i).isFill()==true){//teste si c'est un rectangle plein
		            		out.println("<rect x=\""+x_rect+"\" y=\""+y_rect+"\" width=\""+width+"\" height=\""+height+"\" style=\"fill:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" />");
		            	}else{//si c'est un rectangle vide on ajoute fill="none"
		            		out.println("<rect x=\""+x_rect+"\" y=\""+y_rect+"\" width=\""+width+"\" height=\""+height+"\" fill=\"none\" style=\"stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" />");
		            	}            	
		            }
		            /* POLYGON */
		            if(model.getFormes().get(i) instanceof Polygon){
		            	out.println("<!-- polygon detected -->");
		            	polygon="<polygon points=\"";
		            	//je recupere tous les points
		            	for(int j=0; j<model.getFormes().get(i).getPoints().size();j++){
		            		 out.println("<!--"+model.getFormes().get(i).getPoints().get(j)+"-->");
		            		 polygon+=model.getFormes().get(i).getPoints().get(j).getX()+","+model.getFormes().get(i).getPoints().get(j).getY()+" ";
		            	}
		            	//je recupere les valeurs rgb du polygone
		            	if(model.getFormes().get(i).isFill()==true){//teste si c'est un polygon plein
		            		polygon+="\" style=\" fill:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\"/>";
		            	}else{//fill="none"
		            		polygon+="\" fill=\"none\" style=\" stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\"/>";
		            	}
		            	//ecriture du polygone dans le SVG
		            	out.println(polygon);     
		            }
		            /* CIRCLE */
		            if(model.getFormes().get(i) instanceof Oval){
		            	System.out.println(model.getFormes().get(i).getPos().getX());
		            	int cx=model.getFormes().get(i).getPos().getX()+(model.getFormes().get(i).getSz().getX()/2);
		            	int cy=model.getFormes().get(i).getPos().getY()+(model.getFormes().get(i).getSz().getY()/2);
		            	if(model.getFormes().get(i).isFill()==true){
		            		out.println("<ellipse cx=\""+cx+"\" cy=\""+cy+"\" rx=\""+model.getFormes().get(i).getSz().getX()/2+"\" ry=\""+model.getFormes().get(i).getSz().getY()/2+"\" style=\" fill:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\"/>");
		            	}else{
		            		out.println("<ellipse cx=\""+cx+"\" cy=\""+cy+"\" rx=\""+model.getFormes().get(i).getSz().getX()/2+"\" ry=\""+model.getFormes().get(i).getSz().getY()/2+"\" fill=\"none\" style=\" stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\"/>");
		            	}
		            }
		            /* DEBUG 
		            out.println("<!--"+ model.getFormes().get(i)+ "-->");
		            out.println("<!--"+model.getFormes().get(i).getPos().getX()+"-->");
		            out.println("<!--"+model.getFormes().get(i).getPos().getY()+"-->");
		            out.println("<!--"+ model.getFormes().get(i).getPoints()+ "-->");
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
