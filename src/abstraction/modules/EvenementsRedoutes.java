package abstraction.modules;

import java.util.Hashtable;

import abstraction.autres.Biens;
import abstraction.autres.Critere;

import abstraction.bdc.BDCMetriques;

public class EvenementsRedoutes extends Module {

	private static EvenementsRedoutes evenementsredoutes=new EvenementsRedoutes();
	
	private Hashtable<String, Biens> lesBiens;
	private Hashtable<String,Critere> lesCriteres;;
	
	
	private EvenementsRedoutes(){
		super("Evenements Redoutes");
		this.predecesseurs.add(Metriques.getInstance());
		this.predecesseurs.add(CriteresDeSecurite.getInstance());
		this.successeurs.add(AnalyseDesRisques.getInstance());
		this.lesCriteres=CriteresDeSecurite.getInstance().getCriteresRetenus();
		this.lesBiens=BiensEssentiels.getInstance().getBiensRetenus();
	}
	
	public static EvenementsRedoutes getInstance(){
		return evenementsredoutes;
	}
	
	public void setCriteres(Hashtable<String,Critere> lesCriteres){
		this.lesCriteres=lesCriteres;
		
		
	}
	
	public void setBiens(Hashtable<String,Biens> lesBiens){
		this.lesBiens=lesBiens;
	}

}
