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
	 * L'�tude en cours
	 */
	public static Etude etude;
	
	public static void main(String[] args) {
		
		//---Cr�ation de l'�tude---
		
		etude = new Etude();
		etude.addModule(new CriteresDeSecurite());
		
		//---Cr�ation de la fen�tre CriteresDeSecurite
		
		FenetreCriteresDeSecurite f = new FenetreCriteresDeSecurite();
		
	}

}
