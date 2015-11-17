package modules;

import bdc.BDCMetriques;

public class EvenementsRedoutes extends Module {

	private static EvenementsRedoutes evenementsredoutes=new EvenementsRedoutes();
	
	
	
	private EvenementsRedoutes(){
		
		this.predecesseurs.add(Metriques.getInstance());
		this.predecesseurs.add(CriteresDeSecurite.getInstance());
		
	}
	
	
	

}
