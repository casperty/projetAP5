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
	
	public byte[] getData(){
		return data;
	}

}
