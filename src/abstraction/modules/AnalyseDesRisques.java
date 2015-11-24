package abstraction.modules;

import java.util.ArrayList;

import abstraction.Analyse;
import abstraction.Etude;
import abstraction.autres.Evenement;
import abstraction.autres.Risque;

public class AnalyseDesRisques extends Module{
	
	/*Une analyse de risques est une liste de risques, cette classe fait appel � ses ant�c�dents comme va d'instance
	 * et �galement � l'�tude en cours.
	 */
	
	
	private ArrayList<Risque> risques;
	
	private Etude etude;
	private EvenementsRedoutes evenements;
	private MappingDesBiens mapping;
	private ScenariosDeMenacesTypes scenarios;
	
	
	
	
	public AnalyseDesRisques(Etude etude){
		super("Analyse des risques");
		this.etude=etude;
		this.evenements=(EvenementsRedoutes) this.etude.getModule("Ev�nements redout�s");
		this.mapping=(MappingDesBiens)this.etude.getModule("Mapping des biens");
		this.scenarios=(ScenariosDeMenacesTypes)this.etude.getModule("Scenarios de menaces types");
		ArrayList<Risque> liste=new ArrayList<Risque>();
		
		int a=this.scenarios.getScenariosMenacesTypes().size();
		
		/*l� on construit chacuns des risques de l'arraylist, en allant piocher dans les modules ant�c�dents pour
		 * faire correspondre chaque sc�nario de menace(ScenariosDeMenacesTypes) avec l'�venement correspondant(EvenementsRedoutes)
		 * Les autres arguments du constructeur Risque d�coulent de ce couplage. C'est assez lourd dans l'�criture,
		 * y a moyen que je retouche �a plus tard pour plus de visibilit�.
		 * 
		 */
		
		for (int i=0;i<a;i++){
			
				liste.set(i,new Risque("",this.scenarios.getScenariosMenacesTypes().get(i).getEvenement().getNomEvenement(),this.scenarios.getScenariosMenacesTypes().get(i).getEvenement().getNiveauGravite(),this.scenarios.getScenariosMenacesTypes().get(i).getEvenement().getBienEssentiel().getBienSupportCorrespondant(),this.scenarios.getScenariosMenacesTypes().get(i),this.scenarios.getScenariosMenacesTypes().get(i).getNiveauVraisemblance()));
				
			}
		
		
		
	/*Getter utile pour la construction de la matrice qui vient apr�s*/	
		
	}
	
	public ArrayList<Risque> getAnalyseDesRisques(){
		return this.risques;
	}

}
