package abstraction.autres;

import java.util.ArrayList;


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
	
	private String nomevenement;
	private ArrayList<String> nomgroupes;
	private ArrayList<String> contenugroupes;
	private Bien bienessentiel;
	private Critere critere;
	private Metrique exigence;
	private Metrique gravite;
	
	/*les niveaux sont des int qui permet de stocker les valeurs entrées dans le tableau par l'utilisateur
	 */
	private int niveauexigence;
	private int niveaugravite;
	
	
	public Evenement(String nomevenement,ArrayList<String> nomgroupes,ArrayList<String> contenugroupes,Bien bienessentiel,Critere critere,Metrique exigence,Metrique gravite){
		this.nomevenement=nomevenement;
		this.nomgroupes=nomgroupes;
		this.contenugroupes=contenugroupes;
		this.bienessentiel=bienessentiel;
		this.critere=critere;
		this.exigence=exigence;
		this.gravite=gravite;
		
		/*On met des valeurs par défaut pour les niveaux (peut être inutile mais on sait jamais)
		 */
		this.niveauexigence=1;
		this.niveaugravite=1;
	}
	
	/*Getters
	 */
	
	
	public Metrique getExigence(){
	return this.exigence;
	}
	
	public Metrique getGravite(){
		return this.gravite;
	}
	
	/*retourne  le nom du bien numero i
	 * 
	 */
	
	public String GetNomBien(){
		return this.bienessentiel.toString();
	}
	
	public Critere GetCritere(){
		return this.critere;
	}
	
	public Bien GetBien(){
		return this.bienessentiel;
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
	
	public void setBienEssentiel(Bien bienessentiel){
		this.bienessentiel=bienessentiel;
	}
	
	public void setNomEvenement(String nomevenement){
		this.nomevenement=nomevenement;
	}
	
	/*A noter que setCritere est absent pour éviter des problèmes de cohérence; l'utilisateur doit à tout prix utiliser
	 * les critères choisis dans le module Criteres de Sécurité.
	 */
}
	
