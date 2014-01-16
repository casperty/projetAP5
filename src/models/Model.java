package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

/**
 * 
 * @author François Lamothe Guillaume Leccoq Alexandre Ravaux
 * 
 *
 */
public class Model extends Observable{
	
	/*OUTILS*/
	public static final int OVAL=0,RECTANGLE=1,SELECT=2,LINE=3,POLYGON=4,FILL=5,RESIZE=6,IMAGE=7;
	private List<Forme> formes;
	private ColorModel curColor=ColorModel.BLACK;
	private int curTool=0;
	private boolean shift=false;
	private boolean ctrl=false;
	private Coord areaSz;
	/*LISTE POUR UNDO REDO*/
	private ArrayList<List<Forme>> archiveUndo;
	private int archiveId=0;	
	/*LISTE POUR COPY-PASTE*/
	private ArrayList<Forme> copy;
	private String imgPath;

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
//		for(Forme f : formes){
//			f.setDeep(f.getDeep()+1);
//		}
		formes.add(nf);
		for(int i=0;i<formes.size();i++){
			formes.get(i).setDeep(i);
		}
		unSelectAll();
		checkArchive();
		update();
	}
	/**
	 * Gestion de la souris selon l'outil choisi
	 * @param c coordonnées souris
	 */
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
		case IMAGE:
			drawImg(c);
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
	 * Verifie que les polygones sont tous créés et valides.
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
	/**
	 * Pour valider la création du polygone après avoir taper sur Entrée
	 */
	public void enter(){
		if(!formes.get(formes.size()-1).created){
			formes.get(formes.size()-1).setCreated(true);
		}
	}
	/**
	 * Tri des formes en fonction de la profondeur
	 */
	public void sortFormes(){
		Collections.sort(formes, new Comparator<Forme>(){
			@Override
		    public int compare(Forme f1, Forme f2) {
				int i2=f1.getDeep();
				int i1=f2.getDeep();
		        return (i1>i2 ? -1 : (i1==i2 ? 0 : 1));
		    }
		});
		for(Forme f : formes){
			f.setDeep(formes.indexOf(f));
		}
	}
	/**
	 * Changement de la profondeur : baisse d'un niveau
	 * @param i
	 */
	public void down(int i){
		if(formes.size()-1>i){
			formes.get(i).setDeep(i+1);
			formes.get(i+1).setDeep(i);
		}
		sortFormes();
		update();
	}
	/**
	 *  Changement de la profondeur : monte d'un niveau
	 * @param i
	 */
	public void up(int i){
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
	 * Dessin image et ajout a la liste des formes
	 * @param c
	 */
	public void drawImg(Coord c){
		unSelectAll();
		if(imgPath!=null){
			addForme(Forme.createImg(c, curColor, true, imgPath));
		}
		imgPath=null;
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
		int higher=-5;
		Forme f=null;
		for(Forme f2 : formes){
			if(f2.contains(c)){
				if(f2.getDeep()>higher){
					higher=f2.getDeep();
					f=f2;
				}
			}
		}
		return f;
	}
	/**
	 * Methode pour annuler l'action
	 * On utilise une liste pour gerer cela
	 */
	public void undo(){
		if(archiveId>0 && archiveUndo.size()>=archiveId){
			archiveId--;
			this.formes=archiveUndo.get(archiveId);
			update();
		}
	}
	/**
	 * Methode pour refaire l'action
	 */
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
	/**
	 * 
	 * @param c
	 */
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
	/**
	 * 
	 * @param c
	 */
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
		sortFormes();
		update();
	}
	
	/**
	 * Recuperer la liste des formes
	 * @return
	 */
	public List<Forme> getFormes(){
		return formes;
	}
	/**
	 * 
	 * @param shift
	 */
	public void setShift(boolean shift){
		this.shift=shift;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isShift(){
		return shift;
	}
	/**
	 * Verifie le redimensionnement
	 */
	public void checkResize(){
		for(Forme f : formes){
			f.setResize(false);
		}
	}
	/**
	 * 
	 * @param i
	 */
	public void setTool(int i){
		this.curTool=i;
		checkPoly();
		checkResize();
		update();
	}
	/**
	 * 
	 */
	public void update(){
		setChanged();
		notifyObservers();
	}
	/**
	 * 
	 */
	public void checkArchive(){
		this.archiveUndo.add(getCloneFormes());
		archiveId=archiveUndo.size()-1;
	}
	/**
	 * 
	 * @return
	 */
	public List<Forme> getCloneFormes(){
		List<Forme> l = new ArrayList<Forme>();
		for(Forme f : formes){
			l.add(f.clone());
		}
		return l;
	}
	/**
	 * 
	 * @return curColor : la couleur courante
	 */
	public ColorModel getCurColor() {
		return curColor;
	}
	/**
	 * 
	 * @param curColor
	 */
	public void setCurColor(ColorModel curColor) {
		this.curColor = curColor;
		update();
	}
	/**
	 * Copie de la forme (ou des formes)
	 */
	public void copy(){
		for(Forme f : formes){
			if(f.isSelect()){
				copy.add(f);
			}
		}
	}
	/**
	 * Collage
	 */
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
	
	public void setImgPath(String s){
		this.imgPath=s;
	}

}
