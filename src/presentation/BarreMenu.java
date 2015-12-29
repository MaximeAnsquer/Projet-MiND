package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * 
 * @author Maxime Ansquer
 *
 */

public class BarreMenu extends JMenuBar {
	
	private MainMaximeAnsquer fenetre;

	public BarreMenu(MainMaximeAnsquer fenetrePrincipale){
		super();
		this.fenetre = fenetrePrincipale;
		JMenu fichier = new JMenu("Fichier");
		JMenuItem nouvelleEtude = new JMenuItem("Nouvelle etude");
		nouvelleEtude.setMnemonic('n');
		nouvelleEtude.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetre.nouvelleEtude();				
			}			
		});
		fichier.add(nouvelleEtude);		
		
		
		JMenuItem ouvrir = new JMenuItem("Ouvrir une etude existante");
		ouvrir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetre.choisirEtude();
			}			
		});		
		fichier.add(ouvrir);
		
		JMenuItem enregistrer = new JMenuItem("Enregistrer l'etude");
		enregistrer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetre.enregistrerEtude();
			}			
		});		
		fichier.add(enregistrer);
		
		fichier.addSeparator();
		JMenu preferences = new JMenu("Preferences");
		preferences.add(new JMenuItem("Config. editeur"));
		preferences.add(new JCheckBoxMenuItem("Son"));
		JRadioButtonMenuItem b219 = new JRadioButtonMenuItem("21:9");
		preferences.add(b219);
		JRadioButtonMenuItem b169 = new JRadioButtonMenuItem("16:9");
		preferences.add(b169);
		JRadioButtonMenuItem b43 = new JRadioButtonMenuItem("4:3");
		preferences.add(b43);
		ButtonGroup groupeFormat = new ButtonGroup();
		groupeFormat.add(b219);
		groupeFormat.add(b169);
		groupeFormat.add(b43);
		fichier.add(preferences);
		this.add(fichier);
		
		JMenu options = new JMenu("Options");
		
		JMenuItem modifierNom = new JMenuItem("Modifier le nom de l'etude");
		modifierNom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetre.modifierNomEtude();		
			}			
		});
		options.add(modifierNom);
		
		JMenuItem supprimerEtude = new JMenuItem("Supprimer l'etude en cours");
		supprimerEtude.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("bouton");
				fenetre.supprimerEtude();		
			}			
		});
		options.add(supprimerEtude);
		
		this.add(options);
		
		
	}
	
}
