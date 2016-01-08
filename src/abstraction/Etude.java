package abstraction;
import java.util.Hashtable;

import abstraction.modules.AnalyseDesRisques;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.EvenementsRedoutes;
import abstraction.modules.MappingDesBiens;
import abstraction.modules.Metriques;
import abstraction.modules.Module;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;
import abstraction.modules.TypologieDesBiensSupports;

public class Etude {
	
	//---Variables d'instance
	
	/**
	 * La Hashtable est referencee par le nom de la classe du module correspondant ; 
	 * exemple : "CriteresDeSecurite "
	 */
	private Hashtable<String, Module> lesModules;
	private String nomEtude;
	
	//---Constructeurs---
	
	/**
	 * Une nouvelle etude remplie de nouveaux modules
	 * @param nomEtude
	 */
	public Etude(String nomEtude){
		
		this.nomEtude = nomEtude;
		this.lesModules = new Hashtable<String, Module>();
		
		this.addModule(new CriteresDeSecurite());		
		this.addModule(new SourcesDeMenaces());
		this.addModule(new TypologieDesBiensSupports());
		this.addModule(new Metriques(this));
		this.addModule(new BiensSupports(this));
		this.addModule(new BiensEssentiels(this));
		this.addModule(new MappingDesBiens(this));
		this.addModule(new EvenementsRedoutes(this));
		this.addModule(new ScenariosDeMenacesGeneriques(this));
		this.addModule(new ScenariosDeMenacesTypes(this));
		this.addModule(new AnalyseDesRisques(this));
	}
	
	public Etude(){
		this("Etude fictive");
	}
	
	//---Getters et setters---
	
	public Hashtable<String,Module> getLesModules(){
		return lesModules;
	}	
	
	public void setLesModules(Hashtable<String,Module> lesModules) {
		this.lesModules = lesModules;
	}
	
	public String getNom(){
		return this.nomEtude;
	}
	
	public void setNom(String nomEtude){
		this.nomEtude = nomEtude;
	}
	
	//---Services---

	public void addModule(Module module){
		this.lesModules.put(module.getNom(), module);
		module.setEtude(this);
	}

	/**
	 * @param nomDuModule exemple : "CriteresDeSecurite"
	 * @return
	 */
	public Module getModule(String nomDuModule) {		
		return this.getLesModules().get(nomDuModule);		
	}

}
