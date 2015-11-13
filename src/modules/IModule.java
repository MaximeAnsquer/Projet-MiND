package modules;

/** 
 * Interface représentant un module.
 * @author Maxime Ansquer
 */

public interface IModule {
	boolean estCoherent();
	boolean estCree();
	boolean estDisponible();
	String getNom();
}
