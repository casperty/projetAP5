package models;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author François Lamothe Guillaume Lecocq Alexandre Ravaux
 * Ouverture et lecture d'un fichier afg.
 */
public class OpenAFG {
	/**
	 * ArrayList qui contient toutes les lignes du fichier
	 */
	private ArrayList<String> lines;
	private Model model;
	/**
	 * Procedure d'ouverture du AFG
	 * @param lines
	 * @param model
	 */
	public OpenAFG(ArrayList<String> lines, Model model){
		this.lines=lines;
		this.model=model;
		process();
	}
	/**
	 * La méthode process() permet de regarder la ligne lue et de regarder de quelle forme il s'agit
	 */
	public void process(){
		for(String str : lines){
			String forme=str.substring(0, str.indexOf("/"));
			if(forme.equalsIgnoreCase("rectangle")){
				createRectangle(str);
			}else if(forme.equalsIgnoreCase("Oval")){
				createOval(str);
			}else if(forme.equalsIgnoreCase("line")){
				createLine(str);
			}else if(forme.equalsIgnoreCase("polygon")){
				createPoly(str);
			}else if(forme.equalsIgnoreCase("imgobject")){
				createImgObj(str);
			}
		}
	}
	/**
	 * Creation de rectangle
	 * @param str la ligne lue du fichier
	 */
	public void createRectangle(String str){
		String[] params = str.split("/");
		Rectangle r = new Rectangle(recupCoord(params[3]), new Coord(0,0), recupColor(params[2]), recupBoolean(params[5]));
    	//recalcul pos/sz
		Coord min = new Coord(9999,9999);
		Coord max = new Coord(0,0);
		for(Coord c1 : recupPts(params[1])){
			if(c1.getX()<min.getX()){
				min.setX(c1.getX());
			}
			if(c1.getY()<min.getY()){
				min.setY(c1.getY());
			}
			if(c1.getX()>max.getX()){
				max.setX(c1.getX());
			}
			if(c1.getY()>max.getY()){
				max.setY(c1.getY());
			}
		}
		r.setPos(min);
		r.sz=Coord.dif(max, r.getPos());
		r.updatePoints();
		r.setCreated(true);
		r.setDeep(recupInt(params[4]));
    	r.onMouseReleased(new Coord(0,0));
    	model.addForme(r);
	}
	/**
	 * Creation du oval
	 * @param str la ligne lue du fichier
	 */	
	public void createOval(String str){
		String[] params = str.split("/");
		Oval o = new Oval(recupCoord(params[4]), recupColor(params[2]), recupBoolean(params[6]));
		o.setPos(recupCoord(params[3]));
		o.setDeep(recupInt(params[5]));
		o.setCreated(true);
    	o.onMouseReleased(new Coord(0,0));
    	model.addForme(o);
	}
	/**
	 * Creation de la ligne
	 * @param str la ligne lue du fichier
	 */	
	public void createLine(String str){
		String[] params = str.split("/");
		Line l = new Line(recupCoord(params[3]), new Coord(0,0), recupColor(params[2]), recupBoolean(params[5]), recupInt(params[6]));
		l.setDeep(recupInt(params[4]));
//		for(Coord c : recupPts(params[1])){
//			l.getPoints().add(c);
//		}
		l.getPoints().get(1).set(recupPts(params[1]).get(1));
		l.sz.setX(l.points.get(1).getX()-l.pos.getX());
		l.sz.setY(l.points.get(1).getY()-l.pos.getY());
		l.setCreated(true);
    	l.onMouseReleased(new Coord(0,0));
    	model.addForme(l);
	}
	/**
	 * Creation du polygone
	 * @param str la ligne lue du fichier
	 */	
	public void createPoly(String str){
		String[] params = str.split("/");
		Polygon p = new Polygon(recupCoord(params[3]), recupColor(params[2]), recupBoolean(params[5]));
		p.setDeep(recupInt(params[4]));
		System.out.println(p.getDeep());
		p.getPoints().clear();
		for(Coord c : recupPts(params[1])){
			p.getPoints().add(c);
		}
		p.updatePosSz();
		p.setCreated(true);
    	p.onMouseReleased(new Coord(0,0));
    	model.addForme(p);
	}
	
	/**
	 * Création d'une Image.
	 * @param str la ligne lue du fichier
	 */
	public void createImgObj(String str){
		String[] params = str.split("/");
		ImgObject r = new ImgObject(recupCoord(params[3]), new Coord(0,0), recupColor(params[2]), recupBoolean(params[5]), recupByte(params[7]));
    	//recalcul pos/sz
		Coord min = new Coord(9999,9999);
		Coord max = new Coord(0,0);
		for(Coord c1 : recupPts(params[1])){
			if(c1.getX()<min.getX()){
				min.setX(c1.getX());
			}
			if(c1.getY()<min.getY()){
				min.setY(c1.getY());
			}
			if(c1.getX()>max.getX()){
				max.setX(c1.getX());
			}
			if(c1.getY()>max.getY()){
				max.setY(c1.getY());
			}
		}
		r.setPos(min);
		r.sz=Coord.dif(max, r.getPos());
		r.updatePoints();
		r.setCreated(true);
		r.setDeep(recupInt(params[4]));
    	r.onMouseReleased(new Coord(0,0));
    	model.addForme(r);
	}
	
	/**
	 * Recuperation d'un tableau d'octet a partir d'une cgaine de caracteres.
	 * @param str
	 * @return	tableau d'octet
	 */
	public byte[] recupByte(String str){
		System.out.println("str: "+str);
		ArrayList<Byte> l = new ArrayList<Byte>();
		String[] ch = str.split(" ");
		for(String c : ch){
			System.out.println("c: "+c);
			if(c.length()>0)l.add(Byte.parseByte(c));
		}
		byte[] b = new byte[l.size()];
		for(int i=0;i<l.size();i++){
			b[i]=l.get(i);
		}
		return b;
	}
	
	/**
	 * Récupération de tous les points
	 * @param str la ligne lue
	 * @return la liste de points
	 */
	public List<Coord> recupPts(String str){
		ArrayList<Coord> l = new ArrayList<Coord>();
		str = str.replaceAll(",", " ");
		str= str.replaceAll("\\(", "");
		str= str.replaceAll("\\)", "");
		String[] ch = str.split(" ");

		for(int i=0;i<ch.length;i++){
			l.add(new Coord(Integer.parseInt(ch[i]),Integer.parseInt(ch[i+1])));
			i++;
		}
		System.out.println("list: "+l);
		return l;
	}
	/**
	 * Récupération de la couleur
	 * @param str
	 * @return couleur
	 */
	public ColorModel recupColor(String str){
		str = str.replaceAll(",", " ");
		str= str.replaceAll("\\(", "");
		str= str.replaceAll("\\)", "");
		str= str.replaceAll("#", "");
		String[] ch = str .split(" ");
		return new ColorModel(Integer.parseInt(ch[0]), Integer.parseInt(ch[1]), Integer.parseInt(ch[2]), Integer.parseInt(ch[3]));
	}
	/**
	 * Retourne une coordonnée x et y (utilisé pour la positon et la taille)
	 * @param str
	 * @return coordonnée x,y
	 */
	//pos et sz
	public Coord recupCoord(String str){
		System.out.println("str"+str);
		str = str.replaceAll(",", " ");
		str= str.replaceAll("\\(", "");
		str= str.replaceAll("\\)", "");
		String[] ch = str .split(" ");
		return new Coord(Integer.parseInt(ch[0]), Integer.parseInt(ch[1]));
	}
	/**
	 * conversion string > boolean (pour le fill)
	 * @param str
	 * @return booleen
	 */
	public boolean recupBoolean(String str){
		return Boolean.parseBoolean(str);
	}
	/**
	 * conversion string > int (pour le deep)
	 * @param str
	 * @return integer
	 */
	public int recupInt(String str){
		return Integer.parseInt(str);
	}
	
}
