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
 * Cette classe est un peu pipeau (elle a m�me pas de variable d'instance );
 * le seul truc qu'elle fait c'est initialiser les
 * m�triques des crit�res retenus dans son constructeur.
 */

public class Metriques extends Module {
	
	//--L'objet unique qui sera accessible de partout--
	
	private static Metriques instance = new Metriques();

	//---Variables d'instance---


	//---Constructeurs---
	
	/**
	 * Initialise les m�triques des crit�res retenus en copiant les m�triques de la BDC m�triques
	 * pour les crit�res retenus et pr�sent dans la BDC m�triques, et en cr�ant des m�triques vides
	 * pour les crit�res retenus mais non pr�sents dans la BDC.
	 */
	private Metriques() {
		super("M�triques");
		this.predecesseurs.add(CriteresDeSecurite.getInstance());
		this.successeurs.add(ScenariosDeMenacesTypes.getInstance());
		this.successeurs.add(AnalyseDesRisques.getInstance());
		this.successeurs.add(MatriceDesRisques.getInstance());
		
		Hashtable<String,Critere> criteresRetenus = CriteresDeSecurite.getInstance().getCriteresRetenus();
		BDCMetriques bdcMetriques = BDCMetriques.getInstance();
		for(Critere critere : criteresRetenus.values()){	//pour chaque crit�re retenu 
			if(bdcMetriques.contient(critere)){	  //si son intitul� figure dans le template de l'onglet BDC m�triques	
				critere.setMetrique(bdcMetriques.getMetrique(critere));   //on associe la m�trique correspondante de la BDC au crit�re
			}
			else{
				critere.setMetrique(new Metrique());	//sinon, on lui associe une m�trique vide � 4 lignes	
			}
		}
	}

	//---Getters et setters---

	
	//--Services--
	
	public static Metriques getInstance(){
		return instance;
	}

}











