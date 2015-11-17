package modules;
import java.util.ArrayList;
import java.util.Hashtable;
import autres.*;
import bdc.BDCCriteresDeSecurite;

/**
 * En fait d'un point de vue du code cette classe est exactement la même que la classe
 * BDCCriteresDeSecurite, mais d'un point de vue logique c'est pas la même chose donc je
 * préfère faire deux classes distinctes.
 * @author Maxime Ansquer 
 */

public class CriteresDeSecurite extends Module{
	
	//---L'objet unique qui sera accessible partout---
	
	private static CriteresDeSecurite instance = new CriteresDeSecurite();
	
	//---Variables d'instance
	
	private Hashtable<String,Critere> lesCriteres;	
	
		//UneHashtable permet d'acceder a un critere en fournissant seulement
		//son nom : top cool !
	
	//---Constructeurs---	
	
	/** 
	 * Initialise le module en copiant la BDC Critères de sécurité
	 * @author Maxime Ansquer
	 */
	private CriteresDeSecurite() {
		super("Critères de sécurité");
		this.lesCriteres = BDCCriteresDeSecurite.getInstance().getLesCriteres();
		this.successeurs.add(Metriques.getInstance());
		this.successeurs.add(ScenariosDeMenacesTypes.getInstance());
		this.successeurs.add(AnalyseDesRisques.getInstance());
		this.successeurs.add(MatriceDesRisques.getInstance());
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
	
	//---Services---
	
	public void ajouterCritere(Critere critere){
		this.getLesCriteres().put(critere.getIntitule(), critere);
	}
	
	public void supprimerCritere(String nomCritere){
		this.getLesCriteres().remove(nomCritere);
	}	
	
	/**
	 * @author Maxime Ansquer
	 * @return Renvoie une Hashtable des critères retenus par l'utilisateur
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
	
	/** 
	 * Indiquer que l'utilisateur a retenu (coché) le critère.
	 * @author Maxime Ansquer
	 * @param intituleCritere L'intitule du critère que l'on souhaite déclaré retenu.
	 */
	public void retenirCritere(String intituleCritere){
		this.getCritere(intituleCritere).setRetenu(true);
	}
	
	/** 
	 * Indiquer que l'utilisateur a retenu (coché) le critère.
	 * @author Maxime Ansquer
	 * @param critere L'intitule du critère que l'on souhaite déclaré retenu.
	 */
	public void retenirCritere(Critere critere){
		this.retenirCritere(critere.getIntitule());
	}
	
	public static CriteresDeSecurite getInstance(){
		return instance;
	}
	
}
