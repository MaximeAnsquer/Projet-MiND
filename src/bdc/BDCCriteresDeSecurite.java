/**
 * Maxime A.
 * 
 * En fait d'un point de vue du code cette classe est exactement la même que la classe
 * CriteresDeSecurite, mais d'un point de vue logique c'est pas la même chose donc je
 * préfère faire deux classes distinctes.
 * 
 */

package bdc;
import java.util.Hashtable;
import autres.*;

public class BDCCriteresDeSecurite{
	
	//---Variables d'instance
	
	private Hashtable<String,Critere> lesCriteres;	
	
		//UneHashtable permet d'acceder a un critere en fournissant seulement
		//son nom : top cool !
	
	//---Constructeurs---
	
	public BDCCriteresDeSecurite(){
		this.lesCriteres = new Hashtable<String,Critere>();
	}
	
	public BDCCriteresDeSecurite(Hashtable<String,Critere> lesCriteres) {
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
	
}
