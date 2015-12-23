package presentation;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import abstraction.Etude;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.MappingDesBiens;

/**
 * Classe pour lancer les modules BiensEssentiels, BiensSupports et MappingDesBiens
 * @author Francois Adam
 *
 */

public class MainFrancois extends JFrame{

	private static final long serialVersionUID = 1L;
	public static Etude etude;
	
	public MainFrancois(){
		super("main");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args) {
		MainFrancois main = new MainFrancois();
		//etude = new Etude();
		BiensEssentiels biensEssentiels = new BiensEssentiels();
		BiensSupports biensSupports = new BiensSupports();
		MappingDesBiens mapping = new MappingDesBiens(biensSupports,biensEssentiels);
		//etude.addModule(biensEssentiels);
		//etude.addModule(biensSupports);
		//etude.addModule(mapping);
		
		String[] choix = {"biens supports","biens essentiels","Mapping des biens"};
	    String reponse = (String)JOptionPane.showInputDialog(null, "Quelle fenetre voulez-vous ?", "Choix fenetre",JOptionPane.QUESTION_MESSAGE,  null, choix, choix[0]);
		if (reponse == "biens supports"){
			FenetreBiensSupports f1 = new FenetreBiensSupports(biensSupports);
			main.add(f1);
			main.pack();
		}
		else{
			if (reponse == "biens essentiels"){
				FenetreBiensEssentiels f2 = new FenetreBiensEssentiels(biensEssentiels);
				main.add(f2);
				main.pack();
			}
			else{
				FenetreMappingDesBiens f3 = new FenetreMappingDesBiens(mapping);
				main.add(f3);
				main.pack();
			}
		}
	}
}
