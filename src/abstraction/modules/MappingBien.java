package abstraction.modules;
import java.util.LinkedList;

import abstraction.autres.*;

/**
 * Cette classe permet d'associer un bien essentiel et les biens
 * supports entre eux
 * 
 * @author Francois Adam
 */

public class MappingBien {
	//Variables d'instance
	private LinkedList<String> mappingBien; // table qui relie un bien essentiel aux biens supports
	private BiensSupports biensSupports;
	private Bien bienEssentiel;
	
	//Constructeur
	public MappingBien(BiensSupports biensSupports, Bien bienEssentiel){
		this.mappingBien = new LinkedList<String>();
		this.biensSupports = biensSupports;
		this.bienEssentiel = bienEssentiel;
		for (int i=0; i<this.biensSupports.nombreDeBiens(); i++){
			this.mappingBien.add("");
		}
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
	
	public LinkedList<String> getMappingBien(){
		return this.mappingBien;
	}
	
	public void ajouterCase(){
		this.mappingBien.addLast("");
	}
	
	public String getValueAt(int index){
		return mappingBien.get(index);
	}
	
	public void setValueAt(String value, int index){
		if (value.equals("") || value.equals("x") || value.equals("o")){
			mappingBien.set(index, value);
		}
	}
	
	public void setMappingBien(LinkedList<String> mappingBien){
		this.mappingBien=mappingBien;
	}
	
	public boolean estComplet(){
		int nombreCasesVides = 0;
		int index = 0;
		while (nombreCasesVides<this.getMappingBien().size() && index<this.getMappingBien().size()){
			if (this.getMappingBien().get(index).equals("")){
				nombreCasesVides++;
			}
			index++;
		}
		return nombreCasesVides<this.getMappingBien().size();
	}
	
	public String toString(){
		return "{Mapping Bien : intitul� = "+this.getBienEssentiel().getIntitule()+"}";
	}
}
