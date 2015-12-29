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
 * de biens essentiels augment�e de nouveaux biens essentiels que l'on peut
 * rajouter
 * 
 * @author Francois Adam
 */

public class BiensEssentiels extends Module {

	// ---Variables d'instance

	private Hashtable<String, Bien> lesBiens;
	private LinkedList<String> nomColonnesSup;
	// ---Constructeurs---

	public BiensEssentiels(Etude etude) {
		super("BiensEssentiels");
		this.etude=etude;
		this.lesBiens = new Hashtable<String, Bien>();
		//TODO supprimer quand tous les tests seront ok
		this.lesBiens.put("Disponibilite", new Bien("il s'agit du bien numero 1", "Disponibilite", "", new LinkedList<String>()));
		this.lesBiens.put("Integrite", new Bien("il s'agit du bien numero 2", "Integrite", "", new LinkedList<String>()));
		this.lesBiens.put("Confidentialite", new Bien("il s'agit du bien numero 3", "Confidentialite", "", new LinkedList<String>()));
		//
		this.nomColonnesSup = new LinkedList<String>();

		//this.successeurs.add(this.getEtude().getModule("MappingDesBiens"));
		//this.successeurs.add(this.getEtude().getModule("EvenementsRedoutes"));

		this.cree = false;
		this.coherent = false;
		this.disponible = true;
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

	public int nombreDeBiens(){
		return lesBiens.size();
	}
	
	public void ajouterBien(Bien bien) {
		this.getLesBiens().put(bien.getIntitule(), bien);
	}

	public void supprimerBien(String nomBien) {
		this.getLesBiens().remove(nomBien);
	}
	
	public void ajouterColonne(String nomColonne){
		this.getNomColonnesSup().addFirst(nomColonne);
	}
	
	public void enleverPremiereColonne(){
		this.getNomColonnesSup().removeFirst();
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
		return "Biens Essentiels";
	}
	
	public boolean estCoherent(){
		boolean resultat = true;
		this.problemesDeCoherence = new ArrayList<JLabel>();
		for(Bien b : this.getLesBiens().values()){
			if(!b.estComplet()){
				JLabel label = new JLabel("bien essentiel \" " + b.getIntitule() + " \" incomplet");
				label.setForeground(Color.red);
				this.problemesDeCoherence.add(label);
				resultat = false;
			}
		}
		if(this.getBiensRetenus().size() <= 0){
			JLabel label = new JLabel("Aucun bien essentiel retenu");
			label.setForeground(Color.red);
			this.problemesDeCoherence.add(label);
			resultat = false;
		}
		if(resultat){
			this.problemesDeCoherence.add(new JLabel("Aucun probleme de coherence au niveau des biens essentiels."));
		}		
		return resultat;
	}
}
