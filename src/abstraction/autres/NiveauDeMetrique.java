package abstraction.autres;

/**
 * Une metrique contient un tableau qui present differents niveaux.
 * Cette classe represente un niveau de metrique.
 * @author Maxime Ansquer
 *
 */

public class NiveauDeMetrique {

	//---Variables d'instance---

	private int numero;
	private String intitule;
	private String description;
	private String couleur; //TODO la couleur de la case " numero " ; on pourrait utiliser une variable de type Color, mais pas sur que ca existe en Java 6 donc mefiance

	//---Constructeurs---

	/**
	 * Cree un niveau de metrique vide mais numerote, et assigne une couleur par defaut a la case " numero " en fonction du numero.
	 * @param numero
	 */
	public NiveauDeMetrique(int numero){
		this.numero = numero;
		this.intitule = "";
		this.description = "";
		if(numero == 1){
			this.couleur = "vert";
		}
		else if(numero == 2){
			this.couleur = "jaune-orange";
		}
		else if(numero == 3){
			this.couleur = "orange";
		}
		else{
			this.couleur = "rouge";
		}
	}

	public NiveauDeMetrique(int numero, String intitule, String description){
		this(numero);
		this.intitule = intitule;
		this.description = description;
	}

	//---Getters et setters---

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
	public String getCouleur(){
		return this.couleur;
	}
	
	public void setCouleur(String couleur){
		this.couleur = couleur;
	}
	
	//---Services---

	public String toString(){
		return "{[Ligne de Metrique] : numero = "+this.getNumero()
		+" ; intitule = "+this.intitule+" ; description = "
		+this.description+"}";			
	}

	public boolean estComplet() {
		return !this.getDescription().equals("") && !this.getIntitule().equals("") && !((Integer) this.getNumero() == null);
	}

}
