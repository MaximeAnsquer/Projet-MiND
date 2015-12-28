package presentation;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import abstraction.modules.ScenariosDeMenacesTypes;

public class FenetreScenarioDeMenacesTypes extends JFrame {
	private ScenariosDeMenacesTypes moduleCourant;
	private ModeleScenarioDeMenacesTypes modeleTableau;
	private JTable tableau ;

	public FenetreScenarioDeMenacesTypes() {
		super("Scenarios de menaces types");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		this.creerTableau();
		this.creerBoutonsBas();
		this.pack();
	}
	
	public void creerTableau() {
		this.modeleTableau= new ModeleScenarioDeMenacesTypes();
		this.moduleCourant=this.modeleTableau.getModuleCourant();
		this.tableau = new JTable(this.modeleTableau);
		
		this.getContentPane().add(tableau.getTableHeader());
        this.getContentPane().add(new JScrollPane(tableau));
	}
	
	public void creerBoutonsBas() {
		
	}
}
