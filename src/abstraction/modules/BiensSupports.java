package abstraction.modules;
import java.util.Hashtable;
import abstraction.autres.*;

/**
 * Cette classe correspond à la modélisation de la base de données préexistante
 * de biens supports augmentée de nouveaux biens supports que l'on peut rajouter
 * 
 * @author Francois Adam
 */

public class BiensSupports extends Module{
	// --L'unique instance qui sera accessible de partout--
	private static Hashtable<String,Biens> bdcBiensSupports ;
	
	// ---Variables d'instance

	private Hashtable<String, Biens> lesBiens;

	// ---Constructeurs---

	private BiensSupports(boolean creationBdc) {
		super("Biens essentiels");
		this.importerBDC();
		this.lesBiens = new Hashtable<String, Biens>();
		this.successeurs.add(MappingDesBiens.getInstance());
		this.successeurs.add(EvenementsRedoutes.getInstance());
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		this.lesBiens = this.getBDC();
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
	
	public Hashtable<String,Biens> getBDC(){
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
	
	public void importerBDC(){
		//TODO importer la BDC via le fichier excel
	}
}
