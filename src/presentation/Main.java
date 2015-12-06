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
	 * L'�tude en cours
	 */
	public static Etude etude;
	
	public static void main(String[] args) {
		
		//---Cr�ation de l'�tude---
		
		etude = new Etude();
		etude.addModule(new CriteresDeSecurite());
		etude.addModule(new BiensEssentiels());
		
		//---Cr�ation de la fen�tre CriteresDeSecurite
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
