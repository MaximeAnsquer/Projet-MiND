package abstraction.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

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
	
	/*Je mets dans cette classe pour variable d'instance tout d'abord une arraylist d'évenements
	 */
	private ArrayList<Evenement> evenementsredoutes;
	
	private ArrayList<Evenement> bdcevenementsredoutes;
	
	
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
		this.cree=false;
		this.checkDisponible();
		this.coherent=false;
		
		
		/*A ce stade-ci du constructeur, on remplit l'arraylist en constituant des combinaisons entre Biens Essentiels
		 * et Critères définis dans les antécédents du  module.
		 */
		/*On crée une arraylist qui contiendra les lignes du tableau*/
		
	    ArrayList<Evenement> liste=new ArrayList<Evenement>();
	    
	    /*Si on génére le module à partir des modules précédents*/
	    
	    /*if(importerbdc==false){*/
	
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
				
				
				liste.add(i*b+j,new Evenement(this.etude,"",tableaubiens.get(i).getNomColonneSup(),tableaubiens.get(i).getContenuColonneSup(),tableaubiens.get(i).getIntitule(),tableaucriteres.get(j).getIntitule()));
				
			}
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
	
	public void importerBDC(){
		bdcevenementsredoutes = new ArrayList<Evenement>();

		/*
		 * Etape 1 : recuperation d'une instance de la classe "DocumentBuilderFactory"
		 */
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			/*
			 * Etape 2 : creation d'un parseur
			 */
			final DocumentBuilder builder = factory.newDocumentBuilder();

			/*
			 * Etape 3 : creation d'un Document
			 */
			final Document document= builder.parse(new File("bdc.xml"));	    

			/*
			 * Etape 4 : recuperation de l'Element racine
			 */
			final Element racine = document.getDocumentElement();

			/*
			 * Etape 5 : recuperation du noeud " CriteresDeSecurite "
			 */
			final Element evenements = (Element) racine.getElementsByTagName("EvenementsRedoutes").item(0);
			final NodeList listeEvenements = evenements.getChildNodes();
			final int nbEvenements = listeEvenements.getLength();

			for (int i = 0; i<nbEvenements; i++) {
				if(listeEvenements.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element evenement = (Element) listeEvenements.item(i);

					/*
					 * Construction d'un critere
					 */
					

					String nom = evenement.getElementsByTagName("Nom").item(0).getTextContent();
					String bien = evenement.getElementsByTagName("BienEssentiel").item(0).getTextContent();
					String critere = evenement.getElementsByTagName("Critere").item(0).getTextContent();
					
					ArrayList<String> l=new ArrayList<String>();

					Evenement e = new Evenement(this.etude,nom,l,l, bien, critere);

					/*
					 * Ajout du critere a la bdc
					 */

					bdcevenementsredoutes.add(e);				}				
			}			
		}
		catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (final SAXException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}	
	


	}
	
	
	public boolean estCoherent(){
		boolean resultat=true;
		for (int i=0;i<this.getEvenementsRedoutes().size();i++){
			if(this.getEvenementsRedoutes().get(i).estComplet()!=true){
				resultat=false;
			}
		}
		return resultat;
	}
	
	public void checkDisponible(){
		if(this.etude.getModule("CriteresDeSecurite").estCoherent()==true&&this.etude.getModule("BiensEssentiels").estCoherent()==true&&this.lesMetriques.estCoherent()==true){
			this.disponible=true;
		}
		else{
			this.disponible=false;
		}
	}
	
	
}
