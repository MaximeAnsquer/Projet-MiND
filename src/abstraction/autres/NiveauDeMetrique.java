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

	//---Constructeurs---

	/**
	 * Cree un niveau vide numerote.
	 * @param numero
	 */
	public NiveauDeMetrique(int numero){
		this.numero = numero;
		this.intitule = "";
		this.description = "";
	}

	public NiveauDeMetrique(int numero, String intitule, String description){
		this.numero = numero;
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

	public String toString(){
		return "{[Ligne de Metrique] : numero = "+this.getNumero()
		+" ; intitule = "+this.intitule+" ; description = "
		+this.description+"}";			
	}

}
