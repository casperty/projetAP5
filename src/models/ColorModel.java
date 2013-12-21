package models;

public class ColorModel {
	
	public static final ColorModel WHITE = new ColorModel(255,255,255,255);
	public static final ColorModel BLACK = new ColorModel(0,0,0,255);
	
	private int r,g,b,a;
	
	public ColorModel(int r, int g, int b,int a){
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = (a<=255)?a:255;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = (r<=255)?r:255;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = (g<=255)?g:this.g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = (b<=255)?b:this.b;
	}
	
	public String getHexa(){
		return "#"+int2Hex(r)+int2Hex(g)+int2Hex(b);
	}
	
	public void setHexa(String s){
		if(!(s.charAt(0)!='#' || s.length()==0)){
			s=s.substring(1);
			setR(hex2Int(s.substring(0, 2)));
			s=s.substring(2);
			setG(hex2Int(s.substring(0, 2)));
			s=s.substring(2);
			setB(hex2Int(s.substring(0, 2)));
		}
	}
	
	public String int2Hex(int i){
		return Integer.toHexString(i);
	}
	
	public int hex2Int(String hex){
        return (int) Long.parseLong(hex, 16);
    }
	
	public String toString(){
		return "("+r+","+g+","+b+","+a+")";
	}

}
