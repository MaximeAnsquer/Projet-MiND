package abstraction.autres;

import javax.swing.JCheckBox;

import abstraction.modules.ScenariosDeMenacesTypes;

public class Risque {
	/*Cette classe donne une ligne du tableau AnalyseDesRisques en faisant correspondre divers éléments définis plus tôt
	 * et en leur allouant un intitulé pour désigner chaque risque.
	 */
	
	
	private String intitule;
	private Evenement evenementredoute;
	private int niveaugravite;
	private Bien biensupport;
	private ScenarioType scenarioconcret;
	private int niveauvraisemblance;
	private boolean estretenu;
	
	
	
	public Risque(String intitule,Evenement evenementredoute,int niveaugravite,Bien biensupport,ScenarioType scenarioconcret,int niveauvraisemblance){
		this.intitule=intitule;
		this.evenementredoute=evenementredoute;
		this.niveaugravite=niveaugravite;
		this.biensupport=biensupport;
		this.scenarioconcret=scenarioconcret;
		this.niveauvraisemblance=niveauvraisemblance;
		this.estretenu=true;
		
	}
	
	
	public String getIntitule(){
		return this.intitule;
	}
	
	public void setIntitule(String intitule){
		this.intitule=intitule;
	}
	
	public int getNiveauGravite(){
		return this.niveaugravite;
		
		
	}
	public int getNiveauVraisemblance(){
		return this.niveauvraisemblance;
	}
	
	public boolean getRetenu(){
		return this.estretenu;
		
	}
	
	public ScenarioType getScenarioConcret(){
		return this.scenarioconcret;
	}

	public Bien getBienSupport(){
		return this.biensupport;
	}
	
	public Evenement getEvenementRedoute(){
		return this.evenementredoute;
	}
}
