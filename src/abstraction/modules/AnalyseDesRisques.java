package abstraction.modules;

public class AnalyseDesRisques extends Module{
	
	
	private static AnalyseDesRisques Analyse=new AnalyseDesRisques();
	
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
		
	}

	public static AnalyseDesRisques getInstance(){
		return Analyse;

	
	
	}
	
	/*retourne les scénarios retenus pour la table*/

    public Hashtable<String,Scenarios> getScenariosRetenus(){
    	return evenements.getInstance().getBiens().getBiensCorrespondants().getScenarios();
    }
    
    
    public Hashtable<String, Biens> getBiensCorrespondants(){
    	return mapping.getInstance().get
    }



}





