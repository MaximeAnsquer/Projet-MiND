package abstraction.autres;

import java.awt.Color;
import java.util.Observable;

/**
 * Classe representant les types de biens supports représentés en mémoire par : 
 * id, description, intitule et retenu
 * Cette classe est utile pour le module "Typologie des biens supports"
 * Elle représente plus généralement une ligne du tableau généré dans "Typologie des biens supports"
 * 
 * @author Belghiti Ali
 */

public class TypeBien {
	
	// Variables d'instances
	// Il faudra rajouter la couleur
	private String Id ;
	private String Description;
	private String intitule;
	private boolean retenu; 
	private Color couleur; 
	// Ex couleur : "red", "yellow", etc...
	
	// IHM : quand retenu=true (cad quand on clique sur la case du tableau correspondante)
	//on a un X qui s'affiche dans cette case. Dans l'autre cas, le X disparait (case vide)
	
	// Constructeur
	public TypeBien(String id, String description, String intitule, boolean retenu, Color clr){
		this.Id=id;
		this.Description = description;
		this.intitule = intitule;
		this.retenu = retenu;
		this.couleur = clr;
	}
	
	public TypeBien(String id, String description, String intitule, boolean retenu){
		this.Id=id;
		this.Description = description;
		this.intitule = intitule;
		this.retenu = retenu;
		this.couleur = new Color(255, 255, 255);
	}
	
	// Constructeur : Ligne vide
	public TypeBien(){
		this("","","",false,new Color(255, 255, 255));
	}
	
	public String getId(){
		return this.Id;
	}
	
	public void setId(String id){
		this.Id=id;
	}
	
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
	
	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	// On vérifie si le type a bien toutes les informations renseignées
	// Tous les champs doivent être renseignés
	public boolean isIncomplete() {
		return (this.Id.equals("") || this.Description.equals("") ||
				this.intitule.equals(""));
	}
}
