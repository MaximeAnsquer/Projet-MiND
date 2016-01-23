package abstraction.modules;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JLabel;

import abstraction.Etude;

/** 
 * Classe visant � factoriser tout ce qui est commun aux diff�rents modules.
 * @author Maxime Ansquer 
 */

public class Module extends Observable implements IModule  {

	//---Variables d'instance---

	protected ArrayList<Module> predecesseurs; 
	protected ArrayList<Module> successeurs; 
	protected String nom;
	protected boolean cree;
	protected boolean coherent;
	protected boolean disponible;
	protected Etude etude;
	protected ArrayList<String> problemesDeCoherence;

	//---Constructeurs---

	public Module(String nom) {
		this.predecesseurs=new ArrayList<Module>();
		this.successeurs=new ArrayList<Module>();
		this.nom=nom;
		this.cree = false;
		this.problemesDeCoherence = new ArrayList<String>();
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

	public void setCree(boolean cree){
		this.cree=cree;
	}

	public void setCoherent(){
		this.coherent=true;
	}

	public boolean estDisponible(){
		
		
		boolean resultat = true;		
		/*
		if(this.getNom().equals("AnalyseDesRisques")){
			
			resultat=this.getEtude().getModule("EvenementsRedoutes").estCoherent()&&this.getEtude().getModule("EvenementsRedoutes").estCree();
		    resultat=resultat&&this.getEtude().getModule("MappingDesBiens").estCoherent()&&this.getEtude().getModule("MappingDesBiens").estCree();
		    resultat=resultat&&this.getEtude().getModule("ScenariosDeMenacesTypes").estCoherent()&&this.getEtude().getModule("ScenariosDeMenacesTypes").estCree();
		    
		}
		else  if(this.getNom().equals("MatriceDesRisques")){
			resultat=this.getEtude().getModule("AnalyseDesRisques").estCoherent()&&this.getEtude().getModule("AnalyseDesRisques").estCree();
		}
		
		
		
		else{ */
			
		for(Module m : this.getPredecesseurs()){
			
			resultat = resultat && m.estCree() && m.estCoherent();
		}
		this.disponible = resultat;
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

	public ArrayList<String> getProblemes() {
		return this.problemesDeCoherence;
	}	

	

}
