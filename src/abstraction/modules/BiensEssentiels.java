package abstraction.modules;
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

	private static Hashtable<String, Bien> bdcBiensEssentiels;

	// ---Variables d'instance

	private Hashtable<String, Bien> lesBiens;

	// ---Constructeurs---

	public BiensEssentiels() {
		super("Biens essentiels");
		this.importerBDC();
		this.lesBiens = new Hashtable<String, Bien>(); // useless ?
		this.successeurs.add(MappingDesBiens.getInstance());
		this.successeurs.add(EvenementsRedoutes.getInstance());
		this.cree = false;
		this.coherent = false;
		this.disponible = true;
		this.lesBiens = BiensEssentiels.getBDC();
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

	public static Hashtable<String, Bien> getBDC() {
		return bdcBiensEssentiels;
	}

	// ---Services---

	public void ajouterBien(Bien bien) {
		this.getLesBiens().put(bien.getIntitule(), bien);
	}

	public void supprimerBien(String nomBien) {
		this.getLesBiens().remove(nomBien);
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
	
	public void importerBDC(){
		//TODO importer la bdc avec le fichier excel
	}
}
