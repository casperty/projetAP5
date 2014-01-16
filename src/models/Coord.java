package models;
/**
 * 
 * @author Fran�ois Lamothe Guillaume Lecocq Alexandre Ravaux
 *
 */
public class Coord {
	
	private int x,y;
	/**
	 * Creation d'une coordonn�e x,y
	 * @param x
	 * @param y
	 */
	public Coord(int x,int y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Cr�e une nouvelle coordonn�e �partir de la coordonn�e mise en param�tre
	 */
	public Coord(Coord c){
		this.x=c.getX();
		this.y=c.getY();
	}
	/**
	 * Ajout des valeurs de la coordonn�e � partir des valeurs d'une coordonn�e mise en param�tre
	 * @param c coordon�e dont on va r�cup�rer les valeurs x et y
	 */
	public void add(Coord c){
		this.x+=c.getX();
		this.y+=c.getY();
	}
	/**
	 * Multiplication
	 * @param i
	 */
	public void mul(float i){
		this.x*=i;
		this.y*=i;
	}
	/**
	 * Division
	 * @param i
	 */
	public void div(float i){
		this.x/=i;
		this.y/=i;
	}
	/**
	 * Retourne un Coord avec pour x la difference de x des deux coordonnees donn�s en param�tre
	 * et y la difference de y des deux coordonnees donn�s en parametre
	 * @param c coordonn�e (x1,y2)
	 * @param c1 coordonn�e (x2,y2)
	 * @return nouvelle coordonn�e (x2-x1,y2-y1)
	 */
	public static Coord dif(Coord c,Coord c1){
		return new Coord(c.getX()-c1.getX(),c.getY()-c1.getY());
	}
	/**
	 * D�finir la coordonn�e � partir d'une cordonn�e mise en param�tre
	 * On va r�cup�rer le x et y de la coordonn�e mise en param�tre pour d�finir 
	 * les x et y de notre nouvelle coordonn�e
	 * @param c Coord c
	 */
	public void set(Coord c){
		this.x=c.getX();
		this.y=c.getY();
	}
	/**
	 * Retourne la valeur x de la coordonn�e
	 * @return x valeur x de la coordonn�e
	 */
	public int getX() {
		return x;
	}
	/**
	 * D�finit la valeur x de la coordonn�e
	 * @param x valeur de type int
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Retourne la valeur y de la coordonn�e
	 * @return y valeur y de la coordonn�e
	 */
	public int getY() {
		return y;
	}
	/**
	 * D�finit la valeur x de la coordonn�e
	 * @param y valeur de type int
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Affiche la coordonn�e
	 */
	public String toString(){
		return "("+x+","+y+")";
	}

}
