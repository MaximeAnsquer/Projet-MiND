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
	
	private int nombreniveaux;
	
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
		//this.checkDisponible();
		
		if(this.analysedesrisques.estCoherent()==true){
		
		int a=((Metriques)this.etude.getModule("Metriques")).getMetrique("Gravite").nombreDeNiveaux();
		int b=((Metriques)this.etude.getModule("Metriques")).getVraisemblance().nombreDeNiveaux();
		
		this.nombreniveaux=b;
		
		this.matrice=new ArrayList[a][b+1];
		
		
		
		for (int i=1;i<=a;i++){
			for (int j=1;j<=b;j++){
				for (int k=0;k<this.analysedesrisques.getAnalyseDesRisques().size();k++){
					if(this.analysedesrisques.getAnalyseDesRisques().get(k).getRetenu()==true&&this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauGravite()==i && this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauVraisemblance()==j){
						this.matrice[a-i][j]=new ArrayList<String>();
		this.matrice[a-i][j].add(this.analysedesrisques.getAnalyseDesRisques().get(k).getIntitule());
		System.out.println(i);
		System.out.println(j);
		System.out.println(this.analysedesrisques.getAnalyseDesRisques().get(k).getIntitule());
		
					}
				}
			}
			
		}
			
		
	for (int i=1;i<=a;i++){
		this.matrice[a-i][0]=new ArrayList<String>();
		this.matrice[a-i][0].add(i+"");
	}
	
	
}
			
	else{this.matrice=new ArrayList[1][1];
	this.matrice[0][0]=new ArrayList<String>();
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
	
	
	public int nombre(){
		return this.nombreniveaux;
	}
}
