package models;

public class Oval extends Forme {
	
	private Coord sz;
	private Coord initPos;
	private Coord difPos; //deplacement difference entre coord mse et position de cette forme
	private Coord click;
	/**
	 * 
	 * @param sz
	 * @param color
	 * @param fill
	 */
	public Oval(Coord sz,ColorModel color,boolean fill){
		/* Héritage des attributs color et fill	*/
		super(color,fill);
		/* la taille (sz : size) sera définie avec les méthodes onMouse */
		this.sz=sz;
	}
	/**
	 * 
	 * @return sz (size)
	 */
	public Coord getSize(){
		return sz;
	}
	/**
	 * setting the position
	 */
	@Override
	public void setPos(Coord c){
		initPos=new Coord(c);
		this.pos=c;
	}
	/**
	 * tant qu'on relache pas le bouton gauche de la souris
	 */
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
	/**
	 * quand on relache la souris : fin du dessin
	 */
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
	/**
	 * quand on clique  : récupère la coordonnée là où on a cliqué
	 */
	@Override
	public void onMousePressed(Coord c) {
		click=new Coord(c);
	}
	
	
	

}
