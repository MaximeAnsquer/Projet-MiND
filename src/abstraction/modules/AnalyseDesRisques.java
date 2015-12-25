package abstraction.modules;

import java.util.ArrayList;

import abstraction.Analyse;
import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.autres.Risque;
import abstraction.autres.ScenarioType;

public class AnalyseDesRisques extends Module{
	
	/*Une analyse de risques est une liste de risques, cette classe fait appel à ses antécédents comme va d'instance
	 * et également à l'étude en cours.
	 */
	
	
	private ArrayList<Risque> risques;
	
	private Etude etude;
	private EvenementsRedoutes evenements;
	private MappingDesBiens mapping;
	private ScenariosDeMenacesTypes scenarios;
	
	
	
	
	public AnalyseDesRisques(Etude etude){
		super("AnalyseDesRisques");
		this.etude=etude;
		this.evenements=(EvenementsRedoutes) this.etude.getModule("EvenementsRedoutes");
		this.mapping=(MappingDesBiens)this.etude.getModule("MappingDesBiens");
		this.scenarios=(ScenariosDeMenacesTypes)this.etude.getModule("ScenariosDeMenacesTypes");
		ArrayList<Risque> liste=new ArrayList<Risque>();
		
		int a=this.scenarios.getTableau().size();
		int b=((CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite")).getLesCriteres().size();
		
		/*là on construit chacuns des risques de l'arraylist, en allant piocher dans les modules antécédents pour
		 * faire correspondre chaque scénario de menace(ScenariosDeMenacesTypes) avec l'évenement correspondant(EvenementsRedoutes)
		 * Les autres arguments du constructeur Risque découlent de ce couplage. C'est assez lourd dans l'écriture,
		 * y a moyen que je retouche ça plus tard pour plus de visibilité.
		 * 
		 */
		ScenarioType[] scenarios=this.scenarios.getTableau().values().toArray(new ScenarioType[this.scenarios.getTableau().size()]);
		
		
		/*Pour chaque scénario*/
		for (int i=0;i<a;i++){
			/*Pour chaque critere*/
			for(int k=0;k<b;k++){
			    if (scenarios[i].isRetenuCritere(i)==true){
			    	/*On recupere le bien essentiel correspondant au bien support du scenario considéré*/
			    Bien biencourant=scenarios[i].getBienSupport().getBienEssentielCorrespondant();		
			    /*On récupère le critère correspondant*/
			    Critere[] listecriteres =scenarios[0].getCriteres().values().toArray(new Critere[scenarios[0].getCriteres().size()]);
			    Critere criterecourant=listecriteres[k];
			    
			   Evenement evenement=this.evenements.getEvenementCorrespondant(criterecourant.getIntitule(),biencourant.getIntitule());
			    
				liste.add(new Risque("",evenement,evenement.getNiveauGravite(),scenarios[i].getBienSupport(),scenarios[i],scenarios[i].getVraisemblanceReelle()));
				
			}
			}
		}
		
		
	/*Getter utile pour la construction de la matrice qui vient après*/	
		
	}
	
	public ArrayList<Risque> getAnalyseDesRisques(){
		return this.risques;
	}
	
	public String toString(){
		return "Analyse des risques";
	}

}
