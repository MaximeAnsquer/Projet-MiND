package abstraction.modules;

import java.util.ArrayList;

import abstraction.Etude;

public class MatriceDesRisques extends Module {
	
	/*Ce module syntétise l'analyse effectuée en ce sens qu'elle ordonne différents "risques" tels qu'ils ont été définis 
	 * dans le module AnalyseDesRisques.
	 * Les intitulés de ces risques sont placés dans un tableau; en colonne sont précisés les niveaux de vraisemblance,
	 * en ligne les niveaux de gravité que ces risques induisent.
	 */
	

	
	/*les variables d'instance qui représentent les antécédents du module Matrice des risques et dont le module a 
	 * par conséquent besoin pour fonctionner
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
		int b=((Metriques)this.etude.getModule("Métriques")).getVraisemblance().nombreDeNiveaux();
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
