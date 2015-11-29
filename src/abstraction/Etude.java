package abstraction;
import java.util.ArrayList;
import java.util.Hashtable;
import abstraction.modules.Module;

public class Etude {
	
	//---Variables d'instance
	
	/**
	 * La Hashtable est referencee par le nom de la classe du module correspondant ; 
	 * exemple : "CriteresDeSecurite "
	 */
	private Hashtable<String, Module> lesModules;
	
	//---Constructeurs---
	
	public Etude(){
		this.lesModules = new Hashtable<String, Module>();
	}
	
	//---Getters et setters---
	
	public Hashtable<String,Module> getLesModules(){
		return lesModules;
	}	
	
	public void setLesModules(Hashtable<String,Module> lesModules) {
		this.lesModules = lesModules;
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
