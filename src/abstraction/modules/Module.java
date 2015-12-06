package abstraction.modules;
import java.util.ArrayList;

import abstraction.Etude;

/** 
 * Classe visant � factoriser tout ce qui est commun aux diff�rents modules.
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
	protected Etude etude;
	
	//---Constructeurs---
	
	public Module(String nom) {
		this.predecesseurs=new ArrayList<Module>();
		this.successeurs=new ArrayList<Module>();
		this.nom=nom;
		this.cree = false;
	}
	
	//---Getters et setters---
	
	public boolean estCoherent() {
		return this.coherent;
	}

	public boolean estCree() {
		return this.cree;
	}
	
	public void setDisponible(){
		this.disponible=true;
	}
	
	public void setCree(){
		this.cree=true;
	}
	
	public void setCoherent(){
		this.coherent=true;
	}

	public boolean estDisponible() {
		boolean resultat = true;
		for(Module module : this.getPredecesseurs()){
			resultat = resultat & module.estCree();
		}
		return resultat;
	}

	public String getNom() {
		return this.nom;
	} 
	
	public ArrayList<Module> getPredecesseurs(){
		return this.predecesseurs;
	}
	
	public ArrayList<Module> getSuccesseurs(){
		return this.successeurs;
	}
	
	public Etude getEtude(){
		return this.etude;
	}
	
	public void setEtude(Etude etude){
		this.etude = etude;
	}	

}
