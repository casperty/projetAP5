package models;

import java.util.ArrayList;

public class Line extends Forme {
	
	private int resizeRectid=-1;
	private int width;
	
	public Line(Coord pos,Coord sz,ColorModel color, boolean fill,int width) {
		super(color, fill);
		this.pos=pos;
		this.sz=sz;
		this.width=width;
		this.borderWidth=width;
		points=new ArrayList<Coord>();
		updatePoints();
	}
	
	public Forme clone(){
		Line l = new Line(new Coord(pos),new Coord(sz),color,fill,width);
		l.updatePoints();
		l.onMouseReleased(new Coord(0,0));
		return l;
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
			if((isResize() && onResizeRect(c)!=-1) || resizeRectid!=-1){
				resizeRectid=(resizeRectid==-1)?onResizeRect(c):resizeRectid;
				resize(c);
			}else if(isSelect()){
				moveTo(c);
			}
			updatePoints();
		}else{
			sz=Coord.dif(c, pos);
			updatePoints();
		}
	}
	
	@Override
	public int onResizeRect(Coord c){
		if((c.getX()<pos.getX()+5 && c.getX()>pos.getX()-5) && (c.getY()<pos.getY()+5 && c.getY()>pos.getY()-5)){
			return 0;
		}else if((c.getX()<pos.getX()+sz.getX()+5 && c.getX()>pos.getX()+sz.getX()-5) && (c.getY()<pos.getY()+sz.getY()+5 && c.getY()>pos.getY()+sz.getY()-5)){
			return 1;
		}
		return -1;
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

	@Override
	public void resize(Coord c) {
		points.get(resizeRectid).set(c);
		pos.set(points.get(0));
		sz.setX(points.get(1).getX()-pos.getX());
		sz.setY(points.get(1).getY()-pos.getY());
	}

}
