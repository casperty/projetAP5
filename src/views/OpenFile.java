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
import models.Oval;
import models.Polygon;
import models.Rectangle;

/**
 * 
 * @author Fran�ois Lamothe Guillaume Lecocq Alexandre Ravaux
 *
 */
public class OpenFile {
	private ArrayList<String>listLine=new ArrayList<String>();
	private String ligne, ligneRGB, rect, rectRGB, polygon, polygonRGB, ellipse, ellipseRGB, x1, y1, x2, y2, r, g, b, h, w, tmp, a;
	private int i, indexFill;
	private boolean remplissage;
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
	    	 int i=0;
	    	 try {
	    		 String line;
	    		 /* LECTURE LIGNE PAR LIGNE */
	    		 while ((line = buff.readLine()) != null) {//fin de lecture quand on retourne la valeur null
	    			 listLine.add(line);
	    			 /* DEBUG : pour voir si toutes les lignes ont bien ete enregistrees
						System.out.println(listLine.get(i));*/
	    			 i++;
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
	    	 for(int i=0;i<listLine.size();i++){
	        	/* RECREER LA LIGNE */
	        	if(listLine.get(i).startsWith("<line")){//repere la balise <line>
	        		//DEBUG : System.out.println(listLine.get(i));
	        		ligne=listLine.get(i);
	        	
	        		/* RECUPERATION DES COORDONNES */
	        		x1=getProperties(ligne,"x1=\"","\" y1");
	        		y1=getProperties(ligne,"y1=\"","\" x2");
	        		x2=getProperties(ligne,"x2=\"","\" y2");
	        		y2=getProperties(ligne,"y2=\"","\" style");
	        		System.out.println(x1+","+y1+","+x2+","+y2);
	        		
	        		/* RECUPERATION DE LA COULEUR */
	        		ligneRGB=listLine.get(i).substring(listLine.get(i).indexOf("rgb(")+"rgb(".length(), listLine.get(i).indexOf(");"));
	        		getColor(ligneRGB);
	        		//on recupere la valeur alpha
	        		a=getProperties(listLine.get(i),"opacity=\"","\"/>");
	        		
	        		//je recupere les valeurs importantes pour la creation de notre ligne
	        		Coord pos = new Coord(Integer.parseInt(x1),Integer.parseInt(y1));
	        		Coord sz = new Coord(Integer.parseInt(x2)-Integer.parseInt(x1),Integer.parseInt(y2)-Integer.parseInt(y1));
	        		ColorModel c= new ColorModel(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b),getAlpha());
	        		model.addForme(new Line(pos, sz, c, false, 0));
	        		//DEBUG : System.out.println("apres ajout"+model.getFormes());
	        	}
	        	/* RECREER LE RECTANGLE */
	        	if(listLine.get(i).startsWith("<rect")){//repere la balise <line>
	        		//DEBUG : System.out.println(listLine.get(i));
	        		rect=listLine.get(i);
	        		
	        		/* RECUPERATION DES COORDONNES */
	        		x1=getProperties(rect,"x=\"","\" y");
	        		y1=getProperties(rect,"y=\"","\" width");
	        		w=getProperties(rect,"width=\"","\" height");
	        		h=getProperties(rect,"height=\"","\" style");
	        		
	        		/* RECUPERATION DE LA COULEUR */
	        		rectRGB=listLine.get(i).substring(listLine.get(i).indexOf("rgb(")+"rgb(".length(), listLine.get(i).indexOf(");"));
	        		getColor(rectRGB);
	        		//on recupere la valeur alpha
	        		a=getProperties(rect,"opacity=\"","\"/>");

	        		
	        		/* TESTE LE REMPLISSAGE DU RECTANGLE */
	        		//si "fill=\"none\"" apparait dans rectCoord alors index >=0 sinon <0
	        		indexFill=rect.indexOf("fill=\"none\"");
	        		if(indexFill<0){//le rectangle est plein
	        			remplissage=true;
	        		}else{
	        			remplissage=false;	
	        		}
	        		Coord pos = new Coord(Integer.parseInt(x1),Integer.parseInt(y1));
		        	Coord sz = new Coord(Integer.parseInt(w),Integer.parseInt(h));
		        	ColorModel c= new ColorModel(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b),getAlpha());
		        	model.addForme(new Rectangle(pos, sz, c, remplissage));
	        		//DEBUG : System.out.println("apres ajout"+model.getFormes());
	        	}
	        	if(listLine.get(i).startsWith("<polygon")){
	        		//je recupere des coordonnees par exemple "260,91 433,66 411,196 419,380 290,242 341,148"
	        		polygon=listLine.get(i).substring(listLine.get(i).indexOf("points")+"points=\"".length(), listLine.get(i).indexOf("\" style"));
	        		//je remplace les espaces par des virgules, ensuite on separera toutes les coordonnees en se reperant avec les virgules
	        		//"260,91 433,66 411,196 419,380 290,242 341,148" devient : "260,91,433,66,411,196,419,380,290,242,341,148"
	        		polygon=polygon.replace(" ", ",");
	        		polygonRGB=listLine.get(i).substring(listLine.get(i).indexOf("rgb(")+"rgb(".length(), listLine.get(i).indexOf(");"));
        		
	        		/* RECUPERATION DE LA COULEUR */
	        		getColor(polygonRGB);
	        		//on recupere la valeur alpha
	        		a=getProperties(listLine.get(i),"opacity=\"","\"/>");
	        		
	        		/* RECUPERATION DU PREMIER COUPLE DE COORDONNEES DU POLYGONE */
	        		getPropertiesPolygon(polygon);
	        		ColorModel c= new ColorModel(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b),getAlpha());
	        		
	        		/* TESTE LE REMPLISSAGE DU POLYGONE */
	        		indexFill=listLine.get(i).indexOf("fill=\"none\"");
	        		if(indexFill<0){//le rectangle est plein
	        			remplissage=true;
	        		}else{
	        			remplissage=false;	
	        		}
	        		
	        		/* CREATION DU POLYGONE */
	        		Polygon poly=new Polygon(new Coord(Integer.parseInt(x1),Integer.parseInt(y1)),c,remplissage);
	        		
	        		/* RECUPERATION DES AUTRES COORDONNEES DU POLYGONE */
	        		int j=0;
	        		while(j<polygon.length()){
		        		getPropertiesPolygon(polygon);
		        		/* AJOUTS DES AUTRES POINTS */
		        		poly.setPoints(new Coord(Integer.parseInt(x1),Integer.parseInt(y1)));
		        		j++;
	        		}
	        		
	        		/* AJOUT DU POLYGONE AU DESSIN */
	        		model.addForme(poly);
	        	}
	        	if(listLine.get(i).startsWith("<ellipse")){
	        		ellipse=listLine.get(i);
	        		
	        		/* RECUPERATION DES COORDONNES */
	        		x1=getProperties(ellipse,"cx=\"","\" cy");
	        		y1=getProperties(ellipse,"cy=\"","\" rx");
	        		w=getProperties(ellipse,"rx=\"","\" ry");
	        		h=getProperties(ellipse,"ry=\"","\" style");
	        		/* RECUPERATION DE LA COULEUR */
	        		ellipseRGB=listLine.get(i).substring(listLine.get(i).indexOf("rgb(")+"rgb(".length(), listLine.get(i).indexOf(");"));   		
	        		getColor(ellipseRGB);
	        		//on recupere la valeur alpha
	        		a=getProperties(ellipse,"opacity=\"","\"/>");
	        		
	        		indexFill=ellipse.indexOf("fill=\"none\"");
	        		if(indexFill<0){//le cercle est plein
	        			remplissage=true;
	        		}else{
	        			remplissage=false;	
	        		}
	        		
	        		int x=Integer.parseInt(x1)-Integer.parseInt(w);
	        		int y=Integer.parseInt(y1)-Integer.parseInt(h);
	        		int szX=Integer.parseInt(w)*2;
	        		int szY=Integer.parseInt(h)*2;
	        		
	        		Coord pos = new Coord(x,y);
	        		Coord sz= new Coord(szX,szY);
		        	ColorModel c= new ColorModel(Integer.parseInt(r),Integer.parseInt(g),Integer.parseInt(b),getAlpha());
		        	
		        	Oval o=new Oval(sz, c, remplissage);
		        	o.setPos(pos);
		        	model.addForme(o);
	        	}
	        	
	        	else{
	        		
	        	}
	        	m.repaint();
	        }
	     /**
	      * CREER NOTRE DESSIN A PARTIR D'UN AFG
	      * 
	      */
	     //on peut se permettre de mettre else car seuls deux formats de fichiers peuvent etre ouverts (afg et svg)
	     }else if(chooser.getSelectedFile().getName().endsWith(".afg")){
	    	 for(int i=0;i<listLine.size();i++){
		    	 if(listLine.get(i).startsWith("Rectangle")){
		    		 String tmp=listLine.get(i).substring(listLine.get(i).indexOf("Rectangle/(")+"Rectangle/(".length(), listLine.get(i).indexOf(","));
		    		 System.out.println(tmp);
		    	 }
	    	 }
	     }
        
	}
	/**
	 * Recupere la valeur a partir de la chaine donnee en parametre, cette valeur est entre deux mots donn�s �galement en parametre.
	 * Exemple : String chaine="x1=34 y1=23"; String x1=getProperties(chaine, x1=, y1); x1 vaut "34"
	 * @param chaine
	 * @param debut
	 * @param fin
	 * @return valeur 
	 */
	public String getProperties(String chaine, String debut, String fin){
		String y="";
		int j=chaine.indexOf(debut)+debut.length();
		y=chaine.substring(j, chaine.indexOf(fin));
		return y;
	}
	/**
	 * Recupere les coordonnes x et y du polygone
	 * @param chaine
	 */
	public void getPropertiesPolygon(String chaine){
		x1=""; y1="";
		i=0;
		readValue(chaine,",");
		x1=tmp;
		i++;
		readValue(chaine,",");
		y1=tmp;
		//mise � jour de la chaine
		i++;
		polygon=chaine.substring(i,chaine.length());
	}
	/**
	 * Recupere les valeurs RGB 
	 * @param chaine
	 */
	public void getColor(String chaine){
		r=""; g=""; b="";
		i=0;
		readValue(chaine,",");
		r=tmp;
		i++;
		readValue(chaine,",");
		g=tmp;
		i++;
		readValue(chaine,",");
		b=tmp;
		//recuperation de la valeur alpha >> Pour le afg
		i++;
		readValue(chaine,",");
		a=tmp;

	}
	/**
	 * A partir de la chaine de caractere on lit et recupere les caracteres jusqu'a ce qu'on tombe sur le separateur.
	 * @param chaine
	 * @param separator
	 */
	public void readValue(String chaine, String separator){
		tmp="";
		while(i<chaine.length()){
			if (!chaine.substring(i,i+1).equals(separator)){
				tmp+=chaine.substring(i,i+1);
			}else{
				break;
			}
			i++;
		}
	}
	/**
	 * Ceci permet de recuperer la valeur alpha qui est en pourcentage en valeur decimal (0 � 255)
	 * @return alpha 
	 */
	public int getAlpha(){
		double value=Double.parseDouble(a);
		double calc=Math.round(value*255);
		return (int)calc;
	}
	
}
