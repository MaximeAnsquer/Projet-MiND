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
import controle.ScenariosMenacesGeneriques.ControlJButtonSuppressionScenario;
import controle.ScenariosMenacesGeneriques.ControlJtable;
import abstraction.modules.ScenariosDeMenacesGeneriques;

public class FenetreScenarioDeMenacesGeneriques extends JPanel {
	
	private ScenariosDeMenacesGeneriques moduleCourant;
	private ModeleScenarioDeMenacesGeneriques modeleTableau;
	private JTable tableau ;
	private JButton supprimerLigne ;
	private JButton ajouterLigne ;
	private JButton ajouterCritere;
	private JButton supprimerCritere;
	private JButton aide ;
	
	public FenetreScenarioDeMenacesGeneriques(ScenariosDeMenacesGeneriques module) {
		
		this.moduleCourant=module;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.creerTableau();
		this.creerBoutonsBas();
	}
	
	public void creerTableau() {
		this.modeleTableau= new ModeleScenarioDeMenacesGeneriques(this.moduleCourant);
		this.tableau = new JTable(this.modeleTableau);
		
		ControlJtable controlTableau = new ControlJtable(modeleTableau, tableau);
		this.tableau.addMouseListener(controlTableau);
		
		this.add(tableau.getTableHeader());
        this.add(new JScrollPane(tableau));
	}
	
	public void creerBoutonsBas() {
		JPanel panelBas = new JPanel() ;
		panelBas.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.ajouterLigne = new JButton("Ajouter un scénario de menace générique");
		
		ControlJButtonAjoutScenario controlAjoutLigne = new ControlJButtonAjoutScenario(modeleTableau, tableau, ajouterLigne);
		// this.moduleCourant.addObserver(controlAjoutLigne);
		this.ajouterLigne.addActionListener(controlAjoutLigne);
		
		this.supprimerLigne = new JButton("Supprimer un scénario de menace générique");
		this.supprimerLigne.setEnabled(false);
		
		ControlJButtonSuppressionScenario controlSuppressionScenario = new ControlJButtonSuppressionScenario(modeleTableau, tableau, supprimerLigne);
		this.moduleCourant.addObserver(controlSuppressionScenario);
		this.supprimerLigne.addActionListener(controlSuppressionScenario);;
		
		this.ajouterCritere = new JButton("Ajouter un critère de sécurité");
		
		ControlJButtonAjoutColonne controlAjoutColonne = new ControlJButtonAjoutColonne(modeleTableau, ajouterCritere);
		this.moduleCourant.addObserver(controlAjoutColonne);
		this.ajouterCritere.addActionListener(controlAjoutColonne);
		
		this.supprimerCritere = new JButton("Supprimer un critère de sécurité");
		this.supprimerCritere.setEnabled(false);
		
		ControlJButtonSuppressionColonne controlSupression = new ControlJButtonSuppressionColonne(modeleTableau, supprimerCritere);
		this.moduleCourant.addObserver(controlSupression);
		this.supprimerCritere.addActionListener(controlSupression);
		
		this.aide = new JButton("?");
		
		panelBas.add(ajouterLigne);
		panelBas.add(supprimerLigne);
		panelBas.add(ajouterCritere);
		panelBas.add(supprimerCritere);
		panelBas.add(aide);
		this.add(panelBas);
	}
		
}
