package models;

import java.util.ArrayList;
import java.util.List;

public abstract class Forme {

	protected List<Coord> points;
	protected ColorModel color;
	protected Coord pos;
	protected Coord sz;
	protected int deep;
	protected boolean select=false;
	protected boolean fill=false;
	protected boolean created=false;
	private boolean resize=false;
	protected Coord difPos; //deplacement difference entre coord mse et position de cette forme
	protected Coord click;
	protected int borderWidth=1;
	
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
	public int getBorderWidth(){
		return borderWidth;
	}
	
	public abstract void onMousePressed(Coord c);
	public abstract void onMouseDragged(Coord c);
	public abstract void onMouseReleased(Coord c);
	public abstract boolean contains(Coord c);
	public abstract void resize(Coord c);
	public abstract Forme clone();
	
	public List<Coord> getRectBounds(){
		ArrayList<Coord> pts = new ArrayList<Coord>();
		pts.add(new Coord(getPos().getX(),getPos().getY()+(getSz().getY())));
		pts.add(new Coord(getPos().getX()+(getSz().getX()),getPos().getY()+(getSz().getY())));
		pts.add(new Coord(getPos().getX()+(getSz().getX()),getPos().getY()));
		pts.add(new Coord(getPos().getX(),getPos().getY()));
		return pts;
	}
	
	public int onResizeRect(Coord c){
		ArrayList<Coord> pts = (ArrayList<Coord>) getRectBounds();
		//clic sur l'un des 4 points du rectangle ?
		for(Coord c1 : pts){
			if(!(c.getX()>c1.getX()+5 || c.getX()<c1.getX()-5 || c.getY()>c1.getY()+5 || c.getY()<c1.getY()-5)){
				return pts.indexOf(c1);
			}
		}
		return -1;
	}
	
	public void moveTo(Coord c){
		pos.setX(c.getX()+difPos.getX());
		pos.setY(c.getY()+difPos.getY());
		System.out.println(pos +" "+difPos);
	}
	
	public Coord getSz(){
		return sz;
	}
	
	public boolean isResize(){
		return resize;
	}
	
	public void setResize(boolean resize){
		this.resize=resize;
	}
	
	public static Oval createOval(Coord pos,ColorModel color, boolean fill){
		Oval o = new Oval(new Coord(5,5), color, fill);
		o.setPos(new Coord(pos));
		return o;
	}
	
	public static Rectangle createRectangle(Coord pos,ColorModel color, boolean fill){
		Rectangle rec = new Rectangle(new Coord(pos),new Coord(5,5),color, fill);
		return rec;
	}
	
	public static Line createLine(Coord pos,ColorModel color,int width){
		Line l = new Line(new Coord(pos),new Coord(5,5),color,false,2);
		return l;
	}
	public static Polygon createPolygon(Coord pos,ColorModel color, boolean fill){
		Polygon p = new Polygon(new Coord(pos),color,fill);
		return p;
	}
	
	public String toString() {
		String tmp=this.getClass().getName().substring(this.getClass().getName().indexOf("models.")+"models.".length() ,this.getClass().getName().length());
		System.out.println("tmp : "+tmp);
		if(points==null){//pour le oval points=null
			return tmp + "/" + points + "/" + color + "/" + pos + "/" + deep + "/" + fill 
								+ "/" + borderWidth;
		}
		String pts=this.getStringPoints();
		return tmp + "/" + pts + "/" + color + "/" + pos + "/" + deep + "/" + fill 
				+ "/" + borderWidth;
	}
	/**
	 * Retourne les différentes positions des points de la forme sous une chaine de caracteres
	 * @return pts
	 */
	public String getStringPoints(){
		String pts="";
		for(int i=0;i<points.size();i++){
			pts+="("+points.get(i).getX()+","+points.get(i).getY()+"),";
		}
		pts=pts.substring(0,pts.length()-1);
		return pts;
	}
	
}
