package abstraction.modules;

import java.util.ArrayList;

import abstraction.Etude;
import presentation.ModeleMatriceDesRisques;

public class MatriceDesRisques extends Module {
	
	/*Ce module synthétise l'etude effectuée en ce sens qu'elle ordonne différents "risques" tels qu'ils ont été définis 
	 * dans le module AnalyseDesRisques.
	 * Les intitulés de ces risques sont placés dans un tableau; en colonne sont précisés les niveaux de gravité qui 
	 * leurs sont associés,
	 * en ligne les niveaux de vraisemblance que ces risques induisent.
	 */
	

	
	/*les variables d'instance sont l'etude en cours, le tableau contenant les risques, l'analyse des risques effectuée
	 * avant, et le nombre de niveaux de vraisemblance définis dans le module métriques, qui serviront à définir les entêtes 
	 * du tableau.
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
		
		for (int i=0;i<a;i++){
			for (int j=1;j<=b;j++){
				this.matrice[i][j]=new ArrayList<String>();
			}
		}
		
		
		
		for (int i=1;i<=a;i++){
			for (int j=1;j<=b;j++){
				for (int k=0;k<this.analysedesrisques.getAnalyseDesRisques().size();k++){
					
					
					if(this.analysedesrisques.getAnalyseDesRisques().get(k).getRetenu()==true&&this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauGravite()==i && this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauVraisemblance()==j){
						
		this.matrice[a-i][j].add(this.analysedesrisques.getAnalyseDesRisques().get(k).getIntitule());
		
		
		
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
	
	/*Méthodes
	 * 
	 */

	
	public ArrayList<String>[][] getMatrice(){
		return this.matrice;
	}
	
	public String toString(){
		return "Matrice des risques";
	}
	
	
	public void setMatrice(ArrayList<String>[][] matrice){
		this.matrice=matrice;
	}
	
	public void setNiveaux(int niveaux){
		this.nombreniveaux=niveaux;
	}

	public boolean  estCoherent(){
		
		MatriceDesRisques mat=new MatriceDesRisques(this.getEtude());
		mat.setCree(true);
		
		
		this.setMatrice(mat.getMatrice());
		this.setNiveaux(mat.nombre());
		
		
		
		if (this.matrice.length!=1){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*public void checkDisponible(){
		if(this.analysedesrisques.estCoherent()==true){
			this.disponible=true;
		}
		else{
			this.disponible=false;
		}
	}*/
	
	
	public int nombre(){
		return this.nombreniveaux;
	}
}
