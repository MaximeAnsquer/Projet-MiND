package modules;

import java.util.Hashtable;

import autres.*;
import bdc.BDCBiensSupports;

/**
 * Cette classe correspond � la mod�lisation de la base de donn�es pr�existante
 * de biens supports augment�e de nouveaux biens supports que l'on peut rajouter
 * 
 * @author Francois Adam
 */

public class BiensSupports extends Module {
	// --L'unique instance qui sera accessible de partout--

	public static BDCBiensSupports bdcBiensSupports;

	// ---Variables d'instance

	private Hashtable<String, Biens> lesBiens;

	// ---Constructeurs---

	public BiensSupports() {
		super("Biens supports");
		this.lesBiens = BDCBiensSupports.bdcBiensSupports.getLesBiens();
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

	public Hashtable<String, Biens> getBiensRetenus() {
		Hashtable<String, Biens> resultat = new Hashtable<String, Biens>();
		for (Biens bien : this.getLesBiens().values()) {
			if (bien.isRetenu()) {
				resultat.put(bien.getIntitule(), bien);
			}
		}
		return resultat;
	}

	public void retenirBien(String intituleBien) {
		this.getBien(intituleBien).setRetenu(true);
	}

	public void retenirBien(Biens bien) {
		this.retenirBien(bien.getIntitule());
	}
}
