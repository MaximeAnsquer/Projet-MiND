package abstraction.autres;

/**
 * Classe representant un critere ; utile au module " Criteres de securite ".
 * @author Maxime Ansquer 
 */

public class Critere {
	
	//Variables d'instance
	
	private String id;
	private String intitule;
	private String description;
	private boolean retenu;
	
	//Constructeurs

	/**
	 * Cree un critere initialement retenu.
	 * @param id
	 * @param intitule
	 * @param description
	 */
	public Critere(String id, String intitule, String description) {
		this.id = id;
		this.intitule = intitule;
		this.description = description;
		this.retenu = true;
	}
	
	//Getters et setters
	
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
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}

	public boolean isRetenu() {
		return retenu;
	}

	public void setRetenu(boolean retenu) {
		this.retenu = retenu;
	}
	
	//---Services---
		
	public String toString(){
		return "{Critere : id = "+this.getId()+" ; intitule : "+this.getIntitule()
				+" ; retenu : "+this.isRetenu()+"}";
	}
	
	/**
	 * verifie si tous les attributs sont biens renseignes
	 * @return
	 */
	public boolean estComplet(){
		boolean resultat = true;
		resultat = resultat && !getId().equals("") && !getIntitule().equals("") && !getDescription().equals("");
		return resultat;
	}
	
}
