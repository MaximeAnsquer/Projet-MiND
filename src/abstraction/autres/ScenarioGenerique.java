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
	private ArrayList<String> nomCritereSup; // représente les critères que l'utilisateur peut rajouter
	private ArrayList<Boolean> contenuCriteresSup ;
	private boolean retenu;

	// Constructeur
	public ScenarioGenerique(String type, String id, String intitule,
			ArrayList<String> criteres, ArrayList<Boolean> criteresRetenus, boolean retenu) {
		this.typeBienSupport = type;
		this.Id = id;
		this.intituleGenerique = intitule;
		this.nomCritereSup= criteres;
		this.contenuCriteresSup=criteresRetenus;
		this.retenu = retenu;
	}
	
	// Constructeur : Nouveau Type de bien support défini précédemment
	public ScenarioGenerique(String type) {
		this(type, "", "", new ArrayList<String>(), new ArrayList<Boolean>(),
				true);
	}

	// Constructeur : Scénario non retenu
	public ScenarioGenerique() {
		this("", "", "",
				new ArrayList<String>(), new ArrayList<Boolean>(), false);
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

	public ArrayList<String> getNomCriteresSup() {
		return nomCritereSup;
	}

	public void setNomCriteresSup(ArrayList<String> nomCriteresSup) {
		this.nomCritereSup = nomCriteresSup;
	}

	public ArrayList<Boolean> getContenuCriteresSup() {
		return contenuCriteresSup;
	}

	public void setContenuCriteresSup(ArrayList<Boolean> contenuCriteresSup) {
		this.contenuCriteresSup = contenuCriteresSup;
	}

	public boolean isRetenu() {
		return retenu;
	}

	public void setRetenu(boolean retenu) {
		this.retenu = retenu;
	}

	// i est l'indice du critère dans l'ArrayList
	public boolean isRetenuCritere(int i){
		return this.contenuCriteresSup.get(i);
	}

	public boolean isRetenuScenario() {
		return retenu;
	}

	public void setRetenuScenario(boolean retenu) {
		this.retenu = retenu;
	}
}
