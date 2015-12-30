package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
				if(fenetre.etudeEnCours != null){
					int decision = JOptionPane.showConfirmDialog(null, 
							"Enregistrer l'etude en cours avant de creer une nouvelle etude ?", "Enregistrer l'etude en cours ?", 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					switch(decision){
					case JOptionPane.YES_OPTION:
						fenetre.enregistrerEtude();		
						break;
					case JOptionPane.NO_OPTION:
						break;
					}	
				}
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
