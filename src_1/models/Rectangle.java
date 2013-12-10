package models;

import java.util.ArrayList;

public class Rectangle extends Forme {
	
	private Coord sz;
	private boolean modifX=false, modifY=false;

	public Rectangle(Coord pos,Coord sz,ColorModel color, boolean fill) {
		super(color, fill);
		this.pos=pos;
		this.sz=sz;
		points=new ArrayList<Coord>();
		updatePoints();
	}
	
	public void updatePoints(){
		if(this.points.size()==0){
			points.add(new Coord(pos));
			points.add(new Coord(pos.getX()+sz.getX(),pos.getY()));
			points.add(new Coord(pos.getX()+sz.getX(),pos.getY()+sz.getY()));
			points.add(new Coord(pos.getX(),pos.getY()+sz.getY()));
		}else{
			points.get(0).set(pos);
			points.get(1).set(new Coord(pos.getX()+sz.getX(),pos.getY()));
			points.get(2).set(new Coord(pos.getX()+sz.getX(),pos.getY()+sz.getY()));
			points.get(3).set(new Coord(pos.getX(),pos.getY()+sz.getY()));
		}
	}

	@Override
	public void onMouseDragged(Coord c) {
		if(created){
			this.pos=c;
		}else{
			Coord modif=Coord.dif(pos, c);
			if(modif.getX()<0 && !modifX){
				sz.setX(modif.getX()*-1);
			}else{
				int prevX = pos.getX();
				pos.setX(c.getX());
				sz.setX(sz.getX()+modif.getX());
				modifX=true;
				
			}
			if(modif.getY()<0 && !modifY){
				sz.setY(modif.getY()*-1);
			}else{
				int prevY = pos.getY();
				pos.setY(c.getY());
				sz.setY(sz.getY()+modif.getY());
				modifY=true;
			}
			updatePoints();
		}
	}

	@Override
	public void onMouseReleased(Coord c) {
		
	}

}
