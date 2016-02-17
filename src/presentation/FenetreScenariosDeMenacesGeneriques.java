package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;

import controle.ScenariosMenacesGeneriques.ControlJButtonAide;
import controle.ScenariosMenacesGeneriques.ControlJButtonAjoutColonne;
import controle.ScenariosMenacesGeneriques.ControlJButtonAjoutScenario;
import controle.ScenariosMenacesGeneriques.ControlJButtonSuppressionColonne;
import controle.ScenariosMenacesGeneriques.ControlJButtonSuppressionScenario;
import controle.ScenariosMenacesGeneriques.ControlJtable;
import abstraction.modules.ScenariosDeMenacesGeneriques;

public class FenetreScenariosDeMenacesGeneriques extends JPanel {
	
	private ScenariosDeMenacesGeneriques moduleCourant;
	private ModeleScenarioDeMenacesGeneriques modeleTableau;
	private JTable tableau ;
	private CellRendererToolTip rendererToolTip ;
	private JButton supprimerLigne ;
	private JButton ajouterLigne ;
	private JButton ajouterCritere;
	private JButton supprimerCritere;
	private JButton aide ;
	private JFrame petiteFenetre;
	private JTextArea textAreaPetiteFenetre;
	
	public FenetreScenariosDeMenacesGeneriques(ScenariosDeMenacesGeneriques module) {
		
		this.moduleCourant=module;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.creerPetiteFenetre();
		this.creerTableau();
		this.creerBoutonsBas();
	}
	
	public void creerPetiteFenetre(){
		this.petiteFenetre = new JFrame("Détail de la cellule");
		this.creerTextAreaPetiteFenetre();
		this.petiteFenetre.add(textAreaPetiteFenetre);
		petiteFenetre.setMaximumSize(new Dimension(1000,1000));
		petiteFenetre.setMinimumSize(new Dimension(300,0));
		this.petiteFenetre.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}
	
	public void creerTextAreaPetiteFenetre() {
		this.textAreaPetiteFenetre = new JTextArea("");		
		textAreaPetiteFenetre.setEditable(false);
		textAreaPetiteFenetre.setFont(new Font("Arial", Font.PLAIN, 17));
		textAreaPetiteFenetre.setLineWrap(true);
		textAreaPetiteFenetre.setWrapStyleWord(true);
	}
	
	public void creerTableau() {
		this.modeleTableau= new ModeleScenarioDeMenacesGeneriques(this.moduleCourant);
		this.tableau = new JTable(this.modeleTableau);
		
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setFont(new Font("Arial", Font.PLAIN, 15));
		tableau.setRowHeight(50);
		
		ControlJtable controlTableau = new ControlJtable(modeleTableau, tableau, petiteFenetre);
		this.tableau.addMouseListener(controlTableau);
		this.tableau.addKeyListener(controlTableau);
		
		this.rendererToolTip= new CellRendererToolTip(modeleTableau);
		
		TableColumn colonneIntitule = this.tableau.getColumnModel().getColumn(ModeleScenarioDeMenacesGeneriques.COLONNE_INTITULE);
		colonneIntitule.setCellRenderer(rendererToolTip);
		
		this.add(tableau.getTableHeader());
        this.add(new JScrollPane(tableau));
	}
	
	public void creerBoutonsBas() {
		JLabel label = new JLabel("");
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
		
		ControlJButtonAide controlAide = new ControlJButtonAide(this.aide);
		this.aide.addActionListener(controlAide);
		
		panelBas.add(ajouterLigne);
		panelBas.add(supprimerLigne);
		panelBas.add(ajouterCritere);
		panelBas.add(supprimerCritere);
		panelBas.add(aide);
		this.add(label);
		this.add(panelBas);
	}
		
}
