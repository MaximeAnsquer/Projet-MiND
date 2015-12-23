package presentation;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import abstraction.Etude;
import abstraction.modules.Module;

/**
 * 
 * @author Maxime Ansquer
 *
 */
public class Workflow extends JPanel {

	private Etude etude;
	private Hashtable<String, JButton> lesBoutons;
	private MainMaximeAnsquer fenetre;

	public Workflow(Etude etude, MainMaximeAnsquer fenetrePrincipale) {
		this.etude = etude;
		this.fenetre = fenetrePrincipale;
		this.lesBoutons = new Hashtable<String, JButton>();

		for(final Module m : etude.getLesModules().values()){

			JButton bouton = new JButton(m.toString());
			if(!m.estDisponible()){
				//TODO a decommenter a la fin
//				bouton.setEnabled(false);
			}
			else{
				if(m.estCree()){
					if(!m.estCoherent()){
						bouton.setBackground(Color.RED);
					}
					else{
						bouton.setBackground(Color.GREEN);
					}
				}				
			}

			lesBoutons.put(m.getNom(), bouton);			
			final String nomModule = m.getNom();

			bouton.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					fenetre.setContenu(nomModule);
					m.setCree(true);
				}				

			});
		}

		this.setLayout(new GridLayout(8,8));

		//1ere ligne
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(lesBoutons.get("EvenementsRedoutes"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());

		//2eme ligne
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(lesBoutons.get("BiensEssentiels"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());

		//3eme ligne
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(lesBoutons.get("MappingDesBiens"));
		this.add(new JLabel());
		this.add(new JButton("Module manquant"));
		this.add(new JLabel());
		this.add(new JButton("Module manquant"));

		//4eme ligne
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(lesBoutons.get("BiensSupports"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());

		//5eme ligne
		this.add(new JButton("Module manquant"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());

		//6eme ligne
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JButton("Module manquant"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());

		//7eme ligne
		this.add(lesBoutons.get("SourcesDeMenaces"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JButton("Module manquant"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());

		//8eme ligne
		this.add(lesBoutons.get("CriteresDeSecurite"));
		this.add(new JLabel());
		this.add(lesBoutons.get("Metriques"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(new ImageIcon("images/workflow.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}


}
