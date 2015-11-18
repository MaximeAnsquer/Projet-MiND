/**  
 * En fait d'un point de vue du code cette classe est exactement la même que la classe
 * CriteresDeSecurite, mais d'un point de vue logique c'est pas la même chose donc je
 * préfère faire deux classes distinctes.
 * @author Maxime Ansquer
 */

package abstraction.bdc;
import java.util.Hashtable;

import abstraction.autres.*;

public class BDCCriteresDeSecurite{
	
	//--L'unique instance qui sera accessible de partout--
	
	private static BDCCriteresDeSecurite instance = new BDCCriteresDeSecurite();  //à voir, peut-être qu'il sera plus pratique d'utiliser l'autre constructeur
	
	//---Variables d'instance
	
	private Hashtable<String,Critere> lesCriteres;	
	
		//UneHashtable permet d'acceder a un critere en fournissant seulement
		//son nom : top cool !
	
	//---Constructeurs---
	
	private BDCCriteresDeSecurite(){
		this.lesCriteres = new Hashtable<String,Critere>();
	}
	
	private BDCCriteresDeSecurite(Hashtable<String,Critere> lesCriteres) {
		this.lesCriteres = lesCriteres;
	}

	//---Getters et setters---
	
	public Hashtable<String,Critere> getLesCriteres() {
		return lesCriteres;
	}

	public void setLesCriteres(Hashtable<String,Critere> lesCriteres) {
		this.lesCriteres = lesCriteres;
	}
	
	//---Services---	

	public Critere getCritere(String nomCritere){
		return this.getLesCriteres().get(nomCritere);
	}
	
	public void ajouterCritere(Critere c){
		this.getLesCriteres().put(c.getIntitule(), c);
	}
	
	public void supprimerCritere(String nomCritere){
		this.getLesCriteres().remove(nomCritere);
	}	
	
	public static BDCCriteresDeSecurite getInstance(){
		return instance;
	}
	
}
