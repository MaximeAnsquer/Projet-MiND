package presentation;

import java.util.ArrayList;

import javafx.scene.control.TableColumn;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private JComboBox comboBoxIntrinseque ;
	private JButton ajoutLigne ;

	public FenetreScenarioDeMenacesTypes() {
		super("Scenarios de menaces types");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		this.creerTableau();
		this.creerComboBox();
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
	
	public void creerComboBox(){
		javax.swing.table.TableColumn colonneIntrinseque = this.tableau.getColumnModel().getColumn(this.modeleTableau.COLONNE_VRAISEMBLANCE_I);
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
		
		this.getContentPane().add(ajoutLigne);
	}
}
