package models;

public class Circle extends Forme {
	
	private int radius;
	
	public Circle(int radius){
		this.radius=radius;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void onMouseDragged(Coord c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseReleased(Coord c) {
		// TODO Auto-generated method stub
		
	}
	
	

}
