package abstraction.modules;

public class AnalyseDesRisques extends Module{
	
	
	private static AnalyseDesRisques Analyse=new AnalyseDesRisques();
	
	
	
	
	
	
	
	public AnalyseDesRisques(){
		super("Analyse des Risques");
		this.predecesseurs.add(ScenariosMenacesTypes.getInstance());
		this.predecesseurs.add(EvenementsRedoutes.getInstance());
		this.predecesseurs.add(MappingDesBiens.getInstance());
	}

	
}
