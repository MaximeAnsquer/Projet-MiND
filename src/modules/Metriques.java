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
 * Cette classe est un peu pipeau (elle a même pas de variable d'instance );
 * le seul truc qu'elle fait c'est initialiser les
 * métriques des critères retenus dans son constructeur.
 */

public class Metriques extends Module {
	
	//--L'objet unique qui sera accessible de partout--
	
	private static Metriques instance = new Metriques();

	//---Variables d'instance---


	//---Constructeurs---
	
	/**
	 * Initialise les métriques des critères retenus en copiant les métriques de la BDC métriques
	 * pour les critères retenus et présent dans la BDC métriques, et en créant des métriques vides
	 * pour les critères retenus mais non présents dans la BDC.
	 */
	private Metriques() {
		super("Métriques");
		this.predecesseurs.add(CriteresDeSecurite.getInstance());
		this.successeurs.add(ScenariosDeMenacesTypes.getInstance());
		this.successeurs.add(AnalyseDesRisques.getInstance());
		this.successeurs.add(MatriceDesRisques.getInstance());
		
		Hashtable<String,Critere> criteresRetenus = CriteresDeSecurite.getInstance().getCriteresRetenus();
		BDCMetriques bdcMetriques = BDCMetriques.getInstance();
		for(Critere critere : criteresRetenus.values()){	//pour chaque critère retenu 
			if(bdcMetriques.contient(critere)){	  //si son intitulé figure dans le template de l'onglet BDC métriques	
				critere.setMetrique(bdcMetriques.getMetrique(critere));   //on associe la métrique correspondante de la BDC au critère
			}
			else{
				critere.setMetrique(new Metrique());	//sinon, on lui associe une métrique vide à 4 lignes	
			}
		}
	}

	//---Getters et setters---

	
	//--Services--
	
	public static Metriques getInstance(){
		return instance;
	}

}











