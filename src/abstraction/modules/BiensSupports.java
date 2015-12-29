package abstraction.modules;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import abstraction.autres.*;

/**
 * Cette classe correspond � la mod�lisation de la base de donn�es pr�existante
 * de biens supports augment�e de nouveaux biens supports que l'on peut rajouter
 * 
 * @author Francois Adam
 */

public class BiensSupports extends Module{

	// ---Variables d'instance

	private Hashtable<String, Bien> lesBiens;
	private LinkedList<String> nomColonnesSup;

	// ---Constructeurs---

	public BiensSupports() {
		super("BiensSupports");
		this.lesBiens = new Hashtable<String, Bien>();
		this.lesBiens.put("Disponibilite", new Bien("il s'agit du bien numero 1", "Disponibilite", "type 1", new LinkedList<String>()));
		this.lesBiens.put("Integrite", new Bien("il s'agit du bien numero 2", "Integrite", "type 2", new LinkedList<String>()));
		this.lesBiens.put("Confidentialite", new Bien("il s'agit du bien numero 3", "Confidentialite", "type 3", new LinkedList<String>()));
		this.nomColonnesSup = new LinkedList<String>();
		//TODO Decomenter quand les autres parties seront OK
		/*
		this.successeurs.add(MappingDesBiens.getInstance());
		this.successeurs.add(ScenariosDeMenacesTypes.getInstance());
		this.predecesseurs.add(TypologieDesBiensSupports.getInstance());
		*/
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
	}

	// ---Getters et setters---

	public Hashtable<String, Bien> getLesBiens() {
		return lesBiens;
	}

	public void setLesBiens(Hashtable<String, Bien> lesBiens) {
		this.lesBiens = lesBiens;
	}

	public Bien getBien(String nomBien) {
		return this.getLesBiens().get(nomBien);
	}
	
	public Bien getBien(int index){
		return (Bien) lesBiens.values().toArray()[index];
	}
	
	public LinkedList<String> getNomColonnesSup(){
		return this.nomColonnesSup;
	}
	
	public void setNomColonnesSup(LinkedList<String> nomColonnesSup){
		this.nomColonnesSup=nomColonnesSup;
	}

	// ---Services---
	
	public void ajouterColonne(String nomColonne){
		this.getNomColonnesSup().addFirst(nomColonne);
	}
	
	public void enleverPremiereColonne(){
		this.getNomColonnesSup().removeFirst();
	}

	public int nombreDeBiens(){
		return lesBiens.size();
	}
	
	public void ajouterBien(Bien bien) {
		this.getLesBiens().put(bien.getIntitule(), bien);
	}

	public void supprimerBien(String nomBien) {
		this.getLesBiens().remove(nomBien);
	}

	public Hashtable<String, Bien> getBiensRetenus() {
		Hashtable<String, Bien> resultat = new Hashtable<String, Bien>();
		for (Bien bien : this.getLesBiens().values()) {
			if (bien.isRetenu()) {
				resultat.put(bien.getIntitule(), bien);
			}
		}
		return resultat;
	}

	public void retenirBien(String intituleBien) {
		this.getBien(intituleBien).setRetenu(true);
	}

	public void retenirBien(Bien bien) {
		this.retenirBien(bien.getIntitule());
	}
	
	public String toString(){
		return "Biens Supports";
	}
}
