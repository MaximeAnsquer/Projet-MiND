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
	 * L'�tude en cours
	 */
	public static Etude etude;

	public static void main(String[] args) {

		//---Cr�ation de l'�tude---

		etude = new Etude();
		etude.addModule(new CriteresDeSecurite());
		etude.addModule(new Metriques(etude));
		etude.addModule(new SourcesDeMenaces());

		//---Cr�ation de la fen�tre Criteres de securite

		FenetreCriteresDeSecurite2 f = new FenetreCriteresDeSecurite2();
		
		//---Creation de le fenetre Metrique---
		
		FenetreMetriques fMetriques = new FenetreMetriques();
		
		//---Creation de le fenetre Sources de menaces---
		
		FenetreSourcesDeMenaces source = new FenetreSourcesDeMenaces();
		
	}
}

