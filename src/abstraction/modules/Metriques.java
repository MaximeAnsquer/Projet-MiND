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

import abstraction.Etude;
import abstraction.autres.Critere;
import abstraction.autres.Metrique;
import abstraction.autres.NiveauDeMetrique;

/** 
 * Cahier des charges, page 5 :
 * 
 * " Metriques : cet onglet permet la dï¿½finition des echelles ordinales associees
 *   aux differents criteres de securite retenus (par exemple : dï¿½finition dï¿½une 
 *   echelle ï¿½ quatre niveaux pour le critï¿½re de disponibilite). "
 *   
 * Cette classe modelise le module " Metriques ". Elle est constituee d'une hastable d'objets Metrique
 * indexee par le nom du critere associe (on rappelle qu'un objet Metrique est constitue d'un critere
 * et d'un tableau dans lequel chaque ligne est un niveau de metrique).
 */

public class Metriques extends Module {
		
	//---La BDC Metriques, accessible par la methode statique getBDC()---
	
	private Hashtable<String, Metrique> bdcMetriques;

	//---Variables d'instance---
	
	private Hashtable<String, Metrique> lesMetriques;
	private Metrique vraisemblance;
	private Metrique gravite;
	
	//---Le module CriteresDeSecurite de l'etude associee---
	
	CriteresDeSecurite cds; 

	//---Constructeurs---	
	
	/** 
	 * Initialise le module en commenï¿½ant par initialiser la BDC, puis en copiant les valeurs
	 * de la BDC dans le module.
	 */
	public Metriques(Etude etude) {
		super("Metriques");
		
		this.etude = etude;
		
		/**
		 * TODO : Commente pour faire des tests, a decommenter par la suite
		 */
		this.predecesseurs.add(this.getEtude().getModule("CriteresDeSecurite"));
//		this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesTypes"));
//		this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
//		this.successeurs.add(this.getEtude().getModule("MatriceDesRisques"));
		
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		
		/**
		 * On initialise le module CriteresDeSecurite associe.
		 */
		cds = (CriteresDeSecurite) this.getEtude().getModule("CriteresDeSecurite");
		
		this.importerBDC();  //on remplit la BDC
		this.lesMetriques = new Hashtable<String, Metrique>();  
		CriteresDeSecurite cds = (CriteresDeSecurite) this.getEtude().getModule("CriteresDeSecurite");
		for(Critere critere : cds.getCriteresRetenus().values()){   //pour chaque critere retenu a l'onglet "CritereDeSecurite"
			if(bdcMetriques.containsKey(critere.getIntitule())){   //s'il existe une metrique associee dans la BDC Metriques
				this.lesMetriques.put(critere.getIntitule(), bdcMetriques.get(critere.getIntitule()));   //on ajoute cette metrique dans l'onglet Metriques
			}
			else{
				this.lesMetriques.put(critere.getIntitule(), new Metrique(critere));   //sinon, on ajoute une metrique vide associee a ce critere dans l'onglet Metriques
			}
		}
		//On ajoute également les métriques Gravité et Vraisemblance
		this.lesMetriques.put("Gravité", bdcMetriques.get("Gravité"));
		
		
	}	

	//---Getters et setters---
	
	public Hashtable<String, Metrique> getLesMetriques(){
		return this.lesMetriques;
	}
	
	public void setLesMetriques(Hashtable<String, Metrique> lesMetriques ){
		this.lesMetriques = lesMetriques;
	}
	
	//--Services--		
	
	public Metrique getMetrique(String intituleCritere){
		return this.getLesMetriques().get(intituleCritere);
	}
	
	public void supprimerMetrique(String intituleCritere){
		this.getLesMetriques().remove(intituleCritere);
	}
	
	public void ajouterMetrique(Metrique metrique){
		this.getLesMetriques().put(metrique.getCritere().getIntitule(), metrique);
	}
	
	public void ajouterMetrique(Critere critere){
		this.getLesMetriques().put(critere.getIntitule(), new Metrique(critere));
	}
	
	private void importerBDC() {
		
		bdcMetriques = new Hashtable<String, Metrique>();

		/*
		 * Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
		 */
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			/*
			 * Etape 2 : création d'un parseur
			 */
			final DocumentBuilder builder = factory.newDocumentBuilder();

			/*
			 * Etape 3 : création d'un Document
			 */
			final Document document= builder.parse(new File("bdc.xml"));	    

			/*
			 * Etape 4 : récupération de l'Element racine
			 */
			final Element racine = document.getDocumentElement();

			/*
			 * Etape 5 : récupération du noeud " Metriques "
			 */
			final Element metriques = (Element) racine.getElementsByTagName("Metriques").item(0);
			final NodeList listeMetriques = metriques.getChildNodes();
			final int nbMetriques = listeMetriques.getLength();

			for (int i = 0; i<nbMetriques; i++) {
				if(listeMetriques.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element metrique = (Element) listeMetriques.item(i);
					
					/*
					 * Construction d'une métrique
					 */
					
					String intituleCritere = metrique.getElementsByTagName("Critere").item(0).getTextContent();
					Critere critere = ((CriteresDeSecurite) this.etude.getModule("CriteresDeSecurite")).getCritere(intituleCritere);
					
					ArrayList<NiveauDeMetrique> lesNiveaux = new ArrayList<NiveauDeMetrique>();
					
					final Element niveaux = (Element) metrique.getElementsByTagName("Niveaux").item(0);
					final NodeList listeNiveaux = niveaux.getChildNodes();
					final int nbNiveaux = listeNiveaux.getLength();					
					
					for (int j = 0; j < nbNiveaux; j++){
						if(listeNiveaux.item(j).getNodeType() == Node.ELEMENT_NODE) {
							final Element niveau = (Element) listeNiveaux.item(j);
							int numero = Integer.parseInt(niveau.getElementsByTagName("Numero").item(0).getTextContent());
							String intitule = niveau.getElementsByTagName("Intitule").item(0).getTextContent();
							String description = niveau.getElementsByTagName("Description").item(0).getTextContent();
							
							lesNiveaux.add(new NiveauDeMetrique(numero, intitule, description));
						}
					}					
					
					Metrique m = new Metrique(critere, lesNiveaux);
					
					/*
					 * Ajout de la métrique à la bdc
					 */
					
					bdcMetriques.put(intituleCritere, m);				}				
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
		
		ArrayList<NiveauDeMetrique> niveauxConfidentialite = new ArrayList<NiveauDeMetrique>();
		niveauxConfidentialite.add(new NiveauDeMetrique(1, "Libre", "Aucune mesure particuliere ne doit etre mise en oeuvre"));
		niveauxConfidentialite.add(new NiveauDeMetrique(2, "Entreprise", "La connaissance s'organise sur un perimetre d'acces a la maille d'ERDF."));
		niveauxConfidentialite.add(new NiveauDeMetrique(3, "Restreint", "La connaissance est limite a des personnes, fonctions ou a un perimetre restreint lie a une activite."));
		niveauxConfidentialite.add(new NiveauDeMetrique(4, "Confidentiel", "Ne doit etre connu que par des personnes nommement designees et autorisees a cet effet."));
		Metrique metriqueConfidentialite = new Metrique(cds.getCritere("Confidentialite"), niveauxConfidentialite);
		
		ArrayList<NiveauDeMetrique> niveauxDisponibilite = new ArrayList<NiveauDeMetrique>();
		niveauxDisponibilite.add(new NiveauDeMetrique(1, "Libre", "Aucune mesure particuliere ne doit etre mise en oeuvre"));
		niveauxDisponibilite.add(new NiveauDeMetrique(2, "Entreprise", "La connaissance s'organise sur un perimetre d'acces a la maille d'ERDF."));
		niveauxDisponibilite.add(new NiveauDeMetrique(3, "Restreint", "La connaissance est limite a des personnes, fonctions ou a un perimetre restreint lie a une activite."));
		niveauxDisponibilite.add(new NiveauDeMetrique(4, "Confidentiel", "Ne doit etre connu que par des personnes nommement designees et autorisees a cet effet."));
		Metrique metriqueDisponibilite = new Metrique(cds.getCritere("Disponibilite"), niveauxDisponibilite);
		
		ArrayList<NiveauDeMetrique> niveauxGravite = new ArrayList<NiveauDeMetrique>();
		niveauxGravite.add(new NiveauDeMetrique(1, "Pas grave", "YOLO"));
		niveauxGravite.add(new NiveauDeMetrique(2, "Un peu grave", "Mince"));
		niveauxGravite.add(new NiveauDeMetrique(3, "Ca commence à être la merde", "Saloperie"));
		niveauxGravite.add(new NiveauDeMetrique(4, "On est foutus", "Suicide collectif"));
		Metrique metriqueGravite = new Metrique(new Critere("Gr", "Gravité", "" ), niveauxGravite);
		
				
		bdcMetriques.put("Confidentialite", metriqueConfidentialite);
		bdcMetriques.put("Disponibilite", metriqueDisponibilite);
		bdcMetriques.put("Gravité", metriqueGravite);
		
	}
		
	public Hashtable<String, Metrique> getBDC(){
		return bdcMetriques;
	}

	public int nombreDeMetriques() {
		return getLesMetriques().size();
	}

	/**
	 * Permet d'acceder a une metrique par sa position dans la hashtable
	 * @param index
	 * @return
	 */
	public Metrique getMetrique(int rowIndex) {
		return (Metrique) getLesMetriques().values().toArray()[rowIndex];
	}
	
	public Metrique getVraisemblance(){
		return getMetrique("Vraisemblance");
	}
	
	public Metrique getGravite(){
		return getMetrique("Gravité");
	}
	
	public void setVraisemblance(Metrique vraisemblance){
		this.vraisemblance = vraisemblance;
	}
	
	public void setGravite(Metrique gravite){
		this.gravite = gravite;
	}
	
	public ArrayList<Metrique> getMetriquesDesCriteresRetenus(){
		ArrayList<Metrique> resultat = new ArrayList<Metrique>();
		for(Critere c : ((CriteresDeSecurite) this.getEtude().getModule("CriteresDeSecurite")).getLesCriteres().values() ){
			if(c.isRetenu()){
				if(this.bdcMetriques.get(c.getIntitule()) != null){
					resultat.add(this.bdcMetriques.get(c.getIntitule()));
				}
				else{
					resultat.add(new Metrique(c));
				}
			}			
		}
		resultat.add(this.bdcMetriques.get("Gravité"));
		return resultat;
	}
	
	public String toString(){
		return "Métriques";
	}	
	
	public boolean estCoherent(){
		boolean resultat = true;
		
		this.problemesDeCoherence = new ArrayList<JLabel>();
		for(Metrique m : this.getMetriquesDesCriteresRetenus()){
			if(!m.estComplet()){
				JLabel label = new JLabel("La métrique \" " + m.getIntitule() + " \" est incomplète.");
				label.setForeground(Color.red);
				this.problemesDeCoherence.add(label);
				resultat = false;
			}			
		}
		if(this.getMetrique("Gravité") == null){
			JLabel label = new JLabel("Il faut une métrique \" Gravité \".");
			label.setForeground(Color.red);
			this.problemesDeCoherence.add(label);
			resultat = false;
		}
		if(this.getMetrique("Vraisemblance") == null){
			JLabel label = new JLabel("Il faut une métrique \" Vraisemblance \".");
			label.setForeground(Color.red);
			this.problemesDeCoherence.add(label);
			resultat = false;
		}		
		if(resultat == true){
			this.problemesDeCoherence.add(new JLabel("Aucun problème de cohérence."));
		}
		return resultat;
	}
	
}
