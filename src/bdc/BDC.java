package bdc;

public class BDC implements IBDC {
	
	//---Variables d'instance---
	
	protected String nom;
	
	//---Constructeurs---
	
	public BDC(String nom){
		this.nom=nom;
	}
	
	//---Getters et setters
	
	public String getNom(){
		return this.nom;
	}

}
