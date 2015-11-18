package abstraction.modules;

import abstraction.bdc.BDCMetriques;

public class EvenementsRedoutes extends Module {

	private static EvenementsRedoutes evenementsredoutes=new EvenementsRedoutes();
	
	
	
	private EvenementsRedoutes(){
		super("Evenements Redoutes");
		this.predecesseurs.add(Metriques.getInstance());
		this.predecesseurs.add(CriteresDeSecurite.getInstance());
		
	}
	
	public static EvenementsRedoutes getEvenements(){
		return evenementsredoutes;
	}
	

}
