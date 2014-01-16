package models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImgObject extends Rectangle{
	
	private byte[] data;

	public ImgObject(Coord pos, Coord sz, ColorModel color, boolean fill,String mpath) {
		super(pos, sz, color, fill);
		Path path = Paths.get(mpath);
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ImgObject(Coord pos, Coord sz, ColorModel color, boolean fill,byte[] data) {
		super(pos, sz, color, fill);
		this.data=data;
	}
	
	@Override
	public Forme clone(){
		ImgObject l = new ImgObject(new Coord(pos),new Coord(sz),color,fill,data);
		l.onMouseReleased(new Coord(0,0));
		return l;
	}
	public byte[] getData(){
		return data;
	}
	
	@Override
	public String toString(){
		String str = "ImgObject" + "/" + getStringPoints() + "/" + color + "/" + pos + "/" + deep + "/" + fill 
				+ "/" + borderWidth + "/" ;
		for(byte b : data){
			str+= " "+b;
		}
		return str;
	}

}
