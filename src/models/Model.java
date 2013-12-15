package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable{
	
	public static final int OVAL=0,RECTANGLE=1;
	private List<Forme> formes;
	private ColorModel curColor=ColorModel.BLACK;
	private int curTool=0;
	private boolean shift=false;

	
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
			boolean deselectAll=true;
			int selected =0;
			for(Forme f : formes){
				if(f.isSelect()){
					selected++;
				}
			}
			boolean deselect=false;
			for(Forme f : formes){
				deselect=false;
				if(f.contains(c)){
					if(selected>0){
						if(shift){
							if(!f.isSelect()){
								f.setSelect(true);
								selected++;
							}else{
								f.setSelect(false);
								selected--;
							}
						}
					}else{
						if(f.isSelect() && shift){
							f.setSelect(false);
							selected--;
							deselect=true;
						}
						if(!f.isSelect() && !shift && !deselect){
							f.setSelect(true);
							selected++;
						}
					}
					deselectAll=false;
				}else if(!shift && f.isSelect()){
					if(selected==1){
						f.setSelect(false);
						selected--;
					}
				}
			}
			
			
//			if(deselectAll){
//				unSelectAll();
//			}
			for(Forme f : formes){
				if(f.isSelect()){
					f.onMousePressed(c);
				}
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
	
	public void mouseReleased(Coord c){
		if(formes.size()>0 && !formes.get(formes.size()-1).isCreated()){
			formes.get(formes.size()-1).onMouseReleased(c);
		}
		for(Forme f : formes){
			if(f.contains(c)){
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
	
	public void uimsg(String msg){
		if(msg.equals("del")){
			delselects();
		}
	}
	
	public void delselects(){
		ArrayList<Forme> dels = new ArrayList<Forme>();
		for(Forme f : formes){
			if(f.isSelect()){
				dels.add(f);
			}
		}
		for(Forme f : dels){
			formes.remove(f);
		}
		update();
	}
	
	public void mouseMoved(Coord c){
		for(Forme f : formes){
			if(f.contains(c)){
				f.setColor(new ColorModel(255, 0, 0, 255));
				update();
			}else{
				f.setColor(ColorModel.BLACK);
				update();
			}
		}
	}
	
	public List<Forme> getFormes(){
		return formes;
	}
	
	public void setShift(boolean shift){
		this.shift=shift;
	}
	
	public boolean isShift(){
		return shift;
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
