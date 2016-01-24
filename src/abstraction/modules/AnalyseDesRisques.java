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
	
	/*Une analyse de risques est une liste de risques, cette classe fait appel � ses ant�c�dents comme va d'instance
	 * et �galement � l'�tude en cours.
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
		this.scenarios=(ScenariosDeMenacesTypes)this.etude.getModule("ScenariosDeMenacesTypes");
		
		this.predecesseurs.add(this.scenarios);
		this.predecesseurs.add(this.evenements);
		this.predecesseurs.add(this.mapping);
		
		this.successeurs.add(this.etude.getModule("MatriceDesRisques"));
		
		
		this.cree=false;
		
		this.coherent=false;
		
		
		//this.checkDisponible();
		
		
		ArrayList<Risque> liste=new ArrayList<Risque>();
		
		//Si les ant�c�dents du module sont coh�rents
		
		if(this.scenarios.estCoherent()==true&&this.evenements.estRempli()==true&& this.mapping.estCoherent()==true){
		
		//On construit un tableau en utilisant ces ant�c�dents	
			
		int a=this.scenarios.getTableau().size();
		
		
		int b=((ScenariosDeMenacesGeneriques)this.etude.getModule("ScenariosDeMenacesGeneriques")).getNomColonneSup().size();
		//System.out.println(b);
		//for(int l=0;l<b;l++){
		//	System.out.println(this.scenarios.getNomColonneSup().get(l));
		//}
		
		
		
		/*l� on construit chacuns des risques de l'arraylist, en allant piocher dans les modules ant�c�dents pour
		 * faire correspondre chaque sc�nario de menace(ScenariosDeMenacesTypes) avec l'�venement correspondant(EvenementsRedoutes)
		 * Les autres arguments du constructeur Risque d�coulent de ce couplage. C'est assez lourd dans l'�criture,
		 * y a moyen que je retouche �a plus tard pour plus de visibilit�.
		 * 
		 */
		
		/*TODO a modifier pour ne prendre en compte que les scenarii retenus*/
		
		ScenarioType[] scenarios=this.scenarios.getTableau().toArray(new ScenarioType[this.scenarios.getTableau().size()]);
		
		
		/*Pour chaque sc�nario*/
		for (int i=0;i<a;i++){
			
			/*si le scenario est retenu*/
			
			if(scenarios[i].isRetenuScenario()){
				
				
				
			
			/*Pour chaque critere*/
			for(int k=0;k<b;k++){
				
				/*si le critere est retenu*/
				
				
			
				
				String critere = this.scenarios.getNomColonneSup().get(k);
				
				
				
				boolean resultat=scenarios[i].getCriteresSup().get(critere);
				
				boolean resultatbis=this.criteres.getIntitulesCriteresRetenus().contains(critere);
				
				
			    if (resultatbis==true&&resultat==true){
			    	/*On recupere le bien essentiel correspondant au bien support du scenario consid�r�*/
			    Bien biensupport=scenarios[i].getBienSupport();	
			    
			    
			    
			    
			    ArrayList<Bien> biensessentiels;
				try {
					biensessentiels = this.mapping.getBiensEssentielsCorrespondant(biensupport);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					biensessentiels=null;
					e.printStackTrace();
				}
			    /*On r�cup�re le crit�re correspondant*/
			   // Critere[] listecriteres =this.criteres.getCriteresRetenus().values().toArray(new Critere[this.criteres.getCriteresRetenus().size()]);
			   // Critere criterecourant=listecriteres[k];
				
				
				//System.out.println(critere);
			    
			    
			   
			  
			    if(biensessentiels!=null){
			    for(int j=0;j<biensessentiels.size();j++){
			    
			   Evenement evenement=this.evenements.getEvenementCorrespondant(critere,biensessentiels.get(j).getIntitule());
			   
			   if(evenement!=null){
			   
				liste.add(new Risque("",evenement,evenement.getNiveauGravite(),biensupport,scenarios[i],scenarios[i].getVraisemblanceReelle()));
				
			}}}
			}}
			
		}
		}
		this.risques=liste;
		}
		else{
			
			//Sinon on construit un tableau constitu� d'une ligne vide, pour que le programme ne plante pas.
			
			LinkedList<String> vide=new LinkedList<String>();
			vide.add("");
			Evenement ev=new Evenement(this.etude,"",vide,vide,"","");
			Bien biensupport= new Bien("","","",vide);
			ScenarioType[] scenarios=new ScenarioType[1];
			
			Hashtable<String,Boolean> hash1=new Hashtable<String,Boolean>();
			hash1.put("", true);
			
			SourceDeMenace src=new SourceDeMenace("","","");
			src.setRetenu(true);
			
			ArrayList<SourceDeMenace> sources=new ArrayList<SourceDeMenace>();
			sources.add(src);
			
			
			ScenarioType sc=new ScenarioType("","", "",hash1,sources, biensupport,"", 1, 1, true) ;
				
			liste.add(new Risque("",ev,1,biensupport,sc,1));
			
			this.risques=liste;
		}
	/*Getter utile pour la construction de la matrice qui vient apr�s*/	
	
	}
	
	public ArrayList<Risque> getAnalyseDesRisques(){
		return this.risques;
	}
	
	public String toString(){
		return "Analyse des risques";
	}
	
	
	public void setAnalyseDesRisques(ArrayList<Risque> risques){
		this.risques=risques;
	}
	
	
	/*M�thode qui permet de v�rifier si le bouton associ� au module doit �tre gris� ou non (cf workflow)*/
	
	/*public void checkDisponible(){
		
		if(this.scenarios!=null&&this.evenements.estCoherent()==true&&this.mapping.estCoherent()==true&&this.scenarios.estCoherent()==true){
			this.disponible=true;
		}
		else{
			this.disponible=false;
		}
	}*/
	
	
	public boolean estCoherent(){
		
		
 AnalyseDesRisques nouvellean=new AnalyseDesRisques(this.getEtude());
		 
		 nouvellean.setCree(true);
			
			/*La boucle va servir � mettre � conserver les valeurs modifi�es de l'ancien tableau et de les mettre
			 * dans le nouveau
			 */
			
			int a=nouvellean.getAnalyseDesRisques().size();
			int b=this.getAnalyseDesRisques().size();
			
			
			
			
			for (int i=0;i<a;i++){
				for(int j=0;j<b;j++){
					String evnouveau=nouvellean.getAnalyseDesRisques().get(i).getEvenementRedoute().GetNomEvenement();
					String evancien=this.getAnalyseDesRisques().get(j).getEvenementRedoute().GetNomEvenement();
					
					//System.out.println(nouvellean.getAnalyseDesRisques().get(i).getIntitule());
					//System.out.println(analyse.getAnalyseDesRisques().get(j).getIntitule());
					int nouveauvrai=nouvellean.getAnalyseDesRisques().get(i).getNiveauVraisemblance();
					int ancienvrai=this.getAnalyseDesRisques().get(j).getNiveauVraisemblance();
					
					String biennouveau=nouvellean.getAnalyseDesRisques().get(i).getBienSupport().getIntitule();
					String bienancien=this.getAnalyseDesRisques().get(j).getBienSupport().getIntitule();
					
					String scnouveau=	nouvellean.getAnalyseDesRisques().get(i).getScenarioConcret().getIntitule();
					String scancien= this.getAnalyseDesRisques().get(j).getScenarioConcret().getIntitule();
				
					
					if(evnouveau.equals(evancien)&&biennouveau.equals(bienancien)&&scnouveau.equals(scancien)){
						
						nouvellean.getAnalyseDesRisques().get(i).setIntitule(this.getAnalyseDesRisques().get(j).getIntitule());;
						nouvellean.getAnalyseDesRisques().get(i).setRetenu(this.getAnalyseDesRisques().get(j).getRetenu());
						
						
						
						
					}
					
				}
			}
			
			


this.setAnalyseDesRisques(nouvellean.getAnalyseDesRisques());
		
		
		boolean resultat=true;
		this.problemesDeCoherence=new ArrayList<String>();
		for (int i=0;i<this.getAnalyseDesRisques().size();i++){
			if(this.getAnalyseDesRisques().get(i).estComplet()!=true){
				String probleme = "Un risque est incomplet";
				this.problemesDeCoherence.add(probleme);
				
				resultat=false;
			}
			
for(int k=0;k<this.getAnalyseDesRisques().size();k++){
				
				if(k!=i){
					
					if(this.getAnalyseDesRisques().get(i).getIntitule().equals(this.getAnalyseDesRisques().get(k).getIntitule())){
						resultat=false;
						
						String probleme = "Un ou plusieurs risques ont le même nom";
						this.problemesDeCoherence.add(probleme);
						
					}
				}
			}
			
		}
		return resultat;
	}

}
