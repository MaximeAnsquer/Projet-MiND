package abstraction.autres;

public class Risque {
	
	
	private String intitule;
	private Evenement evenementredoute;
	private int niveaugravite;
	private Bien biensupport;
	private ScenarioDeMenaceType scenarioconcret;
	private int niveauvraisemblance;
	
	
	
	public Risque(String intitule,Evenement evenementredoute,int niveaugravite,Bien biensupport,ScenarioDeMenaceType scenarioconcret,int niveauvraisemblance){
		this.intitule=intitule;
		this.evenementredoute=evenementredoute;
		this.niveaugravite=niveaugravite;
		this.biensupport=biensupport;
		this.scenarioconcret=scenarioconcret;
		this.niveauvraisemblance=niveauvraisemblance;
		
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

}
