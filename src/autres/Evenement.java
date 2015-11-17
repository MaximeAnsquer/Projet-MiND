package autres;

import java.util.ArrayList;

import modules.BienEssentiels;

public class Evenement {
	
	private ArrayList<Biens> bienessentiels;
	private ArrayList<Critere> criteres;
	private ArrayList<Metrique> exigence;
	private ArrayList<Metrique> gravite;
	
	
	private Evenement(ArrayList<Biens> bienessentiels, ArrayList<Critere> criteres,ArrayList<Metrique> exigence,ArrayList<Metrique> gravite){
		this.bienessentiels=bienessentiels;
		this.criteres=criteres;
		this.exigence=exigence;
		this.gravite=gravite;
	}
	
	
	
	
}
