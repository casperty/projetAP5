package models;

import java.util.ArrayList;

public class Polygon extends Forme {
	
	private int resizeRectid=-1;

	public Polygon(Coord pos,ColorModel color, boolean fill) {
		super(color, fill);
		this.pos=new Coord(pos);
		this.sz=new Coord(0,0);
		points = new ArrayList<Coord>();
		points.add(new Coord(pos));
		updatePos();
	}
	
	public Forme clone(){
		Polygon l = new Polygon(new Coord(pos),color,fill);
		l.getPoints().remove(0);
		for(Coord p : this.getPoints()){
			l.points.add(new Coord(p));
		}
		l.updatePos();
		l.onMouseReleased(new Coord(0,0));
		return l;
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
	
	public void updatePos(){
		for(Coord c : points){
			if(c.getX()<pos.getX()){
				pos.setX(c.getX());
			}else if(c.getY()<pos.getY()){
				pos.setY(c.getY());
			}
		}
		updateSz();
	}
	
	public void updateSz(){
		for(Coord c : points){
			if(c.getX()-pos.getX()>sz.getX()){
				sz.setX(c.getX()-pos.getX());
			}else if(c.getY()-pos.getY()>sz.getY()){
				sz.setY(c.getY()-pos.getY());
			}
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
		resizeRectid=-1;
	}

	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
		difPos=Coord.dif(pos, click);
		if(!created){
			if((c.getX()>=pos.getX()-5 && c.getX()<=pos.getX()+5) && (c.getY()>=pos.getY()-5 && c.getY()<=pos.getY()+5) && points.size()>1){
				created=true;
				updatePos();
			}else{
				points.add(new Coord(c));
				updatePos();
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

	@Override
	public void resize(Coord c) {
		Coord nPos=new Coord(pos);
		Coord nSz=new Coord(sz);
		
		ArrayList<Coord> pts = (ArrayList<Coord>) getRectBounds();
		if(c.getX()<nPos.getX()){
			resizeRectid=(resizeRectid==1)?0:(resizeRectid==2)?3:resizeRectid;
		}else if(c.getX()>nPos.getX()+nSz.getX()){
			resizeRectid=(resizeRectid==0)?1:(resizeRectid==3)?2:resizeRectid;
		}
		if(c.getY()<nPos.getY()){
			resizeRectid=(resizeRectid==0)?3:(resizeRectid==1)?2:resizeRectid;
		}else if(c.getY()>nPos.getY()+nSz.getY()){
			resizeRectid=(resizeRectid==3)?0:(resizeRectid==2)?1:resizeRectid;
		}
		int prev = (resizeRectid==0)?3:resizeRectid-1;
		int next = (resizeRectid==3)?0:resizeRectid+1;
		
		boolean prevOnX=pts.get(prev).getX()==pts.get(resizeRectid).getX();
		pts.get(resizeRectid).set(c);
		if(prevOnX){
			pts.get(prev).setX(c.getX());
			pts.get(next).setY(c.getY());
		}else{
			pts.get(prev).setY(c.getY());
			pts.get(next).setX(c.getX());
		}
		
		//recalcul nPos/nSz
		Coord min = new Coord(9999,9999);
		Coord max = new Coord(0,0);
		for(Coord c1 : pts){
			if(c1.getX()<min.getX()){
				min.setX(c1.getX());
			}
			if(c1.getY()<min.getY()){
				min.setY(c1.getY());
			}
			if(c1.getX()>max.getX()){
				max.setX(c1.getX());
			}
			if(c1.getY()>max.getY()){
				max.setY(c1.getY());
			}
		}
		nPos.set(min);
		nSz=Coord.dif(max, nPos);
	}

}
