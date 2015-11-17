package bdc;

import java.util.Hashtable;

import autres.*;

/**
 * Cette classe correspond à la modélisation de la base de données préexistante
 * de biens essentiels
 * 
 * @author Francois Adam
 */

public class BDCBiensEssentiels {
	// --L'unique instance qui sera accessible de partout--

	private static BDCBiensEssentiels bdcBiensEssentiels = new BDCBiensEssentiels();

	// ---Variables d'instance

	private Hashtable<String, Biens> lesBiens;

	// ---Constructeurs---

	private BDCBiensEssentiels() {
		this.lesBiens = new Hashtable<String, Biens>();
	}

	private BDCBiensEssentiels(Hashtable<String, Biens> lesBiens) {
		this.lesBiens = lesBiens;
	}

	// ---Getters et setters---

	public Hashtable<String, Biens> getLesBiens() {
		return lesBiens;
	}

	public void setLesBiens(Hashtable<String, Biens> lesBiens) {
		this.lesBiens = lesBiens;
	}

	public Biens getBien(String nomBien) {
		return this.getLesBiens().get(nomBien);
	}
	
	public static BDCBiensEssentiels getInstance(){
		return bdcBiensEssentiels;
	}

	// ---Services---

	public void ajouterBien(Biens bien) {
		this.getLesBiens().put(bien.getIntitule(), bien);
	}

	public void supprimerBien(String nomBien) {
		this.getLesBiens().remove(nomBien);
	}
	
	
}
