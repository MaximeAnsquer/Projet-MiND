package abstraction.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.autres.Metrique;


public class EvenementsRedoutes extends Module {
	
	/*Je mets dans cette classe pour variable d'instance tout d'abord une arraylist d'�venements, et une autre qui correspond
	 * aux �venements �ventuellement stock�s dans une bdc(xml)
	 */
	private ArrayList<Evenement> evenementsredoutes;
	
	
	
	/*Puis les ant�c�dents du module ainsi que l'�tude car on en a besoin pour cr�er le pr�sent module
	 */
	private Etude etude;
	private BiensEssentiels lesBiensEssentiels;
	private Metriques lesMetriques;
	
	
	public EvenementsRedoutes(Etude etude){
		
		super("EvenementsRedoutes");
		this.etude=etude;
		
		
		this.predecesseurs.add(this.etude.getModule("BiensEssentiels"));
		this.predecesseurs.add(this.etude.getModule("Metriques"));
		
//		this.successeurs.add(this.etude.getModule("AnalyseDesRisques"));
		
		this.lesMetriques=(Metriques)this. etude.getModule("Metriques");
		this.lesBiensEssentiels=(BiensEssentiels)this.etude.getModule("BiensEssentiels");
		
		this.cree=false;
		
		
		this.coherent=false;
		
	
		
		
		
		/*A ce stade-ci du constructeur, on remplit l'arraylist en constituant des combinaisons entre Biens Essentiels
		 * et Crit�res d�finis dans les ant�c�dents du  module.
		 */
		/*On cr�e une arraylist qui contiendra les lignes du tableau*/
		
	    ArrayList<Evenement> liste=new ArrayList<Evenement>();
	    
	    /*Si on g�n�re le module � partir des modules pr�c�dents*/
	    
	   
	
		int a=((BiensEssentiels)this.etude.getModule("BiensEssentiels")).getBiensRetenus().size();
		int b=((CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite")).getCriteresRetenus().size();
		
		if(this.lesMetriques.estCoherent()==true && this.lesBiensEssentiels.estCoherent()==true ){
	
		
		
		CriteresDeSecurite critere=(CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite");
		
		
		ArrayList<Critere> tableaucriteres=new ArrayList<Critere>(critere.getCriteresRetenus());
		ArrayList<Bien> tableaubiens=new ArrayList<Bien>(this.lesBiensEssentiels.getBiensRetenus());
		
		
		for (int i=0;i<a;i++){
			
			for(int j=0;j<b;j++){
				
				
				liste.add(i*b+j,new Evenement(this.etude,"",this.lesBiensEssentiels.getNomColonnesSup(),tableaubiens.get(i).getContenuColonnesSup(),tableaubiens.get(i).getIntitule(),tableaucriteres.get(j).getIntitule()));
				
			}
		}
		}
		else{
			LinkedList<String> vide=new LinkedList<String>();
			vide.add("");
			liste.add(0,new Evenement(this.etude,"",vide,vide,"",""));
		}
		
		this.evenementsredoutes=liste;
	    }
	   /* else{
	    	this.importerBDC();
	    	this.evenementsredoutes = bdcevenementsredoutes;
	    }*/
	
	
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
	
	public void setEvenementsRedoutes(ArrayList<Evenement> evenementsredoutes){
		this.evenementsredoutes=evenementsredoutes;
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
	
	/*M�thode qui permet de lire le fichier xml pour importer les �venements qu'il contient*/
	
	
	
	public boolean estCoherent(){
		
		
EvenementsRedoutes nouveauev=new EvenementsRedoutes(this.getEtude());
		
		nouveauev.setCree(true);
		
		/*La boucle va servir � mettre � conserver les valeurs modifi�es de l'ancien tableau et de les mettre
		 * dans le nouveau
		 */
		
		
		
		int a=nouveauev.getEvenementsRedoutes().size();
		int b=this.getEvenementsRedoutes().size();
		
		for (int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				String biennouveau=nouveauev.getEvenementsRedoutes().get(i).GetNomBien();
				String bienancien=this.getEvenementsRedoutes().get(j).GetNomBien();
				
				String nouveaucrit=nouveauev.getEvenementsRedoutes().get(i).GetCritere();
				String anciencrit=this.getEvenementsRedoutes().get(j).GetCritere();
				
				if(anciencrit.equals(nouveaucrit)&&biennouveau.equals(bienancien)){
					
					nouveauev.getEvenementsRedoutes().get(i).setNomEvenement(this.getEvenementsRedoutes().get(j).GetNomEvenement());
					nouveauev.getEvenementsRedoutes().get(i).setNiveauExigence(this.getEvenementsRedoutes().get(j).getNiveauExigence());
					nouveauev.getEvenementsRedoutes().get(i).setNiveauGravite(this.getEvenementsRedoutes().get(j).getNiveauGravite());
					if(this.getEvenementsRedoutes().get(0).getNomGroupes()!=null){
						for(int k=0;k<this.getEvenementsRedoutes().get(0).getNomGroupes().size();k++){
						nouveauev.getEvenementsRedoutes().get(i).getContenuGroupes().addLast(this.getEvenementsRedoutes().get(j).getContenuGroupes().get(k));
						}
					}
					
				}
				
			}
		}
		
		
		this.setEvenementsRedoutes(nouveauev.getEvenementsRedoutes());
		
		boolean resultat=true;
		this.problemesDeCoherence=new ArrayList<String>();
		for (int i=0;i<this.getEvenementsRedoutes().size();i++){
			if(this.getEvenementsRedoutes().get(i).estComplet()!=true){
				String probleme = "Un ou plusieurs evenements sont incomplets";
				this.problemesDeCoherence.add(probleme);
				
				
				
				resultat=false;	
				
			}
			
			for(int k=0;k<this.getEvenementsRedoutes().size();k++){
				
				if(k!=i){
					
					if(this.getEvenementsRedoutes().get(i).GetNomEvenement().equals(this.getEvenementsRedoutes().get(k).GetNomEvenement())){
						resultat=false;
						
						String probleme = "Un ou plusieurs evenements ont le même nom";
						this.problemesDeCoherence.add(probleme);
						
					}
				}
			}
			
		}
		
		return resultat;
	}
	
	public boolean estRempli(){
		int a=this.getEvenementsRedoutes().size();
		boolean resultat=false;
		for (int i=0;i<a;i++){
			if(this.getEvenementsRedoutes().get(i).estComplet()==true){
				resultat=true;
			}
		}
		return resultat;
	}
	
	
}
