package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.autres.ScenarioType;

public class ScenariosDeMenacesTypes extends Module {

	// Represente la bdc
	private static Hashtable<String, ScenarioType> bdcScenariosMenacesTypes;

	// Variable d'instance
	Hashtable<String, ScenarioType> tableau;
	private ScenarioType scenarioTypeCourant ;
	// On liste les critères de sécurité retenus dans le module Scénarios de menaces génériques
	private ArrayList<String> nomColonneSup ;

	public ScenariosDeMenacesTypes() {
		super("ScenariosDeMenacesTypes");
		this.tableau = new Hashtable<String, ScenarioType>();
		this.nomColonneSup=new ArrayList<String>();
		// this.predecesseurs.add(this.getEtude().getModule("ScenariosDeMenacesGeneriques"));
		// this.predecesseurs.add(this.getEtude().getModule("BiensSupports"));
		// this.predecesseurs.add(this.getEtude().getModule("Metriques"));
		// this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		this.importerBDC();
		// this.tableau = ScenariosDeMenacesTypes.getBDC();
	}
	
	public int getSize(){
		return this.tableau.size();
	}
	
	public ScenarioType getScenarioTypeCourant() {
		return scenarioTypeCourant;
	}

	public void setScenarioTypeCourant(ScenarioType scenarioTypeCourant) {
		this.scenarioTypeCourant = scenarioTypeCourant;
		// this.setChanged();         // PAC
		// this.notifyObservers();    // PAC
	}

	public ArrayList<String> getNomColonneSup() {
		return nomColonneSup;
	}

	public void setNomColonneSup(ArrayList<String> nomColonneSup) {
		this.nomColonneSup = nomColonneSup;
	}

	public Hashtable<String, ScenarioType> getTableau() {
		return tableau;
	}

	public void setTableau(Hashtable<String, ScenarioType> tableau) {
		this.tableau = tableau;
	}
	
	public ScenarioType getScenarioType (int i){
		ArrayList<ScenarioType> scenariosMenacesTypes = new ArrayList<ScenarioType>(tableau.values());
		return scenariosMenacesTypes.get(i);
	}

	public static Hashtable<String, ScenarioType> getBDC() {
		return bdcScenariosMenacesTypes;
	}

	private void importerBDC() {
		// TODO Auto-generated method stub
	}
	
	public String toString(){
		return "Scénarios de menaces typés";
	}
}
