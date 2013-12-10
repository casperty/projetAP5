package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable{
	
	public static final int OVAL=0,RECTANGLE=1;
	private List<Forme> formes;
	private ColorModel curColor=ColorModel.WHITE;
	private int curTool=1;

	
	public Model(){
		formes = new ArrayList<Forme>();
	}
	
	public void addForme(Forme nf){
		for(Forme f : formes){
			f.setDeep(f.getDeep()+1);
		}
		formes.add(nf);
		update();
	}
	
	public void resize(Forme f,Coord sz){
		
	}
	
	public void mousePressed(Coord c){
		//TODO: modifier outils..
		switch(curTool){
		case 0:
			addForme(Forme.createOval(c, curColor, true));
			break;
		case 1:
			addForme(Forme.createRectangle(c, curColor, true));
			break;
		}
		
	}
	
	public void mouseReleased(Coord c){
		
	}
	
	public void mouseDragged(Coord c){
		if(!formes.get(formes.size()-1).isCreated()){
			formes.get(formes.size()-1).onMouseDragged(c);
		}
		update();
	}
	
	public List<Forme> getFormes(){
		return formes;
	}
	
	public void setTool(int i){
		this.curTool=i;
	}
	
	public void update(){
		setChanged();
		notifyObservers();
	}

	public ColorModel getCurColor() {
		return curColor;
	}

	public void setCurColor(ColorModel curColor) {
		this.curColor = curColor;
		update();
	}
	
	

}
