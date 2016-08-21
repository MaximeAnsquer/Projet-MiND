package abstraction.modules;
import java.util.ArrayList;
import java.util.LinkedList;


import abstraction.Etude;
import abstraction.autres.*;

/**
 * Cette classe permet d'associer les biens supports et les biens
 * essentiels entre eux, sachant qu'un bien support peut avoir plusieurs biens 
 * essentiels associ�s et de m�me pour un bien essentiel
 * 
 * @author Francois Adam
 */

public class MappingDesBiens extends Module{
	//Variables d'instance
	private LinkedList<MappingBien> mappingDesBiens; // table qui relie chaque bien essentiel a son mapping
	private BiensSupports biensSupports;
	private BiensEssentiels biensEssentiels;
	
	//Constructeur
	public MappingDesBiens(Etude etude) {
		super("MappingDesBiens");
		this.mappingDesBiens=new LinkedList<MappingBien>();
		this.etude=etude;
		this.biensSupports = (BiensSupports)this.getEtude().getModule("BiensSupports");
		this.biensEssentiels = (BiensEssentiels)this.getEtude().getModule("BiensEssentiels");
		for (int i=0; i<this.biensEssentiels.nombreDeBiens(); i++){
			if (biensEssentiels.getBien(i).isRetenu()){
				mappingDesBiens.add(new MappingBien(biensSupports,biensEssentiels.getBien(i)));
			}
		}

		this.predecesseurs.add(this.getEtude().getModule("BiensEssentiels"));
		this.predecesseurs.add(this.getEtude().getModule("BiensSupports"));
		
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
	}
	
	//Getters et Setters
	public LinkedList<MappingBien> getMappingDesBiens(){
		return this.mappingDesBiens;
	}
	
	public  void setMappingDesBiens(LinkedList<MappingBien> mapping){
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
	
	public int getIndexBienSupport(Bien bienSupport) throws Exception{
		int index=0;
		while (index<this.mappingDesBiens.get(0).getBiensSupports().nombreDeBiens() 
				&& this.getMappingDesBiens().get(0).getBiensSupports().getBien(index)!=bienSupport){
			index++;
		}
		if (index!=this.mappingDesBiens.get(0).getBiensSupports().nombreDeBiens()){
			return index;
		}
		else {
			throw new Exception("bien support pas present");
		}
	}
	
	public ArrayList<Bien> getBiensEssentielsCorrespondant(Bien bienSupport) throws Exception{
		ArrayList<Bien> biens = new ArrayList<Bien>();
		int indexBien = this.getIndexBienSupport(bienSupport);
		for (int i=0; i<this.mappingDesBiens.size(); i++){
			if (this.getMappingDesBiens().get(i).getMappingBien().get(indexBien).equals("x")){
				biens.add(this.getMappingDesBiens().get(i).getBienEssentiel());
			}
		}
		return biens;
	}
	
	public void actualiserMapping(){
		if (this.mappingDesBiens.size()!=this.biensEssentiels.getBiensRetenus().size() 
			|| this.mappingDesBiens.get(0).getMappingBien().size()!=this.biensSupports.getBiensRetenus().size()){
			LinkedList<MappingBien> nouveauMapping = new LinkedList<MappingBien>();
			
			for (int i=0; i<this.biensEssentiels.nombreDeBiens();i++){
				if (this.biensEssentiels.getBien(i).isRetenu()){
					nouveauMapping.add(new MappingBien(this.biensSupports,this.biensEssentiels.getBien(i)));
				}	
			}
			
			String bienEssentiel = "";
			String bienSupport = "";
			String value = "";
			for (int i=0; i<nouveauMapping.size();i++){
				for (int j=0; j<nouveauMapping.get(0).getMappingBien().size();j++){
					bienEssentiel = nouveauMapping.get(i).getBienEssentiel().getIntitule();
					bienSupport = nouveauMapping.get(i).getBiensSupports().getBien(j).getIntitule();
					for (int k=0; k<this.getMappingDesBiens().size();k++){
						if (this.getMappingDesBiens().get(k).getBienEssentiel().equals(bienEssentiel)){
							value = this.getMappingDesBiens().get(k).getCase(bienEssentiel, bienSupport);
						}
						if (!value.equals("")){
							nouveauMapping.get(i).getMappingBien().set(j, value);	
						}
					}
					value = "";
				}
			}
			
			this.mappingDesBiens = nouveauMapping;
		}

	}
	
	public String toString(){
		return "Mapping des biens";
	}
	
	public boolean estCoherent(){
		this.actualiserMapping();
		boolean resultat = true;
		this.erreurs = new ArrayList<String>();
		for(MappingBien m : this.getMappingDesBiens()){
			if(!m.estComplet() && this.biensEssentiels.getBien(m.getBienEssentiel().getIntitule())!=null){
				this.erreurs.add("le bien essentiel \" " + m.getBienEssentiel() + " \" ne correspond a aucun bien support");
				resultat = false;
			}
		}	
		return resultat;
	}
}
