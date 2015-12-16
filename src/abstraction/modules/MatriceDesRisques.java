package abstraction.modules;

import java.util.ArrayList;

import abstraction.Etude;

public class MatriceDesRisques extends Module {
	
	/*Ce module synt�tise l'analyse effectu�e en ce sens qu'elle ordonne diff�rents "risques" tels qu'ils ont �t� d�finis 
	 * dans le module AnalyseDesRisques.
	 * Les intitul�s de ces risques sont plac�s dans un tableau; en colonne sont pr�cis�s les niveaux de vraisemblance,
	 * en ligne les niveaux de gravit� que ces risques induisent.
	 */
	

	
	/*les variables d'instance qui repr�sentent les ant�c�dents du module Matrice des risques et dont le module a 
	 * par cons�quent besoin pour fonctionner
	 */
	private Etude etude;
	
	private ArrayList<String>[][] matrice; 
	private AnalyseDesRisques analysedesrisques;
	
	/*Constructeur
	 * 
	 */
	
	public MatriceDesRisques(Etude etude){
	    
		super("MatriceDesRisques");
		this.etude=etude;
		this.analysedesrisques=(AnalyseDesRisques)this.etude.getModule("AnalyseDesRisques");
		this.successeurs=null;
		this.predecesseurs.add(this.etude.getModule("AnalyseDesRisques"));
		this.coherent=true;
		this.cree=true;
		this.disponible=true;
		int a=((Metriques)this.etude.getModule("Metriques")).getGravite().nombreDeNiveaux();
		int b=((Metriques)this.etude.getModule("M�triques")).getVraisemblance().nombreDeNiveaux();
		this.matrice=new ArrayList[a][b];
		
		for (int i=0;i<a;i++){
			for (int j=0;j<b;j++){
				for (int k=0;k<this.analysedesrisques.getAnalyseDesRisques().size();k++){
					if(this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauGravite()==i && this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauVraisemblance()==j){
		this.matrice[i][j].add(this.analysedesrisques.getAnalyseDesRisques().get(k).getIntitule());
					}
				}
			}
		}
	
			
		}
	
	/*getters
	 * 
	 */
	
	
	
	public ArrayList<String>[][] getMatrice(){
		return this.matrice;
	}

}
