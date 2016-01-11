package abstraction.modules;
import java.util.ArrayList;
import java.util.LinkedList;


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

	private LinkedList<Bien> lesBiens;
	private LinkedList<String> nomColonnesSup;

	// ---Constructeurs---

	public BiensEssentiels(Etude etude) {
		super("BiensEssentiels");
		this.etude=etude;
		this.lesBiens = new LinkedList<Bien>();
		this.nomColonnesSup = new LinkedList<String>();
		this.cree = false;
		this.coherent = false;
		this.disponible = true;
	}

	// ---Getters et setters---

	public LinkedList<Bien> getLesBiens() {
		return lesBiens;
	}

	public void setLesBiens(LinkedList<Bien> lesBiens) {
		this.lesBiens = lesBiens;
	}
	
	public Bien getBien (String intitule){
		int index = 0;
		while (index<this.nombreDeBiens() && !this.getBien(index).getIntitule().equals(intitule)){
			index++;
		}
		if (index<this.nombreDeBiens()){
			return this.getBien(index);
		}
		else{
			return null;
		}
		
	}

	public Bien getBien(int index){
		return this.lesBiens.get(index);
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
		this.getLesBiens().add(bien);
	}

	public void supprimerBien(String nomBien) {
		this.getLesBiens().remove(this.getBien(nomBien));
	}
	
	public void ajouterColonne(String nomColonne){
		this.getNomColonnesSup().addFirst(nomColonne);
	}
	
	public void enleverPremiereColonne(){
		this.getNomColonnesSup().removeFirst();
	}

	public LinkedList<Bien> getBiensRetenus() {
		LinkedList<Bien> resultat = new LinkedList<Bien>();
		for (Bien bien : this.getLesBiens()) {
			if (bien.isRetenu()) {
				resultat.add(bien);
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
		this.problemesDeCoherence = new ArrayList<String>();
		for(Bien b : this.getLesBiens()){
			if(!b.estComplet()){
				this.problemesDeCoherence.add("bien essentiel \" " + b.getIntitule() + " \" incomplet");
				resultat = false;
			}
		}
		if(this.getBiensRetenus().size() <= 0){
			this.problemesDeCoherence.add("Aucun bien essentiel retenu");
			resultat = false;
		}
		return resultat;
	}
}
