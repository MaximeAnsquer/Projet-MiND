package modules;

/** 
 * Interface repr�sentant un module.
 * @author Maxime Ansquer
 */

public interface IModule {
	boolean estCoherent();
	boolean estCree();
	boolean estDisponible();
	String getNom();
}
