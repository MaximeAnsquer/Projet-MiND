package abstraction.modules;
import java.util.Hashtable;

import abstraction.autres.*;

/**
 * Cette classe permet d'associer les biens supports et les biens
 * essentiels entre eux, sachant qu'un bien support peut avoir plusieurs biens 
 * essentiels associés et de même pour un bien essentiel
 * 
 * @author Francois Adam
 */

public class MappingDesBiens extends Module{
	//Variables d'instance
	private Hashtable<String, MappingBien> mappingDesBiens; // table qui relie chaque bien essentiel à son mapping
	private BiensSupports biensSupports;
	private BiensEssentiels biensEssentiels;
	
	//Constructeur
	public MappingDesBiens() {
		super("Mapping des biens");
		this.mappingDesBiens=new Hashtable<String, MappingBien>();
		//TODO Decomenter quand les autres parties seront OK
		/*
		this.successeurs.add(AnalyseDesRisques.getInstance());
		this.predecesseurs.add(BiensEssentiels.getInstance());
		this.predecesseurs.add(BiensSupports.getInstance());
		this.biensSupports = BiensSupports.getInstance();
		this.biensEssentiels = BiensEssentiels.getInstance();
		*/
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
	}
	
	//Getters et Setters
	public Hashtable<String, MappingBien> getMappingDesBiens(){
		return this.mappingDesBiens;
	}
	
	public  void setMappingDesBiens(Hashtable<String, MappingBien> mapping){
		this.mappingDesBiens=mapping;
	}
	
	public BiensSupports getBiensSupports(){
		return this.biensSupports;
	}
	
	public void setBiensSupports(BiensSupports biensSupports){
		this.biensSupports = biensSupports;
	}
	
	public BiensEssentiels getBiensEssentiels(){
		return this.biensEssentiels;
	}
	
	public void setBiensEssentiels(BiensEssentiels biensEssentiels){
		this.biensEssentiels=biensEssentiels;
	}
	
	
	//Services
	
	public void mappingDesBiens(){
		//TODO association des biens entre eux via l'IHM
	}
}
