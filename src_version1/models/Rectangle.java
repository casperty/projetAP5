package models;

//TODO
import java.awt.Point;

public class Rectangle extends Forme {

	private java.awt.Rectangle rec;
	
	public Rectangle(Point p1, Point p2){
		x=p1.x;
		y=p1.y;
		rec = new java.awt.Rectangle(p1.x, p1.y, p2.x-p1.x, p2.y-p1.y);
	}
	
}
