package abstraction.modules;
import java.util.Hashtable;
import abstraction.autres.*;

/**
 * Cette classe permet d'associer un bien essentiel et les biens
 * supports entre eux
 * 
 * @author Francois Adam
 */

public class MappingBien {
	//Variables d'instance
	private Hashtable<String, String[]> mappingBien; // table qui relie un bien essentiels aux biens supports
	private BiensSupports biensSupports;
	private Bien bienEssentiel;
	
	//Constructeur
	public MappingBien(Hashtable<String, String[]> mappingBien, BiensSupports biensSupports, Bien bienEssentiel){
		this.mappingBien = mappingBien;
		this.biensSupports = biensSupports;
		this.bienEssentiel = bienEssentiel;
	}
	
	//Getters et Setters
	public BiensSupports getBiensSupports(){
		return this.biensSupports;
	}
	
	public void setBiensSupports(BiensSupports biensSupports){
		this.biensSupports = biensSupports;
	}
	
	public Bien getBienEssentiel(){
		return bienEssentiel;
	}
	
	public void setBienEssentiel(Bien bienEssentiel){
		this.bienEssentiel=bienEssentiel;
	}
	
	public Hashtable<String, String[]> getMappingBien(){
		return this.mappingBien;
	}
	
	public void setMappingBien(Hashtable<String, String[]> mappingBien){
		this.mappingBien=mappingBien;
	}
	
	public String toString(){
		return "{Mapping Bien : intitulé = "+this.getBienEssentiel().getIntitule()+"}";
	}
}
