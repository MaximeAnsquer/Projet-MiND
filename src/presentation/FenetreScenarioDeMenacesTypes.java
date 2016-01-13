package presentation;


import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import controle.ScenariosDeMenacesTypes.ControlJButtonAjoutLigne;
import controle.ScenariosDeMenacesTypes.ControlJTable;
import abstraction.modules.ScenariosDeMenacesTypes;

public class FenetreScenarioDeMenacesTypes extends JPanel {
	private ScenariosDeMenacesTypes moduleCourant;
	private ModeleScenarioDeMenacesTypes modeleTableau;
	private JTable tableau ;
	private CellRendererVraisemblance rendererTooltip ;
	private CellRendererEv rendererEv ;
	private JComboBox comboBoxIntrinseque ;
	private JComboBox comboBoxReelle ;
	private JButton ajoutLigne ;

	public FenetreScenarioDeMenacesTypes(ScenariosDeMenacesTypes module) {
		
		this.moduleCourant=module;
		this.moduleCourant.importerDonnees();
		this.moduleCourant.actualiserDonnees();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.creerTableau();
		this.creerComboBox();
		//this.creerCheckBox();
		this.creerBoutonsBas();
	}
	
	public void creerTableau() {
		this.modeleTableau= new ModeleScenarioDeMenacesTypes(this.moduleCourant);
		// this.moduleCourant=this.modeleTableau.getModuleCourant();
		this.tableau = new JTable(this.modeleTableau);
		
		ControlJTable controlTableau = new ControlJTable(modeleTableau, tableau);
		this.tableau.addMouseListener(controlTableau);
		
		this.rendererTooltip=new CellRendererVraisemblance(modeleTableau);
		this.rendererEv=new CellRendererEv();
		
		TableColumn colonneScenarioConcret = this.tableau.getColumnModel().getColumn(ModeleScenarioDeMenacesTypes.COLONNE_SCENARIO_CONCRET);
		colonneScenarioConcret.setCellRenderer(this.rendererEv);
		
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
		
		colonneIntrinseque.setCellRenderer(this.rendererTooltip);
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
		colonneReelle.setCellRenderer(this.rendererTooltip);
		colonneReelle.setCellEditor(new DefaultCellEditor(comboBoxReelle));
		
	}
	
	/*
	public void creerCheckBox(){
		TableColumn colonneSourcesMenaces = this.tableau.getColumnModel().getColumn(this.modeleTableau.COLONNE_SOURCES_MENACES);
		
		this.checkBoxSourcesMenaces = new ArrayList<JCheckBox>();
		for(SourceDeMenace source :  this.modeleTableau.getSourcesDeMenaces().getBDC().values()){
			JCheckBox idSource = new JCheckBox(source.getId(),source.isRetenu());
			this.checkBoxSourcesMenaces.add(idSource);
		}
	}
	*/
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
