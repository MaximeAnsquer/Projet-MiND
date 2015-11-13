/**
 * Maxime A.
 */

/**
 * Classe representant un critere, utile au module " Criteres de securite "
 */

package autres;

public class Critere {
	
	//Variables d'instance
	
	private String nom;
	private String id;
	private String intitule;
	private boolean retenu;
	
	//Constructeurs

	public Critere(String nom, String id, String intitule, boolean retenu) {
		this.nom = nom;
		this.id = id;
		this.intitule = intitule;
		this.retenu = retenu;
	}
	
	//Getters et setters
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public boolean isRetenu() {
		return retenu;
	}

	public void setRetenu(boolean retenu) {
		this.retenu = retenu;
	}
	
}
