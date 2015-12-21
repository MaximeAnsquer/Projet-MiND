package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.autres.Metrique;


public class EvenementsRedoutes extends Module {
	
	/*Je mets dans cette classe pour variable d'instance tout d'abord une arraylist d'évenements
	 */
	private ArrayList<Evenement> evenementsredoutes;
	
	
	/*Puis les antécédents du module ainsi que l'étude car on en a besoin pour créer le présent module
	 */
	private Etude etude;
	private BiensEssentiels lesBiensEssentiels;
	private Metriques lesMetriques;
	
	
	public EvenementsRedoutes(Etude etude){
		
		super("EvenementsRedoutes");
		this.etude=etude;
		this.predecesseurs.add(this.etude.getModule("BiensEssentiels"));
		this.predecesseurs.add(this.etude.getModule("Metriques"));
		this.successeurs.add(this.etude.getModule("AnalyseDesRisques"));
		this.lesMetriques=(Metriques)this. etude.getModule("Metriques");
		this.lesBiensEssentiels=(BiensEssentiels)this.etude.getModule("BiensEssentiels");
		this.cree=true;
		this.disponible=true;
		this.coherent=true;
		
		
		/*A ce stade-ci du constructeur, on remplit l'arraylist en constituant des combinaisons entre Biens Essentiels
		 * et Critères définis dans les antécédents du  module.
		 */
		/*On crée une arraylist qui contiendra les lignes du tableau*/
		
	    ArrayList<Evenement> liste=new ArrayList<Evenement>();
	    
		int a=((BiensEssentiels)this.etude.getModule("BiensEssentiels")).getLesBiens().size();
		int b=((CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite")).getLesCriteres().size();
		
		
		CriteresDeSecurite critere=(CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite");
		ArrayList<Critere> tableaucriteres=new ArrayList<Critere>(critere.getLesCriteres().values());
		BiensEssentiels bienessentiel=this.lesBiensEssentiels;
		Metrique exigence=this.lesMetriques.getMetrique("Exigence");
		Metrique gravite=this.lesMetriques.getMetrique("Gravite");
		ArrayList<Bien> tableaubiens=new ArrayList<Bien>(this.lesBiensEssentiels.getLesBiens().values());
		
		
		for (int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				System.out.println(tableaubiens.get(i).getIntitule());
				System.out.println(tableaucriteres.get(j));
				liste.add(i*b+j,new Evenement(this.etude,"",tableaubiens.get(i).getNomColonneSup(),tableaubiens.get(i).getContenuColonneSup(),tableaubiens.get(i).getIntitule(),tableaucriteres.get(j).getIntitule()));
				
			}
		}
		
		this.evenementsredoutes=liste;
	}
	
	public Etude getEtude(){
		return etude;
	}
	
	public Module getLesBiensEssentiels(){
		return this.lesBiensEssentiels;
	}
	
	public Module getLesMetriques(){
		return this.lesMetriques;
	}
	
	public ArrayList<Evenement> getEvenementsRedoutes(){
		return this.evenementsredoutes;
	}
	
	public void setMetriques(Metriques lesMetriques){
		this.lesMetriques=lesMetriques;
		
		
	}
	
	public void setBiens(BiensEssentiels lesBiensEssentiels){
		this.lesBiensEssentiels=lesBiensEssentiels;
	}
	
	public Evenement getEvenementCorrespondant(String critere,String bien){
		int a=this.evenementsredoutes.size();
		for (int i=0;i<a;i++){
			if (this.evenementsredoutes.get(i).GetNomBien()==bien){
					if(this.evenementsredoutes.get(i).GetCritere()==critere){
						return this.evenementsredoutes.get(i);
					}
				}
			}
		return null;
		
	}
	
	public String toString(){
		return "Evenements redoutés";
	}
	
	

}
