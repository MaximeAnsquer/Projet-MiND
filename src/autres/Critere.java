package autres;

/**
 * Classe representant un critère ; utile au module " Critères de securite ".
 * @author Maxime Ansquer 
 */

public class Critere {
	
	//Variables d'instance
	
	private String id;
	private String intitule;
	private boolean retenu;
	
	//Constructeurs

	public Critere(String id, String intitule, boolean retenu) {
		this.id = id;
		this.intitule = intitule;
		this.retenu = retenu;
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

	public boolean isRetenu() {
		return retenu;
	}

	public void setRetenu(boolean retenu) {
		this.retenu = retenu;
	}
	
	public String toString(){
		return "{Critère : id = "+this.getId()+" ; intitule : "+this.getIntitule()
				+" ; retenu : "+this.isRetenu()+"}";
	}
	
}
