package presentation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import abstraction.Etude;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.MappingDesBiens;
import abstraction.modules.Metriques;
import abstraction.modules.Module;
import abstraction.modules.SourcesDeMenaces;

/**
 * Classe pour lancer les modules BiensEssentiels, BiensSupports et MappingDesBiens
 * @author Francois Adam
 *
 */

public class MainFrancois extends JFrame{
	public static Etude etude;
	
	public MainFrancois(){
		super("main");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args) {
		MainFrancois main = new MainFrancois();
		etude = new Etude();
		etude.addModule(new BiensEssentiels());
		etude.addModule(new BiensSupports());
		etude.addModule(new MappingDesBiens());
		
		String[] choix = {"biens supports","biens essentiels","Mapping des biens"};
	    String reponse = (String)JOptionPane.showInputDialog(null, "Quelle fenetre voulez-vous ?", "Choix fenetre",JOptionPane.QUESTION_MESSAGE,  null, choix, choix[0]);
		if (reponse == "biens supports"){
			FenetreBiensSupports f1 = new FenetreBiensSupports();
			main.add(f1);
			main.pack();
		}
		else{
			if (reponse == "biens essentiels"){
				FenetreBiensEssentiels f2 = new FenetreBiensEssentiels();
				main.add(f2);
				main.pack();
			}
			else{
				FenetreMappingDesBiens f3 = new FenetreMappingDesBiens();
				main.add(f3);
				main.pack();
			}
		}
	}
}
