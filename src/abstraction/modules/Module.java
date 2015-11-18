package abstraction.modules;
import java.util.ArrayList;

/** 
 * Classe visant à factoriser tout ce qui est commun aux différents modules.
 * @author Maxime Ansquer 
 */

public class Module implements IModule{
	
	//---Variables d'instance---
	
	protected ArrayList<Module> predecesseurs; 
	protected ArrayList<Module> successeurs; 
	protected String nom;
	protected boolean cree;
	protected boolean coherent;
	protected boolean disponible;
	
	//---Constructeurs---
	
	public Module(String nom) {
		this.predecesseurs=new ArrayList<>();
		this.successeurs=new ArrayList<>();
		this.nom=nom ;
	}
	
	//---Getters et setters---
	
	@Override
	public boolean estCoherent() {
		return this.coherent;
	}

	@Override
	public boolean estCree() {
		return this.cree;
	}

	@Override
	public boolean estDisponible() {
		return this.disponible;
	}

	@Override
	public String getNom() {
		return this.nom;
	} 
	
	public ArrayList getPredecesseurs(){
		return this.predecesseurs;
	}
	
	public ArrayList getSuccesseurs(){
		return this.successeurs;
	}

}
