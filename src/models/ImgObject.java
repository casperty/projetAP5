package models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * 
 * @author François Lamothe Guillaume Lecocq Alexandre Ravaux
 *
 */
public class ImgObject extends Rectangle{
	
	private byte[] data;
	/**
	 * Creation de l'objet ImgObject qui hérite des attibuts de Rectangle et qui contient une image
	 * (import de l'image dans notre dessin)
	 * @param pos position de l'image
	 * @param sz taille de l'image
	 * @param color couleur de l'image
	 * @param fill booléen qui indique si la forme est pleine
	 * @param mpath chemin de l'image, chemin choisie dans ImgChooser
	 */
	public ImgObject(Coord pos, Coord sz, ColorModel color, boolean fill,String mpath) {
		/*héritage des attributs de la forme Rectangle*/
		super(pos, sz, color, fill);
		Path path = Paths.get(mpath);
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creation de l'objet ImgObject qui hérite des attibuts de Rectangle et qui contient une image
	 * (import de l'image dans notre dessin)
	 * @param pos position de l'image
	 * @param sz taille de l'image
	 * @param color couleur de l'image
	 * @param fill booléen qui indique si la forme est pleine
	 * @param data données en bits de l'image
	 */
	public ImgObject(Coord pos, Coord sz, ColorModel color, boolean fill,byte[] data) {
		super(pos, sz, color, fill);
		this.data=data;
	}
	/**
	 * Retourne la valeur en bits de notre image
	 * @return data
	 */
	public byte[] getData(){
		return data;
	}

}
