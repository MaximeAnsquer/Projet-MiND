package presentation;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

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
		JMenuItem nouvelleEtude = new JMenuItem("Nouvelle étude");
		nouvelleEtude.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));		
		nouvelleEtude.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				fenetre.nouvelleEtude();				
			}			
		});

		fichier.add(nouvelleEtude);		


		JMenuItem ouvrir = new JMenuItem("Ouvrir une étude existante");
		ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));		
		ouvrir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetre.choisirEtude();
			}			
		});		
		fichier.add(ouvrir);

		JMenuItem enregistrer = new JMenuItem("Enregistrer l'étude");
		enregistrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));		
		enregistrer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetre.enregistrerEtude();
			}			
		});		
		fichier.add(enregistrer);

		JMenuItem quitter = new JMenuItem("Quitter");
		quitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int confirmation = JOptionPane.showConfirmDialog(fenetre, "Enregistrer l'étude en cours avant de quitter ?");
				if(confirmation == JOptionPane.YES_OPTION){
					fenetre.enregistrerEtude();
					System.exit(0);
				}
				else if(confirmation == JOptionPane.NO_OPTION){
					System.exit(0);
				}
			}			
		});		
		fichier.add(quitter);

		this.add(fichier);

		JMenu options = new JMenu("Options");

		JMenuItem modifierNom = new JMenuItem("Modifier le nom de l'étude");
		modifierNom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetre.modifierNomEtude();		
			}			
		});
		options.add(modifierNom);

		JMenuItem supprimerEtude = new JMenuItem("Supprimer l'étude en cours");
		supprimerEtude.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetre.supprimerEtude();		
			}			
		});
		options.add(supprimerEtude);

		this.add(options);


	}

}
