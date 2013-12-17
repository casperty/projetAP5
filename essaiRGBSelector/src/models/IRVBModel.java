package models;

import java.util.Observer;

public interface IRVBModel {
	public int getR();
	public int getV();
	public int getB();
	public void setR(int r);
	public void setV(int v);
	public void setB(int b);
	
	public void addObserver(Observer o);
}