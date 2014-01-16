package models;

public class ColorModel {
	
	/**
	 * Définition des constantes pour les couleur blanche(WHITE) et noire(BLACK)
	 * ayant respectivement pour valeur (255,255,255,255) et (0,0,0,225)
	 */
	public static final ColorModel WHITE = new ColorModel(255,255,255,255);
	public static final ColorModel BLACK = new ColorModel(0,0,0,255);
	
	private int r,g,b,a;
	
	/**
	 * Constructeur permettant de créer d'une couleur
	 * @param r Integer qui représente la valeur du composant rouge
	 * @param g Integer qui représente la valeur du composant vert
	 * @param b Integer qui représente la valeur du composant bleu
	 * @param a Integer qui représente la valeur du composant alpha
	 */
	public ColorModel(int r, int g, int b,int a){
		this.r=r;
		this.g=g;
		this.b=b;
		this.a=a;
	}

	/**
	 * Méthode pour récupérer la valeur du composant alpha (compris entre 0 et 255)
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
	 * Récupérer la valeur du composant rouge (compris entre 0 et 255)
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
	 * Récupérer la valeur du composant alpha (compris entre 0 et 255)
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
	 * Récupérer la valeur du composant bleu (compris entre 0 et 255)
	 * @return
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
	 * Méthode pour récupérer la valeur en hexadécimal de la couleur
	 * @return valeur héxadécimal de la couleur
	 */
	public String getHexa(){
		return "#"+int2Hex(r)+int2Hex(g)+int2Hex(b);
	}
	
	/**
	 * Méthode pour saisir le code héxadécimal de la couleur
	 * @param s valeur en héxacimal de la couleur
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
	 * Convertir un int en héxadécimal
	 * @param i valeur en base 10 que l'on souhaite convertir
	 * @return valeur converti en héxadécimal
	 */
	public String int2Hex(int i){
		String s =Integer.toHexString(i);
		if(i==0) return s+"0";
		if(s.length()==1) return "0"+s;
		return s;
	}
	
	/**
	 * Convertir un code héxadécimal en base 10
	 * @param hex code héxadécimal que l'on souhaite convertir
	 * @return code couleur converti en base 10
	 */
	public int hex2Int(String hex){
        return (int) Long.parseLong(hex, 16);
    }
	
	/** 
	 * Afficher le code de la couleur avec sa valeur en base 10 (r,g,b,a) et sa valeur héxadécimale
	 */
	public String toString(){
		return "("+r+","+g+","+b+","+a+","+getHexa()+")";
	}

}
