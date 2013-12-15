package models;

public class Oval extends Forme {
	
	private Coord sz;
	private Coord initPos;
//	private Coord difPos; //deplacement difference entre coord mse et position de cette forme
//	private Coord click;
	
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
			moveTo(c);
		}else{
			Coord modif=Coord.dif(pos, c);
			if(modif.getX()<0 && c.getX()>initPos.getX()){
				sz.setX(modif.getX()*-1);
			}else{
				int prevX = pos.getX();
				pos.setX(c.getX());
				sz.setX(sz.getX()+modif.getX());
				
			}
			if(modif.getY()<0 && c.getY()>initPos.getY()){
				sz.setY(modif.getY()*-1);
			}else{
				int prevY = pos.getY();
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
	}

	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
		difPos=Coord.dif(pos, click);
//		difPos=null;
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
	
	
	

}
