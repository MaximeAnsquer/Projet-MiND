package autres;

import java.util.ArrayList;

/**
 * Classe representant un bien ; utile aux modules " Biens essentiels " et " Biens supports "
 * 
 * @author Francois Adam 
 */

public class Biens {
	//Variables d'instance
	
	private ArrayList<String> nomColonneSup; //ArrayList represantant les titres des colonnes supplementaires que l'utilisateur peut rajouter
	private ArrayList<String> contenuColonneSup; //ArrayList represantant le contenu des colonnes supplementaires que l'utilisateur peut rajouter
	private String Description;
	private String intitule;
	private boolean retenu;
		
	//Constructeurs

	public Biens(String description, String intitule, boolean retenu, ArrayList<String> nomColonneSup, ArrayList<String> contenuColonneSup) {
		this.Description = description;
		this.intitule = intitule;
		this.retenu = retenu;
		this.nomColonneSup=nomColonneSup;
		this.contenuColonneSup=contenuColonneSup;
	}
		
	//Getters et setters
		
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
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
	
	public ArrayList<String> getNomColonneSup(){
		return nomColonneSup;
	}
	
	public void setNomColonneSup(ArrayList<String> nomColonneSup){
		this.nomColonneSup=nomColonneSup;
	}
	
	public ArrayList<String> getContenuColonneSup(){
		return contenuColonneSup;
	}
	
	public void setContenuColonneSup(ArrayList<String> contenuColonneSup){
		this.contenuColonneSup=contenuColonneSup;
	}
		
	public String toString(){
		return "{Critère : id = "+this.getDescription()+" ; intitule : "+this.getIntitule()
				+" ; retenu : "+this.isRetenu()+"}";
	}
		
}
