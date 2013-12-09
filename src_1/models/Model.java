package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable{
	
	private List<Forme> formes;
	private ColorModel curColor=ColorModel.WHITE;
	
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
	
	public List<Forme> getFormes(){
		return formes;
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
