package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.autres.TypeBien;
import abstraction.autres.TypeBien;

/**
 * Cette classe représente le tableau crée lorqu'on clique sur l'onglet de
 * typologie de biens supports
 * 
 * @author Belghiti Ali
 */

public class TypologieBiensSupports extends Module {

	// Represente la bdc
	private static Hashtable<String, TypeBien> bdcTypeBiensSupports;

	// Variable d'instance
	Hashtable<String, TypeBien> tableau;

	public TypologieBiensSupports() {
		super("Typologie des biens supports");
		this.tableau = new Hashtable<String, TypeBien>();
		//this.successeurs.add(BiensSupports.getInstance());
		//this.successeurs.add(ScenariosDeMenacesGeneriques.getInstance());
		this.cree = false;
		this.coherent = false;
		this.disponible = true;
		this.tableau = TypologieBiensSupports.getBDC();
	}

	// ---Getters et setters---

	public Hashtable<String, TypeBien> getTableau() {
		return this.tableau;
	}

	public void setTableau(Hashtable<String, TypeBien> tab) {
		this.tableau = tab;
	}

	public TypeBien getTypeBien(String type) {
		return this.getTableau().get(type);
	}

	public static Hashtable<String, TypeBien> getBDC() {
		return bdcTypeBiensSupports;
	}

	// ---Services---

	// On ajoute une ligne au tableau seulement si tous les champs sont
	// renseignés
	public void addTypeBienSupport(TypeBien type) {
		if (!type.isIncomplete()) {
			this.tableau.put(type.getIntitule(), type);
		}
	}

	// Suppression d'une ligne du tableau
	public void deleteTypeBienSupport(TypeBien type) {
		this.tableau.remove(type);
	}
	
	// On liste les Types de bien retenus
	public Hashtable<String, TypeBien> getTypeBiensRetenus() {
		Hashtable<String, TypeBien> resultat = new Hashtable<String, TypeBien>();
		for (TypeBien type : this.getTableau().values()) {
			if (type.isRetenu()) {
				resultat.put(type.getIntitule(), type);
			}
		}
		return resultat;
	}
	
	// On retient un Type de Bien)
	// Cela correspond à une croix cochée dans la colonne des types de biens retenus
	public void retenirTypeBien(String intitule) {
		this.getTypeBien(intitule).setRetenu(true);
	}

	public void importerBDC() {
		// TODO importer la bdc avec le fichier excel
	}

}
