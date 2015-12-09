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
	 * L'étude en cours
	 */
	public static Etude etude;
	
	public static void main(String[] args) {
		
		//---Création de l'étude---
		
		etude = new Etude();
		etude.addModule(new BiensEssentiels());
		
		//---Création de la fenêtre Biens Essentiels
        FenetreBiensEssentiels f2 = new FenetreBiensEssentiels();
	}

}
