package modules;
import java.util.Hashtable;

import autres.*;
import bdc.*;

/** 
 * Cahier des charges, page 5 :
 * 
 * " Métriques : cet onglet permet la définition des échelles ordinales associées
 *   aux différents critères de sécurité retenus (par exemple : définition d’une 
 *   échelle à quatre niveaux pour le critère de disponibilité). "
 *   
 * Le module Metrique est ici représenté par un ensemble (Hashtable indexée par le nom 
 * du critère associé à la métrique) d'objets Metrique.
 * @author Maxime Ansquer
 */

public class Metriques extends Module {
	
	//--L'objet unique qui sera accessible de partout--
	
	public static Metriques metriques;

	//---Variables d'instance---

	private Hashtable<String,Metrique> lesMetriques;

	//---Constructeurs---
	
	/**
	 * Initialise le module en copiant les métriques de la BDC métriques
	 * pour les critères retenus et présent dans la BDC métriques, 
	 * et en créant des métriques vides pour les critères retenus mais non
	 * présents dans la BDC.
	 */
	public Metriques() {
		super("Métriques");
		this.lesMetriques = new Hashtable<String,Metrique>();
		Hashtable<String,Critere> criteresRetenus = CriteresDeSecurite.criteresDeSecurite.getCriteresRetenus();
		BDCMetriques bdcMetriques = BDCMetriques.bdcMetriques;
		for(Critere critere : criteresRetenus.values()){	//pour chaque critère retenu 
			if(bdcMetriques.contient(critere)){	//si son intitulé figure dans le template de l'onglet BDC métriques	
				this.lesMetriques.put(critere.getIntitule(), bdcMetriques.getMetrique(critere));	//on ajoute la métrique correspondante
			}
			else{
				this.lesMetriques.put(critere.getIntitule(), new Metrique(critere));	//sinon, on génère un tableau vide à 4 lignes	
			}
		}
	}

	//---Getters et setters---

	public Hashtable<String, Metrique> getLesMetriques() {
		return lesMetriques;
	}

	public void setLesMetriques(Hashtable<String, Metrique> lesMetriques) {
		this.lesMetriques = lesMetriques;
	}
	
	//--Services--
	
	/** 
	 * @param intituleCritere Le critère dont on souhaite avoir la métrique.
	 * @return Renvoie la métrique associée au critère.
	 */
	public Metrique getMetrique(String intituleCritere){
		return this.getLesMetriques().get(intituleCritere);
	}
	
	/** 
	 * @param critere Le critère dont on souhaite avoir la métrique.
	 * @return Renvoie la métrique associée au critère.
	 */
	public Metrique getMetrique(Critere critere){
		return this.getMetrique(critere.getIntitule());
	}

}











