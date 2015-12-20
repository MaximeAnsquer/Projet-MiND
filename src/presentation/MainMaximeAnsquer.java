package presentation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import abstraction.Etude;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.Metriques;
import abstraction.modules.Module;
import abstraction.modules.SourcesDeMenaces;

/**
 * Classe brouillon pour faire des tests
 * @author Maxime Ansquer
 *
 */

public class MainMaximeAnsquer extends JFrame {
	
	public Etude etudeEnCours;
	private JPanel partieDeGauche;
	private JPanel contenuPrincipal;
	private Hashtable<String, JPanel> lesJpanels;
	private Workflow workflow;
	private Module moduleEnCours;
	private JLabel label;
	private Container contentPane;
	private int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int hauteurEcran = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public MainMaximeAnsquer(){
		
		super("Outil d'analyse de risques");
		this.setPreferredSize(new Dimension(largeurEcran, (int) (0.95*hauteurEcran)));
		this.partieDeGauche = new JPanel();
		this.etudeEnCours = this.ouvrirEtude();
		this.moduleEnCours = new Module("Workflow");
		this.contenuPrincipal = new JPanel();
		this.contenuPrincipal.setLayout(new BorderLayout());
		this.workflow = new Workflow(etudeEnCours, this);
		
		this.lesJpanels = new Hashtable<String, JPanel>();
		this.lesJpanels.put("CriteresDeSecurite", new FenetreCriteresDeSecurite((CriteresDeSecurite) etudeEnCours.getModule("CriteresDeSecurite")));
		this.lesJpanels.put("Metriques", new FenetreMetriques((Metriques) etudeEnCours.getModule("Metriques")));
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = this.getContentPane();		
		
		contentPane.add(contenuPrincipal, BorderLayout.CENTER);
		
		//TODO a enlever (utile pour les tests intermediaires)		
		this.setContenu("Workflow");		
		
		this.pack();
	}
	
	/**
	 * 
	 * @param nom le nom du module a afficher, ou bien " workflow " si on veut afficher le workflow
	 */
	public void setContenu(String nom) {		
		
		contentPane.remove(partieDeGauche);
		
		if(nom.equals("Workflow")){					
			this.moduleEnCours = new Module("Workflow");
			this.contenuPrincipal.removeAll();
			this.contenuPrincipal.add(workflow, BorderLayout.CENTER);
		}
		else{
			this.moduleEnCours = etudeEnCours.getModule(nom);
			setPartieDeGauche();
			contentPane.add(partieDeGauche, BorderLayout.WEST);
			this.contenuPrincipal.removeAll();	
			this.label = new JLabel(moduleEnCours.toString());
			
			if(nom.equals("CriteresDeSecurite")){
				this.lesJpanels.put(nom, new FenetreCriteresDeSecurite((CriteresDeSecurite) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("Metriques")){
				this.lesJpanels.put(nom, new FenetreMetriques((Metriques) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("SourcesDeMenaces")){
				this.lesJpanels.put(nom, new FenetreSourcesDeMenaces((SourcesDeMenaces) etudeEnCours.getModule(nom)));
			}
			
			this.contenuPrincipal.add(label, BorderLayout.NORTH);
			this.contenuPrincipal.add(lesJpanels.get(nom), BorderLayout.CENTER)		;
		}		
		
		this.contenuPrincipal.validate();
		this.contenuPrincipal.repaint();	
		contentPane.validate();
		contentPane.repaint();
				
	}

	private void setPartieDeGauche() {
		
		partieDeGauche = new JPanel();
		partieDeGauche.setLayout(new BoxLayout(partieDeGauche, BoxLayout.Y_AXIS));
		String nom = moduleEnCours.getNom();
		
		if(nom.equals("Workflow")){
		}		
		else{			
			JButton boutonWorkflow = new JButton("Workflow"); 
			boutonWorkflow.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					moduleEnCours = new Module("Workflow");
					setContenu("Workflow");				
				}				
			});
			partieDeGauche.add(boutonWorkflow);
			
			JButton boutonMetriques = new JButton("Metriques"); 
			boutonMetriques.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setContenu("Metriques");								}
				
			});
			partieDeGauche.add(boutonMetriques);
			
			JButton boutonCriteres = new JButton("Criteres"); 
			boutonCriteres.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setContenu("CriteresDeSecurite");			
				}				
			});
			partieDeGauche.add(boutonCriteres);
			
			JButton boutonSourcesDeMenaces = new JButton("Sources de menaces"); 
			boutonSourcesDeMenaces.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					setContenu("SourcesDeMenaces");		
				}				
			});
			partieDeGauche.add(boutonSourcesDeMenaces);			
			
			partieDeGauche.validate();
			partieDeGauche.repaint();
		}		
	}

	public Etude ouvrirEtude(){
		//TODO: permettre a l'utilisateur de choisir parmi une liste d'etudes sauvegardees,
		//puis set la variable etudeEnCours		
		
		Etude etudeDeTest = new Etude("Etude test");
		etudeDeTest.addModule(new CriteresDeSecurite());		
		etudeDeTest.addModule(new Metriques(etudeDeTest));
		etudeDeTest.addModule(new SourcesDeMenaces());
		
		return etudeDeTest;
	}
	
	public Etude getEtude(){
		return etudeEnCours;
	}
	
	public void setModuleEnCours(Module m){
		this.moduleEnCours = m;
	}

	public static void main(String[] args) {
		
		//---La fenetre principale---
		
		MainMaximeAnsquer fenetrePrinciaple = new MainMaximeAnsquer(); 
				
	}
}

