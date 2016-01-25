package abstraction.modules;

import java.awt.Color;
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

import abstraction.autres.TypeBien;

/**
 * Cette classe représente le tableau crée lorqu'on clique sur l'onglet de
 * typologie de biens supports
 * 
 * @author Belghiti Ali
 */

public class TypologieDesBiensSupports extends Module {

	// Represente la bdc
	private static ArrayList<TypeBien> bdcTypeBiensSupports;

	// Variable d'instance
	// La clé de la Hashtable représente l'intitulé du type de bien
	private ArrayList<TypeBien> tableau;
	private TypeBien typeBienCourant;

	public TypologieDesBiensSupports() {
		super("TypologieDesBiensSupports");
		this.tableau = new ArrayList<TypeBien>();
		this.typeBienCourant=new TypeBien();
		//this.successeurs.add(this.getEtude().getModule("Biens Supports"));
		//this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesGeneriques"));
		this.cree = false;
		this.coherent = false;
		this.disponible = true;
		this.importerBDC();
		this.tableau = TypologieDesBiensSupports.getBDC();
	}

	// ---Getters et setters---

	public ArrayList<TypeBien> getTableau() {
		return this.tableau;
	}
	
	public int getSize(){
		return this.tableau.size();
	}

	public void setTableau(ArrayList<TypeBien> tab) {
		this.tableau = tab;
	}
	
	public TypeBien getTypeBienCourant() {
		return typeBienCourant;
	}

	public void setTypeBienCourant(TypeBien typeBienCourant) {
		this.typeBienCourant = typeBienCourant;
		this.setChanged();        // PAC
		this.notifyObservers();   // PAC
	}

	public TypeBien getTypeBien(String type) {
		int i = 0 ;
		while (i<this.tableau.size() && !this.tableau.get(i).getIntitule().equals(type)){
			i++ ;
		}
		return this.tableau.get(i);
	}
	
	public void setTypeBien (int i, TypeBien type){
		this.tableau.add(type);
		this.setChanged();               // PAC
		this.notifyObservers();          // PAC
	}
	
	public TypeBien getTypeBien(int i){
		return this.tableau.get(i);
	}

	public static ArrayList<TypeBien> getBDC() {
		return bdcTypeBiensSupports;
	}

	// ---Services---
	
	// On verifie si un type de bien support est nouveau
	// cad s'il n'est pas présent dans la bdc et qu'il a été ajouté au tableau
	// ALGO A AMELIORER
	public boolean estNouveauTypeBien(TypeBien type) {
		boolean b = true;
		for (TypeBien t : TypologieDesBiensSupports.getBDC()) {
			b = b && (t.getIntitule() != type.getIntitule());
		}
		if (b) {
			for (TypeBien t : this.tableau) {
				b = b && (t.getIntitule() != type.getIntitule());
			}
		}
		return b;
	}

	// On ajoute une ligne au tableau seulement si tous les champs sont
	// renseignés
	// ATTENTION : si 2 types ont le même intitulé, un seul sera présent dans la JTable
	public void addTypeBienSupport(TypeBien type) {
		if (!type.isIncomplete()) {
			this.tableau.add(type);
			this.setChanged();      // PAC
			this.notifyObservers(); // PAC
		}
	}

	// Suppression d'une ligne du tableau
	public void removeTypeBienSupport(TypeBien type) {
		this.tableau.remove(type);
		this.setChanged();      // PAC
		this.notifyObservers(); // PAC
	}
	
	// NEW !!!
	public void setDescriptionTypeBienSupport (String description, TypeBien type){
		this.getTypeBien(type.getIntitule()).setDescription(description);
		this.setChanged();      // PAC
		this.notifyObservers(); // PAC
	}
	
	// On liste les Types de bien retenus
	public ArrayList<TypeBien> getTypeBiensRetenus() {
		ArrayList<TypeBien> resultat = new ArrayList<TypeBien>();
		for (TypeBien type : this.getTableau()) {
			if (type.isRetenu()) {
				resultat.add(type);
			}
		}
		return resultat;
	}
	
	public boolean isTypeBienRetenu(String value) {
		ArrayList<String> resultat = new ArrayList<String>();
		for (TypeBien type : this.getTableau()) {
			if (type.isRetenu()) {
				resultat.add(type.getIntitule());
			}
		}
		return resultat.contains(value);
	}
	
	public Object[] getIntituleTypeBiensRetenus() {
		ArrayList<String> resultat = new ArrayList<String>();
		for (TypeBien type : this.getTableau()) {
			if (type.isRetenu()) {
				resultat.add(type.getIntitule());
			}
		}
		return resultat.toArray();
	}
	
	// On retient un Type de Bien)
	// Cela correspond à une croix cochée dans la colonne des types de biens retenus
	public void retenirTypeBien(String intitule) {
		this.getTypeBien(intitule).setRetenu(true);
	}

	public void importerBDC() {
		
		bdcTypeBiensSupports= new ArrayList<TypeBien>();
		
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
			 * Etape 5 : recuperation du noeud " TypologieBiensSupports "
			 */
			final Element TypesBiens = (Element) racine.getElementsByTagName("TypologieBiensSupports").item(0);
			final NodeList listeTypesBiens = TypesBiens.getChildNodes();
			final int nbTypesBiens = listeTypesBiens.getLength();
			
			for (int i = 0; i<nbTypesBiens; i++) {
				if(listeTypesBiens.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element typeBien = (Element) listeTypesBiens.item(i);

					/*
					 * Construction d'un type de bien
					 */

					String id = typeBien.getElementsByTagName("Id").item(0).getTextContent();
					String intitule = typeBien.getElementsByTagName("Intitule").item(0).getTextContent();
					String description = typeBien.getElementsByTagName("Description").item(0).getTextContent();
					String couleur = typeBien.getElementsByTagName("Couleur").item(0).getTextContent();
					String [] nbsCouleurs = couleur.split(",");
					
					Color clr = new Color(Integer.parseInt(nbsCouleurs[0]), Integer.parseInt(nbsCouleurs[1]), Integer.parseInt(nbsCouleurs[2]));

					TypeBien type = new TypeBien(id, description, intitule, true, clr);

					/*
					 * Ajout du type de bien à la bdc
					 */

					bdcTypeBiensSupports.add(type);
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
		return "Typologies des biens supports" ;
	}
	
	public boolean estCoherent() {
		int compteur = 1 ;
		boolean resultat = true;
		this.problemesDeCoherence = new ArrayList<String>();
		for (TypeBien type : this.tableau) {
			if (type.isIncomplete()) {
				String s = "Le type de bien support situé à la ligne " + compteur
						+ " est incomplet";
				this.problemesDeCoherence.add(s);
				resultat = false;
			}
			compteur++ ;
		}

		if (this.getTypeBiensRetenus().size() < 1) {
			String s = "Aucun type de bien support retenu";
			this.problemesDeCoherence.add(s);
			resultat = false;
		}
		return resultat;
	}
}
