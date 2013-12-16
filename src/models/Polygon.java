package models;

import java.util.ArrayList;

public class Polygon extends Forme {

	public Polygon(Coord pos,ColorModel color, boolean fill) {
		super(color, fill);
		this.pos=new Coord(pos);
		points = new ArrayList<Coord>();
		points.add(new Coord(pos));
	}
	
	@Override
	public void moveTo(Coord c){
		int mvX=pos.getX();
		int mvY=pos.getY();
		pos.setX(c.getX()+difPos.getX());
		pos.setY(c.getY()+difPos.getY());
		mvX=mvX-pos.getX();
		mvY=mvY-pos.getY();
		for(Coord p : points){
			p.setX(p.getX()-mvX);
			p.setY(p.getY()-mvY);
		}
	}

	@Override
	public void onMouseDragged(Coord c) {
		if(created){
			moveTo(c);
		}
	}

	
	@Override
	public void onMouseReleased(Coord c) {
		difPos=null;
	}

	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
		difPos=Coord.dif(pos, click);
		if(!created){
			if((c.getX()>=pos.getX()-5 && c.getX()<=pos.getX()+5) && (c.getY()>=pos.getY()-5 && c.getY()<=pos.getY()+5) && points.size()>1){
				System.out.println(c+" "+pos);
				created=true;
			}else{
				points.add(new Coord(c));
			}
		}
	}

	@Override
	public boolean contains(Coord c) {
		boolean result = false;
	      for (int i = 0, j = points.size() - 1; i < points.size(); j = i++) {
	        if ((points.get(i).getY() > c.getY()) != (points.get(j).getY() > c.getY()) &&
	            (c.getX() < (points.get(j).getX() - points.get(i).getX()) * (c.getY() - points.get(i).getY()) / (points.get(j).getY()-points.get(i).getY()) + points.get(i).getX())) {
	          result = !result;
	         }
	      }
	      return result;
	}

}
