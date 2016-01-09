package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.ScenarioGenerique;
import abstraction.autres.ScenarioType;

public class ScenariosDeMenacesTypes extends Module {

	// Represente la bdc
	private static ArrayList<ScenarioType> bdcScenariosMenacesTypes;

	// Variable d'instance
	ArrayList<ScenarioType> tableau;
	private ScenarioType scenarioTypeCourant ;
	// On liste les critères de sécurité retenus dans le module Scénarios de menaces génériques
	private ArrayList<String> nomColonneSup ;

	public ScenariosDeMenacesTypes(Etude etude) {
		super("ScenariosDeMenacesTypes");
		this.etude=etude;
		
		this.tableau = new ArrayList<ScenarioType>();
		this.nomColonneSup=new ArrayList<String>();
		
		this.predecesseurs.add(this.getEtude().getModule("ScenariosDeMenacesGeneriques"));
		this.predecesseurs.add(this.getEtude().getModule("BiensSupports"));
		this.predecesseurs.add(this.getEtude().getModule("Metriques"));
		this.predecesseurs.add(this.getEtude().getModule("SourcesDeMenaces"));
		
		// this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
		this.scenarioTypeCourant=new ScenarioType();
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		this.importerBDC();
		this.tableau = ScenariosDeMenacesTypes.getBDC();
	}
	
	public int getSize(){
		return this.tableau.size();
	}
	
	public ScenarioType getScenarioTypeCourant() {
		return scenarioTypeCourant;
	}

	public void setScenarioTypeCourant(ScenarioType scenarioTypeCourant) {
		this.scenarioTypeCourant = scenarioTypeCourant;
		this.setChanged();         // PAC
		this.notifyObservers();    // PAC
	}

	public ArrayList<String> getNomColonneSup() {
		return nomColonneSup;
	}

	public void setNomColonneSup(ArrayList<String> nomColonneSup) {
		this.nomColonneSup = nomColonneSup;
	}

	public ArrayList<ScenarioType> getTableau() {
		return tableau;
	}

	public void setTableau(ArrayList<ScenarioType> tableau) {
		this.tableau = tableau;
	}
	
	public ScenarioType getScenarioType (int i){
		return this.tableau.get(i);
	}
	
	public void addScenarioType(ScenarioType scenario,int indiceInsertion){
		this.tableau.add(indiceInsertion,scenario);
	}

	public static ArrayList<ScenarioType> getBDC() {
		return bdcScenariosMenacesTypes;
	}

	private void importerBDC() {
		bdcScenariosMenacesTypes= new ArrayList<ScenarioType>();
		
		BiensSupports biensSupports = (BiensSupports) this.etude.getModule("BiensSupports");
		
		ScenariosDeMenacesGeneriques moduleScenarioGene = (ScenariosDeMenacesGeneriques) this.etude
				.getModule("ScenariosDeMenacesGeneriques");
		
		SourcesDeMenaces SourcesDeMenaces = (SourcesDeMenaces) this.etude.getModule("SourcesDeMenaces");
		
		
		for (ScenarioGenerique sGene : moduleScenarioGene.getTableau()){
			ScenarioType scenario = new ScenarioType(sGene.getTypeBienSupport(), sGene.getId(), sGene.getIntitule(), sGene.getCriteresSup(), SourcesDeMenaces.getSourcesDeMenacesRetenues(), null, true);
			for (Bien b : biensSupports.getBiensRetenus()){
				if (sGene.getTypeBienSupport().contains(b.getType())){
					scenario.setBienSupport(b);
					bdcScenariosMenacesTypes.add(scenario);
				}
			}
		}
	}
	
	public boolean estCoherent() {
		boolean resultat = true ;
		return resultat;
	}
	
	public String toString(){
		return "Scénarios de menaces typés";
	}
}
