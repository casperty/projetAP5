package models;

import java.util.ArrayList;

public class Line extends Forme {
	
//	private Coord sz;
//	private boolean modifX=false, modifY=false;//pr redimensionnement a la creation

	public Line(Coord pos,Coord sz,ColorModel color, boolean fill,int width) {
		super(color, fill);
		this.pos=pos;
		this.sz=sz;
		this.borderWidth=width;
		points=new ArrayList<Coord>();
		updatePoints();
	}
	
	public void updatePoints(){
		if(this.points.size()==0){
			points.add(new Coord(pos));
			points.add(new Coord(pos.getX()+sz.getX(),pos.getY()+sz.getY()));
		}else{
			points.get(0).set(pos);
			points.get(1).set(new Coord(pos.getX()+sz.getX(),pos.getY()+sz.getY()));
		}
	}

	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
		difPos=Coord.dif(pos, click);
	}

	@Override
	public void onMouseDragged(Coord c) {
		if(created){
			moveTo(c);
			updatePoints();
		}else{
			sz=Coord.dif(c, pos);
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
	public boolean contains(Coord c) {
		Coord pA = getPoints().get(0);
		Coord pB = getPoints().get(1);
		int xA=pA.getX();
		int yA=pA.getY();
		int xB=pB.getX();
		int yB=pB.getY();
		int xC=c.getX();
		int yC=c.getY();
		double normalLength = Math.sqrt((xB-xA)*(xB-xA)+(yB-yA)*(yB-yA));
		double dist = Math.abs((xC-xA)*(yB-yA)-(yC-yA)*(xB-xA))/normalLength;
		return dist<=1;
	}

}
