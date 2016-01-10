package abstraction.modules;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JLabel;

import abstraction.Etude;

/** 
 * Classe visant ï¿½ factoriser tout ce qui est commun aux diffï¿½rents modules.
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
		
		System.out.println(" \n ------------------- \n");
		
		System.out.println("Vérification de la disponibilité de " + this + "...");
		boolean resultat = true;
		System.out.println("predecesseurs : " + this.getPredecesseurs() );
		
		
		if(this.getNom()=="AnalyseDesRisques"){
			resultat=this.getEtude().getModule("EvenementsRedoutes").estCoherent()&&this.getEtude().getModule("EvenementsRedoutes").estCree();
		    resultat=resultat&&this.getEtude().getModule("MappingDesBiens").estCoherent()&&this.getEtude().getModule("MappingDesBiens").estCree();
		    resultat=resultat&&this.getEtude().getModule("ScenariosDeMenacesTypes").estCoherent()&&this.getEtude().getModule("ScenariosDeMenacesTypes").estCree();
		    System.out.println(this.getNom());
		}
		else  if(this.getNom()=="MatriceDesRisques"){
			resultat=this.getEtude().getModule("AnalyseDesRisques").estCoherent()&&this.getEtude().getModule("AnalyseDesRisques").estCree();
			System.out.println(this.getNom());
		}
		
		
		
		else{ 
			
		for(Module m : this.getPredecesseurs()){
			if(m.estCree()){
				System.out.println("Le predecesseur " + m + " est cree");
			}
			else{
				System.out.println("Le predecesseur " + m + " n'est pas cree");
			}
			if(m.estCoherent()){
				System.out.println("Le predecesseur " + m + " est coherent");
			}
			else{
				System.out.println("Le predecesseur " + m + " n'est pas coherent");
			}
			resultat = resultat && m.estCree() && m.estCoherent();
		}}
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
