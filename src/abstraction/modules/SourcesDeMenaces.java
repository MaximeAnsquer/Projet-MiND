package abstraction.modules;
import java.util.Hashtable;
import abstraction.autres.SourceDeMenace;
import abstraction.bdc.BDCSourcesDeMenaces;

/**
 * @author Maxime Ansquer
 */

public class SourcesDeMenaces extends Module {
	
	//---L'instance unique---
	
	private static SourcesDeMenaces bdcSourcesDeMenaces = new SourcesDeMenaces();
	
	//---Variables d'instance---
	
	/**
	 * Une hashtable d'objets " SourceDeMenace " indexes par leurs id.
	 * Attention les id doivent etre distincts !
	 */
	private Hashtable<String,SourceDeMenace>  lesSourcesDeMenaces;
	
	//---Constructeurs---
	
	/**
	 * Initialise les sources de menaces a partir de la bdc correspondante.
	 */
	private SourcesDeMenaces(){
		super("Sources de menaces");
		this.lesSourcesDeMenaces = BDCSourcesDeMenaces.getInstance().getLesSourcesDeMenaces();
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
	 * hashtable de SourceDeMenace indexée par leurs id.
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
	
	public static SourcesDeMenaces getInstance(){
		return bdcSourcesDeMenaces;
	}
	
}
