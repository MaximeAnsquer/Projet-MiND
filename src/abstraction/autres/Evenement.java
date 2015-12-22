package abstraction.autres;

import java.util.ArrayList;

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
	private ArrayList<String> nomgroupes;
	private ArrayList<String> contenugroupes;
	private String bienessentiel;
	private String critere;
	
	
	
	
	/*les niveaux sont des int qui permet de stocker les valeurs entrées dans le tableau par l'utilisateur
	 */
	private JComboBox exigence;
	private JComboBox gravite;
	
	
	public Evenement(Etude etude,String nomevenement,ArrayList<String> nomgroupes,ArrayList<String> contenugroupes,String bienessentiel,String critere){
		this.etude=etude;
		this.nomevenement=nomevenement;
		this.nomgroupes=nomgroupes;
		this.contenugroupes=contenugroupes;
		this.bienessentiel=bienessentiel;
		this.critere=critere;
		
		
		/*On met des valeurs par défaut pour les niveaux (peut être inutile mais on sait jamais)
		 */
		
		
		
		
		int a=((Metriques) this.etude.getModule("Metriques")).getMetrique("Gravité").nombreDeNiveaux();
		
		String[] liste=new String[a];
		for(int i=1;i<=a;i++){
			
			liste[i-1]=""+i;
		}
		this.gravite=new JComboBox(liste);
		
		int b=((Metriques)this.etude.getModule("Metriques")).getMetrique(this.critere).nombreDeNiveaux();
		
		String[] listebis=new String[b];
        for(int i=1;i<=b;i++){
			
			listebis[i-1]=""+i;
			
		}
        this.exigence=new JComboBox(listebis);
	}
	
	/*Getters
	 */
	
	public ArrayList<String> getNomGroupes(){
		return this.nomgroupes;
	}
	public ArrayList<String> getContenuGroupes(){
		return this.contenugroupes;
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
	
	
	public JComboBox getComboGravite(){
		return this.gravite;
	}
	
	public JComboBox getComboExigence(){
		return this.exigence;
	}
	
	/*Setters*/
	/*
	public void setNiveauGravite(int i){
		this.niveaugravite=i;
	}
	
	public void setNiveauExigence(int i){
		this.niveauexigence=i;
	}
	*/
	public void setBienEssentiel(String bienessentiel){
		this.bienessentiel=bienessentiel;
	}
	
	public void setNomEvenement(String nomevenement){
		this.nomevenement=nomevenement;
	}
	
	/*A noter que setCritere est absent pour éviter des problèmes de cohérence; l'utilisateur doit à tout prix utiliser
	 * les critères choisis dans le module Criteres de Sécurité.
	 */
}
	
