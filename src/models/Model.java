package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import views.MainFrame;

public class Model extends Observable{
	
	//Outils
	public static final int OVAL=0,RECTANGLE=1,SELECT=2,LINE=3,POLYGON=4,FILL=5,RESIZE=6;
	private List<Forme> formes;
	private ColorModel curColor=ColorModel.BLACK;
	private int curTool=0;
	private boolean shift=false;
	private Coord areaSz;

	
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
		checkPoly();
		switch(curTool){
		case OVAL:
			drawOval(c);
			break;
		case RECTANGLE:
			drawRect(c);
			break;
		case SELECT:
			select(c);
			break;
		case LINE:
			drawLine(c);
			break;
		case POLYGON:
			drawPoly(c);
			break;
		case FILL:
			fill(c);
			break;
		case RESIZE:
			resize(c);
			break;
		}
	}
	
	public void select(Coord c){
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
						if(f==getHighestContains(c)){
							f.setSelect(true);
							selected++;
						}
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
		if(deselectAll){
			unSelectAll();
		}
		for(Forme f : formes){
			if(f.isSelect()){
				f.onMousePressed(c);
			}
		}
		
		update();
	}
	
	public void checkPoly(){
		boolean remPoly=false;
		for(Forme f : formes){
			if(f instanceof Polygon && !f.isCreated()){
				if(curTool!=POLYGON){
					f.setCreated(true);
					if(f.getPoints().size()<=2){
						unSelectAll();
						f.setSelect(true);
						remPoly=true;
					}
				}
			}
		}
		if(remPoly){
			delselects();
			update();
		}
	}
	
	public void drawPoly(Coord c){
		Forme cur=null;
		for(Forme f : formes){
			if(f instanceof Polygon && !f.isCreated()){
				cur=f;
			}
		}
		if(cur==null){
			addForme(Forme.createPolygon(c,curColor,true));
		}else{
			cur.onMousePressed(c);
		}
	}
	
	public void drawRect(Coord c){
		unSelectAll();
		addForme(Forme.createRectangle(c, curColor, true));
	}
	
	public void drawOval(Coord c){
		unSelectAll();
		addForme(Forme.createOval(c, curColor, true));
	}
	
	public void drawLine(Coord c){
		unSelectAll();
		addForme(Forme.createLine(c, curColor,1));
	}
	
	public void resize(Coord c){
		unSelectAll();
		Forme f1 = getHighestContains(c);
		if(f1!=null){
			f1.setResize(!f1.isResize());
		}
	}
	
	public void fill(Coord c){
		unSelectAll();
		Forme f = getHighestContains(c);
		if(f!=null){
			f.setColor(curColor);
		}
	}
	
	public Forme getHighestContains(Coord c){
		int higher=500;
		Forme f=null;
		for(Forme f2 : formes){
			if(f2.contains(c)){
				if(f2.getDeep()<higher){
					higher=f2.getDeep();
					f=f2;
				}
			}
		}
		return f;
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
		checkPoly();
		update();
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
	
	public int getCurTool(){
		return curTool;
	}
	
	public void setAreaSz(Coord sz){
		this.areaSz=sz;
	}
	
	public Coord getAreaSz(){
		return areaSz;
	}

}
