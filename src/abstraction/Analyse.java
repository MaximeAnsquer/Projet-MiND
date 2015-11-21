package abstraction;
import java.util.ArrayList;
import java.util.Hashtable;
import abstraction.modules.Module;

public class Analyse {
	
	private Hashtable<String,Module> lesModules;	
	
	public Hashtable<String,Module> getLesModules(){
		return lesModules;
	}	
	
	public void setLesModules(Hashtable<String,Module> lesModules) {
		this.lesModules = lesModules;
	}

	public void addModule(Module module){
		this.lesModules.put(module.getNom(), module);
		//module.setAnalyse(this);
	}

	public Module getModule(String nomDuModule) {
		// TODO Auto-generated method stub
		return null;
	}

}
