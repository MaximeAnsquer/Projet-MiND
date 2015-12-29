package abstraction.autres;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComboBox;

import abstraction.Etude;
import abstraction.modules.Metriques;


/*Un �v�nement redout� est mat�rialis� par un manquement aux exigences minimales fonctionnelles selon certains crit�res, 
 * qui peut toucher des biens essentiels.
 * Exemple d'�v�nement: Il ya une perte de confidentialit� sur les emails sensibles de l'entreprise.
 */


public class Evenement {
	
	/*Chaque evenement correspond � une ligne du tableau repr�sent� page 17. En effet un evenement est une association
	 * d'un bien , d'un critere auquel on donne deux niveaux; l'un d'exigence et l'autre de gravit�.
	 * De plus, cet evenement peut appartenir � des groupe d�finis dans le module Biens Essentiels.
	 * On donne un nom � cet evenement.
	 */
	private Etude etude;
	
	private String nomevenement;
	private LinkedList<String> nomgroupes;
	private LinkedList<String> contenugroupes;
	private String bienessentiel;
	private String critere;
	
	private int niveaugravite;
	private int niveauexigence;
	
	
	
	
	/*les niveaux sont des int qui permet de stocker les valeurs entr�es dans le tableau par l'utilisateur
	 */
	
	
	
	public Evenement(Etude etude,String nomevenement,LinkedList<String> nomgroupes,LinkedList<String> contenugroupes,String bienessentiel,String critere){
		this.etude=etude;
		this.nomevenement=nomevenement;
		this.nomgroupes=nomgroupes;
		this.contenugroupes=contenugroupes;
		this.bienessentiel=bienessentiel;
		this.critere=critere;
		this.niveauexigence=1;
		this.niveaugravite=1;
		
		
		/*On met des valeurs par d�faut pour les niveaux (peut �tre inutile mais on sait jamais)
		 */
		
		
		
		
		
	}
	
	/*Getters
	 */
	
	public LinkedList<String> getNomGroupes(){
		return this.nomgroupes;
	}
	public LinkedList<String> getContenuGroupes(){
		return this.contenugroupes;
	}
	
	public int getNiveauExigence(){
		return this.niveauexigence;
	}
	
	public int getNiveauGravite(){
		return this.niveaugravite;
	}
	
	
	/*retourne  le nom du bien numero i
	 * 
	 */
	
	public String GetNomBien(){
		return this.bienessentiel;
	}
	
	public String GetCritere(){
		return this.critere;
	}
	
	
	public String GetNomEvenement(){
		return this.nomevenement;
	}
	
	/*retourne le nom du critere num�ro i
	 */
	public String getNomCritere(){
		return this.critere.toString();
	}
	
	
	
	/*Setters*/
	
	public void setNiveauGravite(int i){
		this.niveaugravite=i;
	}
	
	public void setNiveauExigence(int i){
		this.niveauexigence=i;
	}
	
	public void setBienEssentiel(String bienessentiel){
		this.bienessentiel=bienessentiel;
	}
	
	public void setNomEvenement(String nomevenement){
		this.nomevenement=nomevenement;
	}
	
	public boolean estComplet(){
		boolean resultat = true;
		resultat = resultat && !this.GetNomEvenement().equals("");
		return resultat;
	}
	
	/*A noter que setCritere est absent pour �viter des probl�mes de coh�rence; l'utilisateur doit � tout prix utiliser
	 * les crit�res choisis dans le module Criteres de S�curit�.
	 */
}
	
