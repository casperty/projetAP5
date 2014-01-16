package models;
/**
 * 
 * @author François Lamothe Guillaume Lecocq Alexandre Ravaux
 *
 */
public class Coord {
	
	private int x,y;
	/**
	 * Creation d'une coordonnée x,y
	 * @param x
	 * @param y
	 */
	public Coord(int x,int y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Crée une nouvelle coordonnée à partir de la coordonnée mise en paramètre
	 */
	public Coord(Coord c){
		this.x=c.getX();
		this.y=c.getY();
	}
	/**
	 * Ajout des valeurs de la coordonnée à partir des valeurs d'une coordonnée mise en paramètre
	 * @param c coordonée dont on va récupérer les valeurs x et y
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
	 * Retourne un Coord avec pour x la difference de x des deux coordonnees donnés en paramètre
	 * et y la difference de y des deux coordonnees donnés en parametre
	 * @param c coordonnée (x1,y2)
	 * @param c1 coordonnée (x2,y2)
	 * @return nouvelle coordonnée (x2-x1,y2-y1)
	 */
	public static Coord dif(Coord c,Coord c1){
		return new Coord(c.getX()-c1.getX(),c.getY()-c1.getY());
	}
	/**
	 * Définir la coordonnée à partir d'une cordonnée mise en paramètre
	 * On va récupérer le x et y de la coordonnée mise en paramètre pour définir 
	 * les x et y de notre nouvelle coordonnée
	 * @param c Coord c
	 */
	public void set(Coord c){
		this.x=c.getX();
		this.y=c.getY();
	}
	/**
	 * Retourne la valeur x de la coordonnée
	 * @return x valeur x de la coordonnée
	 */
	public int getX() {
		return x;
	}
	/**
	 * Définit la valeur x de la coordonnée
	 * @param x valeur de type int
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Retourne la valeur y de la coordonnée
	 * @return y valeur y de la coordonnée
	 */
	public int getY() {
		return y;
	}
	/**
	 * Définit la valeur x de la coordonnée
	 * @param y valeur de type int
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Affiche la coordonnée
	 */
	public String toString(){
		return "("+x+","+y+")";
	}

}
