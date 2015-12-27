package abstraction.modules;

import java.util.Hashtable;

import abstraction.autres.ScenarioType;

public class ScenariosDeMenacesTypes extends Module {

	// Represente la bdc
	private static Hashtable<String, ScenarioType> bdcScenariosMenacesTypes;

	// Variable d'instance
	Hashtable<String, ScenarioType> tableau;

	public ScenariosDeMenacesTypes() {
		super("Scenario de Menaces Types");
		this.tableau = new Hashtable<String, ScenarioType>();
		this.predecesseurs.add(this.getEtude().getModule(
				"ScenariosDeMenacesGeneriques"));
		this.predecesseurs.add(this.getEtude().getModule("BiensSupports"));
		this.predecesseurs.add(this.getEtude().getModule("Metriques"));
		this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		this.importerBDC();
		this.tableau = ScenariosDeMenacesTypes.getBDC();
	}

	public Hashtable<String, ScenarioType> getTableau() {
		return tableau;
	}

	public void setTableau(Hashtable<String, ScenarioType> tableau) {
		this.tableau = tableau;
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
