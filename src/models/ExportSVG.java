package models;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.DatatypeConverter;

public class ExportSVG {
	private String polygon;
	public ExportSVG(Model model, PrintWriter out) throws IOException{
		/* ENTETE XML */
		out.println("<?xml version=\"1.0\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1 Basic//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11-basic.dtd\">");
		//commentaire XML
		out.println("<!-- Created with AFG -->");
		out.println("<!-- SVG 1.1 Basic (W3C standard) -->");
		//svg
		out.println("<svg width=\""+model.getAreaSz().getX()+"px\" height=\""+model.getAreaSz().getY()+"px\" viewBox=\"0 0 "+model.getAreaSz().getX()+" "+model.getAreaSz().getX()+"\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
		/* ECRITURE DES FORMES A PARTIR DE LA LISTE DES FORMES */
        for(int i=0; i<model.getFormes().size();i++){
        	/* LINE */
            if(model.getFormes().get(i) instanceof Line ){
            	/* DESSIN DE LA LIGNE */
            	out.println("<line x1=\""+model.getFormes().get(i).getPoints().get(0).getX()+"\" y1=\""+model.getFormes().get(i).getPoints().get(0).getY()+"\" x2=\""+model.getFormes().get(i).getPoints().get(1).getX()+"\" y2=\""+model.getFormes().get(i).getPoints().get(1).getY()+"\" style=\" stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" fill-opacity=\""+setAlpha(model.getFormes().get(i).getColor().getA())+"\"/>");
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
            	/* IMAGE */
                if(model.getFormes().get(i).getClass() == ImgObject.class ){
                	out.println("<image x=\""+x_rect+"\" y=\""+y_rect+"\" width=\""+width+"\" height=\""+height+"\""+" xlink:href=\"data:image/jpg;base64,"+getBase64(model.getFormes().get(i))+"\"/>");
                }else{
	            	/* DESSIN DU RECTANGLE */
	            	if(model.getFormes().get(i).isFill()==true){//teste si c'est un rectangle plein
	            		out.println("<rect x=\""+x_rect+"\" y=\""+y_rect+"\" width=\""+width+"\" height=\""+height+"\" style=\"fill:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" fill-opacity=\""+setAlpha(model.getFormes().get(i).getColor().getA())+"\"/>");
	            	}else{//si c'est un rectangle vide on ajoute fill="none"
	            		out.println("<rect x=\""+x_rect+"\" y=\""+y_rect+"\" width=\""+width+"\" height=\""+height+"\" fill=\"none\" style=\"stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" stroke-opacity=\""+setAlpha(model.getFormes().get(i).getColor().getA())+"\"/>");
	            	}  
                }
            }
            /* POLYGON */
            if(model.getFormes().get(i) instanceof Polygon){
            	polygon="<polygon points=\"";
            	//je recupere tous les points
            	for(int j=0; j<model.getFormes().get(i).getPoints().size();j++){
            		 polygon+=model.getFormes().get(i).getPoints().get(j).getX()+","+model.getFormes().get(i).getPoints().get(j).getY()+" ";
            	}
            	//je recupere les valeurs rgb du polygone
            	if(model.getFormes().get(i).isFill()==true){//teste si c'est un polygon plein
            		polygon+="\" style=\" fill:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" fill-opacity=\""+setAlpha(model.getFormes().get(i).getColor().getA())+"\"/>";
            	}else{//fill="none"
            		polygon+="\" fill=\"none\" style=\" stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" stroke-opacity=\""+setAlpha(model.getFormes().get(i).getColor().getA())+"\"/>";
            	}
            	//ecriture du polygone dans le SVG
            	out.println(polygon);     
            }
            /* CIRCLE */
            if(model.getFormes().get(i) instanceof Oval){
            	int cx=model.getFormes().get(i).getPos().getX()+(model.getFormes().get(i).getSz().getX()/2);
            	int cy=model.getFormes().get(i).getPos().getY()+(model.getFormes().get(i).getSz().getY()/2);
            	if(model.getFormes().get(i).isFill()==true){
            		out.println("<ellipse cx=\""+cx+"\" cy=\""+cy+"\" rx=\""+model.getFormes().get(i).getSz().getX()/2+"\" ry=\""+model.getFormes().get(i).getSz().getY()/2+"\" style=\" fill:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" fill-opacity=\""+setAlpha(model.getFormes().get(i).getColor().getA())+"\"/>");
            	}else{
            		out.println("<ellipse cx=\""+cx+"\" cy=\""+cy+"\" rx=\""+model.getFormes().get(i).getSz().getX()/2+"\" ry=\""+model.getFormes().get(i).getSz().getY()/2+"\" fill=\"none\" style=\" stroke:rgb("+model.getFormes().get(i).getColor().getR()+","+model.getFormes().get(i).getColor().getG()+","+model.getFormes().get(i).getColor().getB()+"); stroke-width:2\" stroke-opacity=\""+setAlpha(model.getFormes().get(i).getColor().getA())+"\"/>");
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
	}
	
	
	public double setAlpha(int value){
		return (double)value/255;
	}
	
	public String getBase64(Forme f){
		ImgObject o = (ImgObject)f;
		String s = DatatypeConverter.printBase64Binary(o.getData());
		return s;
	}
	
}
