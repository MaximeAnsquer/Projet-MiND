package abstraction.modules;

import java.util.ArrayList;

import abstraction.autres.TypeBiens;

/**
 * Cette classe représente le tableau crée lorqu'on clique 
 * sur l'onglet de typologie de biens supports
 * 
 * @author Belghiti Ali
 */

public class TypologieBiensSupports extends Module {
	// Variable d'instance
	ArrayList<TypeBiens> tableau ;
	

	public TypologieBiensSupports() {
		super("Typologie des biens supports");
		this.tableau=new ArrayList<>();
		//this.successeurs.add()
	}
	
	// On ajoute une ligne au tableau seulement si tous les champs sont renseignés
	public void addTypeBienSupport(TypeBiens type){
		if (!type.isIncomplete()){
			this.tableau.add(type);
		}
	}
	
	// Suppression d'une ligne du tableau
	public void deleteTypeBienSupport(TypeBiens type){
		this.tableau.remove(type);
	}

}
