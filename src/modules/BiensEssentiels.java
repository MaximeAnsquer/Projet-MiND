package modules;

import java.util.Hashtable;

import autres.*;
import bdc.BDCBiensEssentiels;
import bdc.BDCCriteresDeSecurite;

/**
 * Cette classe correspond à la modélisation de la base de données préexistante
 * de biens essentiels augmentée de nouveaux biens essentiels que l'on peut
 * rajouter
 * 
 * @author Francois Adam
 */

public class BiensEssentiels extends Module{
	// --L'unique instance qui sera accessible de partout--

	private static BiensEssentiels instance = new BiensEssentiels();
	
	// ---Variables d'instance

	private Hashtable<String, Biens> lesBiens;

	// ---Constructeurs---

	private BiensEssentiels() {
		super("Biens essentiels");
		this.successeurs.add(MappingBiens.getInstance());
		this.successeurs.add(EvenementsRedoutes.getInstance());
		this.cree=false;
		this.coherent=false;
		this.disponible=false;
		this.lesBiens = BDCBiensEssentiels.getInstance().getLesBiens();
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
	
	public static BiensEssentiels getInstance(){
		return instance;
	}

	// ---Services---

	public void ajouterBien(Biens bien) {
		this.getLesBiens().put(bien.getIntitule(), bien);
	}

	public void supprimerBien(String nomBien) {
		this.getLesBiens().remove(nomBien);
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
