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
		super("Criteres de securite");
		this.successeurs.add(this.getEtude().getModule("ScenariosDeMenaceTypes"));
		this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
		this.successeurs.add(this.getEtude().getModule("MatriceDesRisques"));
		this.successeurs.add(this.getEtude().getModule("MatricDesRisques"));
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
		
		//valeurs fictives pour faire des tests :
		bdcCriteresDeSecurite.put("Disponibilité", new Critere("D","Disponibilité","Durée maximale admissible pour la réalisation d’un processus métier."));
		bdcCriteresDeSecurite.put("Intégrité", new Critere("I","Intégrité","Altération admissible et/ou mesures de récupération des données liées à mettre en oeuvre pour un processus métier."));
		bdcCriteresDeSecurite.put("Confidentialité", new Critere("C","Confidentialité","Diffusabilité des informations liées à un processus métier."));

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
