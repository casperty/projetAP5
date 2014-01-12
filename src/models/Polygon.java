package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Polygon extends Forme {
	
	private int resizeRectid=-1;
	private HashMap<Coord, float[]> ratio;

	public Polygon(Coord pos,ColorModel color, boolean fill) {
		super(color, fill);
		this.pos=new Coord(pos);
		this.sz=new Coord(0,0);
		points = new ArrayList<Coord>();
		points.add(new Coord(pos));
		updatePosSz();
	}
	
	public Forme clone(){
		Polygon l = new Polygon(new Coord(pos),color,fill);
		l.getPoints().remove(0);
		for(Coord p : this.getPoints()){
			l.points.add(new Coord(p));
		}
		l.updatePosSz();
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
	
	public void updatePosSz(){
		//recalcul pos/sz
		Coord min = new Coord(9999,9999);
		Coord max = new Coord(0,0);
		for(Coord c1 : getPoints()){
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
	
	@Override
	public void onMouseDragged(Coord c) {
		if(created){
			if((isResize() && onResizeRect(c)!=-1) || resizeRectid!=-1){
				resizeRectid=(resizeRectid==-1)?onResizeRect(c):resizeRectid;
				resize(c);
			}else if(isSelect()){
				moveTo(c);
			}
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
				updatePosSz();
			}else{
				points.add(new Coord(c));
				updatePosSz();
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
		if(created && ratio==null){
			ratio = new HashMap<Coord, float[]>();
			for(Coord p : getPoints()){
				//ratio
				float x,y;
				x=((float)p.getX())/((float)sz.getX());
				y=((float)p.getY())/((float)sz.getY());
				System.out.println(x+" "+y);
				float[] r = {x,y};
				ratio.put(p, r);
			}
		}
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
		
		

		//update coord w ratio
		for(Coord p : ratio.keySet()){
			float[] r = ratio.get(p);
			if(resizeRectid==0){
				p.setX((int) (((1-r[0])*((float)sz.getX()))));
				p.setY((int) (((1-r[1])*((float)sz.getY()))));
			}
			p.setX((int) ((r[0]*((float)sz.getX()))));
			p.setY((int) ((r[1]*((float)sz.getY()))));
		}
		
		int xm=getPoints().get(0).getX();
		int ym=getPoints().get(0).getX();
		for(Coord p : getPoints()){
			if(p.getX()>xm)xm=p.getX();
			if(p.getY()>ym)ym=p.getY();
		}
		int difx=xm-pts.get(1).getX();
		int dify=ym-pts.get(1).getY();
		for(Coord p : getPoints()){
			p.setX(p.getX()-difx);
			p.setY(p.getY()-dify);
		}
		
		int xmin=getPoints().get(0).getX();
		int ymin=getPoints().get(0).getX();
		for(Coord p : getPoints()){
			if(p.getX()<xmin)xmin=p.getX();
			if(p.getY()<ymin)ymin=p.getY();
		}
		int difxmin=pts.get(3).getX()-xmin;
		int difymin=pts.get(3).getY()-ymin;
		for(Coord p : getPoints()){
			p.setX(p.getX()+difxmin);
			p.setY(p.getY()+difymin);
		}
	}

}
