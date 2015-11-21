package abstraction.modules;
import java.util.ArrayList;

import abstraction.Analyse;

/** 
 * Classe visant à factoriser tout ce qui est commun aux différents modules.
 * @author Maxime Ansquer 
 */

public class Module implements IModule{
	
	//---Variables d'instance---
	
	protected ArrayList<Module> predecesseurs; 
	protected ArrayList<Module> successeurs; 
	protected String nom;
	protected boolean cree;
	protected boolean coherent;
	protected boolean disponible;
	protected Analyse analyse;
	
	//---Constructeurs---
	
	public Module(String nom) {
		this.predecesseurs=new ArrayList<Module>();
		this.successeurs=new ArrayList<Module>();
		this.nom=nom;
		this.cree = false;
	}
	
	//---Getters et setters---
	
	@Override
	public boolean estCoherent() {
		return this.coherent;
	}

	@Override
	public boolean estCree() {
		return this.cree;
	}

	@Override
	public boolean estDisponible() {
		boolean resultat = true;
		for(Module module : this.getPredecesseurs()){
			resultat = resultat & module.estCree();
		}
		return resultat;
	}

	@Override
	public String getNom() {
		return this.nom;
	} 
	
	public ArrayList<Module> getPredecesseurs(){
		return this.predecesseurs;
	}
	
	public ArrayList<Module> getSuccesseurs(){
		return this.successeurs;
	}
	
	public Analyse getAnalyse(){
		return this.analyse;
	}
	
	public void setAnalyse(Analyse analyse){
		this.analyse = analyse;
	}

}
