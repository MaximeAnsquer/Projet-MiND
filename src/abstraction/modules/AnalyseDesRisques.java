package abstraction.modules;

import abstraction.Analyse;

public class AnalyseDesRisques extends Module{
	
	/*Note: ce Module est aussi appelé dans le cdc Mapping des risques!
	 * 
	 */
	private Analyse analyse;
	
	private EvenementsRedoutes evenements;
	private ScenariosMenacesTypes scenarios;
	private MappingDesBiens mapping;
	
	
	
	public AnalyseDesRisques(){
		super("Analyse des Risques");
		this.predecesseurs.add(ScenariosMenacesTypes.getInstance());
		this.predecesseurs.add(EvenementsRedoutes.getInstance());
		this.predecesseurs.add(MappingDesBiens.getInstance());
		this.mapping=MappingDesBiens.getInstance();
		this.scenarios=ScenariosMenacesTypes.getInstance();
		this.evenements=EvenementsRedoutes.getInstance();
		this.cree=false;
		this.coherent=false;
		this.disponible=false;
		
	}

	public Analyse getAnalyse(){
		return analyse;

	}
	
	/*retourne les scénarios retenus pour la table*/

    public Hashtable<String,Scenarios> getScenariosConcrets(){
    	return scenarios.getBiens().getBiensCorrespondants().getScenarios();
    }
    
    
    
    
   


}





