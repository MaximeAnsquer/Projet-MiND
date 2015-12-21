package abstraction.autres;

/**
 * Cette classe represente une source de menace. Elle est utile au module SourcesDeMenaces.
 * cf cahier des charges page 19.
 * Notez bien que j'ai mis " Source " et " Menace " au singulier ! 8)
 * @author Maxime Ansquer
 *
 */

public class SourceDeMenace {

	//---Variables d'instance---
	
	private String id;
	private String intitule;
	private String exemple;
	private boolean retenu;
	
	//---Constructeurs---
	
	/**
	 * Cree une nouvelle source de menace initialement non retenue.
	 * @param id
	 * @param intitule
	 * @param exemple
	 */
	public SourceDeMenace(String id, String intitule, String exemple) {
		this.id = id;
		this.intitule = intitule;
		this.exemple = exemple;
		this.retenu = false;
	}
	
	//---Getters et setters---
	
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
	public String getExemple() {
		return exemple;
	}
	public void setExemple(String exemple) {
		this.exemple = exemple;
	}
	public boolean isRetenu() {
		return retenu;
	}
	public void setRetenu(boolean retenu) {
		this.retenu = retenu;
	}

	public boolean estComplet() {
		return !this.getId().equals("") && !this.getIntitule().equals("") && !this.getExemple().equals("");   
	}	
	
}
