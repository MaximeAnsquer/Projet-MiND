package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.autres.*;

/**
 * Cette classe correspond à la modélisation de la base de données préexistante
 * de biens supports augmentée de nouveaux biens supports que l'on peut rajouter
 * 
 * @author Francois Adam
 */

public class BiensSupports extends Module {
	// --L'unique instance qui sera accessible de partout--
	private static BiensSupports bdcBiensSupports = new BiensSupports();
	
	private static BiensSupports instance = new BiensSupports();
	
	// ---Variables d'instance

	private Hashtable<String, Biens> lesBiens;

	// ---Constructeurs---

	private BiensSupports() {
		super("Biens supports");
		this.predecesseurs.add(TypologieBiensSupports.getInstance());
		this.successeurs.add(MappingDesBiens.getInstance());
		this.successeurs.add(ScenariosDeMenacesTypes.getInstance());
		this.cree=false;
		this.coherent=false;
		this.disponible=false;
		this.lesBiens = BDCBiensSupports.getInstance().getLesBiens();
		
	}
	
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
	
	public static BiensSupports getInstance(){
		return instance;
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
