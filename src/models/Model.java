package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import views.MainFrame;
/**
 * 
 * @author François Lamothe Guillaume Leccoq Alexandre Ravaux
 * 
 *
 */
public class Model extends Observable{
	
	//Outils
	public static final int OVAL=0,RECTANGLE=1,SELECT=2,LINE=3,POLYGON=4,FILL=5,RESIZE=6;
	private List<Forme> formes;
	private ColorModel curColor=ColorModel.BLACK;
	private int curTool=0;
	private boolean shift=false;
	private boolean ctrl=false;
	private Coord areaSz;
	
	private ArrayList<List<Forme>> archiveUndo;
	private int archiveId=0;
	
	private ArrayList<Forme> copy;

	
	@SuppressWarnings("unchecked")
	public Model(){
		formes = new ArrayList<Forme>();
		copy = new ArrayList<Forme>();
		archiveUndo=new ArrayList<List<Forme>>();
		archiveUndo.add(getCloneFormes());
	}
	
	/**
	 * Ajout d'une forme a la liste des formes
	 * @param nf
	 */
	public void addForme(Forme nf){
		for(Forme f : formes){
			f.setDeep(f.getDeep()+1);
		}
		formes.add(nf);
		unSelectAll();
		update();
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
	
	/**
	 * Selection de l'objet
	 * @param c
	 */
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
	
	/**
	 * Verifie que les polygones sont tous crée et valide.
	 */
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
	
	public void enter(){
		if(!formes.get(formes.size()-1).created){
			formes.get(formes.size()-1).setCreated(true);
		}
	}
	
	public void sortFormes(){
		Collections.sort(formes, new Comparator<Forme>(){
			@Override
		    public int compare(Forme f1, Forme f2) {
				int i1=f1.getDeep();
				int i2=f2.getDeep();
		        return (i1>i2 ? -1 : (i1==i2 ? 0 : 1));
		    }
		});
	}
	
	public void up(int i){
		Forme cur=null;
		Forme next=null;
		for(Forme f : formes){
			if(f.getDeep()==i){
				cur=f;
			}else if(f.getDeep()==i+1){
				next=f;
			}
		}
		if(cur!=null && next!=null){
			cur.setDeep(i+1);
			next.setDeep(i);
		}
		sortFormes();
		update();
	}
	
	public void down(int i){
		Forme cur=null;
		Forme prev=null;
		for(Forme f : formes){
			if(f.getDeep()==i){
				cur=f;
			}else if(f.getDeep()==i-1){
				prev=f;
			}
		}
		if(cur!=null && prev!=null){
			cur.setDeep(i-1);
			prev.setDeep(i);
		}
		sortFormes();
		update();
	}
	
	public void selectByDeep(int deep){
		unSelectAll();
		for(Forme f : formes){
			if(f.getDeep()==deep){
				f.setSelect(true);
			}
		}
		update();
	}
	
	/**
	 * Dessin du polygone et ajout a la liste des formes
	 * @param c
	 */
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
	
	/**
	 * Dessin du rectangle et ajout a la liste des formes
	 * @param c
	 */
	public void drawRect(Coord c){
		unSelectAll();
		addForme(Forme.createRectangle(c, curColor, true));
	}
	
	/**
	 * Dessin de l'oval et ajout a la liste des formes
	 * @param c
	 */
	public void drawOval(Coord c){
		unSelectAll();
		addForme(Forme.createOval(c, curColor, true));
	}
	
	/**
	 * Dessin de la ligne et ajout a la liste des formes
	 * @param c
	 */
	public void drawLine(Coord c){
		unSelectAll();
		addForme(Forme.createLine(c, curColor,1));
	}
	
	/**
	 * Redimensionnement
	 * @param c
	 */
	public void resize(Coord c){
		unSelectAll();
		Forme cur=null;
		for(Forme f : formes){
			if(f.isResize()){
				cur=f;
			}
		}
		Forme f1 = getHighestContains(c);
		if(f1!=null){
			if((cur!=null && cur.onResizeRect(c)==-1) || cur==null){
				f1.setResize(!f1.isResize());
				for(Forme f : formes){
					if(f.isResize() && f!=f1 && f1.isResize()){
						f.setResize(false);
					}
				}
			}
		}else{
			for(Forme f : formes){
				if(f.isResize() && f.onResizeRect(c)==-1){
					f.setResize(false);
				}
			}
		}
	}
	
	/**
	 * Remplissage
	 * @param c
	 */
	public void fill(Coord c){
		unSelectAll();
		Forme f = getHighestContains(c);
		if(f!=null){
			f.setColor(curColor);
		}
	}
	
	/**
	 * Retourne la forme ayant la plus petit profondeur au point de coordonnées c.
	 * @param c
	 * @return
	 */
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
	
	public void undo(){
		System.out.println(archiveId+ " "+archiveUndo.size());
		if(archiveId>0 && archiveUndo.size()>=archiveId){
			archiveId--;
			this.formes=archiveUndo.get(archiveId);
			update();
		}
	}
	
	public void redo(){
		if(archiveId!=archiveUndo.size()-1 && archiveUndo.size()>0){
			archiveId++;
			this.formes=archiveUndo.get(archiveId);
			update();
		}
	}
	/**
	 * Annule toute sélection
	 */
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
			if(f.contains(c) || f.isResize()){
				f.onMouseReleased(c);
			}
		}
		checkArchive();
		update();
	}
	
	public void mouseDragged(Coord c){
		if(formes.size()>0 && !formes.get(formes.size()-1).isCreated()){
			formes.get(formes.size()-1).onMouseDragged(c);
		}
		for(Forme f : formes){
			if(f.isSelect() || f.isResize()){
				f.onMouseDragged(c);
			}
		}
		update();
	}
	
	/**
	 * Suppression des formes sélectionnées
	 */
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
	
	/**
	 * Recuperer la liste des formes
	 * @return
	 */
	public List<Forme> getFormes(){
		return formes;
	}
	
	public void setShift(boolean shift){
		this.shift=shift;
	}
	
	public boolean isShift(){
		return shift;
	}
	
	public void checkResize(){
		for(Forme f : formes){
			f.setResize(false);
		}
	}
	
	public void setTool(int i){
		this.curTool=i;
		checkPoly();
		checkResize();
		update();
	}
	
	public void update(){
		setChanged();
		notifyObservers();
	}

	public void checkArchive(){
		this.archiveUndo.add(getCloneFormes());
		archiveId=archiveUndo.size()-1;
	}
	
	public List<Forme> getCloneFormes(){
		List<Forme> l = new ArrayList<Forme>();
		for(Forme f : formes){
			l.add(f.clone());
		}
		return l;
	}
	
	public ColorModel getCurColor() {
		return curColor;
	}

	public void setCurColor(ColorModel curColor) {
		this.curColor = curColor;
		update();
	}
	
	public void copy(){
		for(Forme f : formes){
			if(f.isSelect()){
				copy.add(f);
			}
		}
	}
	
	public void paste(){
		for(Forme f : copy){
			Forme f1 = f.clone();
			addForme(f1);
		}
		copy.clear();
	}
	
	public void newArea(Coord sz){
		formes.clear();
		System.out.println("resize");
		setSz(sz);
	}
	
	public void setSz(Coord c){
		this.areaSz=new Coord(c);
		setChanged();
		notifyObservers();
	}
	
	public boolean isCtrl(){
		return ctrl;
	}
	
	public void setCtrl(boolean b){
		ctrl=b;
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
