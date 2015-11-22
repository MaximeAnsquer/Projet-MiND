package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.Analyse;
import abstraction.autres.Biens;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.bdc.BDCMetriques;

public class EvenementsRedoutes extends Module {
	
	/*Je mets dans cette classe pour variable d'instance tout d'abord une hashtable d'�venements
	 */
	private ArrayList<Evenement> evenementsredoutes;
	
	
	/*Puis les ant�c�dents du module car on en a besoin pour cr�er le pr�sent module
	 */
	private Analyse analyse;
	private BiensEssentiels lesBiensEssentiels;
	private Metriques lesMetriques;
	
	
	private EvenementsRedoutes(Analyse analyse){
		super("Evenements Redoutes");
		this.predecesseurs.add(this.analyse.getModule("BiensEssentiels"));
		this.predecesseurs.add(this.analyse.getModule("Metriques"));
		this.successeurs.add(this.analyse.getModule("AnalyseDesRisques"));
		this.lesMetriques=(Metriques) this.analyse.getModule("Metriques");
		this.lesBiensEssentiels=(BiensEssentiels) this.analyse.getModule("BiensEssentiels");
		this.cree=false;
		this.disponible=false;
		this.coherent=false;
		this.analyse=analyse;
		
		/*A ce stade-ci du constructeur, on remplit la hashtable en constituant des combinaisons entre Biens Essentiels
		 * et Crit�res d�finis dans les ant�c�dents du  module.
		 */
		
	    ArrayList<Evenement> liste=new ArrayList<Evenement>();
		int a=((BiensEssentiels)this.analyse.getModule("BiensEssentiels")).getBiensEssentiels().size();
		int b=((CriteresDeSecurite)this.analyse.getModule("Crit�res")).getLesCriteres().size();
		Module critere=this.analyse.getModule("Critere");
		Module bienessentiel=this.analyse.getModule("BiensEssentiels");
		
		
		for (int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				liste.set(i*b+j,new Evenement("",this.lesBiensEssentiels.get))
				hashtable[i*b+j].setCritere(critere.getCriteres().get());j
			}
		}
		
		this.evenementsredoutes=hashtable;
	}
	
	public Analyse getAnalyse(){
		return analyse;
	}
	
	public Module getLesBiensEssentiels(){
		return this.lesBiensEssentiels;
	}
	
	public Module getLesMetriques(){
		return this.lesMetriques;
	}
	
	public Hashtable<String,Evenement> getEvenementsRedoutes(){
		return this.evenementsredoutes;
	}
	
	public void setMetriques(Metriques lesMetriques){
		this.lesMetriques=lesMetriques;
		
		
	}
	
	public void setBiens(BiensEssentiels lesBiensEssentiels){
		this.lesBiensEssentiels=lesBiensEssentiels;
	}
	
	

}
