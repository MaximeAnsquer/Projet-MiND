package abstraction.autres;

import java.util.Hashtable;

import abstraction.modules.BiensSupports;

/**
 * Classe representant les scénarios de menaces typés. Cette classe étend la
 * classe ScenarioGenerique : un scénario typé n'est qu'un cas particulier de
 * scénario générique auquel on ajoute des précisions. La classe représente plus
 * généralement une ligne du tableau généré dans "Scenario De Menaces Typés"
 * 
 * @author Belghiti Ali
 */

public class ScenarioType extends ScenarioGenerique {
	private Hashtable<String, SourceDeMenace> menaces;
	private Bien bienSupport; // Il s'agit d'un bien support ici
	private String intituleConcret;
	private int vraisemblanceIntrinseque;
	private int vraisemblanceReelle;
	
	public ScenarioType(String type, String id, String intitule,
			Hashtable<String, Boolean> criteres,
			Hashtable<String, SourceDeMenace> menaces, Bien bienSupport,
			String intituleConcret, int intrinseque, int reelle, boolean retenu) {
		
		super(type,id,intitule,criteres,true);
		this.menaces=menaces;
		this.bienSupport=bienSupport;
		this.intituleConcret=intituleConcret;
		this.vraisemblanceIntrinseque=intrinseque;
		this.vraisemblanceReelle=reelle;
	}
	
	public ScenarioType(String type, String id, String intitule,
			Hashtable<String, Boolean> criteres,
			Hashtable<String, SourceDeMenace> menaces, Bien bienSupport, boolean retenu) {
		
		super(type,id,intitule,criteres,true);
		this.menaces=menaces;
		this.bienSupport=bienSupport;
		this.intituleConcret="";
		this.vraisemblanceIntrinseque=1;
		this.vraisemblanceReelle=1;
	}
	
	public ScenarioType(){
		super("","","",new Hashtable<String, Boolean>(),true);
		this.menaces=new Hashtable<String, SourceDeMenace>();
		this.intituleConcret="";
		this.vraisemblanceIntrinseque=1;
		this.vraisemblanceReelle=1;
	}
	
	public Hashtable<String, SourceDeMenace> getMenaces() {
		return menaces;
	}

	public void setMenaces(Hashtable<String, SourceDeMenace> menaces) {
		this.menaces = menaces;
	}

	public Bien getBienSupport() {
		return bienSupport;
	}

	public void setBienSupport(Bien bienSupport) {
		this.bienSupport = bienSupport;
	}

	public String getIntituleConcret() {
		return intituleConcret;
	}

	public void setIntituleConcret(String intituleConcret) {
		this.intituleConcret = intituleConcret;
	}

	public int getVraisemblanceIntrinseque() {
		return vraisemblanceIntrinseque;
	}

	public void setVraisemblanceIntrinseque(int vraisemblanceIntrinseque) {
		this.vraisemblanceIntrinseque = vraisemblanceIntrinseque;
	}

	public int getVraisemblanceReelle() {
		return vraisemblanceReelle;
	}

	public void setVraisemblanceReelle(int vraisemblanceReelle) {
		this.vraisemblanceReelle = vraisemblanceReelle;
	}
	
	public String listeSourcesMenaces(){
		String s="";
		if(this.menaces!=null){
			for (SourceDeMenace sourceMenace : this.menaces.values()){
				s=s+sourceMenace.getId() + ", ";
			}
		}
		return s ;
	}

}
