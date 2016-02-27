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
	private static ArrayList<SourceDeMenace> bdcSourcesDeMenaces = new ArrayList<SourceDeMenace>();

	//---Variables d'instance---

	/**
	 * Une hashtable d'objets " SourceDeMenace " indexes par leurs id.
	 * Attention les id doivent etre distincts !
	 */
	private ArrayList<SourceDeMenace> lesSourcesDeMenaces;

	//---Constructeurs---

	/** 
	 * Initialise le module en commencant par initialiser la BDC, puis en copiant les valeurs
	 * de la BDC dans le module.
	 */
	public SourcesDeMenaces(){
		super("SourcesDeMenaces");
		this.cree = false;
		this.coherent = false;
		this.disponible = false;

		this.importerBDC();  //on remplit la BDC
		this.lesSourcesDeMenaces = bdcSourcesDeMenaces;	 //on initialise l'onglet avec les valeurs de la BDC
	}

	//---Getters et setters	

	public ArrayList<SourceDeMenace> getLesSourcesDeMenaces() {
		return lesSourcesDeMenaces;
	}

	public void setLesSources(ArrayList<SourceDeMenace> lesSourcesDeMenaces) {
		this.lesSourcesDeMenaces = lesSourcesDeMenaces;
	}

	//---Services---

	public SourceDeMenace getSourceDeMenace(SourceDeMenace sourceDeMenace){
		SourceDeMenace resultat = null;
		for(SourceDeMenace source : this.getLesSourcesDeMenaces()){
			if(source.getId().equals(sourceDeMenace.getId())){
				resultat = source;
			}
		}		
		return resultat;
	}

	public SourceDeMenace getSourceDeMenace(String idSourceDeMenace){
		SourceDeMenace resultat = null;
		for(SourceDeMenace source : this.getLesSourcesDeMenaces()){
			if(source.getId().equals(idSourceDeMenace)){
				resultat = source;
			}
		}		
		return resultat;
	}

	public void ajouterSourceDeMenace(SourceDeMenace sourceDeMenace){
		this.getLesSourcesDeMenaces().add(sourceDeMenace);
	}

	public void supprimerSourceDeMenace(SourceDeMenace sourceDeMenace){
		this.getLesSourcesDeMenaces().remove(sourceDeMenace);
	}

	public ArrayList<SourceDeMenace> getSourcesDeMenacesRetenues(){
		ArrayList<SourceDeMenace> resultat = new ArrayList<SourceDeMenace>();
		for(SourceDeMenace sourceDeMenace : this.getLesSourcesDeMenaces()){
			if(sourceDeMenace.isRetenu()){
				resultat.add(sourceDeMenace);
			}
		}
		return resultat;
	}

	public static ArrayList<SourceDeMenace> getInstance(){
		return bdcSourcesDeMenaces;
	}

	private void importerBDC() {

		bdcSourcesDeMenaces = new ArrayList<SourceDeMenace>();

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

					bdcSourcesDeMenaces.add(s);				}				
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

	public ArrayList<SourceDeMenace> getBDC(){
		return bdcSourcesDeMenaces;
	}

	public int nombreDeSources(){
		return this.getLesSourcesDeMenaces().size();
	}

	public SourceDeMenace getSource(int index){
		return this.getLesSourcesDeMenaces().get(index);
	}

	public String toString(){
		return "Sources de menaces";
	}

	public boolean estCoherent(){

		//Gestion des avertissements
		avertissements.clear();
		if(this.getSourcesDeMenacesRetenues().size() <= 0)
			avertissements.add("Aucune source de menace retenue.");

		//Gestion des erreurs
		boolean resultat = true;
		this.erreurs.clear();
		for(SourceDeMenace s : this.getLesSourcesDeMenaces()){
			if(!s.estComplet()){
				String probleme = "La source de menace \" " + s.getId() + " \" est incomplète.";
				erreurs.add(probleme);
				resultat = false;
			}
		}
		return resultat;
	}

}
