package presentation;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;

import controle.ScenariosDeMenacesTypes.ControlJButtonAjoutLigne;
import controle.ScenariosDeMenacesTypes.ControlJCheckBox;
import controle.ScenariosDeMenacesTypes.ControlJTable;
import abstraction.autres.SourceDeMenace;
import abstraction.modules.ScenariosDeMenacesTypes;

public class FenetreScenarioDeMenacesTypes extends JPanel {
	
	private ScenariosDeMenacesTypes moduleCourant;
	private ModeleScenarioDeMenacesTypes modeleTableau;
	private JTable tableau ;
	private CellRendererVraisemblance rendererVraisemblance ;
	private CellRendererToolTip rendererToolTip;
	private CellRendererEv rendererEv ;
	private JComboBox comboBoxIntrinseque ;
	private JComboBox comboBoxReelle ;
	private JButton ajoutLigne ;
	
	private ArrayList<JCheckBox> checkBoxSourcesMenaces ;
	private JFrame fenetreSourcesMenaces ;
	
	private JFrame petiteFenetre;;
	private JTextArea textAreaPetiteFenetre;

	public FenetreScenarioDeMenacesTypes(ScenariosDeMenacesTypes module) {
		
		this.moduleCourant=module;
		this.moduleCourant.importerDonnees();
		this.moduleCourant.actualiserDonnees();
		this.moduleCourant.actualiserScenarios();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.creerPetiteFenetre();
		this.creerFenetreSourcesMenaces();
		this.creerTableau();
				
		this.creerComboBox();
		this.creerCheckBox();
		this.creerBoutonsBas();
	}
	
	public void creerPetiteFenetre(){
		this.petiteFenetre = new JFrame("Détail de la cellule");
		this.creerTextAreaPetiteFenetre();
		this.petiteFenetre.add(textAreaPetiteFenetre);
		petiteFenetre.setMaximumSize(new Dimension(1000,1000));
		petiteFenetre.setMinimumSize(new Dimension(300,0));
		this.petiteFenetre.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.petiteFenetre.pack();
	}
	
	public void creerTextAreaPetiteFenetre() {
		this.textAreaPetiteFenetre = new JTextArea("");		
		textAreaPetiteFenetre.setEditable(false);
		textAreaPetiteFenetre.setFont(new Font("Arial", Font.PLAIN, 17));
		textAreaPetiteFenetre.setLineWrap(true);
		textAreaPetiteFenetre.setWrapStyleWord(true);
	}
	
	public void creerFenetreSourcesMenaces(){
		this.fenetreSourcesMenaces= new JFrame("Liste sources de menaces");
		this.fenetreSourcesMenaces.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}
	
	public void creerTableau() {
		this.modeleTableau= new ModeleScenarioDeMenacesTypes(this.moduleCourant);
		this.tableau = new JTable(this.modeleTableau);
		
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setFont(new Font("Arial", Font.PLAIN, 15));
		tableau.setRowHeight(50);
		
		ControlJTable controlTableau = new ControlJTable(modeleTableau, tableau, fenetreSourcesMenaces, petiteFenetre);
		this.tableau.addMouseListener(controlTableau);
		
		this.rendererVraisemblance=new CellRendererVraisemblance(modeleTableau);
		this.rendererToolTip= new CellRendererToolTip(modeleTableau);
		this.rendererEv=new CellRendererEv();
		
		TableColumn colonneScenarioConcret = this.tableau.getColumnModel().getColumn(ModeleScenarioDeMenacesTypes.COLONNE_SCENARIO_CONCRET);
		colonneScenarioConcret.setCellRenderer(this.rendererEv);
		
		TableColumn colonneScenarioGenerique = this.tableau.getColumnModel().getColumn(ModeleScenarioDeMenacesTypes.COLONNE_SCENARIO_GENERIQUE);
		colonneScenarioGenerique.setCellRenderer(rendererToolTip);
		
		TableColumn colonneType = this.tableau.getColumnModel().getColumn(ModeleScenarioDeMenacesTypes.COLONNE_TYPE);
		colonneType.setCellRenderer(rendererToolTip);
		
		this.add(tableau.getTableHeader());
        this.add(new JScrollPane(tableau));
	}
	
	public void creerComboBox(){
		
		// Création du JcomboBox pour chaque case de la colonne Vraisemblance intrinsèque
		
		TableColumn colonneIntrinseque = this.tableau.getColumnModel().getColumn(this.modeleTableau.getColumnCount()-3);
		int nbNiveauxIntrinseque = this.modeleTableau.getMetriques().getMetrique("Vraisemblance").nombreDeNiveaux();
		String[] niveauxIntrinseque = new String[nbNiveauxIntrinseque];
		for (int i = 1 ; i<=nbNiveauxIntrinseque ; i++){
			niveauxIntrinseque[i-1]=""+ i;
		}
		this.comboBoxIntrinseque = new JComboBox(niveauxIntrinseque);
		
		// On gère la couleur des JcomboBox et les infobulles (ToolTips)
		CellRendererVraisemblance rendererScenario = new CellRendererVraisemblance(modeleTableau);
		
		colonneIntrinseque.setCellRenderer(this.rendererVraisemblance);
		colonneIntrinseque.setCellEditor(new DefaultCellEditor(comboBoxIntrinseque));
		
		// Création du JcomboBox pour chaque case de la colonne Vraisemblance réelle
		
		TableColumn colonneReelle = this.tableau.getColumnModel().getColumn(this.modeleTableau.getColumnCount()-2);
		int nbNiveauxReelle = this.modeleTableau.getMetriques().getMetrique("Vraisemblance").nombreDeNiveaux();
		String[] niveauxReelle = new String[nbNiveauxReelle];
		for (int i = 1 ; i<=nbNiveauxReelle ; i++){
			niveauxReelle[i-1]=""+ i;
		}
		this.comboBoxReelle = new JComboBox(niveauxReelle);
		// On gère la couleur des JcomboBox
		colonneReelle.setCellRenderer(this.rendererVraisemblance);
		colonneReelle.setCellEditor(new DefaultCellEditor(comboBoxReelle));
		
	}
	
	///*
	public void creerCheckBox(){
		TableColumn colonneSourcesMenaces = this.tableau.getColumnModel().getColumn(this.modeleTableau.COLONNE_SOURCES_MENACES);
		
		Container conteneur = this.fenetreSourcesMenaces.getContentPane();
		conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.Y_AXIS));
		
		this.checkBoxSourcesMenaces = new ArrayList<JCheckBox>();
		for(SourceDeMenace source :  this.modeleTableau.getSourcesDeMenaces().getLesSourcesDeMenaces()){
			JCheckBox idSource = new JCheckBox(source.getId(),source.isRetenu());
			
			// PAC
			ControlJCheckBox controlCheckbox = new ControlJCheckBox(modeleTableau, tableau);
			idSource.addActionListener(controlCheckbox);
			
			this.checkBoxSourcesMenaces.add(idSource);
		}
		
		for (JCheckBox idSource : this.checkBoxSourcesMenaces){
			conteneur.add(idSource);
		}
		this.fenetreSourcesMenaces.pack();
	}
	//*/
	public void creerBoutonsBas() {
		JLabel label = new JLabel("");
		this.add(label);
		
		this.ajoutLigne=new JButton("Particulariser un scénario générique");
		
		ControlJButtonAjoutLigne controlAjoutLigne = new ControlJButtonAjoutLigne(modeleTableau, tableau, ajoutLigne);
		this.moduleCourant.addObserver(controlAjoutLigne);
		this.ajoutLigne.addActionListener(controlAjoutLigne);
		
		this.add(ajoutLigne);
	}
}
