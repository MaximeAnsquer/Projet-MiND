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
import abstraction.autres.ScenarioGenerique;
import abstraction.autres.TypeBien;

public class ScenariosDeMenacesGeneriques extends Module {
	
	// Represente la bdc
	private static Hashtable<String, ScenarioGenerique> bdcScenariosMenacesGeneriques;

	// Variable d'instance
	private Hashtable<String, ScenarioGenerique> tableau;
	private ScenarioGenerique scenarioCourant ;
	private ArrayList<String> nomColonneSup;
	
	public ScenariosDeMenacesGeneriques(Etude etude){
		super("ScenariosDeMenacesGeneriques");
		this.tableau = new Hashtable<String, ScenarioGenerique>();
		
		this.etude=etude;
		this.predecesseurs.add(this.getEtude().getModule("TypologieDesBiensSupports"));
		this.predecesseurs.add(this.getEtude().getModule("CriteresDeSecurite"));
		//this.successeurs.add(this.getEtude().getModule("Biens Supports"));
		//this.successeurs.add(this.getEtude().getModule("Scenario de Menaces Types"));
		this.scenarioCourant= new ScenarioGenerique();
		this.nomColonneSup = new ArrayList<String>();
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		this.importerBDC();
		this.tableau = ScenariosDeMenacesGeneriques.getBDC();
	}
	
	// ---Getters et setters---
	
	public int getSize(){
		return this.tableau.size();
	}
	
	public ScenarioGenerique getScenarioCourant(){
		return this.scenarioCourant;
	}
	
	public void setScenarioCourant(ScenarioGenerique scenarioCourant){
		this.scenarioCourant=scenarioCourant;
		this.setChanged();         // PAC
		this.notifyObservers();    // PAC
	}
	
	public ArrayList<String> getNomColonneSup() {
		return nomColonneSup;
	}

	public void setNomColonneSup(ArrayList<String> nomColonneSup) {
		this.nomColonneSup = nomColonneSup;
	}

	public Hashtable<String, ScenarioGenerique> getTableau() {
		return tableau;
	}

	public void setTableau(Hashtable<String, ScenarioGenerique> tableau) {
		this.tableau = tableau;
	}
	
	public ScenarioGenerique getScenarioGenerique(int i) {
		ArrayList<ScenarioGenerique> scenariosMenacesGeneriques = new ArrayList<ScenarioGenerique>(
				tableau.values());
		return scenariosMenacesGeneriques.get(i);
	}
	
	public static Hashtable<String, ScenarioGenerique> getBDC(){
		return bdcScenariosMenacesGeneriques;
	}
	
	// ---Services---
	
	// L'idée n'est pas encore finalisée : 
	
	// Un TypeBien est nouveau dans le cas où il n'est pas contenu dans la bdc de cette classe
	// et qu'il n'est pas dans la bdc de la classe TypeBien
	public boolean estNouveauTypeBien(TypeBien type) {
		boolean b = ((TypologieDesBiensSupports) this.getEtude().getModule(
				"Typologie des biens supports")).estNouveauTypeBien(type);
		if (b) {
			for (ScenarioGenerique scenario : bdcScenariosMenacesGeneriques
					.values()) {
				b = b
						&& (scenario.getTypeBienSupport() != type
								.getIntitule());
			}
		}
		return b;
	}
	
	// L'ajout d'une ligne dans le tableau correspond ici
	// à un type de bien support référencé
	
	public void addScenarioGenerique(ScenarioGenerique scenario) {
		if (this.nomColonneSup!=null){
			for(String nomCritere : this.nomColonneSup){
				scenario.getCriteresSup().put(nomCritere,false);
			}
		}
		this.tableau.put(scenario.getIntitule(), scenario);
		this.setChanged();                           // PAC
		this.notifyObservers();                      // PAC
	}
	
	public void removeScenarioGenerique(ScenarioGenerique scenario){
		if (this.nomColonneSup!=null){
			for(String nomCritere : this.nomColonneSup){
				scenario.getCriteresSup().remove(nomCritere);
			}
		}
		this.tableau.remove(scenario.getIntitule(), scenario);
		this.setChanged();                           // PAC
		this.notifyObservers();                      // PAC
	}
	
	// Ajout d'une colonne
	public void addCritere (String nomCritere){
		this.nomColonneSup.add(nomCritere);
		for (int i=0 ; i<this.tableau.size() ; i++){
			if (!this.getScenarioGenerique(i).getCriteresSup().containsKey(nomCritere)){
				this.getScenarioGenerique(i).getCriteresSup().put(nomCritere, false);
			}
		}
		this.setChanged();                           // PAC
		this.notifyObservers();                      // PAC
	}
	
	//Suppression d'une colonne
	public void removeCritere (String nomCritere){
		this.nomColonneSup.remove(nomCritere);
		/*
		for (int i=0 ; i<this.tableau.size() ; i++){
			this.getScenarioGenerique(i).getCriteresSup().remove(nomCritere);
		}
		//*/
		this.setChanged();                           // PAC
		this.notifyObservers();                      // PAC
	}
	
	public Hashtable<String, ScenarioGenerique> getScenariosGeneriquesRetenus() {
		Hashtable<String, ScenarioGenerique> resultat = new Hashtable<String, ScenarioGenerique>();
		for (ScenarioGenerique scenario : this.getTableau().values()) {
			if (scenario.isRetenu()) {
				resultat.put(scenario.getIntitule(), scenario);
			}
		}
		return resultat;
	}
	
	private void importerBDC() {
		
		bdcScenariosMenacesGeneriques = new Hashtable<String, ScenarioGenerique>();
		
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
			 * Etape 5 : recuperation du noeud " ScenariosMenacesGeneriques "
			 */
			final Element scenariosDeMenacesGeneriques = (Element) racine.getElementsByTagName("ScenariosMenacesGeneriques").item(0);
			final NodeList listeScenarios = scenariosDeMenacesGeneriques.getChildNodes();
			final int nbScenarios = listeScenarios.getLength();
			
			for (int i = 0; i<nbScenarios; i++) {
				if(listeScenarios.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element scenarioGenerique = (Element) listeScenarios.item(i);
					
					/*
					 * Construction d'un scenario
					 */
					
					String intituleTypeBien = scenarioGenerique.getElementsByTagName("TypeBien").item(0).getTextContent();
					String id = scenarioGenerique.getElementsByTagName("Id").item(0).getTextContent();
					String intituleScenario = scenarioGenerique.getElementsByTagName("Intitule").item(0).getTextContent(); 
					
					Hashtable<String, Boolean> CriteresSup = new Hashtable<String, Boolean>();
					
					final Element criteres = (Element) scenarioGenerique.getElementsByTagName("CriteresRetenus").item(0);
					final NodeList listeCriteres = criteres.getChildNodes();
					final int nbCriteres = listeCriteres.getLength();					
					
					for (int j = 0; j < nbCriteres; j++){
						if(listeCriteres.item(j).getNodeType() == Node.ELEMENT_NODE) {
							final Element critere = (Element) listeCriteres.item(j);
							String intituleCritere = critere.getElementsByTagName("IntituleCritere").item(0).getTextContent();
							Boolean retenu = Boolean.parseBoolean(critere.getElementsByTagName("Retenu").item(0).getTextContent());
							
							CriteresSup.put(intituleCritere,retenu);
						}
					}					
					
					ScenarioGenerique scenario = new ScenarioGenerique(intituleTypeBien, id, intituleScenario, CriteresSup, true);
					
					/*
					 * Ajout du scenario à la bdc
					 */
					
					bdcScenariosMenacesGeneriques.put(intituleScenario, scenario);				
					}				
			}
			
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (final SAXException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return "Scenarios de menaces generiques";
	}
	
}
