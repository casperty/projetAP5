package models;

public class Oval extends Forme {
	
	private Coord sz;
	private Coord initPos;
	private Coord difPos; //deplacement difference entre coord mse et position de cette forme
	private Coord click;
	
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
			if(difPos==null)difPos=Coord.dif(pos, c);
			Coord curDif = Coord.dif(pos, c);
			pos.setX(pos.getX()-(curDif.getX()-difPos.getX()));
			pos.setY(pos.getY()-(curDif.getY()-difPos.getY()));
			difPos=Coord.dif(pos, c);
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
		}else{
			if(click!=null && click.getX()==c.getX() && click.getY()==c.getY()){
				select=!select;
			}
			difPos=null;
		}
	}

	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
	}
	
	
	

}
