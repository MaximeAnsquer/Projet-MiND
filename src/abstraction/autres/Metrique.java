package abstraction.autres;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Cette classe represente une metrique, c'est-à-dire une echelle ordinale associee à
 * un critere retenu (cf page 10 du cahier des charges).
 * Ce tableau est represente par l'ensemble (Hashtable indexee par le numero des niveaux)
 * de ses niveaux (un niveau correspond a une ligne du tableau) (cf la classe NiveauDeMetrique).
 * @author Maxime Ansquer 
 */

public class Metrique {	
	
	//---Variables d'instance---
	
	private Critere critere;
	private Hashtable<Integer, NiveauDeMetrique> lesNiveaux;

	//---Constructeurs---
	
	/**	
	 * Cree une metrique vide a 4 niveaux ; a utiliser dans le cas ou l'utilisateur 
	 * a retenu un critere qui n'etait pas present dans la BDC criteres.
	 * @param critere Le critere associe a la metrique.
	 */
	public Metrique(Critere critere){
		this.critere = critere;
		this.lesNiveaux = new Hashtable<Integer, NiveauDeMetrique>();
		for(int i=1;i<=4;i++){
			this.lesNiveaux.put(i, new NiveauDeMetrique(i));
		}
	}
	
	/**
	 * Cree une metrique en fournissant le critere associe et la liste des niveaux.
	 * @author Maxime Ansquer
	 * @param critere Le critere associe a la metrique.
	 * @param lesNiveaux Les niveaux du tableau definissant la metrique.
	 */
	public Metrique(Critere critere, Hashtable<Integer, NiveauDeMetrique> lesNiveaux) {
		this.critere = critere;
		this.lesNiveaux = lesNiveaux;
	}
	
	//---Getters et setters---
	
	public Critere getCritere(){
		return this.critere;
	}
	
	public void setCritere(Critere critere){
		this.critere = critere;
	}
	
	public Hashtable<Integer, NiveauDeMetrique> getLesNiveaux() {
		return lesNiveaux;
	}

	public void setLesNiveaux(Hashtable<Integer, NiveauDeMetrique> lesNiveaux) {
		this.lesNiveaux = lesNiveaux;
	}	
		
	//---Services---	

	public NiveauDeMetrique getNiveau(int numeroNiveau){
		return this.getLesNiveaux().get(numeroNiveau);
	}
	
	public void ajouterNiveau(int numero, String intitule, String description){
		this.getLesNiveaux().put(numero, new NiveauDeMetrique(numero, intitule, description));
	}
	
	public void ajouterNiveau(NiveauDeMetrique niveau){
		this.getLesNiveaux().put(niveau.getNumero(), niveau);
	}
	
	public void supprimerNiveau(int numero){
		this.getLesNiveaux().remove(numero);
	}	
	
	public int nombreDeNiveaux(){
		return this.getLesNiveaux().size();
	}	
	
}
