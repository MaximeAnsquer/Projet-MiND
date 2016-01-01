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
		
		this.coherent=false;
		this.cree=false;
		this.checkDisponible();
		
		if(this.analysedesrisques.estCoherent()==true){
		
		int a=((Metriques)this.etude.getModule("Metriques")).getMetrique("Gravite").nombreDeNiveaux();
		int b=((Metriques)this.etude.getModule("Metriques")).getVraisemblance().nombreDeNiveaux();
		this.matrice=new ArrayList[a][b];
		
		for (int i=1;i<a;i++){
			for (int j=1;j<b;j++){
				for (int k=0;k<this.analysedesrisques.getAnalyseDesRisques().size();k++){
					if(this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauGravite()==i && this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauVraisemblance()==j){
		this.matrice[i][j].add(this.analysedesrisques.getAnalyseDesRisques().get(k).getIntitule());
					}
				}
			}
		
		
		
		
			
		}
			
			
		
	for (int i=1;i<a;i++){
		this.matrice[i][0].add((i+1)+"");
	}
	for (int i=1;i<b;i++){
		this.matrice[0][i].add((i+1)+"");
	}
	
	
	this.matrice[0][0].add("Gravit�");
	this.matrice[0][0].add("Vraisemblance");
}
			
	else{this.matrice=new ArrayList[1][1];
	this.matrice[0][0].add("");
	
		
	}
		}
	
	/*getters
	 * 
	 */
	
	
	
	public ArrayList<String>[][] getMatrice(){
		return this.matrice;
	}
	
	public String toString(){
		return "Matrice des risques";
	}

	public boolean  estCoherent(){
		if (this.matrice.length!=1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void checkDisponible(){
		if(this.analysedesrisques.estCoherent()==true){
			this.disponible=true;
		}
		else{
			this.disponible=false;
		}
	}
	
}
