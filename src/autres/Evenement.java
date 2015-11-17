package autres;

import modules.BienEssentiels;

public class Evenement {
	
	private Biens bienessentiels;
	private Critere criteres;
	private int metrique;
	private int gravite;
	
	private Evenement(Biens bienessentiels, Critere criteres,int metrique,int gravite){
		this.bienessentiels=bienessentiels;
		this.criteres=criteres;
		this.gravite=gravite;
		this.metrique=metrique;
	}
	
	public Biens getBiens(){
		return this.bienessentiels;
	}
	
	public Critere getCriteres(){
		return this.criteres;
		
		
	}
	
	public int getMetrique(){
		return this.metrique;
		
		
	}
	public int getGravite(){
		return this.gravite;
	}

}
