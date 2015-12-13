package presentation;

import abstraction.Etude;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.Metriques;
import abstraction.modules.SourcesDeMenaces;

/**
 * Classe brouillon pour faire des tests
 * @author Maxime Ansquer
 *
 */

public class MainMaximeAnsquer {

	/**
	 * L'etude en cours
	 */
	public static Etude etude;

	public static void main(String[] args) {

		//---Creation de l'etude---

		etude = new Etude();
		etude.addModule(new CriteresDeSecurite());
		etude.addModule(new Metriques(etude));
		etude.addModule(new SourcesDeMenaces());

		//---Creation de la fenetre Criteres de securite

		FenetreCriteresDeSecurite f = new FenetreCriteresDeSecurite();
		
		//---Creation de le fenetre Metrique---
		
		FenetreMetriques fMetriques = new FenetreMetriques();
		
		//---Creation de le fenetre Sources de menaces---
		
		FenetreSourcesDeMenaces source = new FenetreSourcesDeMenaces();
		
	}
}

