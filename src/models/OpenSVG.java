package models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import views.MainFrame;

public class OpenSVG {
	private String ligne, ligneRGB, rect, rectRGB, polygon, polygonRGB, ellipse, ellipseRGB, x1, y1, x2, y2, r, g, b, h, w, tmp, a;
	private int i, indexFill;
	private boolean remplissage;
	public OpenSVG(Model model, ArrayList<String> listLine){
   	 for(int i=0;i<listLine.size();i++){
   		     /* TAILLE DU CANVAS */
        if(listLine.get(i).startsWith("<svg")){
        	ligne=listLine.get(i);
        	w=getProperties(ligne,"width=\"","px");
        	h=getProperties(ligne,"height=\"","px\" viewBox=");
        	Coord size=new Coord(Integer.parseInt(w),Integer.parseInt(h));
            model.setSz(size);
        }
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
	        	Rectangle r = new Rectangle(pos, sz, c, remplissage);
	        	r.setCreated(true);
	        	r.onMouseReleased(new Coord(0,0));
	        	model.addForme(r);
     		//DEBUG : System.out.println("apres ajout"+model.getFormes());
     	}
     	if(listLine.get(i).startsWith("<polygon")){
     		//je recupere des coordonnees par exemple "260,91 433,66 411,196 419,380 290,242 341,148"
     		polygon=listLine.get(i).substring(listLine.get(i).indexOf("points")+"points=\"".length(), listLine.get(i).indexOf("\" style"));
     		//je remplace les espaces par des virgules, ensuite on separera toutes les coordonnees en se reperant avec les virgules
     		//"260,91 433,66 411,196 419,380 290,242 341,148" devient : "260,91,433,66,411,196,419,380,290,242,341,148"
     		ArrayList<Coord> pts = (ArrayList<Coord>) getPts(polygon);
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
//	        		poly.getPoints().add(new Coord(Integer.parseInt(x1),Integer.parseInt(y1)));
	        		j++;
     		}
     		for(Coord coord : pts){
     			poly.getPoints().add(coord);
     		}
     		/* AJOUT DU POLYGONE AU DESSIN */
     		poly.updatePosSz();
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
     		
     		indexFill=rect.indexOf("fill=\"none\"");
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
     	if(listLine.get(i).startsWith("<image")){
     		String imgobj = listLine.get(i);
     		String[] str = imgobj.split(" ");
     		int x=0,y=0,width=0,height=0;
     		String path=null;
     		for(String s : str){
     			if(s.contains("x=")){
     				x=Integer.parseInt(s.substring(s.indexOf("\"")+1,s.lastIndexOf("\"")));
     			}else if(s.contains("y=")){
     				y=Integer.parseInt(s.substring(s.indexOf("\"")+1,s.lastIndexOf("\"")));
     			}else if(s.contains("width=")){
     				width=Integer.parseInt(s.substring(s.indexOf("\"")+1,s.lastIndexOf("\"")));
     			}else if(s.contains("height=")){
     				height=Integer.parseInt(s.substring(s.indexOf("\"")+1,s.lastIndexOf("\"")));
     			}else if(s.contains("href")){
     				path = s;
     			}
     		}
     		ImgObject img = new ImgObject(new Coord(x,y), new Coord(width,height), ColorModel.BLACK, true, getData(path));
     		img.setCreated(true);
     		img.onMouseReleased(new Coord(0,0));
     		model.addForme(img);
     	}
   	 }
	}
	
	//test fix poly
	public List<Coord> getPts(String s){
		ArrayList<Coord> l = new ArrayList<Coord>();
		String[] str = s.split(" ");
		for(String coord : str){
			int x = Integer.parseInt(coord.split(",")[0]);
			int y = Integer.parseInt(coord.split(",")[1]);
			l.add(new Coord(x,y));
		}
		return l;
	}
	
 	/**
 	 * Recupere la valeur a partir de la chaine donnee en parametre, cette valeur est entre deux mots donnés également en parametre.
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
 		//mise à jour de la chaine
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
 	 * Ceci permet de recuperer la valeur alpha qui est en pourcentage en valeur decimal (0 à 255)
 	 * @return alpha 
 	 */
 	public int getAlpha(){
 		double value=Double.parseDouble(a);
 		double calc=Math.round(value*255);
 		return (int)calc;
 	}
 	
 	public	byte[] getData(String s){
 		ArrayList<Byte> l = new ArrayList<Byte>();
 		String str = s.substring(s.indexOf("base64,")+"base64,".length(), s.lastIndexOf("="));
 		return DatatypeConverter.parseBase64Binary(str);
 	}
}
