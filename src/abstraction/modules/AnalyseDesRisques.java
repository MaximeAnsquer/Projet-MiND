package abstraction.modules;



import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.autres.Risque;
import abstraction.autres.ScenarioType;
import abstraction.autres.SourceDeMenace;

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
		
		this.predecesseurs.add(this.etude.getModule("ScenariosDeMenacesTypes"));
		this.predecesseurs.add(this.evenements);
		this.predecesseurs.add(this.mapping);
		
		this.successeurs.add(this.etude.getModule("MatriceDesRisques"));
		
		
		this.cree=false;
		this.checkDisponible();
		
		
		ArrayList<Risque> liste=new ArrayList<Risque>();
		
		
		
		if(this.scenarios!=null&&this.scenarios.estCoherent()==true&&this.evenements.estCoherent()==true&& this.mapping.estCoherent()==true){
		
		int a=this.scenarios.getTableau().size();/*TODO a modifier pour ne prendre en compte que les scenarii retenus*/
		int b=((CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite")).getCriteresRetenus().size();
		
		/*là on construit chacuns des risques de l'arraylist, en allant piocher dans les modules antécédents pour
		 * faire correspondre chaque scénario de menace(ScenariosDeMenacesTypes) avec l'évenement correspondant(EvenementsRedoutes)
		 * Les autres arguments du constructeur Risque découlent de ce couplage. C'est assez lourd dans l'écriture,
		 * y a moyen que je retouche ça plus tard pour plus de visibilité.
		 * 
		 */
		
		/*TODO a modifier pour ne prendre en compte que les scenarii retenus*/
		
		ScenarioType[] scenarios=this.scenarios.getTableau().values().toArray(new ScenarioType[this.scenarios.getTableau().size()]);
		
		
		/*Pour chaque scénario*/
		for (int i=0;i<a;i++){
			
			/*si le scenario est retenu*/
			
			if(scenarios[i].isRetenuScenario()){
				
			
			/*Pour chaque critere*/
			for(int k=0;k<b;k++){
				
				/*si le critere est retenu*/
			
				
				String critere = this.scenarios.getNomColonneSup().get(k);
				boolean resultat=scenarios[i].getCriteresSup().get(critere);
				
			    if (resultat==true){
			    	/*On recupere le bien essentiel correspondant au bien support du scenario considéré*/
			    Bien biensupport=scenarios[i].getBienSupport();	
			    
			    
			    ArrayList<Bien> biensessentiels;
				try {
					biensessentiels = this.mapping.getBiensEssentielsCorrespondant(biensupport);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					biensessentiels=null;
					e.printStackTrace();
				}
			    /*On récupère le critère correspondant*/
			    Critere[] listecriteres =this.criteres.getCriteresRetenus().values().toArray(new Critere[this.criteres.getCriteresRetenus().size()]);
			    Critere criterecourant=listecriteres[k];
			   
			  
			    if(biensessentiels!=null){
			    for(int j=0;j<biensessentiels.size();j++){
			    
			   Evenement evenement=this.evenements.getEvenementCorrespondant(criterecourant.getIntitule(),biensessentiels.get(j).getIntitule());
			    
				liste.add(new Risque("",evenement,evenement.getNiveauGravite(),biensupport,scenarios[i],scenarios[i].getVraisemblanceReelle()));
				
			}}
			}}
			
		}
		}
		this.risques=liste;
		}
		else{
			
			LinkedList<String> vide=new LinkedList<String>();
			vide.add("");
			Evenement ev=new Evenement(this.etude,"",vide,vide,"","");
			Bien biensupport= new Bien("","","",vide);
			ScenarioType[] scenarios=new ScenarioType[1];
			
			Hashtable<String,Boolean> hash1=new Hashtable<String,Boolean>();
			hash1.put("", true);
			
			SourceDeMenace src=new SourceDeMenace("","","");
			src.setRetenu(true);
			
			Hashtable<String,SourceDeMenace> hash2=new Hashtable<String,SourceDeMenace>();
			hash2.put("",src);
			
			
			ScenarioType sc=new ScenarioType("","", "",hash1,hash2, biensupport,"", 1, 1, true) ;
				
			liste.add(new Risque("",ev,1,biensupport,sc,1));
			
			this.risques=liste;
		}
	/*Getter utile pour la construction de la matrice qui vient après*/	
	
	}
	
	public ArrayList<Risque> getAnalyseDesRisques(){
		return this.risques;
	}
	
	public String toString(){
		return "Analyse des risques";
	}
	
	
	
	
	
	/*Méthode qui permet de vérifier si le bouton associé au module doit être grisé ou non (cf workflow)*/
	
	public void checkDisponible(){
		if(this.scenarios!=null&&this.evenements.estCoherent()==true&&this.mapping.estCoherent()==true&&this.scenarios.estCoherent()==true){
			this.disponible=true;
		}
		else{
			this.disponible=false;
		}
	}
	
	
	public boolean estCoherent(){
		boolean resultat=true;
		this.problemesDeCoherence=new ArrayList<String>();
		for (int i=0;i<this.getAnalyseDesRisques().size();i++){
			if(this.getAnalyseDesRisques().get(i).estComplet()!=true){
				String probleme = "Un risque est incomplet";
				this.problemesDeCoherence.add(probleme);
				
				resultat=false;
			}
		}
		return resultat;
	}

}
