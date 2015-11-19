package abstraction.autres;

/**
 * Classe representant un critère ; utile au module " Critères de securite ".
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
	 * Cree un critere qui n'est initialement pas retenu.
	 * @param id
	 * @param intitule
	 * @param description
	 */
	public Critere(String id, String intitule, String description) {
		this.id = id;
		this.intitule = intitule;
		this.description = description;
		this.retenu = false;
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
		
	public String toString(){
		return "{Critère : id = "+this.getId()+" ; intitule : "+this.getIntitule()
				+" ; retenu : "+this.isRetenu()+"}";
	}
	
}
