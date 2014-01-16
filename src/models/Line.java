package models;

import java.util.ArrayList;

/**
* Classe de gestion des lignes
* @author François Lamothe Guillaume Leccoq Alexandre Ravaux
*
*/
public class Line extends Forme {
	
	private int resizeRectid=-1;
	private int width;

	/**
	 * Constructeur d'une ligne
	 * @param pos position
	 * @param sz taille
	 * @param color couleur
	 * @param fill remplissage
	 * @param width largeur
	 */
	public Line(Coord pos,Coord sz,ColorModel color, boolean fill,int width) {
		super(color, fill);
		this.pos=pos;
		this.sz=sz;
		this.width=width;
		this.borderWidth=width;
		points=new ArrayList<Coord>();
		updatePoints();
	}
	
	/**
	 * Clone une nouvelle ligne à partir de ...
	 */
	public Forme clone(){
		Line l = new Line(new Coord(pos),new Coord(sz),color,fill,width);
		l.updatePoints();
		l.onMouseReleased(new Coord(0,0));
		return l;
	}
	
	/**
	 * Mise à jour des points
	 */
	public void updatePoints(){
		if(this.points.size()==0){
			points.add(new Coord(pos));
			points.add(new Coord(pos.getX()+sz.getX(),pos.getY()+sz.getY()));
		}else{
			points.get(0).set(pos);
			points.get(1).set(new Coord(pos.getX()+sz.getX(),pos.getY()+sz.getY()));
		}
	}

	/**
	 * Gestion d'une pression à la souris
	 * @param c Coordonnées du clic de la souris
	 */
	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
		difPos=Coord.dif(pos, click);
	}

	@Override
	public void onMouseDragged(Coord c) {
		if(created){
			if((isResize() && onResizeRect(c)!=-1) || resizeRectid!=-1){
				resizeRectid=(resizeRectid==-1)?onResizeRect(c):resizeRectid;
				resize(c);
			}else if(isSelect()){
				moveTo(c);
			}
			updatePoints();
		}else{
			sz=Coord.dif(c, pos);
			updatePoints();
		}
	}
	
	/**
	 * Test si une coordonnées est sur l'un des 2 points de la ligne de redimmensionnement
	 * et retourne l'identifiant de ce point.
	 * @param c coordonnée a tester.
	 */
	@Override
	public int onResizeRect(Coord c){
		if((c.getX()<pos.getX()+5 && c.getX()>pos.getX()-5) && (c.getY()<pos.getY()+5 && c.getY()>pos.getY()-5)){
			return 0;
		}else if((c.getX()<pos.getX()+sz.getX()+5 && c.getX()>pos.getX()+sz.getX()-5) && (c.getY()<pos.getY()+sz.getY()+5 && c.getY()>pos.getY()+sz.getY()-5)){
			return 1;
		}
		return -1;
	}

	/**
	 * Gestion du relachement avec la souris
	 * Si la ligne n'existe pas, elle sera crée
	 * @param c coordonnées du clic de la souris
	 */
	@Override
	public void onMouseReleased(Coord c) {
		if(!created){
			created=true;
		}
		difPos=null;
		resizeRectid=-1;
	}

	/**
	 * Savoir si le point c appartient à la ligne
	 * @param c point que l'on souhaite tester
	 */
	@Override
	public boolean contains(Coord c) {
		Coord pA = getPoints().get(0);
		Coord pB = getPoints().get(1);
		int xA=pA.getX();
		int yA=pA.getY();
		int xB=pB.getX();
		int yB=pB.getY();
		int xC=c.getX();
		int yC=c.getY();
		double normalLength = Math.sqrt((xB-xA)*(xB-xA)+(yB-yA)*(yB-yA));
		double dist = Math.abs((xC-xA)*(yB-yA)-(yC-yA)*(xB-xA))/normalLength;
		return dist<=1;
	}

	/**
	 * Redimensionne la ligne à partir d'une nouvelle coordonnée
	 * @param c nouvelle coordonée pour le redimensionnement
	 */
	@Override
	public void resize(Coord c) {
		points.get(resizeRectid).set(c);
		pos.set(points.get(0));
		sz.setX(points.get(1).getX()-pos.getX());
		sz.setY(points.get(1).getY()-pos.getY());
	}

}
