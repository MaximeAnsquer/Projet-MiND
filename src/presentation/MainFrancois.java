package presentation;

import abstraction.Etude;
import abstraction.modules.BiensEssentiels;

/**
 * Classe brouillon pour faire des tests
 * @author Maxime Ansquer
 *
 */

public class MainFrancois {
	
	/**
	 * L'�tude en cours
	 */
	public static Etude etude;
	
	public static void main(String[] args) {
		
		//---Cr�ation de l'�tude---
		
		etude = new Etude();
		etude.addModule(new BiensEssentiels());
		
		//---Cr�ation de la fen�tre Biens Essentiels
        FenetreBiensEssentiels f2 = new FenetreBiensEssentiels();
	}

}
