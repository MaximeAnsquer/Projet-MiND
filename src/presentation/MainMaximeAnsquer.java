package presentation;

import abstraction.Etude;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.Metriques;

/**
 * Classe brouillon pour faire des tests
 * @author Maxime Ansquer
 *
 */

public class MainMaximeAnsquer {

	/**
	 * L'�tude en cours
	 */
	public static Etude etude;

	public static void main(String[] args) {

		//---Cr�ation de l'�tude---

		etude = new Etude();
		etude.addModule(new CriteresDeSecurite());
		etude.addModule(new Metriques(etude));

		//---Cr�ation de la fen�tre CriteresDeSecurite

		FenetreCriteresDeSecurite2 f = new FenetreCriteresDeSecurite2();
		
		//---Creation de le fenetre Metrique---
		
		FenetreMetriques fMetriques = new FenetreMetriques();
		
	}
}

