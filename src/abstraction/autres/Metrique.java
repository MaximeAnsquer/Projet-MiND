package abstraction.autres;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Cette classe represente une metrique, c'est-a-dire une echelle ordinale (un tableau) associee a
 * un critere retenu (cf page 10 du cahier des charges).
 * Ce tableau est represente par l'ensemble (hashtable indexee par le numero des niveaux)
 * de ses niveaux (un niveau correspond a une ligne du tableau) (cf la classe NiveauDeMetrique).
 * @author Maxime Ansquer 
 */

public class Metrique {	
	
	//---Variables d'instance---
	
	private Critere critere;
	private ArrayList<NiveauDeMetrique> lesNiveaux;

	//---Constructeurs---
	
	/**	
	 * Cree une metrique vide a 4 niveaux ; a utiliser dans le cas ou l'utilisateur 
	 * a retenu un critere qui n'etait pas present dans la BDC criteres.
	 * @param critere Le critere associe a la metrique.
	 */
	public Metrique(Critere critere){
		this.critere = critere;
		this.lesNiveaux = new ArrayList<NiveauDeMetrique>();
		for(int i=1;i<=4;i++){
			this.lesNiveaux.add(new NiveauDeMetrique(i));
		}
	}
	
	/**
	 * Metrique encore plus vide qui ne contient absolument rien (et pourtant ce constructeur est utile !)
	 */
	public Metrique() {
		this.critere = new Critere("", "", "");
		this.lesNiveaux = new ArrayList<NiveauDeMetrique>();
	}
	
	/**
	 * Cree une metrique en fournissant le critere associe et la liste des niveaux.
	 * @author Maxime Ansquer
	 * @param critere Le critere associe a la metrique.
	 * @param lesNiveaux Les niveaux du tableau definissant la metrique.
	 */
	public Metrique(Critere critere, ArrayList<NiveauDeMetrique> lesNiveaux) {
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
	
	public ArrayList<NiveauDeMetrique> getLesNiveaux() {
		return lesNiveaux;
	}

	public void setLesNiveaux(ArrayList<NiveauDeMetrique> lesNiveaux) {
		this.lesNiveaux = lesNiveaux;
	}	
		
	//---Services---	

	public NiveauDeMetrique getNiveau(int index){		
		return (NiveauDeMetrique) lesNiveaux.get(index);
	}
	
	public void ajouterNiveau(int numero, String intitule, String description){
		this.getLesNiveaux().add(new NiveauDeMetrique(numero, intitule, description));
	}
	
	public void ajouterNiveau(NiveauDeMetrique niveau){
		this.getLesNiveaux().add(niveau);
	}
	
	public void supprimerNiveau(int numero){
		this.getLesNiveaux().remove(numero);
	}	
	
	public int nombreDeNiveaux(){
		return this.getLesNiveaux().size();
	}	
	
	public String toString(){
		return getCritere().getIntitule();
	}
	
	public String getIntitule(){
		return getCritere().getIntitule();
	}

	public boolean estComplet() {
		boolean resultat = true;
		for(NiveauDeMetrique n : this.getLesNiveaux()){
			resultat = resultat && n.estComplet();
		}
		return resultat;
	}
	
}
