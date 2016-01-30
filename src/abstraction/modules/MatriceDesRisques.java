package abstraction.modules;

import java.util.ArrayList;

import abstraction.Etude;
import presentation.ModeleMatriceDesRisques;

public class MatriceDesRisques extends Module {
	
	/*Ce module synth�tise l'etude effectu�e en ce sens qu'elle ordonne diff�rents "risques" tels qu'ils ont �t� d�finis 
	 * dans le module AnalyseDesRisques.
	 * Les intitul�s de ces risques sont plac�s dans un tableau; en colonne sont pr�cis�s les niveaux de gravit� qui 
	 * leurs sont associ�s,
	 * en ligne les niveaux de vraisemblance que ces risques induisent.
	 */
	

	
	/*les variables d'instance sont l'etude en cours, le tableau contenant les risques, l'analyse des risques effectu�e
	 * avant, et le nombre de niveaux de vraisemblance d�finis dans le module m�triques, qui serviront � d�finir les ent�tes 
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
					
					
					if(this.analysedesrisques.getAnalyseDesRisques().get(k).getRetenu()==true&&this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauGravite()==i && this.analysedesrisques.getAnalyseDesRisques().get(k).getNiveauVraisemblance()==j&&this.matrice[a-i][j].contains(this.analysedesrisques.getAnalyseDesRisques().get(k).getIntitule())!=true){
						
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
	
	/*M�thodes
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
