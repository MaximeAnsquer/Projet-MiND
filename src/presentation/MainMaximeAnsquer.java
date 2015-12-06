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
	 * L'étude en cours
	 */
	public static Etude etude;

	public static void main(String[] args) {

		//---Création de l'étude---

		etude = new Etude();
		etude.addModule(new CriteresDeSecurite());
		etude.addModule(new Metriques(etude));

		//---Création de la fenêtre CriteresDeSecurite

		FenetreCriteresDeSecurite2 f = new FenetreCriteresDeSecurite2();
		
		//---Creation de le fenetre Metrique---
		
		FenetreMetriques fMetriques = new FenetreMetriques();
		
	}
}

