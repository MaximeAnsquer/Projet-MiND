package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.autres.ScenarioGenerique;
import abstraction.autres.TypeBien;

public class ScenariosDeMenacesGeneriques extends Module {
	
	// Represente la bdc
	private static Hashtable<String, ScenarioGenerique> bdcScenariosMenacesGeneriques;

	// Variable d'instance
	private Hashtable<String, ScenarioGenerique> tableau;
	private ScenarioGenerique scenarioCourant ;
	
	public ScenariosDeMenacesGeneriques(){
		super("Scenario de Menaces Generiques");
		this.tableau = new Hashtable<String, ScenarioGenerique>();
		//this.predecesseurs.add(this.getEtude().getModule("Typologie des biens supports"));
		//this.predecesseurs.add(this.getEtude().getModule("Sources de menaces"));
		//this.successeurs.add(this.getEtude().getModule("Biens Supports"));
		//this.successeurs.add(this.getEtude().getModule("Scenario de Menaces Types"));
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
						&& (scenario.getType().getIntitule() != type
								.getIntitule());
			}
		}
		return b;
	}
	
	// L'ajout d'une ligne dans le tableau correspond ici
	// à un nouveau type de bien support non référencé dans la bdc
	public void addTypeBienSupport(TypeBien type) {
		/*
		if (estNouveauTypeBien(type)){
			
		}
		//*/
		this.tableau.put(type.getIntitule(),new ScenarioGenerique(type));
	}
	
	// L'ajout d'une ligne dans le tableau correspond ici
	// à un type de bien support référencé
	
	public void addScenarioGenerique(TypeBien type) {
		// TODO
	}
	
	private void importerBDC() {
		// TODO Auto-generated method stub		
	}
	
	public String toString(){
		return "Scenarios de menaces génériques";
	}
	
}
