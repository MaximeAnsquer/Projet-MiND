package presentation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import abstraction.Etude;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.Metriques;
import abstraction.modules.SourcesDeMenaces;

/**
 * Classe brouillon pour faire des tests
 * @author Maxime Ansquer
 *
 */

public class MainMaximeAnsquer extends JFrame {
	
	public static Etude etudeEnCours;
	private JPanel contenuPrincipal;
	private Hashtable<String, JPanel> lesJpanels;
	private Workflow workflow;
	
	public MainMaximeAnsquer(){
		
		super("Outil d'analyse de risques");
		this.etudeEnCours = this.ouvrirEtude();
		this.contenuPrincipal = new JPanel();
		this.contenuPrincipal.setLayout(new BorderLayout());
		this.workflow = new Workflow(etudeEnCours);
		
		this.lesJpanels = new Hashtable<String, JPanel>();
		this.lesJpanels.put("CriteresDeSecurite", new FenetreCriteresDeSecurite((CriteresDeSecurite) etudeEnCours.getModule("CriteresDeSecurite")));
		this.lesJpanels.put("Metriques", new FenetreMetriques((Metriques) etudeEnCours.getModule("Metriques")));
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();		

		//Au lancement, on affiche le workflow (schema avec tous les modules)
		contenuPrincipal.add(workflow);
		
		contentPane.add(contenuPrincipal, BorderLayout.CENTER);
		contentPane.add(partieDeGauche(), BorderLayout.WEST);
		
		//TODO a enlever (utile pour les tests intermediaires)		
		this.setContenu("CriteresDeSecurite");		
		
		this.pack();
	}
	
	/**
	 * 
	 * @param nom le nom du module a afficher, ou bien " workflow " si on veut afficher le workflow
	 */
	private void setContenu(String nom) {		
		
		if(nom.equals("workflow")){
			this.contenuPrincipal = this.workflow;
		}
		else{
			this.contenuPrincipal.removeAll();	
			
			if(nom.equals("CriteresDeSecurite")){
				this.lesJpanels.put(nom, new FenetreCriteresDeSecurite((CriteresDeSecurite) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("Metriques")){
				this.lesJpanels.put(nom, new FenetreMetriques((Metriques) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("SourcesDeMenaces")){
				this.lesJpanels.put(nom, new FenetreSourcesDeMenaces((SourcesDeMenaces) etudeEnCours.getModule(nom)));
			}
			
			this.contenuPrincipal.add(lesJpanels.get(nom), BorderLayout.CENTER);
			this.contenuPrincipal.validate();
			this.contenuPrincipal.repaint();
		}	
		
		//Si la fenêtre est trop petite, on ne voit pas forcément tous les elements après avoir changé le JPanel " contenuPrincipal " et il faut donc appeler pack().
		//On ne le fait pas si la fenetre est " grande ", car cela veut probablement dire que l'utilisateur l'a mise en plein ecran, et alors l'appel a la methode
		//pack() quittera le mode plein ecran
		int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;
		if(this.size().width < 0.9*largeurEcran){
			this.pack();
		}
		
	}

	private JPanel partieDeGauche() {
		JPanel partieDeGauche = new JPanel();
		partieDeGauche.setLayout(new BoxLayout(partieDeGauche, BoxLayout.Y_AXIS));
		
		JButton boutonMetriques = new JButton("Metriques"); 
		boutonMetriques.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setContenu("Metriques");				
			}
			
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
		
		return partieDeGauche;
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

	public static void main(String[] args) {
		
		//---La fenetre principale---
		
		MainMaximeAnsquer fenetrePrinciaple = new MainMaximeAnsquer(); 
		
		/**
		 * Bidouille pour tester :
		 */

		//---Creation de l'etude---

//		etudeEnCours = new Etude("Etude de test");
//		etudeEnCours.addModule(new CriteresDeSecurite());
//		etudeEnCours.addModule(new Metriques(etudeEnCours));
//		etudeEnCours.addModule(new SourcesDeMenaces());	
		
		//---Creation de le fenetre Metrique---
		
//		FenetreMetriques fMetriques = new FenetreMetriques();
		
		//---Creation de le fenetre Sources de menaces---
		
//		FenetreSourcesDeMenaces source = new FenetreSourcesDeMenaces();		
		
	}
}

