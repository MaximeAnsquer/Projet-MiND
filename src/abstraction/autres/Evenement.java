package abstraction.autres;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComboBox;

import abstraction.Etude;
import abstraction.modules.Metriques;


/*Un évènement redouté est matérialisé par un manquement aux exigences minimales fonctionnelles selon certains critères, 
 * qui peut toucher des biens essentiels.
 * Exemple d'évènement: Il ya une perte de confidentialité sur les emails sensibles de l'entreprise.
 */


public class Evenement {
	
	/*Chaque evenement correspond à une ligne du tableau représenté page 17. En effet un evenement est une association
	 * d'un bien , d'un critere auquel on donne deux niveaux; l'un d'exigence et l'autre de gravité.
	 * De plus, cet evenement peut appartenir à des groupe définis dans le module Biens Essentiels.
	 * On donne un nom à cet evenement.
	 */
	private Etude etude;
	
	private String nomevenement;
	private LinkedList<String> nomgroupes;
	private LinkedList<String> contenugroupes;
	private String bienessentiel;
	private String critere;
	
	private int niveaugravite;
	private int niveauexigence;
	
	
	
	
	/*les niveaux sont des int qui permet de stocker les valeurs entrées dans le tableau par l'utilisateur
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
		
		
		/*On met des valeurs par défaut pour les niveaux (peut être inutile mais on sait jamais)
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
	
	/*retourne le nom du critere numéro i
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
	
	/*A noter que setCritere est absent pour éviter des problèmes de cohérence; l'utilisateur doit à tout prix utiliser
	 * les critères choisis dans le module Criteres de Sécurité.
	 */
}
	
