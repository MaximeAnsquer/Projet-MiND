package presentation;


import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
	private JComboBox comboBoxIntrinseque ;
	private JButton ajoutLigne ;

	public FenetreScenarioDeMenacesTypes(ScenariosDeMenacesTypes module) {
		
		this.moduleCourant=module;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.creerTableau();
		this.creerComboBox();
		this.creerBoutonsBas();
	}
	
	public void creerTableau() {
		this.modeleTableau= new ModeleScenarioDeMenacesTypes(this.moduleCourant);
		// this.moduleCourant=this.modeleTableau.getModuleCourant();
		this.tableau = new JTable(this.modeleTableau);
		
		ControlJTable controlTableau = new ControlJTable(modeleTableau, tableau);
		this.tableau.addMouseListener(controlTableau);
		
		this.add(tableau.getTableHeader());
        this.add(new JScrollPane(tableau));
	}
	
	public void creerComboBox(){
		TableColumn colonneIntrinseque = this.tableau.getColumnModel().getColumn(this.modeleTableau.getColumnCount()-3);
		int vraisemblanceMax = this.modeleTableau.getMetriques().getMetrique("Vraisemblance").nombreDeNiveaux();
		String[] liste = new String[vraisemblanceMax];
		
		for (int i = 1 ; i<=vraisemblanceMax ; i++){
			liste[i-1]=""+ i;
		}
		this.comboBoxIntrinseque = new JComboBox(liste);
		colonneIntrinseque.setCellEditor(new DefaultCellEditor(comboBoxIntrinseque));
		
	}
	
	public void creerBoutonsBas() {
		this.ajoutLigne=new JButton("Particulariser un scénario générique");
		
		ControlJButtonAjoutLigne controlAjoutLigne = new ControlJButtonAjoutLigne(modeleTableau, tableau, ajoutLigne);
		this.moduleCourant.addObserver(controlAjoutLigne);
		this.ajoutLigne.addActionListener(controlAjoutLigne);
		
		this.add(ajoutLigne);
	}
}
