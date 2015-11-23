package abstraction.modules;

import java.util.ArrayList;

import abstraction.Analyse;
import abstraction.autres.Evenement;
import abstraction.autres.Risque;

public class AnalyseDesRisques extends Module{
	
	
	
	private ArrayList<Risque> risques;
	
	private Analyse analyse;
	private EvenementsRedoutes evenements;
	private MappingDesBiens mapping;
	private ScenariosDeMenacesTypes scenarios;
	
	
	
	
	public AnalyseDesRisques(){
		super("Analyse des risques");
		this.evenements=(EvenementsRedoutes) this.analyse.getModule("Evènements redoutés");
		this.mapping=(MappingDesBiens)this.analyse.getModule("Mapping des biens");
		this.scenarios=(ScenariosDeMenacesTypes)this.analyse.getModule("Scenarios de menaces types");
		ArrayList<Risque> liste=new ArrayList<Risque>();
		
		
		
		for (int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				liste.set(i*b+j,new Risque("",this.scenarios.getScenarios().get(i),this.analyse.getModule("Métriques").getMetrique("Gravité"),,exigence,gravite));
				
			}
		}
		
		this.intitule=intitule;
		this.evenementredoute=evenementredoute;
		this.gravite=gravite;
		this.biensupport=biensupport;
		this.scenarioconcret=scenarioconcret;
		this.vraisemblance=vraisemblance;
		
		
	}

}
