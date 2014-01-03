package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
/**
 * 
 * @author Franï¿½ois Lamothe Guillaume Leccoq Alexandre Ravaux
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
	private Coord areaSz;

	public Model(){
		formes = new ArrayList<Forme>();
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
		update();
	}
	/**
	 * Pour selectionner la forme avec la souris
	 * @param c
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
	 * Verifie si l'endroit cliqué appartient au polygone
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
	 * 
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
	 * Suppression de la forme sélectionnée
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
	/** 
	 * Utilisé pour la selection multiple en utilisant la touche shift
	 * @param shift
	 */
	public void setShift(boolean shift){
		this.shift=shift;
	}
	/**
	 * Verifie si shift=true ou false
	 * @return boolean
	 */
	public boolean isShift(){
		return shift;
	}
	/**
	 * 
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
	
	public void update(){
		setChanged();
		notifyObservers();
	}
	/**
	 * Retourne la couleur sélectionnée
	 * @return curColor
	 */
	public ColorModel getCurColor() {
		return curColor;
	}
	/**
	 * Couleur sélectionnée
	 * @param curColor
	 */
	public void setCurColor(ColorModel curColor) {
		this.curColor = curColor;
		update();
	}
	/**
	 * Outil sélectionné
	 * @return
	 */
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
