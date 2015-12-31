package abstraction.modules;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JLabel;

import abstraction.Etude;
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
	private TypologieDesBiensSupports typologie;
	private LinkedList<String> nomColonnesSup;

	// ---Constructeurs---

	public BiensSupports(Etude etude) {
		super("BiensSupports");
		this.etude=etude;
		
		this.lesBiens = new Hashtable<String, Bien>();
		//TODO supprimer quand tous les tests seront ok
		//this.lesBiens.put("Disponibilite", new Bien("il s'agit du bien numero 1", "Disponibilite", "type 1", new LinkedList<String>()));
		//this.lesBiens.put("Integrite", new Bien("il s'agit du bien numero 2", "Integrite", "type 2", new LinkedList<String>()));
		//this.lesBiens.put("Confidentialite", new Bien("il s'agit du bien numero 3", "Confidentialite", "type 3", new LinkedList<String>()));
		//
		this.nomColonnesSup = new LinkedList<String>();

		//this.successeurs.add(this.getEtude().getModule("MappingDesBiens"));
		//this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesTypes"));
		this.predecesseurs.add(this.getEtude().getModule("TypologieDesBiensSupports"));
		
		typologie = (TypologieDesBiensSupports) this.getEtude().getModule("TypologieDesBiensSupports");
		
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
	
	public TypologieDesBiensSupports getTypologie(){
		return typologie;
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
	
	public boolean estCoherent(){
		boolean resultat = true;
		this.problemesDeCoherence = new ArrayList<String>();
		for(Bien b : this.getLesBiens().values()){
			if(!b.estComplet()){
				this.problemesDeCoherence.add("bien support \" " + b.getIntitule() + " \" incomplet");
				resultat = false;
			}
		}
		if(this.getBiensRetenus().size() <= 0){
			this.problemesDeCoherence.add("Aucun bien support retenu");
			resultat = false;
		}	
		return resultat;
	}
}
