package abstraction.modules;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import abstraction.autres.Critere;
import abstraction.autres.SourceDeMenace;

/**
 * Classe representant l'onglet " Sources de menaces ".
 * @author Maxime Ansquer
 */

public class SourcesDeMenaces extends Module {
	
	//---La BDC Sources de menaces, accessible par la methode statique getBDC()---
	
	/**
	 * Une hashtable d'objets " SourceDeMenace " indexes par leurs id.
	 * Attention les id doivent etre distincts !
	 */
	private static Hashtable<String, SourceDeMenace> bdcSourcesDeMenaces = new Hashtable<String, SourceDeMenace>();
	
	//---Variables d'instance---
	
	/**
	 * Une hashtable d'objets " SourceDeMenace " indexes par leurs id.
	 * Attention les id doivent etre distincts !
	 */
	private Hashtable<String, SourceDeMenace>  lesSourcesDeMenaces;
	
	//---Constructeurs---
	
	/** 
	 * Initialise le module en commencant par initialiser la BDC, puis en copiant les valeurs
	 * de la BDC dans le module.
	 */
	public SourcesDeMenaces(){
		super("SourcesDeMenaces");
		//TODO commente pour faire des test, a decommenter par la suite
		
//		this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesGeneriques"));
//		this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesTypes"));
//		this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
//		this.successeurs.add(this.getEtude().getModule("MatriceDesRisques"));
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		
		this.importerBDC();  //on remplit la BDC
		this.lesSourcesDeMenaces = bdcSourcesDeMenaces;	 //on initialise l'onglet avec les valeurs de la BDC
	}
	
	//---Getters et setters	

	public Hashtable<String, SourceDeMenace> getLesSourcesDeMenaces() {
		return lesSourcesDeMenaces;
	}

	public void setLesSources(Hashtable<String, SourceDeMenace> lesSourcesDeMenaces) {
		this.lesSourcesDeMenaces = lesSourcesDeMenaces;
	}
	
	//---Services---
	
	public SourceDeMenace getSourceDeMenace(SourceDeMenace sourceDeMenace){
		return this.getLesSourcesDeMenaces().get(sourceDeMenace.getId());
	}
	
	public SourceDeMenace getSourceDeMenace(String idSourceDeMenace){
		return this.getLesSourcesDeMenaces().get(idSourceDeMenace);
	}
	
	public void ajouterSourceDeMenace(SourceDeMenace sourceDeMenace){
		this.getLesSourcesDeMenaces().put(sourceDeMenace.getId(), sourceDeMenace);
	}
	
	public void supprimerSourceDeMenace(SourceDeMenace sourceDeMenace){
		this.getLesSourcesDeMenaces().remove(sourceDeMenace.getId());
	}
	
	/**
	 * @return Renvoie la liste des sources de menaces retenues sous la forme d'une
	 * hashtable de SourceDeMenace indexee par leurs id.
	 */
	public Hashtable<String, SourceDeMenace> getSourcesDeMenacesRetenues(){
		Hashtable<String, SourceDeMenace> resultat = new Hashtable<String, SourceDeMenace>();
		for(SourceDeMenace sourceDeMenace : this.getLesSourcesDeMenaces().values()){
			if(sourceDeMenace.isRetenu()){
				resultat.put(sourceDeMenace.getId(), sourceDeMenace);
			}
		}
		return resultat;
	}
	
	public static Hashtable<String, SourceDeMenace> getInstance(){
		return bdcSourcesDeMenaces;
	}
	
	private void importerBDC() {
		
		bdcSourcesDeMenaces = new Hashtable<String, SourceDeMenace>();

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
			 * Etape 5 : recuperation du noeud " SourcesDeMenaces "
			 */
			final Element sources = (Element) racine.getElementsByTagName("SourcesDeMenaces").item(0);
			final NodeList listeSources = sources.getChildNodes();
			final int nbSources = listeSources.getLength();

			for (int i = 0; i<nbSources; i++) {
				if(listeSources.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element source = (Element) listeSources.item(i);
					
					/*
					 * Construction d'une source de menace
					 */
					
					String id = source.getAttribute("id");
					String intitule = source.getAttribute("intitule");
					String exemple = source.getAttribute("exemple");
					
					SourceDeMenace s = new SourceDeMenace(id, intitule, exemple);
					
					/*
					 * Ajout de la source a la bdc
					 */
					
					bdcSourcesDeMenaces.put(id, s);				}				
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
	
	public Hashtable<String, SourceDeMenace> getBDC(){
		return bdcSourcesDeMenaces;
	}
	
	public int nombreDeSources(){
		return this.getLesSourcesDeMenaces().size();
	}
	
	public SourceDeMenace getSource(int index){
		return (SourceDeMenace) getLesSourcesDeMenaces().values().toArray()[index];
	}
	
	public String toString(){
		return "Sources de menaces";
	}
	
	public boolean estCoherent(){
		boolean resultat = true;
		this.problemesDeCoherence = new ArrayList<String>();
		for(SourceDeMenace s : this.getLesSourcesDeMenaces().values()){
			if(!s.estComplet()){
				String probleme = "La source de menace \" " + s.getId() + " \" est incomplete";
				problemesDeCoherence.add(probleme);
				resultat = false;
			}
		}
		if(this.getSourcesDeMenacesRetenues().size() <= 0){
			String probleme = "Aucune source de menace retenue.";
			problemesDeCoherence.add(probleme);
			resultat = false;
		}
		return resultat;
	}
	
}
