package abstraction.modules;

import java.util.ArrayList;

import abstraction.Analyse;
import abstraction.Etude;
import abstraction.autres.Evenement;
import abstraction.autres.Risque;

public class AnalyseDesRisques extends Module{
	
	
	
	private ArrayList<Risque> risques;
	
	private Etude etude;
	private EvenementsRedoutes evenements;
	private MappingDesBiens mapping;
	private ScenariosDeMenacesTypes scenarios;
	
	
	
	
	public AnalyseDesRisques(Etude etude){
		super("Analyse des risques");
		this.etude=etude;
		this.evenements=(EvenementsRedoutes) this.etude.getModule("Evènements redoutés");
		this.mapping=(MappingDesBiens)this.etude.getModule("Mapping des biens");
		this.scenarios=(ScenariosDeMenacesTypes)this.etude.getModule("Scenarios de menaces types");
		ArrayList<Risque> liste=new ArrayList<Risque>();
		
		int a=this.scenarios.getScenariosMenacesTypes().size();
		
		for (int i=0;i<a;i++){
			
				liste.set(i,new Risque("",this.scenarios.getScenariosMenacesTypes().get(i).getEvenement().getNomEvenement(),this.scenarios.getScenariosMenacesTypes().get(i).getEvenement().getNiveauGravite(),this.scenarios.getScenariosMenacesTypes().get(i).getEvenement().getBienEssentiel().getBienSupportCorrespondant(),this.scenarios.getScenariosMenacesTypes().get(i),this.scenarios.getScenariosMenacesTypes().get(i).getNiveauVraisemblance()));
				
			}
		
		
		
		
		
	}
	
	public ArrayList<Risque> getAnalyseDesRisques(){
		return this.risques;
	}

}
