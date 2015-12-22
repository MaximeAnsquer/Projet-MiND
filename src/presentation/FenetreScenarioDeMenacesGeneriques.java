package presentation;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import abstraction.modules.ScenariosDeMenacesGeneriques;

public class FenetreScenarioDeMenacesGeneriques extends JFrame {
	
	private ScenariosDeMenacesGeneriques moduleCourant;
	private ModeleScenarioDeMenacesGeneriques modeleTableau;
	private JTable tableau ;
	private JButton supprimerLigne ;
	private JButton ajouterLigne ;
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
		
		this.supprimerLigne = new JButton("Supprimer un scénario de menace générique"); 
		
		this.aide = new JButton("?");
		
		panelBas.add(ajouterLigne);
		panelBas.add(supprimerLigne);
		panelBas.add(aide);
		this.getContentPane().add(panelBas);
	}
		
}
