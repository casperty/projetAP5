package views;

import java.awt.Color;
import java.awt.Shape;

import models.ColorModel;
import models.Forme;

public class AdvShape{
	
	private Shape shape;
	private Forme forme;
	
	public AdvShape(Shape shape, Forme forme) {
		this.shape = shape;
		this.forme = forme;
	}
	
	public Color getColor(){
		ColorModel cm= forme.getColor();
		return new Color(cm.getR(),cm.getG(),cm.getB(),cm.getA());
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Forme getForme() {
		return forme;
	}

	public void setForme(Forme forme) {
		this.forme = forme;
	}

}
