package presentation;



import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
//		System.out.println("\n --- RECHARGEMENT DU WORKFLOW --- \n");
		this.etude = etude;
		this.fenetre = fenetrePrincipale;
		this.lesBoutons = new Hashtable<String, JButton>();		

		for(final Module m : etude.getLesModules().values()){

//			System.out.println("\n Création du bouton " + m.toString() + "... \n");

			JButton bouton = new JButton(mettreSurDeuxLignes(m.toString()));

			if(!m.estDisponible()){
//				System.out.println(m + " n'est pas disponible.");
				bouton.setEnabled(false);
			}
			else{
//				System.out.println(m + " est disponible.");
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
		this.add(lesBoutons.get("AnalyseDesRisques"));
		this.add(new JLabel());
		this.add(lesBoutons.get("MatriceDesRisques"));

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
		this.add(lesBoutons.get("TypologieDesBiensSupports"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());

		//6eme ligne
		this.add(new JLabel());
		this.add(lesBoutons.get("ScenariosDeMenacesGeneriques"));		
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(new JLabel());

		//7eme ligne
		this.add(lesBoutons.get("SourcesDeMenaces"));
		this.add(new JLabel());
		this.add(new JLabel());
		this.add(lesBoutons.get("ScenariosDeMenacesTypes"));
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

	private String mettreSurDeuxLignes(String string) {
		String resultat = string;
		if(string.equals("Typologies des biens supports")){			
			resultat = "<html><div align=\"center\">Typologies des biens <br /> supports  </div> </html>";		

		}
		else if(string.equals("Scenarios de menaces generiques")){
			Module module = etude.getModule("ScenariosDeMenacesGeneriques");
			if(!module.estCree() && !module.estDisponible()){
				resultat = "<html><div style=\"color:#999999;\" align=\"center\"> Scénarios de menaces <br /> génériques </div> </html>";				
			}
			else{
				resultat = "<html><div align=\"center\"> Scénarios de menaces <br /> génériques </div> </html>";		
			}
		}
		else if(string.equals("Scenarios de menaces types")){
			Module module = etude.getModule("ScenariosDeMenacesTypes");
			if(!module.estCree() && !module.estDisponible()){
				resultat = "<html><div style=\"color:#999999;\" align=\"center\"> Scénarios de menaces <br /> typés </div> </html>";				
			}
			else{
				resultat = "<html><div align=\"center\"> Scénarios de menaces <br /> typés </div> </html>";		
			}
		}
		return resultat;
	}

	private JButton moduleManquant() {
		JButton moduleManquant = new JButton("Module manquant"); //TODO: a enlever pour le rendu final
		moduleManquant.setEnabled(false); //TODO: a enlever pour le rendu final
		return moduleManquant;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(new ImageIcon("images/workflow.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}


}
