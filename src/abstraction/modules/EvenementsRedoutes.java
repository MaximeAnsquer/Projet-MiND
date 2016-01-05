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
	
	private ArrayList<Evenement> bdcevenementsredoutes;
	
	
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
		
		this.successeurs.add(this.etude.getModule("AnalyseDesRisques"));
		
		this.lesMetriques=(Metriques)this. etude.getModule("Metriques");
		this.lesBiensEssentiels=(BiensEssentiels)this.etude.getModule("BiensEssentiels");
		
		this.cree=false;
		this.checkDisponible();
		this.coherent=false;
		
		
		/*A ce stade-ci du constructeur, on remplit l'arraylist en constituant des combinaisons entre Biens Essentiels
		 * et Crit�res d�finis dans les ant�c�dents du  module.
		 */
		/*On cr�e une arraylist qui contiendra les lignes du tableau*/
		
	    ArrayList<Evenement> liste=new ArrayList<Evenement>();
	    
	    /*Si on g�n�re le module � partir des modules pr�c�dents*/
	    
	    /*if(importerbdc==false){*/
	
		int a=((BiensEssentiels)this.etude.getModule("BiensEssentiels")).getBiensRetenus().size();
		int b=((CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite")).getCriteresRetenus().size();
		
		if(this.lesMetriques.estCoherent()==true && this.lesBiensEssentiels.estCoherent()==true ){
	
		
		
		CriteresDeSecurite critere=(CriteresDeSecurite)this.etude.getModule("CriteresDeSecurite");
		
		
		ArrayList<Critere> tableaucriteres=new ArrayList<Critere>(critere.getCriteresRetenus().values());
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
		return "Evenements redout�s";
	}
	
	/*M�thode qui permet de lire le fichier xml pour importer les �venements qu'il contient*/
	
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
			 * Etape 5 : recuperation du noeud " EvenementsRedoutes "
			 */
			final Element evenements = (Element) racine.getElementsByTagName("EvenementsRedoutes").item(0);
			final NodeList listeEvenements = evenements.getChildNodes();
			final int nbEvenements = listeEvenements.getLength();

			for (int i = 0; i<nbEvenements; i++) {
				if(listeEvenements.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element evenement = (Element) listeEvenements.item(i);

					/*
					 * Construction d'un evenement
					 */
					

					String nom = evenement.getElementsByTagName("Nom").item(0).getTextContent();
					String bien = evenement.getElementsByTagName("BienEssentiel").item(0).getTextContent();
					String critere = evenement.getElementsByTagName("Critere").item(0).getTextContent();
					
					LinkedList<String> l=new LinkedList<String>();

					Evenement e = new Evenement(this.etude,nom,l,l, bien, critere);

					/*
					 * Ajout de l'�venement a la bdc
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
		this.problemesDeCoherence=new ArrayList<String>();
		for (int i=0;i<this.getEvenementsRedoutes().size();i++){
			if(this.getEvenementsRedoutes().get(i).estComplet()!=true){
				String probleme = "Un ou plusieurs evenements sont incomplets";
				this.problemesDeCoherence.add(probleme);
				
				resultat=false;
			}
		}
		return resultat;
	}
	
	/*D�termine si le bouton doit �tre disponible(non gris�) ou indisponible(gris�) dans le workflow
	 * 	
	 */
	public void checkDisponible(){
		if(this.etude.getModule("CriteresDeSecurite").estCoherent()==true&&this.etude.getModule("BiensEssentiels").estCoherent()==true&&this.lesMetriques.estCoherent()==true){
			this.disponible=true;
		}
		else{
			this.disponible=false;
		}
	}
	
	
}
