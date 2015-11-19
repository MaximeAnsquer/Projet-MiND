package abstraction.modules;
import java.util.Hashtable;

import abstraction.autres.*;
import abstraction.bdc.*;
import asbtraction.Analyse;

/** 
 * Cahier des charges, page 5 :
 * 
 * " Metriques : cet onglet permet la définition des echelles ordinales associees
 *   aux differents criteres de securite retenus (par exemple : définition d’une 
 *   echelle à quatre niveaux pour le critère de disponibilite). "
 *   
 * Cette classe modelise le module " Metriques ". Elle est constituee d'une hastable d'objets Metrique
 * indexee par le nom du critere associe (on rappelle qu'un objet Metrique est constitue d'un critere
 * et d'un tableau des differents niveaux de l'echelle).
 */

public class Metriques extends Module {
		
	//--L'instance representant la BDC---
	
	private static Metriques bdc = new Metriques(true);

	//---Variables d'instance---
	
	private Hashtable<String, Metrique> lesMetriques;

	//---Constructeurs---	
	
	/**
	 * Constructeur qui sert a creer soit la BDC Metriques, soit le module Metriques lui-meme.
	 * @param bdc Indique si on construit une BDC ou non (si non, on construit le module).
	 */
	public Metriques(boolean creationBdc) {
		super("Métriques");
		if(bdc){  //si on construit une bdc			
			this.lesMetriques = new Hashtable<String, Metrique>();
			//TODO Quand on aura le fichier excel, c'est la qu'on ajoutera les Metriques par defaut de la BDC Metrique
		}
		else{	//si on ne construit pas une bdc, mais bien le module lui meme
			
			this.predecesseurs.add(CriteresDeSecurite.getInstance());
			this.successeurs.add(ScenariosDeMenacesTypes.getInstance());
			this.successeurs.add(AnalyseDesRisques.getInstance());
			this.successeurs.add(MatriceDesRisques.getInstance());		
			
			this.lesMetriques = getBDC().getLesMetriques();  //on importe toutes les metriques de la bdc
			for(Metrique metrique : this.lesMetriques.values()){
				if(!metrique.getCritere().isRetenu()){	//on enleve toutes celles qui correspondent a des criteres non retenus.
					this.lesMetriques.remove(metrique.getCritere().getIntitule());    
				}
			}			
		}		
	}

	//---Getters et setters---
	
	public Hashtable<String, Metrique> getLesMetriques(){
		return this.lesMetriques;
	}
	
	public void setLesMetriques(Hashtable<String, Metrique> lesMetriques ){
		this.lesMetriques = lesMetriques;
	}
	
	//--Services--		
	
	private boolean contient(Critere critere) {
		return this.getLesMetriques().contains(critere.getIntitule()); //TODO
	}
	
	public static Metriques getBDC(){
		return bdc;
	}
	
	public void ajouterMetrique(Metrique metrique){
		this.getLesMetriques().put(metrique.getCritere().getIntitule(), metrique);
	}

}
