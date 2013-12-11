package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable{
	
	public static final int OVAL=0,RECTANGLE=1;
	private List<Forme> formes;
	private ColorModel curColor=ColorModel.BLACK;
	private int curTool=1;
	private Forme onMouse;

	
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
		switch(curTool){
		case 0:
			unSelectAll();
			addForme(Forme.createOval(c, curColor, true));
			break;
		case 1:
			unSelectAll();
			addForme(Forme.createRectangle(c, curColor, true));
			break;
		case 2:
			for(Forme f : formes){
				if(f!=onMouse){
					f.setSelect(false);
				}
			}
			if(onMouse!=null){
				onMouse.onMousePressed(c);
			}
			update();
			break;
		}
	}
	
	public void unSelectAll(){
		for(Forme f : formes){
			f.setSelect(false);
		}
	}
	
//	public void mouseClicked(Coord c){
//		if(onMouse!=null){
//			onMouse.setSelect(!onMouse.isSelect());
//		}
//	}
	
	
	public void mouseReleased(Coord c){
		if(formes.size()>0 && !formes.get(formes.size()-1).isCreated()){
			formes.get(formes.size()-1).onMouseReleased(c);
		}
		for(Forme f : formes){
			if(f==onMouse){
				f.onMouseReleased(c);
			}
		}
		update();
	}
	
	public void mouseDragged(Coord c){
		if(formes.size()>0 && !formes.get(formes.size()-1).isCreated()){
			formes.get(formes.size()-1).onMouseDragged(c);
		}
		for(Forme f : formes){
			if(f.isSelect()){
				f.onMouseDragged(c);
			}
		}
		update();
	}
	
	public void setOnMouse(Forme f){
		onMouse=f;
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
