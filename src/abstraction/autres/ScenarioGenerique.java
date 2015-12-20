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
	private TypeBien type;
	private String Id;
	private String intituleGenerique;
	private Hashtable<String, SourceDeMenace> menaces;
	private Hashtable<String, Critere> criteres; // représente les colonnes
	private Hashtable<String, Boolean> CritereRetenu ;
	private boolean retenu;

	// Constructeur
	public ScenarioGenerique(TypeBien type, String id, String intitule,
			Hashtable<String, SourceDeMenace> menaces,
			Hashtable<String, Critere> criteres, boolean retenu) {
		this.type = type;
		this.Id = id;
		this.intituleGenerique = intitule;
		this.menaces = menaces;
		this.criteres = criteres;
		this.retenu = retenu;
	}
	
	// Constructeur : Nouveau Type Bien défini précédemment
	public ScenarioGenerique(TypeBien type){
		this(type,"", "",new Hashtable<String, SourceDeMenace>(),
				new Hashtable<String, Critere>(), true);
	}

	// Constructeur : Ligne vide
	public ScenarioGenerique() {
		this(new TypeBien(), "", "", new Hashtable<String, SourceDeMenace>(),
				new Hashtable<String, Critere>(), false);
	}
	
	// ---Getters et setters---
	
	public TypeBien getType() {
		return type;
	}

	public void setType(TypeBien type) {
		this.type = type;
	}

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

	public Hashtable<String, SourceDeMenace> getMenaces() {
		return menaces;
	}

	public void setMenaces(Hashtable<String, SourceDeMenace> menaces) {
		this.menaces = menaces;
	}

	public Hashtable<String, Critere> getCriteres() {
		return criteres;
	}

	public void setCriteres(Hashtable<String, Critere> criteres) {
		this.criteres = criteres;
	}
	
	// i est l'indice du critère dans l'ArrayList/ou Hashtable
	public boolean isRetenuCritere(int i){
		ArrayList<Boolean> listeCriteres = new ArrayList<Boolean>(this.CritereRetenu.values()) ;
		return (boolean) listeCriteres.get(i) ;
	}

	public boolean isRetenu() {
		return retenu;
	}

	public void setRetenu(boolean retenu) {
		this.retenu = retenu;
	}
}
