package abstraction.autres;

import java.util.ArrayList;

/**
 * Classe representant un bien ; utile aux modules " Biens essentiels " et " Biens supports "
 * 
 * @author Francois Adam 
 */

public class Bien{
	//Variables d'instance

	private ArrayList<String> nomColonneSup; //ArrayList representant les titres des colonnes supplementaires que l'utilisateur peut rajouter
	private ArrayList<String> contenuColonneSup; //ArrayList representant le contenu des colonnes supplementaires que l'utilisateur peut rajouter
	private String description;
	private String intitule;
	private String type;
	private boolean retenu;
		
	//Constructeurs
	public Bien(String description, String intitule, String type, boolean retenu, ArrayList<String> nomColonneSup, ArrayList<String> contenuColonneSup) {
		this.description = description;
		this.intitule = intitule;
		this.type=type;
		this.retenu = retenu;
		this.nomColonneSup=nomColonneSup;
		this.contenuColonneSup=contenuColonneSup;
	}
		
	//Getters et setters
		
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setType (String type){
		this.type=type;
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
	
	public void ajouterColonne(String nomColonne, String contenuColonne){
		this.getNomColonneSup().add(nomColonne);
		this.getContenuColonneSup().add(contenuColonne);
	}
		
	public String toString(){
		return "{Bien : id = "+this.getDescription()+" ; intitule : "+this.getIntitule()
				+" ; retenu : "+this.isRetenu()+"}";
	}
		
}
