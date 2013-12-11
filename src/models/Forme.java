package models;
import java.util.List;
/**
 * 
 * @author François Lamothe Guillaume Lecocq Alexandre Ravaux
 * Classe abstraite qui permet ensuite de spécifier les différentes formes (Rectangle, Oval...)
 *
 */
public abstract class Forme {

	protected List<Coord> points;
	protected ColorModel color;
	protected Coord pos;
	protected int deep;
	protected boolean select=false;
	protected boolean fill=false;
	protected boolean created=false;
	/**
	 * reprend la couleur choisie et determine le remplissage de la forme (fill : remplissage de l'objet)
	 * @param color
	 * @param fill
	 */
	public Forme(ColorModel color,boolean fill){
		this.color=color;
		this.fill=fill;
	}
	
	public List<Coord> getPoints() {
		return points;
	}
	public void setPoints(List<Coord> points) {
		this.points = points;
	}
	public ColorModel getColor() {
		return color;
	}
	public void setColor(ColorModel color) {
		this.color = color;
	}
	public Coord getPos() {
		return pos;
	}
	public void setPos(Coord pos) {
		this.pos = pos;
	}
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	public boolean isCreated() {
		return created;
	}
	public void setCreated(boolean created) {
		this.created = created;
	}
	public abstract void onMousePressed(Coord c);
	public abstract void onMouseDragged(Coord c);
	public abstract void onMouseReleased(Coord c);
	/**
	 * Création du losange
	 * @param pos
	 * @param color
	 * @param fill
	 * @return
	 */
	public static Oval createOval(Coord pos,ColorModel color, boolean fill){
		Oval o = new Oval(new Coord(5,5), color, fill);
		o.setPos(new Coord(pos));
		return o;
	}
	/**
	 * Création du rectangle
	 * @param pos
	 * @param color
	 * @param fill
	 * @return
	 */
	public static Rectangle createRectangle(Coord pos,ColorModel color, boolean fill){
		Rectangle rec = new Rectangle(new Coord(pos),new Coord(5,5),color, fill);
		return rec;
	}
	
}
