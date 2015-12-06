package abstraction.modules;
import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.autres.*;

/**
 * Cette classe correspond à la modélisation de la base de données préexistante
 * de biens essentiels augmentée de nouveaux biens essentiels que l'on peut
 * rajouter
 * 
 * @author Francois Adam
 */

public class BiensEssentiels extends Module {

	// ---Variables d'instance

	private Hashtable<String, Bien> lesBiens;
	private ArrayList<String> nomColonneSup;
	// ---Constructeurs---

	public BiensEssentiels() {
		super("BiensEssentiels");
		this.lesBiens = new Hashtable<String, Bien>();
		this.lesBiens.put("Disponibilite", new Bien("il s'agit du bien numero 1", "bien 1", "", null, null));
		this.lesBiens.put("Integrite", new Bien("il s'agit du bien numero 2", "bien 2", "", null, null));
		this.lesBiens.put("Confidentialite", new Bien("il s'agit du bien numero 3", "bien 3", "", null, null));
		this.nomColonneSup = new ArrayList<String>();
		//TODO Decomenter quand les autres parties seront OK
		/*
		this.successeurs.add(MappingDesBiens.getInstance());
		this.successeurs.add(EvenementsRedoutes.getInstance());
		*/
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
	
	public ArrayList<String> getNomColonneSup(){
		return this.nomColonneSup;
	}
	
	public void setNomColonneSup(ArrayList<String> nomColonneSup){
		this.nomColonneSup=nomColonneSup;
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
		this.getNomColonneSup().add(nomColonne);
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
}
