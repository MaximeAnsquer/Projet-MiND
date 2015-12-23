package presentation;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controle.ScenariosMenacesGeneriques.ControlJButtonAjoutColonne;
import controle.ScenariosMenacesGeneriques.ControlJButtonAjoutScenario;
import controle.ScenariosMenacesGeneriques.ControlJButtonSuppressionColonne;
import abstraction.modules.ScenariosDeMenacesGeneriques;

public class FenetreScenarioDeMenacesGeneriques extends JFrame {
	
	private ScenariosDeMenacesGeneriques moduleCourant;
	private ModeleScenarioDeMenacesGeneriques modeleTableau;
	private JTable tableau ;
	private JButton supprimerLigne ;
	private JButton ajouterLigne ;
	private JButton ajouterCritere;
	private JButton supprimerCritere;
	private JButton aide ;
	
	public FenetreScenarioDeMenacesGeneriques() {
		super("Typologie des Biens Supports");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		this.creerTableau();
		this.creerBoutonsBas();
		this.pack();
	}
	
	public void creerTableau() {
		this.modeleTableau= new ModeleScenarioDeMenacesGeneriques();
		this.moduleCourant=this.modeleTableau.getModuleCourant();
		this.tableau = new JTable(this.modeleTableau);
		
		this.getContentPane().add(tableau.getTableHeader());
        this.getContentPane().add(new JScrollPane(tableau));
	}
	
	public void creerBoutonsBas() {
		JPanel panelBas = new JPanel() ;
		panelBas.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.ajouterLigne = new JButton("Ajouter un scénario de menace générique");
		
		ControlJButtonAjoutScenario controlAjoutLigne = new ControlJButtonAjoutScenario(modeleTableau, tableau, ajouterLigne);
		this.moduleCourant.addObserver(controlAjoutLigne);
		this.ajouterLigne.addActionListener(controlAjoutLigne);
		
		this.supprimerLigne = new JButton("Supprimer un scénario de menace générique"); 
		
		this.ajouterCritere = new JButton("Ajouter un critère de sécurité");
		
		ControlJButtonAjoutColonne controlAjoutColonne = new ControlJButtonAjoutColonne(modeleTableau, ajouterCritere);
		this.moduleCourant.addObserver(controlAjoutColonne);
		this.ajouterCritere.addActionListener(controlAjoutColonne);
		
		this.supprimerCritere = new JButton("Supprimer un critère de sécurité");
		
		ControlJButtonSuppressionColonne controlSupression = new ControlJButtonSuppressionColonne(modeleTableau, supprimerCritere);
		this.moduleCourant.addObserver(controlSupression);
		this.supprimerCritere.addActionListener(controlSupression);
		
		this.aide = new JButton("?");
		
		panelBas.add(ajouterLigne);
		panelBas.add(supprimerLigne);
		panelBas.add(ajouterCritere);
		panelBas.add(supprimerCritere);
		panelBas.add(aide);
		this.getContentPane().add(panelBas);
	}
		
}
