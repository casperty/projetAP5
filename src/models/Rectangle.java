package models;

import java.util.ArrayList;

public class Rectangle extends Forme {
	
//	private Coord sz;
	private boolean modifX=false, modifY=false;//pr redimensionnement a la creation

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
			moveTo(c);
			updatePoints();
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
		if(!created){
			created=true;
		}
		difPos=null;
	}

	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
		difPos=Coord.dif(pos, click);
	}

	@Override
	public boolean contains(Coord c) {
		Coord min=new Coord(150000,150000);
		Coord max = new Coord (0,0);
		for(Coord c1 : getPoints()){
			if(c1.getX()<min.getX()){
				min.setX(c1.getX());
			}
			if(c1.getX()>max.getX()){
				max.setX(c1.getX());
			}
			if(c1.getY()<min.getY()){
				min.setY(c1.getY());
			}
			if(c1.getY()>max.getY()){
				max.setY(c1.getY());
			}
		}
		return (c.getX()>=min.getX() && c.getX()<=max.getX() && c.getY()>=min.getY() && c.getY()<=max.getY());
	}
	

}
