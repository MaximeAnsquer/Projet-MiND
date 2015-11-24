package abstraction.modules;
import java.util.Hashtable;

import abstraction.Etude;
import abstraction.autres.*;

/** 
 * Cahier des charges, page 5 :
 * 
 * " Metriques : cet onglet permet la définition des echelles ordinales associees
 *   aux differents criteres de securite retenus (par exemple : définition d’une 
 *   echelle à quatre niveaux pour le critère de disponibilite). "
 *   
 * Cette classe modelise le module " Metriques ". Elle est constituee d'une hastable d'objets Metrique
 * indexee par le nom du critere associe (on rappelle qu'un objet Metrique est constitue d'un critere
 * et d'un tableau dans lequel chaque ligne est un niveau de metrique).
 */

public class Metriques extends Module {
		
	//---La BDC Metriques, accessible par la methode statique getBDC()---
	
	private static Hashtable<String, Metrique> bdcMetriques;

	//---Variables d'instance---
	
	private Hashtable<String, Metrique> lesMetriques;

	//---Constructeurs---	
	
	/** 
	 * Initialise le module en commençant par initialiser la BDC, puis en copiant les valeurs
	 * de la BDC dans le module.
	 */
	public Metriques() {
		super("Métriques");
		this.predecesseurs.add(this.getEtude().getModule("Critères de sécurité"));
		this.successeurs.add(this.getEtude().getModule("Scénarios de menaces typés"));
		this.successeurs.add(this.getEtude().getModule("Analyse des risques"));
		this.successeurs.add(this.getEtude().getModule("Matrice des risques"));
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		
		this.importerBDC();  //on remplit la BDC
		this.lesMetriques = new Hashtable<String, Metrique>();  
		CriteresDeSecurite cds = (CriteresDeSecurite) this.getEtude().getModule("Critères de sécurité");
		for(Critere critere : cds.getCriteresRetenus().values()){   //pour chaque critere retenu a l'onglet "Critères de sécurité"
			if(bdcMetriques.containsKey(critere.getIntitule())){   //s'il existe une metrique associee dans la BDC Metriques
				this.lesMetriques.put(critere.getIntitule(), bdcMetriques.get(critere.getIntitule()));   //on ajoute cette metrique dans l'onglet Metriques
			}
			else{
				this.lesMetriques.put(critere.getIntitule(), new Metrique(critere));   //sinon, on ajoute une metrique vide associee a ce critere dans l'onglet Metriques
			}
		}
	}	

	//---Getters et setters---
	
	public Hashtable<String, Metrique> getLesMetriques(){
		return this.lesMetriques;
	}
	
	public void setLesMetriques(Hashtable<String, Metrique> lesMetriques ){
		this.lesMetriques = lesMetriques;
	}
	
	//--Services--		
	
	public Metrique getMetrique(String intituleCritere){
		return this.getLesMetriques().get(intituleCritere);
	}
	
	public void supprimerMetrique(String intituleCritere){
		this.getLesMetriques().remove(intituleCritere);
	}
	
	public void ajouterMetrique(Metrique metrique){
		this.getLesMetriques().put(metrique.getCritere().getIntitule(), metrique);
	}
	
	public void ajouterMetrique(Critere critere){
		this.getLesMetriques().put(critere.getIntitule(), new Metrique(critere));
	}
	
	private void importerBDC() {
		// TODO Auto-generated method stub		
	}
		
	public static Hashtable<String, Metrique> getBDC(){
		return bdcMetriques;
	}
	
}
