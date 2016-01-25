package abstraction.autres;

import java.util.ArrayList;
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
	private ArrayList<SourceDeMenace> menaces;
	private Hashtable<String,Boolean> criteresTypes;
	private Bien bienSupport; // Il s'agit d'un bien support ici
	private String intituleConcret;
	private int vraisemblanceIntrinseque;
	private int vraisemblanceReelle;
	
	public ScenarioType(TypeBien type, String id, String intitule,
			Hashtable<String, Boolean> criteres,
			ArrayList<SourceDeMenace> menaces, Bien bienSupport,
			String intituleConcret, int intrinseque, int reelle, boolean retenu) {
		
		super(type,id,intitule,criteres,true);
		this.menaces=menaces;
		this.criteresTypes=criteres;
		this.bienSupport=bienSupport;
		this.intituleConcret=intituleConcret;
		this.vraisemblanceIntrinseque=intrinseque;
		this.vraisemblanceReelle=reelle;
	}
	
	public ScenarioType(TypeBien type, String id, String intitule,
			Hashtable<String, Boolean> criteres,
			ArrayList<SourceDeMenace> menaces, Bien bienSupport, boolean retenu) {
		
		super(type,id,intitule,criteres,retenu);
		this.menaces=menaces;
		this.criteresTypes=criteres;
		this.bienSupport=bienSupport;
		this.intituleConcret="";
		this.vraisemblanceIntrinseque=1;
		this.vraisemblanceReelle=1;
	}
	
	public ScenarioType(ScenarioType scenario) {
		this(scenario.getTypeBienSupport(), scenario.getId(), scenario
				.getIntitule(), scenario.getCriteresTypes(), scenario
				.getMenaces(), scenario.getBienSupport(), true);
	}
	
	public ScenarioType(){
		super(new TypeBien(),"","",new Hashtable<String, Boolean>(),true);
		this.menaces=new ArrayList<SourceDeMenace>();
		this.criteresTypes=new Hashtable<String, Boolean>();
		this.intituleConcret="";
		this.vraisemblanceIntrinseque=1;
		this.vraisemblanceReelle=1;
	}
	
	public ArrayList<SourceDeMenace> getMenaces() {
		return menaces;
	}

	public void setMenaces(ArrayList<SourceDeMenace> menaces) {
		this.menaces = menaces;
	}
	
	public Hashtable<String, Boolean> getCriteresTypes() {
		return this.criteresTypes;
	}

	public void setCriteresTypes(Hashtable<String, Boolean> criteresSup) {
		this.criteresTypes = criteresSup;
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
			for (SourceDeMenace sourceMenace : this.menaces){
				s=s+sourceMenace.getId() + ", ";
			}
		}
		// On supprime le dernier charactère qui est une virgule
		s=s.substring(0, s.length()-2);
		return s ;
	}
	
	public Boolean isIncompleteType(){
		return super.isIncomplete() || this.intituleConcret.equals("") ;
	}

}
