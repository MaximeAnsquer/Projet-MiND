package autres;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Cette classe représente une métrique, c'est-à-dire une échelle ordinale associée à
 * un critère retenu. En gros c'est un tableau associé à un critère (cf page 10 du cahier des charges).
 * Je représente ce tableau par l'ensemble (Hashtable indexé par le numéro des lignes)
 * de ses lignes, d'où la classe locale Ligne.
 * @author Maxime Ansquer 
 */

public class Metrique {
	
	//Classe locale representant une ligne du tableau
	
	private class Ligne{
		
		//---Variables d'instance---
		
		private int numero;
		private String intitule;
		private String description;
		
		//---Constructeurs---
		
		/**
		 * Crée une ligne vide numérotée.
		 * @param numero
		 */
		public Ligne(int numero){
			this.numero = numero;
			this.intitule = "";
			this.description = "";
		}
		
		public Ligne(int numero, String intitule, String description){
			this.numero = numero;
			this.intitule = intitule;
			this.description = description;
		}
		
		//---Getters et setters---

		public int getNumero() {
			return numero;
		}

		public void setNumero(int numero) {
			this.numero = numero;
		}

		public String getIntitule() {
			return intitule;
		}

		public void setIntitule(String intitule) {
			this.intitule = intitule;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}	
		
		public String toString(){
			return "{[Ligne de Metrique] : numero = "+this.getNumero()
				   +" ; intitule = "+this.intitule+" ; description = "
				   +this.description+"}";			
		}
		
	}
	
	//Fin de la classe locale
	
	//---Variables d'instance---
	
	private Hashtable<Integer, Ligne> lesLignes;

	//---Constructeurs---
	
	/**	
	 * Crée une " métrique " vide ; à utiliser dans le cas où l'utilisateur 
	 * a retenu un critère qui n'était pas présent dans la BDC critères.
	 */
	public Metrique(){
		this.lesLignes = new Hashtable<Integer, Ligne>();
		for(int i=1;i<=4;i++){
			this.lesLignes.put(i, new Ligne(i));
		}
	}
	
	/**
	 * Crée une métrique en fournissant la liste des lignes du tableau.
	 * @author Maxime Ansquer
	 * @param lesLignes Les lignes du tableau définissant la métrique.
	 */
	public Metrique(Hashtable<Integer, Ligne> lesLignes) {
		this.lesLignes = lesLignes;
	}
	
	//---Getters et setters---
	
	public Hashtable<Integer, Ligne> getLesLignes() {
		return lesLignes;
	}

	public void setLesLignes(Hashtable<Integer, Ligne> lesLignes) {
		this.lesLignes = lesLignes;
	}	
		
	//---Services---	

	public Ligne getLigne(int numeroLigne){
		return this.getLesLignes().get(numeroLigne);
	}
	
	public void ajouterLigne(int numero, String intitule, String description){
		this.getLesLignes().put(numero, new Ligne(numero, intitule, description));
	}
	
	public void supprimerLigne(int numero){
		this.getLesLignes().remove(numero);
	}	
	
	public int nbLignes(){
		return this.getLesLignes().size();
	}	
	
	/** 
	 * @param numeroDeLaLigneAModifier Le numéro de la ligne dont on souhaite changer le numéro.
	 * @param nouveauNumero Le nouveau numéro que l'on veut lui attribuer.
	 */
	public void modifierNumeroLigne(int numeroDeLaLigneAModifier, int nouveauNumero){
		this.getLesLignes().get(numeroDeLaLigneAModifier).setNumero(nouveauNumero);
	}
	
	/** 
	 * @param numeroDeLaLigneAModifier Le numéro de la ligne dont on souhaite changer l'intitulé.
	 * @param nouvelIntitule  Le nouvel intitulé que l'on veut lui attribuer.
	 */
	public void modifierIntituleLigne(int numeroDeLaLigneAModifier, String nouvelIntitule){
		this.getLesLignes().get(numeroDeLaLigneAModifier).setIntitule(nouvelIntitule);
	}
	
	/** 
	 * @param numeroDeLaLigneAModifier Le numéro de la ligne dont on souhaite changer la description.
	 * @param nouvelleDescription La nouvelle description que l'on veut lui attribuer.
	 */
	public void modifierDescriptionLigne(int numeroDeLaLigneAModifier, String nouvelleDescription){
		this.getLesLignes().get(numeroDeLaLigneAModifier).setDescription(nouvelleDescription);
	}	

}
