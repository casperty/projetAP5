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

import models.ColorModel;
import models.Coord;
import models.Line;
import models.Model;
import models.Rectangle;

/**
 * 
 * @author François Lamothe Guillaume Lecocq Alexandre Ravaux
 * 
 *
 */
public class OpenFile {
	private ArrayList<String>listLine=new ArrayList<String>();
	private String ligne, rect, x1, y1, x2, y2, r, g, b, h, w, tmpCoord, tmpRGB;
	private int i;
	public OpenFile(MainFrame m, Model model){
		 JFileChooser chooser = new JFileChooser();
		 //ne peut ouvrir que des fichiers AFG ou SVG
		 FileNameExtensionFilter filter = new FileNameExtensionFilter("AFG & SVG Images", "afg", "svg");//on ne veut pouvoir ouvrir que des fichiers AFG ou SVG
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
 			model.getFormes().clear();
 			m.repaint();
	     /* CREER NOTRE DESSIN A PARTIR D'UN SVG */
	     if(chooser.getSelectedFile().getName().endsWith(".svg")){
	        for(int i=0;i<listLine.size();i++){
	        	/* RECREER LA LIGNE */
	        	if(listLine.get(i).startsWith("<line")){//repere la balise <line>
	        		//DEBUG : System.out.println(listLine.get(i));
	        		ligne=listLine.get(i);
	        		//je recupere les valeurs importantes pour la creation de notre ligne
	        		getPropertiesLine(ligne);
	        		Coord pos = new Coord(Integer.parseInt(x1),Integer.parseInt(y1));
	        		Coord sz = new Coord(Integer.parseInt(x2)-Integer.parseInt(x1),Integer.parseInt(y2)-Integer.parseInt(y1));
	        		ColorModel c= new ColorModel(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b),255);
	        		model.addForme(new Line(pos, sz, c, false, 0));
	        		m.repaint();
	        		//DEBUG : System.out.println("apres ajout"+model.getFormes());
	        	}
	        	if(listLine.get(i).startsWith("<rect")){
	        		System.out.println("un rectangle !");
	        		//System.out.println("\" width=\"436\" height=\"425\" style=\" fill:rgb(".length());
	        		rect=listLine.get(i);
	        		/* DETECTION VALEUR FILL*/
	        		//si "fill=\"none\"" apparait dans rect alors index >=0 sinon <0
	        		int indexFill=rect.indexOf("fill=\"none\"");
	        		if(indexFill<0){//le rectangle est plein
	        			getPropertiesFullRect(rect);
		        		Coord pos = new Coord(Integer.parseInt(x1),Integer.parseInt(y1));
		        		Coord sz = new Coord(Integer.parseInt(w),Integer.parseInt(h));
		        		ColorModel c= new ColorModel(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b),255);
		        		model.addForme(new Rectangle(pos, sz, c, true));
	        		}else{
	        			System.out.println("NULL");
	        			getPropertiesEmptyRect(rect);
	        			Coord pos = new Coord(Integer.parseInt(x1),Integer.parseInt(y1));
		        		Coord sz = new Coord(Integer.parseInt(w),Integer.parseInt(h));
		        		ColorModel c= new ColorModel(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b),255);
		        		model.addForme(new Rectangle(pos, sz, c, false));
	        		}

	        		//DEBUG : System.out.println("apres ajout"+model.getFormes());
	        	}
	        }
	     //sinon c'est un fichier AFG	
	     }else{
	       	System.out.println("Not yet implemented");
	     }
        
	}
	/**
	 * Methode propre a l'ouverture d'un fichier au format SVG
	 * Methode permettant de recuperer sous formes de chaines de caracteres (String) les principales valeurs nécessaires pour reproduire la ligne.
	 */
	/* RECUPERATION DES PROPRIETES DE LA LIGNE */
	public void getPropertiesLine(String ligne){
		x1=""; y1=""; x2=""; y2=""; r=""; g=""; b="";
		/*
		int i=10;
		while(i<ligne.length()){
			if (!ligne.substring(i,i+1).equals("\"")){
				x1+=ligne.substring(i,i+1);
			}else{
				break;
			}
			i++;
		}*/
		/* VALEURS x1,y1 ET x2, y2 */
		//exemple pour comprendre la fonction : <line x1="274" y1="60" x2="153" y2="436" style=" stroke:rgb(0,0,0); stroke-width:2" />
		//on met i à 10 pour zapper le <line x1="
		i=10;
		lectureCoord(ligne);
		x1=tmpCoord;
		i=i+6;
		lectureCoord(ligne);
		y1=tmpCoord;
		i=i+6;
		lectureCoord(ligne);
		x2=tmpCoord;
		i=i+6;
		lectureCoord(ligne);
		y2=tmpCoord;
		/* VALEURS RGB */
		//on est arrivé ici " style=" stroke:rgb(0,0,0); stroke-width:2" />
		//on va couper le  " style=" stroke:rgb( pour obtenir 0,0,0); stroke-width:2" />
		i=i+21;
		lectureCoord(ligne);
		//maintenant on ne s'interresse plus a la chaine ligne mais tmpCoord pour recuperer les valeurs 0,0,0
		i=0;
		lectureRGB();//on recupere le 0
		r=tmpRGB;
		i++;
		lectureRGB();
		g=tmpRGB;
		i++;
		lectureRGB();
		b=tmpRGB;
	}
	/**
	 * Methode propre a l'ouverture d'un fichier au format SVG
	 * Methode permettant de recuperer sous formes de chaines de caracteres (String) les principales valeurs nécessaires pour reproduire le rectangle.
	 */
	/* RECUPERATION DES PROPRIETES DU RECTANGLE */
	public void getPropertiesFullRect(String rect){
		x1=""; y1=""; w=""; h=""; r=""; g=""; b="";
		i=9;
		lectureCoord(rect);
		x1=tmpCoord;
		i=i+5;
		lectureCoord(rect);
		y1=tmpCoord;
		i=i+9;
		lectureCoord(rect);
		w=tmpCoord;
		i=i+10;
		lectureCoord(rect);
		h=tmpCoord;
		i=i+18;
		lectureCoord(rect);
		i=0;
		lectureRGB();
		r=tmpRGB;
		i++;
		lectureRGB();
		g=tmpRGB;
		i++;
		lectureRGB();
		b=tmpRGB;
	}
	public void getPropertiesEmptyRect(String rect){
		x1=""; y1=""; w=""; h=""; r=""; g=""; b="";
		i=9;
		lectureCoord(rect);
		x1=tmpCoord;
		i=i+5;
		lectureCoord(rect);
		y1=tmpCoord;
		i=i+9;
		lectureCoord(rect);
		w=tmpCoord;
		i=i+10;
		lectureCoord(rect);
		h=tmpCoord;
		i=i+32;
		lectureCoord(rect);
		i=0;
		lectureRGB();
		r=tmpRGB;
		i++;
		lectureRGB();
		g=tmpRGB;
		i++;
		lectureRGB();
		b=tmpRGB;
	}
	//pour recuper les coordonnees de la ligne
	public void lectureCoord(String chaine){
		tmpCoord="";
		while(i<chaine.length()){
			if (!chaine.substring(i,i+1).equals("\"")){
				tmpCoord+=chaine.substring(i,i+1);
			}else{
				break;
			}
			i++;
		}
	}
	//pour recuperer les couleurs
	public void lectureRGB(){
		tmpRGB="";
		while(i<tmpCoord.length()){
			if (!tmpCoord.substring(i,i+1).equals(",") && !tmpCoord.substring(i,i+1).equals(")")){
				tmpRGB+=tmpCoord.substring(i,i+1);
			}else{
				break;
			}
			i++;
		}
	}

}
