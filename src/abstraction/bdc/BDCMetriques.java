package abstraction.bdc;
import java.util.Hashtable;

import abstraction.autres.*;
import abstraction.modules.Module;

/** 
 * @author Maxime Ansquer
 */
public class BDCMetriques {
	
	//---L'objet unique qui sera accessible de partout---
	
	private static BDCMetriques instance = new BDCMetriques();	
	
	//---Variables d'instance---

	private Hashtable<String,Metrique> lesMetriques;
	
	//---Constructeurs---
	
	private BDCMetriques(){
		this.lesMetriques = new Hashtable<String,Metrique>();
		//TODO : importer les métriques d'une base de donnée persistante
	}
	
	//---Getters et setters	
	
	public Hashtable<String,Metrique> getLesMetriques() {
		return this.lesMetriques;
	}	

	public void setLesMetriques(Hashtable<String, Metrique> lesMetriques) {
		this.lesMetriques = lesMetriques;
	}	
	
	//---Services---
	
	/** 
	 * @param critere Le critère dont on veut savoir s'il est contenu dans la BDC métriques.
	 * @return Indique si la BDC contient une metrique associée au critere passé en argument.
	 */
	public boolean contient(Critere critere) {
		return this.getLesMetriques().containsKey(critere.getIntitule());
	}
	
	/** 
	 * @param critere Le critère dont ont cherche la métrique.
	 * @return Renvoie la métrique correspondant au critère passé en argument.
	 */
	public Metrique getMetrique(Critere critere) {
		return this.getLesMetriques().get(critere.getIntitule());
	}
	
	
	public static BDCMetriques getInstance(){
		return instance;
	}



	

}
