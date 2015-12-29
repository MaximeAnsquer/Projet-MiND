package presentation;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import abstraction.Etude;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.TypologieDesBiensSupports;

public class MainAli extends JFrame {
	
	/**
	 * Classe destinée à tester les modules
	 * @author Belghiti Ali
	 *
	 */
	
	public MainAli() {
		super("tests");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		//this.pack();
	}
	
	public static Etude etude ;
	
	public static void main(String[] args) {
		
		MainAli mainTest= new MainAli();
		etude = new Etude();
		
		TypologieDesBiensSupports firstModule = new TypologieDesBiensSupports();
		etude.addModule(firstModule);
		
		CriteresDeSecurite moduleIntermed = new CriteresDeSecurite();
		etude.addModule(moduleIntermed);
		
		ScenariosDeMenacesGeneriques secondModule = new ScenariosDeMenacesGeneriques();
		etude.addModule(secondModule);
		
		String module1 = "Typologie des biens supports";
		String module2 = "Scénario de menaces génériques";
		String module3 = "Scenario de menaces typés" ;
				
		String[] choix = {module1,module2,module3};
		String reponse = (String) JOptionPane.showInputDialog(null,
				"Quelle module voulez-vous ouvrir ?", "Test : Choix du module",
				JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
		
		if (reponse==choix[0]){
			FenetreTypologieBiensSupports fen1 = new FenetreTypologieBiensSupports(firstModule);
			mainTest.add(fen1);
			mainTest.pack();
		}
		if (reponse==choix[1]){
			FenetreScenarioDeMenacesGeneriques fen2 = new FenetreScenarioDeMenacesGeneriques(secondModule);
			mainTest.add(fen2);
			mainTest.pack();
		}
		if (reponse==choix[2]){
			FenetreScenarioDeMenacesTypes fen3 = new FenetreScenarioDeMenacesTypes();
		}
	}
}
