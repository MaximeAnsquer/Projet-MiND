package modules;
import java.util.Hashtable;

import autres.*;
import bdc.*;

/** 
 * Cahier des charges, page 5 :
 * 
 * " M�triques : cet onglet permet la d�finition des �chelles ordinales associ�es
 *   aux diff�rents crit�res de s�curit� retenus (par exemple : d�finition d�une 
 *   �chelle � quatre niveaux pour le crit�re de disponibilit�). "
 *   
 * Le module Metrique est ici repr�sent� par un ensemble (Hashtable index�e par le nom 
 * du crit�re associ� � la m�trique) d'objets Metrique.
 * @author Maxime Ansquer
 */

public class Metriques extends Module {
	
	//--L'objet unique qui sera accessible de partout--
	
	public static Metriques metriques;

	//---Variables d'instance---

	private Hashtable<String,Metrique> lesMetriques;

	//---Constructeurs---
	
	/**
	 * Initialise le module en copiant les m�triques de la BDC m�triques
	 * pour les crit�res retenus et pr�sent dans la BDC m�triques, 
	 * et en cr�ant des m�triques vides pour les crit�res retenus mais non
	 * pr�sents dans la BDC.
	 */
	public Metriques() {
		super("M�triques");
		this.lesMetriques = new Hashtable<String,Metrique>();
		Hashtable<String,Critere> criteresRetenus = CriteresDeSecurite.criteresDeSecurite.getCriteresRetenus();
		BDCMetriques bdcMetriques = BDCMetriques.bdcMetriques;
		for(Critere critere : criteresRetenus.values()){	//pour chaque crit�re retenu 
			if(bdcMetriques.contient(critere)){	//si son intitul� figure dans le template de l'onglet BDC m�triques	
				this.lesMetriques.put(critere.getIntitule(), bdcMetriques.getMetrique(critere));	//on ajoute la m�trique correspondante
			}
			else{
				this.lesMetriques.put(critere.getIntitule(), new Metrique(critere));	//sinon, on g�n�re un tableau vide � 4 lignes	
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
	 * @param intituleCritere Le crit�re dont on souhaite avoir la m�trique.
	 * @return Renvoie la m�trique associ�e au crit�re.
	 */
	public Metrique getMetrique(String intituleCritere){
		return this.getLesMetriques().get(intituleCritere);
	}
	
	/** 
	 * @param critere Le crit�re dont on souhaite avoir la m�trique.
	 * @return Renvoie la m�trique associ�e au crit�re.
	 */
	public Metrique getMetrique(Critere critere){
		return this.getMetrique(critere.getIntitule());
	}

}











