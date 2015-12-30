package abstraction.modules;



import java.util.ArrayList;


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
	private CriteresDeSecurite criteres;
	
	
	
	
	public AnalyseDesRisques(Etude etude){
		super("AnalyseDesRisques");
		this.etude=etude;
		this.criteres=(CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite");
		this.evenements=(EvenementsRedoutes) this.etude.getModule("EvenementsRedoutes");
		this.mapping=(MappingDesBiens)this.etude.getModule("MappingDesBiens");
		this.scenarios=(ScenariosDeMenacesTypes)this.etude.getModule("ScenarioDeMenacesTypes");
		
		this.cree=false;
		this.checkDisponible();
		
		
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
			    if (/*scenarios[i].isRetenuCritere(k)*/true==true){
			    	/*On recupere le bien essentiel correspondant au bien support du scenario considéré*/
			    Bien biensupport=scenarios[i].getBienSupport();	
			    System.out.println(scenarios[i].getIntituleConcret());
			    
			    ArrayList<Bien> biensessentiels;
				try {
					biensessentiels = this.mapping.getBiensEssentielsCorrespondant(biensupport);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					biensessentiels=null;
					e.printStackTrace();
				}
			    /*On récupère le critère correspondant*/
			    Critere[] listecriteres =this.criteres.getLesCriteres().values().toArray(new Critere[this.criteres.getLesCriteres().size()]);
			    Critere criterecourant=listecriteres[k];
			    System.out.println(criterecourant.getIntitule());
			  
			    if(biensessentiels!=null){
			    for(int j=0;j<biensessentiels.size();j++){
			    
			   Evenement evenement=this.evenements.getEvenementCorrespondant(criterecourant.getIntitule(),biensessentiels.get(j).getIntitule());
			    
				liste.add(new Risque("",evenement,evenement.getNiveauGravite(),biensupport,scenarios[i],scenarios[i].getVraisemblanceReelle()));
				
			}}
			}}
			
		}
		
		this.risques=liste;
	/*Getter utile pour la construction de la matrice qui vient après*/	
	
	}
	
	public ArrayList<Risque> getAnalyseDesRisques(){
		return this.risques;
	}
	
	public String toString(){
		return "Analyse des risques";
	}
	
	
	public boolean estCoherent(){
		if(this.etude.getModule("CriteresDeSecurite").estCoherent()==true&&this.etude.getModule("MappingDesBiens").estCoherent()==true&&this.scenarios.estCoherent()==true){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void checkDisponible(){
		if(this.etude.getModule("CriteresDeSecurite").estCoherent()==true&&this.mapping.estCoherent()==true&&this.scenarios.estCoherent()==true){
			this.disponible=true;
		}
		else{
			this.disponible=false;
		}
	}

}
