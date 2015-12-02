package presentation;

import abstraction.Etude;
import abstraction.modules.CriteresDeSecurite;

/**
 * Classe brouillon pour faire des tests
 * @author Maxime Ansquer
 *
 */

public class Main {
	
	/**
	 * L'étude en cours
	 */
	public static Etude etude;
	
	public static void main(String[] args) {
		
		//---Création de l'étude---
		
		etude = new Etude();
		etude.addModule(new CriteresDeSecurite());
		
		//---Création de la fenêtre CriteresDeSecurite
		
		FenetreCriteresDeSecurite f = new FenetreCriteresDeSecurite();
		
	}

}
