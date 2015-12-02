package abstraction.modules;
import java.util.ArrayList;
import java.util.Hashtable;
import abstraction.autres.*;

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
	private static Hashtable<String,Critere> bdcCriteresDeSecurite;
	
	//---Variables d'instance---
	
	/**
	 * Hashtable reference par l'intitule des criteres
	 */
	private Hashtable<String,Critere> lesCriteres;	
		
	//---Constructeurs---	
	
	/** 
	 * Initialise le module en commencant par initialiser la BDC, puis en copiant les valeurs
	 * de la BDC dans le module.
	 */
	public CriteresDeSecurite() {
		super("CriteresDeSecurite");		
		/**
		 * TODO : comment√e pour faire des tests, e† d√ecommenter apres
		 * 
		 * this.successeurs.add(this.getEtude().getModule("ScenariosDeMenaceTypes"));
		 * this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
		 * this.successeurs.add(this.getEtude().getModule("MatriceDesRisques"));
		 * this.successeurs.add(this.getEtude().getModule("MatricDesRisques"));
		 */
		
		this.cree = false;
		this.coherent = false;
		this.disponible = true;
		
		this.importerBDC();  //on remplit la BDC
		this.lesCriteres = bdcCriteresDeSecurite;  //on initialise l'onglet avec les valeurs de la BDC
		
	}

	//---Getters et setters---
	
	public Hashtable<String,Critere> getLesCriteres() {
		return lesCriteres;
	}

	public void setLesCriteres(Hashtable<String,Critere> lesCriteres) {
		this.lesCriteres = lesCriteres;
	}
	
	public Critere getCritere(String intituleCritere){
		return this.lesCriteres.get(intituleCritere);
	}
	
	public static Hashtable<String,Critere> getBDC(){
		return bdcCriteresDeSecurite;
	}
	
	//---Services---	

	private void importerBDC() {
		// TODO remplit la hashtable bdcCriteresDeSecurite avec les valeurs fournies par le client (fichier excel)
		bdcCriteresDeSecurite = new Hashtable<String,Critere>();
		//valeurs fictives pour faire des tests :
		bdcCriteresDeSecurite.put("Disponibilite", new Critere("D","Disponibilite","Duree maximale admissible pour la realisation d'Äun processus metier."));
		bdcCriteresDeSecurite.put("Integrite", new Critere("I","Integrite","Alteration admissible et/ou mesures de recuperation des donnes liees a† mettre en oeuvre pour un processus metier."));
		bdcCriteresDeSecurite.put("Confidentialite", new Critere("C","Confidentialite","Diffusabilite des informations liees a† un processus metier."));

	}

	
	public void ajouterCritere(Critere critere){
		this.getLesCriteres().put(critere.getIntitule(), critere);
	}
	
	public void supprimerCritere(String nomCritere){
		this.getLesCriteres().remove(nomCritere);
	}	
	
	/**
	 * @author Maxime Ansquer
	 * @return Renvoie une Hashtable des criteres retenus par l'utilisateur
	 */
	public Hashtable<String,Critere> getCriteresRetenus(){
		Hashtable<String,Critere> resultat = new Hashtable<String,Critere>();
		for(Critere c : this.getLesCriteres().values()){
			if(c.isRetenu()){
				resultat.put(c.getIntitule(), c);
			}
		}
		return resultat;
	}
	
}
