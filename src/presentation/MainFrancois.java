package presentation;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import abstraction.Etude;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.CriteresDeSecurite;

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
		etude.addModule(new BiensSupports());
		
		//---Cr�ation de la fen�tre CriteresDeSecurite
		String[] choix = {"biens supports","biens essentiels"};
	    String reponse = (String)JOptionPane.showInputDialog(null, "Quelle fenetre voulez-vous ?", "Choix fenetre",JOptionPane.QUESTION_MESSAGE,  null, choix, choix[0]);
		if (reponse == "biens supports"){
			FenetreBiensSupports f1 = new FenetreBiensSupports();
		}
		else{
			FenetreBiensEssentiels f2 = new FenetreBiensEssentiels();
		}
	}

}
