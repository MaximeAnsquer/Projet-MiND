package abstraction.bdc;
import java.util.Hashtable;
import abstraction.autres.SourceDeMenace;

/**
 * @author Maxime Ansquer
 */

public class BDCSourcesDeMenaces {
	
	//---L'instance unique---
	
	private static BDCSourcesDeMenaces bdcSourcesDeMenaces = new BDCSourcesDeMenaces();
	
	//---Variables d'instance---
	
	/**
	 * Une hashtable d'objets " SourceDeMenace " indexes par leurs id.
	 * Attention les id doivent etre distincts !
	 */
	private Hashtable<String,SourceDeMenace>  lesSourcesDeMenaces;
	
	//---Constructeurs---
	
	private BDCSourcesDeMenaces(){
		//TODO : ajouter les valeurs depuis la bdd fournie par le client
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
	
	public static BDCSourcesDeMenaces getInstance(){
		return bdcSourcesDeMenaces;
	}
	
}
