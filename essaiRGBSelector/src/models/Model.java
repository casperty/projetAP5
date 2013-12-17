package models;

import java.util.Observable;

public class Model extends Observable implements IRVBModel {
	
	private int r=0,v=0,b=0;
	
	public Model(){
		this.r=255;
		this.v=255;
		this.b=255;
	}

	@Override
	public int getR() {
		return r;
	}

	@Override
	public int getV() {
		return v;
	}

	@Override
	public int getB() {
		return b;
	}



	@Override
	public void setR(int r) {
		this.r=r;
		setChanged();
		notifyObservers();
	}

	@Override
	public void setV(int v) {
		this.v=v;
		setChanged();
		notifyObservers();
	}

	@Override
	public void setB(int b) {
		this.b=b;
		setChanged();
		notifyObservers();
	}


}