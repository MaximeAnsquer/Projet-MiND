package abstraction.autres;

import java.util.LinkedList;

/**
 * Classe representant un bien ; utile aux modules " Biens essentiels " et " Biens supports "
 * 
 * @author Francois Adam 
 */

public class Bien{
	//Variables d'instance
	private LinkedList<String> contenuColonnesSup;
	private String description;
	private String intitule;
	private String type;
	private boolean retenu;
		
	//Constructeurs
	public Bien(String description, String intitule, String type, LinkedList<String> contenuColonnesSup) {
		this.description = description;
		this.intitule = intitule;
		this.type=type;
		this.contenuColonnesSup=contenuColonnesSup;
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
	
	public LinkedList<String> getContenuColonnesSup(){
		return contenuColonnesSup;
	}
	
	public void ajouterColonne(String contenu){
		this.getContenuColonnesSup().addFirst(contenu);
	}
	
	public void enleverPremiereColonne(){
		this.getContenuColonnesSup().removeFirst();
	}
	
	public void setContenuColonnesSup(LinkedList<String> contenuColonnesSup){
		this.contenuColonnesSup=contenuColonnesSup;
	}
		
	public String toString(){
		return "{Bien : id = "+this.getDescription()+" ; intitule : "+this.getIntitule()
				+" ; retenu : "+this.isRetenu()+"}";
	}
		
}
