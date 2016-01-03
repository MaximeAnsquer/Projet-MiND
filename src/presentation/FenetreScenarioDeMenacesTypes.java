package presentation;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controle.ScenariosDeMenacesTypes.ControlJButtonAjoutLigne;
import controle.ScenariosDeMenacesTypes.ControlJTable;
import abstraction.modules.ScenariosDeMenacesTypes;

public class FenetreScenarioDeMenacesTypes extends JFrame {
	private ScenariosDeMenacesTypes moduleCourant;
	private ModeleScenarioDeMenacesTypes modeleTableau;
	private JTable tableau ;
	private JButton ajoutLigne ;

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
		
		ControlJTable controlTableau = new ControlJTable(modeleTableau, tableau);
		this.tableau.addMouseListener(controlTableau);
		
		this.getContentPane().add(tableau.getTableHeader());
        this.getContentPane().add(new JScrollPane(tableau));
	}
	
	public void creerBoutonsBas() {
		this.ajoutLigne=new JButton("Particulariser un scénario générique");
		
		ControlJButtonAjoutLigne controlAjoutLigne = new ControlJButtonAjoutLigne(modeleTableau, tableau, ajoutLigne);
		this.moduleCourant.addObserver(controlAjoutLigne);
		this.ajoutLigne.addActionListener(controlAjoutLigne);
		
		this.getContentPane().add(ajoutLigne);
	}
}
