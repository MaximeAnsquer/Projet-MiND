package presentation;

import javax.swing.JOptionPane;

import abstraction.Etude;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.TypologieDesBiensSupports;

public class MainAli {
	
	/**
	 * Classe destinée à tester les modules
	 * @author Belghiti Ali
	 *
	 */
	
	public static Etude etude ;
	
	public static void main(String[] args) {
		
		//etude.addModule(new TypologieDesBiensSupports());
		//etude.addModule(new ScenariosDeMenacesGeneriques());
		
		String module1 = "Typologie des biens supports";
		String module2 = "Scénario de menaces génériques";
		String module3 = "Scenario de menaces typés" ;
				
		String[] choix = {module1,module2,module3};
		String reponse = (String) JOptionPane.showInputDialog(null,
				"Quelle module voulez-vous ouvrir ?", "Test : Choix du module",
				JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
		
		if (reponse==choix[0]){
			FenetreTypologieBiensSupports fen1 = new FenetreTypologieBiensSupports();
		}
		if (reponse==choix[1]){
			FenetreScenarioDeMenacesGeneriques fen2 = new FenetreScenarioDeMenacesGeneriques();
		}
	}
}
