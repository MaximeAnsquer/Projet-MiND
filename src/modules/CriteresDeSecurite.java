package modules;
import java.util.Hashtable;
import autres.*;

/**
 * Maxime A.
 * 
 * En fait d'un point de vue du code cette classe est exactement la même que la classe
 * BDCCriteresDeSecurite, mais d'un point de vue logique c'est pas la même chose donc je
 * préfère faire deux classes distinctes.
 * 
 */

public class CriteresDeSecurite{
	
	//---Variables d'instance
	
	private Hashtable<String,Critere> lesCriteres;	
	
		//UneHashtable permet d'acceder a un critere en fournissant seulement
		//son nom : top cool !
	
	//---Constructeurs---
	
	public CriteresDeSecurite(){
		this.lesCriteres = new Hashtable<String,Critere>();
	}
	
	public CriteresDeSecurite(Hashtable<String,Critere> lesCriteres) {
		this.lesCriteres = lesCriteres;
	}

	//---Getters et setters---
	
	public Hashtable<String,Critere> getLesCriteres() {
		return lesCriteres;
	}

	public void setLesCriteres(Hashtable<String,Critere> lesCriteres) {
		this.lesCriteres = lesCriteres;
	}
	
	public Critere getCritere(String nomCritere){
		return this.lesCriteres.get(nomCritere);
	}
	
	//---Services---
	
	public void ajouterCritere(Critere c){
		this.lesCriteres.put(c.getNom(), c);
	}
	
	public void supprimerCritere(String nomCritere){
		this.lesCriteres.remove(nomCritere);
	}	
	
	public Hashtable<String,Critere> getCriteresRetenus(){
		Hashtable<String,Critere> resultat = new Hashtable<String,Critere>();
		for(Critere c : this.getLesCriteres().values()){
			if(c.isRetenu()){
				resultat.put(c.getNom(), c);
			}
		}
		return resultat;
	}
	
}
