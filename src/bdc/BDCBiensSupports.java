package bdc;

import java.util.Hashtable;

import autres.*;

/**
 * Cette classe correspond à la modélisation de la base de données préexistante
 * de biens supports
 * 
 * @author Francois Adam
 */

public class BDCBiensSupports {
	// --L'unique instance qui sera accessible de partout--

	public static BDCBiensSupports bdcBiensSupports;

	// ---Variables d'instance

	private Hashtable<String, Biens> lesBiens;

	// ---Constructeurs---

	public BDCBiensSupports() {
		this.lesBiens = new Hashtable<String, Biens>();
	}

	public BDCBiensSupports(Hashtable<String, Biens> lesBiens) {
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
		return this.lesBiens.get(nomBien);
	}

	// ---Services---

	public void ajouterBien(Biens bien) {
		this.lesBiens.put(bien.getIntitule(), bien);
	}

	public void supprimerBien(String nomBien) {
		this.lesBiens.remove(nomBien);
	}
}
