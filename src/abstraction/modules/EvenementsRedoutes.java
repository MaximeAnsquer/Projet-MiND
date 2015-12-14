package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.autres.Metrique;


public class EvenementsRedoutes extends Module {
	
	/*Je mets dans cette classe pour variable d'instance tout d'abord une hashtable d'évenements
	 */
	private ArrayList<Evenement> evenementsredoutes;
	
	
	/*Puis les antécédents du module car on en a besoin pour créer le présent module
	 */
	private Etude etude;
	private BiensEssentiels lesBiensEssentiels;
	private Metriques lesMetriques;
	
	
	private EvenementsRedoutes(Etude etude){
		super("EvenementsRedoutes");
		this.predecesseurs.add(this.etude.getModule("BiensEssentiels"));
		this.predecesseurs.add(this.etude.getModule("Metriques"));
		this.successeurs.add(this.etude.getModule("AnalyseDesRisques"));
		this.lesMetriques=(Metriques) this.etude.getModule("Metriques");
		this.lesBiensEssentiels=(BiensEssentiels) this.etude.getModule("BiensEssentiels");
		this.cree=false;
		this.disponible=false;
		this.coherent=false;
		this.etude=etude;
		
		/*A ce stade-ci du constructeur, on remplit la hashtable en constituant des combinaisons entre Biens Essentiels
		 * et Critères définis dans les antécédents du  module.
		 */
		
	    ArrayList<Evenement> liste=new ArrayList<Evenement>();
		int a=((BiensEssentiels)this.etude.getModule("BiensEssentiels")).getLesBiens().size();
		int b=((CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite")).getLesCriteres().size();
		CriteresDeSecurite critere=(CriteresDeSecurite) this.etude.getModule("CriteresDeSecurite");
		BiensEssentiels bienessentiel=this.lesBiensEssentiels;
		Metrique exigence=this.lesMetriques.getMetrique("Exigence");
		Metrique gravite=this.lesMetriques.getMetrique("Gravite");
		Bien[] tableaubiens=this.lesBiensEssentiels.getLesBiens().values().toArray(new Bien[this.lesBiensEssentiels.getLesBiens().size()]);
		
		
		for (int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				liste.set(i*b+j,new Evenement("",tableaubiens[i].getNomColonneSup(),tableaubiens[i].getContenuColonneSup(),tableaubiens[i].getIntitule(),critere.getLesCriteres().get(j).getIntitule()));
				
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
	
	public String getEvenementCorrespondant(String critere,String bien){
		int a=this.evenementsredoutes.size();
		for (int i=0;i<a;i++){
			if (this.evenementsredoutes.get(i).GetNomBien()==bien){
					if(this.evenementsredoutes.get(i).GetCritere()==critere){
						return this.evenementsredoutes.get(i).GetNomEvenement();
					}
				}
			}
		return null;
		
	}
	
	

}
