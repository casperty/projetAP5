package models;

import java.util.ArrayList;
import java.util.List;

public class Oval extends Forme {
	
	private Coord initPos;
	
	private int resizeRectid=-1;
	
	public Oval(Coord sz,ColorModel color,boolean fill){
		super(color,fill);
		this.sz=sz;
	}

	public Coord getSize(){
		return sz;
	}
	
	@Override
	public void setPos(Coord c){
		initPos=new Coord(c);
		this.pos=c;
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
		}else{
			Coord modif=Coord.dif(pos, c);
			if(modif.getX()<0 && c.getX()>initPos.getX()){
				sz.setX(modif.getX()*-1);
			}else{
				pos.setX(c.getX());
				sz.setX(sz.getX()+modif.getX());
			}
			if(modif.getY()<0 && c.getY()>initPos.getY()){
				sz.setY(modif.getY()*-1);
			}else{
				pos.setY(c.getY());
				sz.setY(sz.getY()+modif.getY());
			}
		}
	}
	
	

	@Override
	public void onMouseReleased(Coord c) {
		if(!created){
			created=true;
		}
		resizeRectid=-1;
	}

	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
		difPos=Coord.dif(pos, click);
	}

	@Override
	public boolean contains(Coord c) {
		double rx=((double)sz.getX())/2.0;
		double ry=((double)sz.getY())/2.0;
		double x=pos.getX()+rx;
		double y=pos.getY()+ry;
		double cX = x-c.getX();
		double cY = y-c.getY();
		return (((cX*cX)/(rx*rx)) +((cY*cY)/(ry*ry)))<=1.0;
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
