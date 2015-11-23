package abstraction.autres;

public class Risque {
	
	
	private String intitule;
	private Evenement evenementredoute;
	private Metrique gravite;
	private Bien biensupport;
	private Scenario scenarioconcret;
	private Metrique vraisemblance;
	
	
	
	public Risque(String intitule,Evenement evenementredoute,Metrique gravite,Bien biensupport,Scenario scenarioconcret,Metrique vraisemblance){
		this.intitule=intitule;
		this.evenementredoute=evenementredoute;
		this.gravite=gravite;
		this.biensupport=biensupport;
		this.scenarioconcret=scenarioconcret;
		this.vraisemblance=vraisemblance;
		
	}

}
