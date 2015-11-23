package abstraction.modules;

import java.util.Hashtable;

import abstraction.autres.ScenarioGenerique;

public class ScenariosDeMenacesGeneriques extends Module {
	
	// Represente la bdc
	private static Hashtable<String, ScenarioGenerique> bdcScenariosMenacesGeneriques;

	// Variable d'instance
	Hashtable<String, ScenarioGenerique> tableau;
	
	public ScenariosDeMenacesGeneriques(){
		super("Scenario de Menaces Generiques");
		this.tableau = new Hashtable<String, ScenarioGenerique>();
		this.predecesseurs.add(this.getEtude().getModule("Typologie des biens supports"));
		this.predecesseurs.add(this.getEtude().getModule("Sources de menaces"));
		this.successeurs.add(this.getEtude().getModule("Biens Supports"));
		this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesGeneriques"));
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
	}
	
	
}
