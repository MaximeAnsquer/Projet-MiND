package abstraction.modules;

import java.util.ArrayList;

import abstraction.Etude;

public class MatriceDesRisques extends Module {
	
	/*Ce module synt�tise l'analyse effectu�e en ce sens qu'elle ordonne diff�rents "risques" tels qu'ils ont �t� d�finis 
	 * dans le module AnalyseDesRisques.
	 * Les intitul�s de ces risques sont plac�s dans un tableau; en colonne sont pr�cis�s les niveaux d'exigence,
	 * en ligne les niveaux de gravit� que ces risques induisent.
	 */
	

	
	/*les variables d'instance qui repr�sentent les ant�c�dents du module Matrice des risques et dont le module a 
	 * par cons�quent besoin pour fonctionner
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
		this.matrice=new ArrayList[this.analyse.getLesModules().get("M�triques").getLesMetriques().get("Gravit�").getSize()][this.analyse.getLesModules().get("M�triques").getLesMetriques().get("Exigence").getSize()];
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
