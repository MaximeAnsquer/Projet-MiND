package abstraction.bdc;

import java.util.Hashtable;

import abstraction.autres.*;

/**
 * Cette classe correspond à la modélisation de la base de données préexistante
 * de biens supports
 * 
 * @author Francois Adam
 */

public class BDCBiensSupports {
	// --L'unique instance qui sera accessible de partout--

	private static BDCBiensSupports bdcBiensSupports = new BDCBiensSupports();

	// ---Variables d'instance

	private Hashtable<String, Biens> lesBiens;

	// ---Constructeurs---

	private BDCBiensSupports() {
		this.lesBiens = new Hashtable<String, Biens>();
	}

	private BDCBiensSupports(Hashtable<String, Biens> lesBiens) {
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
	
	public static BDCBiensSupports getInstance(){
		return bdcBiensSupports;
	}

	// ---Services---

	public void ajouterBien(Biens bien) {
		this.getLesBiens().put(bien.getIntitule(), bien);
	}

	public void supprimerBien(String nomBien) {
		this.getLesBiens().remove(nomBien);
	}
}
