package models;
/**
 * 
 * @author Fran�ois Lamothe Guillaume Lecocq Alexandre Ravaux
 *
 */
public class ColorModel {
	
	/**
	 * D�finition des constantes pour les couleur blanche(WHITE) et noire(BLACK)
	 * ayant respectivement pour valeur (255,255,255,255) et (0,0,0,225)
	 */
	public static final ColorModel WHITE = new ColorModel(255,255,255,255);
	public static final ColorModel BLACK = new ColorModel(0,0,0,255);
	
	private int r,g,b,a;
	
	/**
	 * Constructeur permettant de cr�er d'une couleur
	 * @param r Integer qui repr�sente la valeur du composant rouge
	 * @param g Integer qui repr�sente la valeur du composant vert
	 * @param b Integer qui repr�sente la valeur du composant bleu
	 * @param a Integer qui repr�sente la valeur du composant alpha
	 */
	public ColorModel(int r, int g, int b,int a){
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
	}

	/**
	 * M�thode pour r�cup�rer la valeur du composant alpha (compris entre 0 et 255)
	 * @return le composant alpha
	 */
	public int getA() {
		return a;
	}

	/**
	 * Saisir la valeur du composant alpha
	 * @param a valeur du composant alpha que l'on souhaite saisir
	 */
	public void setA(int a) {
		this.a = (a<=255)?a:255;
	}

	/**
	 * R�cup�rer la valeur du composant rouge (compris entre 0 et 255)
	 * @return valeur du composant rouge
	 */
	public int getR() {
		return r;
	}

	/**
	 * Saisir la valeur du composant rouge
	 * @param r valeur du composant rouge que l'on souhaite saisir
	 */
	public void setR(int r) {
		this.r = (r<=255)?r:255;
	}

	/**
	 * R�cup�rer la valeur du composant alpha (compris entre 0 et 255)
	 * @return valeur du composant alpha
	 */
	public int getG() {
		return g;
	}

	/**
	 * Saisir la valeur du composant vert (valeur compris entre 0 et 255)
	 * @param g valeur du composant vert que l'on souhaite saisir
	 */
	public void setG(int g) {
		this.g = (g<=255)?g:this.g;
	}

	/**
	 * R�cup�rer la valeur du composant bleu (compris entre 0 et 255)
	 * @return b valeur du composant bleu
	 */
	public int getB() {
		return b;
	}

	/**
	 * Saisir la valeur du composant bleu (valeur compris entre 0 et 255)
	 * @param b valeur du composant bleu
	 */
	public void setB(int b) {
		this.b = (b<=255)?b:this.b;
	}
	
	/**
	 * M�thode pour r�cup�rer la valeur en hexad�cimal de la couleur
	 * @return valeur h�xad�cimal de la couleur
	 */
	public String getHexa(){
		return "#"+int2Hex(r)+int2Hex(g)+int2Hex(b);
	}
	
	/**
	 * M�thode pour saisir le code h�xad�cimal de la couleur
	 * @param s valeur en h�xacimal de la couleur
	 */
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
	
	/**
	 * Convertir un int en h�xad�cimal
	 * @param i valeur en base 10 que l'on souhaite convertir
	 * @return valeur converti en h�xad�cimal
	 */
	public String int2Hex(int i){
		String s =Integer.toHexString(i);
		if(i==0) return s+"0";
		if(s.length()==1) return "0"+s;
		return s;
	}
	
	/**
	 * Convertir un code h�xad�cimal en base 10
	 * @param hex code h�xad�cimal que l'on souhaite convertir
	 * @return code couleur converti en base 10
	 */
	public int hex2Int(String hex){
        return (int) Long.parseLong(hex, 16);
    }
	
	/** 
	 * Afficher le code de la couleur avec sa valeur en base 10 (r,g,b,a) et sa valeur h�xad�cimale
	 */
	public String toString(){
		return "("+r+","+g+","+b+","+a+","+getHexa()+")";
	}

}
