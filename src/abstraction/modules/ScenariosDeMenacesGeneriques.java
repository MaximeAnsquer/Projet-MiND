package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.autres.ScenarioGenerique;
import abstraction.autres.ScenarioGenerique;
import abstraction.autres.TypeBien;

public class ScenariosDeMenacesGeneriques extends Module {
	
	// Represente la bdc
	private static Hashtable<String, ScenarioGenerique> bdcScenariosMenacesGeneriques;

	// Variable d'instance
	private Hashtable<String, ScenarioGenerique> tableau;
	private ScenarioGenerique scenarioCourant ;
	private ArrayList<String> nomColonneSup;
	
	public ScenariosDeMenacesGeneriques(){
		super("Scenario de Menaces Generiques");
		this.tableau = new Hashtable<String, ScenarioGenerique>();
		//this.predecesseurs.add(this.getEtude().getModule("Typologie des biens supports"));
		//this.predecesseurs.add(this.getEtude().getModule("Sources de menaces"));
		//this.successeurs.add(this.getEtude().getModule("Biens Supports"));
		//this.successeurs.add(this.getEtude().getModule("Scenario de Menaces Types"));
		this.scenarioCourant= new ScenarioGenerique();
		this.nomColonneSup = new ArrayList<String>();
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		this.importerBDC();
		//this.tableau = ScenariosDeMenacesGeneriques.getBDC();
	}
	
	// ---Getters et setters---
	
	public int getSize(){
		return this.tableau.size();
	}
	
	public ScenarioGenerique getScenarioCourant(){
		return this.scenarioCourant;
	}
	
	public void setScenarioCourant(ScenarioGenerique scenarioCourant){
		this.scenarioCourant=scenarioCourant;
		this.setChanged();         // PAC
		this.notifyObservers();    // PAC
	}
	
	public ArrayList<String> getNomColonneSup() {
		return nomColonneSup;
	}

	public void setNomColonneSup(ArrayList<String> nomColonneSup) {
		this.nomColonneSup = nomColonneSup;
	}

	public Hashtable<String, ScenarioGenerique> getTableau() {
		return tableau;
	}

	public void setTableau(Hashtable<String, ScenarioGenerique> tableau) {
		this.tableau = tableau;
	}
	
	public ScenarioGenerique getScenarioGenerique(int i) {
		ArrayList<ScenarioGenerique> scenariosMenacesGeneriques = new ArrayList<ScenarioGenerique>(
				tableau.values());
		return scenariosMenacesGeneriques.get(i);
	}
	
	public static Hashtable<String, ScenarioGenerique> getBDC(){
		return bdcScenariosMenacesGeneriques;
	}
	
	// ---Services---
	
	// L'idée n'est pas encore finalisée : 
	
	// Un TypeBien est nouveau dans le cas où il n'est pas contenu dans la bdc de cette classe
	// et qu'il n'est pas dans la bdc de la classe TypeBien
	public boolean estNouveauTypeBien(TypeBien type) {
		boolean b = ((TypologieDesBiensSupports) this.getEtude().getModule(
				"Typologie des biens supports")).estNouveauTypeBien(type);
		if (b) {
			for (ScenarioGenerique scenario : bdcScenariosMenacesGeneriques
					.values()) {
				b = b
						&& (scenario.getTypeBienSupport() != type
								.getIntitule());
			}
		}
		return b;
	}
	
	// L'ajout d'une ligne dans le tableau correspond ici
	// à un type de bien support référencé
	
	public void addScenarioGenerique(ScenarioGenerique scenario) {
		if (this.nomColonneSup!=null){
			for(String nomCritere : this.nomColonneSup){
				scenario.getCriteresSup().put(nomCritere,false);
			}
		}
		this.tableau.put(scenario.getIntitule(), scenario);
		this.setChanged();                           // PAC
		this.notifyObservers();                      // PAC
	}
	
	public void removeScenarioGenerique(ScenarioGenerique scenario){
		if (this.nomColonneSup!=null){
			for(String nomCritere : this.nomColonneSup){
				scenario.getCriteresSup().remove(nomCritere);
			}
		}
		this.tableau.remove(scenario.getIntitule(), scenario);
		this.setChanged();                           // PAC
		this.notifyObservers();                      // PAC
	}
	
	// Ajout d'une colonne
	public void addCritere (String nomCritere){
		this.nomColonneSup.add(nomCritere);
		for (int i=0 ; i<this.tableau.size() ; i++){
			this.getScenarioGenerique(i).getCriteresSup().put(nomCritere, false);
		}
		this.setChanged();                           // PAC
		this.notifyObservers();                      // PAC
	}
	
	//Suppression d'une colonne
	public void removeCritere (String nomCritere){
		this.nomColonneSup.remove(nomCritere);
		for (int i=0 ; i<this.tableau.size() ; i++){
			this.getScenarioGenerique(i).getCriteresSup().remove(nomCritere);
		}
		this.setChanged();                           // PAC
		this.notifyObservers();                      // PAC
	}
	
	public Hashtable<String, ScenarioGenerique> getScenariosGeneriquesRetenus() {
		Hashtable<String, ScenarioGenerique> resultat = new Hashtable<String, ScenarioGenerique>();
		for (ScenarioGenerique scenario : this.getTableau().values()) {
			if (scenario.isRetenu()) {
				resultat.put(scenario.getIntitule(), scenario);
			}
		}
		return resultat;
	}
	
	private void importerBDC() {
		// TODO Auto-generated method stub		
	}
	
	public String toString(){
		return "Scenarios de menaces generiques";
	}
	
}
