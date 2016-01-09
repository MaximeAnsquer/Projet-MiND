package presentation;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import abstraction.Etude;
import abstraction.modules.BiensSupports;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.Metriques;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;
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
		
		ScenariosDeMenacesGeneriques secondModule = new ScenariosDeMenacesGeneriques(etude);
		etude.addModule(secondModule);
		
		BiensSupports moduleBiens = new BiensSupports(etude);
		etude.addModule(moduleBiens);
		
		Metriques moduleMetriques = new Metriques(etude);
		etude.addModule(moduleMetriques);
		
		SourcesDeMenaces moduleSources = new SourcesDeMenaces();
		etude.addModule(moduleSources);
		
		ScenariosDeMenacesTypes dernierModule = new ScenariosDeMenacesTypes(etude);
		etude.addModule(dernierModule);
		
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
			FenetreScenariosDeMenacesGeneriques fen2 = new FenetreScenariosDeMenacesGeneriques(secondModule);
			mainTest.add(fen2);
			mainTest.pack();
		}
		if (reponse==choix[2]){
			FenetreScenarioDeMenacesTypes fen3 = new FenetreScenarioDeMenacesTypes(dernierModule);
			mainTest.add(fen3);
			mainTest.pack();
		}
	}
}
