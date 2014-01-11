package models;

import java.util.ArrayList;

public class Rectangle extends Forme {
	
	private boolean modifX=false, modifY=false;//pr redimensionnement a la creation
	private int resizeRectid=-1;

	public Rectangle(Coord pos,Coord sz,ColorModel color, boolean fill) {
		super(color, fill);
		this.pos=pos;
		this.sz=sz;
		points=new ArrayList<Coord>();
		updatePoints();
	}
	
	public Forme clone(){
		Rectangle l = new Rectangle(new Coord(pos),new Coord(sz),color,fill);
		l.onMouseReleased(new Coord(0,0));
		return l;
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
			if((isResize() && onResizeRect(c)!=-1) || resizeRectid!=-1){
				resizeRectid=(resizeRectid==-1)?onResizeRect(c):resizeRectid;
				resize(c);
			}else if(isSelect()){
				moveTo(c);
			}
			updatePoints();
		}else{
			Coord modif=Coord.dif(pos, c);
			//bug ?
//			if(modif.getX()<0 && !modifX){
				sz.setX(modif.getX()*-1);
//			}else{
//				sz.setX(sz.getX()+modif.getX());
//				modifX=true;
//			}
			if(modif.getY()<0 && !modifY){
				sz.setY(modif.getY()*-1);
			}else{
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
		resizeRectid=-1;
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

	@Override
	public void resize(Coord c) {
		ArrayList<Coord> pts = (ArrayList<Coord>) getRectBounds();
		if(c.getX()<pos.getX()){
			resizeRectid=(resizeRectid==1)?0:(resizeRectid==2)?3:resizeRectid;
		}else if(c.getX()>pos.getX()+sz.getX()){
			resizeRectid=(resizeRectid==0)?1:(resizeRectid==3)?2:resizeRectid;
		}
		if(c.getY()<pos.getY()){
			resizeRectid=(resizeRectid==0)?3:(resizeRectid==1)?2:resizeRectid;
		}else if(c.getY()>pos.getY()+sz.getY()){
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
		
		//recalcul pos/sz
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
		pos.set(min);
		sz=Coord.dif(max, pos);
	}
	

}
