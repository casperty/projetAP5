package models;

import java.awt.Color;
import java.util.List;

public class Gestion {

	private Color curColor;
	private List<Forme> formes;
	
	public void add(Forme f){
		f.setColor(curColor);
		f.deep=1;
		for(Forme f1 : formes){
			f1.deep++;
		}
		formes.add(f);
	}
	
	public void remove(Forme f){
		formes.remove(f);
		for(Forme f1 : formes){
			f1.deep--;
		}
	}

	public Color getCurColor() {
		return curColor;
	}

	public void setCurColor(Color curColor) {
		this.curColor = curColor;
	}

	public List<Forme> getFormes() {
		return formes;
	}
	
	
	
	
	
}
