package abstraction.autres;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Classe representant les scénarios génériques représentés en mémoire par : un
 * type de bien, un numéro Id, un intitule, un ensemble de sources menaces et de
 * critères de sécurité Cette classe est utile pour le module
 * "Scenario De Menaces Generiques" Elle représente plus généralement une ligne
 * du tableau généré dans "Scenario De Menaces Generiques"
 * 
 * @author Belghiti Ali
 */

public class ScenarioGenerique {
	private String typeBienSupport;
	private String Id;
	private String intituleGenerique;
	private Hashtable<String,Boolean> CriteresSup; // représente les critères que l'utilisateur peut rajouter
	private boolean retenu;

	// Constructeur
	public ScenarioGenerique(String type, String id, String intitule,
			Hashtable<String,Boolean> criteres, boolean retenu) {
		this.typeBienSupport = type;
		this.Id = id;
		this.intituleGenerique = intitule;
		this.CriteresSup= criteres;
		this.retenu = retenu;
	}
	
	// Constructeur : Nouveau Type de bien support défini précédemment
	public ScenarioGenerique(String type, String id, String intitule) {
		this(type, id, intitule, new Hashtable<String,Boolean>(),
				true);
	}

	// Constructeur : Scénario non retenu
	public ScenarioGenerique() {
		this("", "", "",
				new Hashtable<String,Boolean>(), false);
	}
	
	// ---Getters et setters---

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getIntitule() {
		return intituleGenerique;
	}

	public void setIntitule(String intitule) {
		this.intituleGenerique = intitule;
	}
	
	public String getTypeBienSupport() {
		return typeBienSupport;
	}

	public void setTypeBienSupport(String typeBienSupport) {
		this.typeBienSupport = typeBienSupport;
	}

	public Hashtable<String,Boolean> getCriteresSup() {
		return CriteresSup;
	}

	public void setCriteresSup(Hashtable<String,Boolean> nomCriteresSup) {
		this.CriteresSup = nomCriteresSup;
	}

	public boolean isRetenu() {
		return retenu;
	}

	public void setRetenu(boolean retenu) {
		this.retenu = retenu;
	}

	// i est l'indice du critère dans l'ArrayList
	public boolean isRetenuCritere(int i){
		ArrayList<Boolean> criteresRetenus = new ArrayList<Boolean>(this.CriteresSup.values());
		return criteresRetenus.get(i);
	}
	
	

	public boolean isRetenuScenario() {
		return retenu;
	}

	public void setRetenuScenario(boolean retenu) {
		this.retenu = retenu;
	}
	
	// On vérifie si le type a bien toutes les informations renseignées
	// Tous les champs doivent être renseignés
	public boolean isIncomplete() {
		return (this.typeBienSupport.equals("") || this.Id.equals("") || this.intituleGenerique
				.equals(""));
	}
}
