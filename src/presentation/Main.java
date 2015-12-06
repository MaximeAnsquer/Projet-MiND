package presentation;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import abstraction.Etude;
import abstraction.modules.BiensEssentiels;
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
		etude.addModule(new BiensEssentiels());
		
		//---Création de la fenêtre CriteresDeSecurite
		String[] choix = {"critere de securite","biens essentiels"};
	    String reponse = (String)JOptionPane.showInputDialog(null, "Quelle fenetre voulez-vous ?", "Choix fenetre",JOptionPane.QUESTION_MESSAGE,  null, choix, choix[0]);
		if (reponse == "critere de securite"){
			FenetreCriteresDeSecurite2 f = new FenetreCriteresDeSecurite2();
		}
		else{
			FenetreBiensEssentiels f2 = new FenetreBiensEssentiels();
		}
	}

}
