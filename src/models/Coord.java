package models;
/**
 * 
 * @author Fran�ois Lamothe Guillaume Lecocq Alexandre Ravaux
 *
 */
public class Coord {
	
	private int x,y;
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Coord(int x,int y){
		this.x=x;
		this.y=y;
	}
	/**
	 * Cr�ation d'un Coord � partir d'un autre Coord (?)
	 * @param x
	 * @param y
	 */
	public Coord(Coord c){
		this.x=c.getX();
		this.y=c.getY();
	}
	/**
	 * 
	 * @param c
	 */
	public void add(Coord c){
		this.x+=c.getX();
		this.y+=c.getY();
	}
	
	public void mul(float i){
		this.x*=i;
		this.y*=i;
	}
	
	public void div(float i){
		this.x/=i;
		this.y/=i;
	}
	
	public static Coord dif(Coord c,Coord c1){
		return new Coord(c.getX()-c1.getX(),c.getY()-c1.getY());
	}
	
	public void set(Coord c){
		this.x=c.getX();
		this.y=c.getY();
	}
	/**
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	/**
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}
	/**
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * retourne les coordonn�es
	 */
	public String toString(){
		return "("+x+","+y+")";
	}

}
