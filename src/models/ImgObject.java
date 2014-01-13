package models;

public class ImgObject extends Rectangle{
	
	private String path;

	public ImgObject(Coord pos, Coord sz, ColorModel color, boolean fill,String path) {
		super(pos, sz, color, fill);
		this.path=path;
	}
	
	public String getPath(){
		return path;
	}
	
	public void setPath(String path){
		this.path=path;
	}

}
