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

/**
 * Classe representant l'onglet CriteresDeSecurite.
 * Elle est constituee d'une Hashtable d'objets Critere indexee par l'intitule du critere.
 * 
 * @author Maxime Ansquer
 */

public class CriteresDeSecurite extends Module{

	//---La BDC Criteres De Securite, accessible par la methode statique getBDC()---

	/**
	 * Hashtable reference par l'intitule des criteres
	 */
	private static ArrayList<Critere> bdcCriteresDeSecurite;

	//---Variables d'instance---

	/**
	 * Hashtable reference par l'intitule des criteres
	 */
	private ArrayList<Critere> lesCriteres;

	private Critere gravite;	
	private Critere vraisemblance;

	//---Constructeurs---	

	/** 
	 * Initialise le module en commencant par initialiser la BDC, puis en copiant les valeurs
	 * de la BDC dans le module.
	 */
	public CriteresDeSecurite() {
		super("CriteresDeSecurite");

		this.cree = false;
		this.coherent = false;
		this.disponible = true;

		this.gravite = new Critere("Gravite", "Gravite", "Gravite");
		this.vraisemblance = new Critere("Vraisemblance", "Vraisemblance", "Vraisemblance");

		this.importerBDC();  //on remplit la BDC
		this.lesCriteres = bdcCriteresDeSecurite;  //on initialise l'onglet avec les valeurs de la BDC
	}

	//---Getters et setters---

	public ArrayList<Critere> getLesCriteres() {
		return lesCriteres;
	}

	public void setLesCriteres(ArrayList<Critere> lesCriteres) {
		this.lesCriteres = lesCriteres;
	}

	public Critere getCritere(String intituleCritere){
		if(intituleCritere.equals("Gravite")){
			return this.getGravite();
		}
		else if(intituleCritere.equals("Vraisemblance")){
			return this.getVraisemblance();
		}
		else{
			Critere resultat = null;
			for(Critere critere : this.getLesCriteres()){
				if(critere.getIntitule().equals(intituleCritere)){
					resultat = critere;
				}
			}
			return resultat;
		}
	}

	private Critere getVraisemblance() {
		return this.vraisemblance;
	}

	private Critere getGravite() {
		return this.gravite;
	}

	public static ArrayList<Critere> getBDC(){
		return bdcCriteresDeSecurite;
	}

	//---Services---	

	private void importerBDC() {
		bdcCriteresDeSecurite = new ArrayList<Critere>();

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
			final Element criteres = (Element) racine.getElementsByTagName("CriteresDeSecurite").item(0);
			final NodeList listeCriteres = criteres.getChildNodes();
			final int nbCriteres = listeCriteres.getLength();

			for (int i = 0; i<nbCriteres; i++) {
				if(listeCriteres.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element critere = (Element) listeCriteres.item(i);

					/*
					 * Construction d'un critere
					 */

					String id = critere.getElementsByTagName("Id").item(0).getTextContent();
					String intitule = critere.getElementsByTagName("Intitule").item(0).getTextContent();
					String description = critere.getElementsByTagName("Description").item(0).getTextContent();

					Critere c = new Critere(id, intitule, description);

					/*
					 * Ajout du critere a la bdc
					 */

					bdcCriteresDeSecurite.add(c);				}				
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


	public void ajouterCritere(Critere critere){
		this.getLesCriteres().add(critere);
		( (Metriques) this.getEtude().getModule("Metriques")).ajouterMetrique(critere);
	}

	public void supprimerCritere(String intituleCritere){
		Critere critereASupprimer = null;
		for(Critere critere : this.getLesCriteres()){
			if(critere.getIntitule().equals(intituleCritere)){
				critereASupprimer = critere;
			}
		}
		this.getLesCriteres().remove(critereASupprimer);
	}	

	/**
	 * @author Maxime Ansquer
	 * @return Renvoie une Hashtable des criteres retenus par l'utilisateur
	 */
	public ArrayList<Critere> getCriteresRetenus(){
		ArrayList<Critere> resultat = new ArrayList<Critere>();
		for(Critere c : this.getLesCriteres()){
			if(c.isRetenu()){
				resultat.add(c);
			}
		}
		return resultat;
	}

	public ArrayList<String> getIntitulesCriteresRetenus(){
		ArrayList<String> resultat = new ArrayList<String>();
		for(Critere c : this.getLesCriteres()){
			if(c.isRetenu()){
				resultat.add(c.getIntitule());
			}
		}
		return resultat;
	}


	public int nombreDeCriteres(){
		return lesCriteres.size();
	}

	/**
	 * Permet d'acceder a un critere par sa position dans la hashtable
	 * @param index
	 * @return
	 */
	public Critere getCritere(int index){
		return this.getLesCriteres().get(index);
	}

	public String toString(){
		return "Critères de sécurité";
	}

	public boolean estCoherent(){
		
		//Traitement des avertissements
		this.avertissements.clear();
		if(this.getCriteresRetenus().size() <= 0){
			this.avertissements.add("Aucun critère retenu.");
		}

		//Traitement des erreurs		
		boolean resultat = true;
		this.erreurs.clear();
		for(Critere c : getLesCriteres()){
			if(!c.estComplet()){
				String probleme = "Le critère \" " + c.getIntitule() + " \" est incomplet";
				this.erreurs.add(probleme);
				resultat = false;
			}
		}
		return resultat;
	}

}
