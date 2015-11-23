package abstraction.modules;

import java.util.ArrayList;

import abstraction.Etude;

public class MatriceDesRisques extends Module {
	
	/*Ce module syntétise l'analyse effectuée en ce sens qu'elle ordonne différents "risques" tels qu'ils ont été définis 
	 * dans le module AnalyseDesRisques.
	 * Les intitulés de ces risques sont placés dans un tableau; en colonne sont précisés les niveaux d'exigence,
	 * en ligne les niveaux de gravité que ces risques induisent.
	 */
	

	
	/*les variables d'instance qui représentent les antécédents du module Matrice des risques et dont le module a 
	 * par conséquent besoin pour fonctionner
	 */
	private Etude analyse;
	
	private ArrayList<String>[][] matrice; 
	private AnalyseDesRisques analysedesrisques;
	
	/*Constructeur
	 * 
	 */
	
	public MatriceDesRisques(){
		super("Matrice des Risques");
		this.successeurs=null;
		this.predecesseurs.add(AnalyseDesRisques.getInstance());
		this.coherent=false;
		this.cree=false;
		this.disponible=false;
		this.matrice=new ArrayList[this.analyse.getLesModules().get("Métriques").getLesMetriques().get("Gravité").getSize()][this.analyse.getLesModules().get("Métriques").getLesMetriques().get("Exigence").getSize()];
	}
	
	/*getters
	 * 
	 */
	
	public MatriceDesRisques getInstance(){
		return instance;
	}
	
	public ArrayList<String>[][] getMatrice(){
		return this.matrice;
	}

}
