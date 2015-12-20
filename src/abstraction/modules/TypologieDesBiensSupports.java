package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;

import abstraction.autres.TypeBien;
import abstraction.autres.TypeBien;

/**
 * Cette classe représente le tableau crée lorqu'on clique sur l'onglet de
 * typologie de biens supports
 * 
 * @author Belghiti Ali
 */

public class TypologieDesBiensSupports extends Module {

	// Represente la bdc
	private static Hashtable<String, TypeBien> bdcTypeBiensSupports;

	// Variable d'instance
	// La clé de la Hashtable représente l'intitulé du type de bien
	private Hashtable<String, TypeBien> tableau;
	private TypeBien typeBienCourant;

	public TypeBien getTypeBienCourant() {
		return typeBienCourant;
	}

	public void setTypeBienCourant(TypeBien typeBienCourant) {
		this.typeBienCourant = typeBienCourant;
		this.setChanged();        // PAC
		this.notifyObservers();   // PAC
	}

	public TypologieDesBiensSupports() {
		super("Typologie des biens supports");
		this.tableau = new Hashtable<String, TypeBien>();
		
		this.addTypeBienSupport(new TypeBien(
				"MAT",
				"Ce type de biens supports est constitué de l’ensemble des éléments physiques d'un système informatique (hardware) et des supports de données électroniques) participant au stockage et au traitement de tout ou partie des biens essentiels.",
				"Matériels", true));
		
		this.addTypeBienSupport(new TypeBien(
				"LOG",
				"Ce type de biens supports est constitué de l'ensemble des programmes participant au traitement de tout ou partie des biens essentiels (software).",
				"Logiciels", true));
		
		this.typeBienCourant=this.getTypeBien(0);
		//this.successeurs.add(this.getEtude().getModule("Biens Supports"));
		//this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesGeneriques"));
		this.cree = false;
		this.coherent = false;
		this.disponible = true;
		this.importerBDC();
		//this.tableau = TypologieDesBiensSupports.getBDC();
	}

	// ---Getters et setters---

	public Hashtable<String, TypeBien> getTableau() {
		return this.tableau;
	}
	
	public int getSize(){
		return this.tableau.size();
	}

	public void setTableau(Hashtable<String, TypeBien> tab) {
		this.tableau = tab;
	}

	public TypeBien getTypeBien(String type) {
		return this.tableau.get(type);
	}
	
	public void setTypeBien (int i, TypeBien type){
		this.tableau.replace(this.getTypeBien(i).getIntitule(), type);
		this.setChanged();               // PAC
		this.notifyObservers();          // PAC
	}
	
	public TypeBien getTypeBien(int i){
		ArrayList<TypeBien> typesBien = new ArrayList<TypeBien>(tableau.values());
		return typesBien.get(i);
	}

	public static Hashtable<String, TypeBien> getBDC() {
		return bdcTypeBiensSupports;
	}

	// ---Services---
	
	// On verifie si un type de bien support est nouveau
	// cad s'il n'est pas présent dans la bdc et qu'il a été ajouté au tableau
	// ALGO A AMELIORER
	public boolean estNouveauTypeBien(TypeBien type) {
		boolean b = true;
		for (TypeBien t : TypologieDesBiensSupports.getBDC().values()) {
			b = b && (t.getIntitule() != type.getIntitule());
		}
		if (b) {
			for (TypeBien t : this.tableau.values()) {
				b = b && (t.getIntitule() != type.getIntitule());
			}
		}
		return b;
	}

	// On ajoute une ligne au tableau seulement si tous les champs sont
	// renseignés
	// ATTENTION : si 2 types ont le même intitulé, un seul sera présent dans la JTable
	public void addTypeBienSupport(TypeBien type) {
		if (!type.isIncomplete()) {
			this.tableau.put(type.getIntitule(), type);
			this.setChanged();      // PAC
			this.notifyObservers(); // PAC
		}
	}

	// Suppression d'une ligne du tableau
	public void removeTypeBienSupport(TypeBien type) {
		this.tableau.remove(type.getIntitule());
		this.setChanged();      // PAC
		this.notifyObservers(); // PAC
	}
	
	// NEW !!!
	public void setDescriptionTypeBienSupport (String description, TypeBien type){
		this.tableau.get(type.getIntitule()).setDescription(description);
		this.setChanged();      // PAC
		this.notifyObservers(); // PAC
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
