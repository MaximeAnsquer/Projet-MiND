package abstraction.modules;
import java.util.Hashtable;

import abstraction.autres.SourceDeMenace;

/**
 * Classe representant l'onglet " Sources de menaces ".
 * @author Maxime Ansquer
 */

public class SourcesDeMenaces extends Module {
	
	//---La BDC Sources de menaces, accessible par la methode statique getBDC()---
	
	/**
	 * Une hashtable d'objets " SourceDeMenace " indexes par leurs id.
	 * Attention les id doivent etre distincts !
	 */
	private static Hashtable<String, SourceDeMenace> bdcSourcesDeMenaces = new Hashtable<String, SourceDeMenace>();
	
	//---Variables d'instance---
	
	/**
	 * Une hashtable d'objets " SourceDeMenace " indexes par leurs id.
	 * Attention les id doivent etre distincts !
	 */
	private Hashtable<String, SourceDeMenace>  lesSourcesDeMenaces;
	
	//---Constructeurs---
	
	/** 
	 * Initialise le module en commencant par initialiser la BDC, puis en copiant les valeurs
	 * de la BDC dans le module.
	 */
	public SourcesDeMenaces(){
		super("SourcesDeMenaces");
		//TODO commente pour faire des test, a decommenter par la suite
		
//		this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesGeneriques"));
//		this.successeurs.add(this.getEtude().getModule("ScenariosDeMenacesTypes"));
//		this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
//		this.successeurs.add(this.getEtude().getModule("MatriceDesRisques"));
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		
		this.importerBDC();  //on remplit la BDC
		this.lesSourcesDeMenaces = bdcSourcesDeMenaces;	 //on initialise l'onglet avec les valeurs de la BDC
	}
	
	//---Getters et setters	

	public Hashtable<String, SourceDeMenace> getLesSourcesDeMenaces() {
		return lesSourcesDeMenaces;
	}

	public void setLesSources(Hashtable<String, SourceDeMenace> lesSourcesDeMenaces) {
		this.lesSourcesDeMenaces = lesSourcesDeMenaces;
	}
	
	//---Services---
	
	public SourceDeMenace getSourceDeMenace(SourceDeMenace sourceDeMenace){
		return this.getLesSourcesDeMenaces().get(sourceDeMenace.getId());
	}
	
	public SourceDeMenace getSourceDeMenace(String idSourceDeMenace){
		return this.getLesSourcesDeMenaces().get(idSourceDeMenace);
	}
	
	public void ajouterSourceDeMenace(SourceDeMenace sourceDeMenace){
		this.getLesSourcesDeMenaces().put(sourceDeMenace.getId(), sourceDeMenace);
	}
	
	public void supprimerSourceDeMenace(SourceDeMenace sourceDeMenace){
		this.getLesSourcesDeMenaces().remove(sourceDeMenace.getId());
	}
	
	/**
	 * @return Renvoie la liste des sources de menaces retenues sous la forme d'une
	 * hashtable de SourceDeMenace indexee par leurs id.
	 */
	public Hashtable<String, SourceDeMenace> getSourcesDeMenacesRetenues(){
		Hashtable<String, SourceDeMenace> resultat = new Hashtable<String, SourceDeMenace>();
		for(SourceDeMenace sourceDeMenace : this.getLesSourcesDeMenaces().values()){
			if(sourceDeMenace.isRetenu()){
				resultat.put(sourceDeMenace.getId(), sourceDeMenace);
			}
		}
		return resultat;
	}
	
	public static Hashtable<String, SourceDeMenace> getInstance(){
		return bdcSourcesDeMenaces;
	}
	
	private void importerBDC() {
		// TODO Valeurs fictives pour faire des tests ; à changer
		bdcSourcesDeMenaces.put("IMF", new SourceDeMenace("IMF", "Source humaine interne, malveillante, avec de faibles capacités", "Stagiaire"));
		bdcSourcesDeMenaces.put("IMI", new SourceDeMenace("IMF", "Source humaine interne, malveillante, avec des capacités importantes", "Prestataire d'un service sensible"));		
	}
	
	public Hashtable<String, SourceDeMenace> getBDC(){
		return bdcSourcesDeMenaces;
	}
	
	public int nombreDeSources(){
		return this.getLesSourcesDeMenaces().size();
	}
	
	public SourceDeMenace getSource(int index){
		return (SourceDeMenace) getLesSourcesDeMenaces().values().toArray()[index];
	}
	
	public String toString(){
		return "Sources de menaces";
	}
	
}
