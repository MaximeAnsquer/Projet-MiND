package controle.ScenariosDeMenacesTypes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JTable;

import abstraction.autres.SourceDeMenace;
import presentation.ModeleScenarioDeMenacesTypes;

public class ControlJCheckBox implements ActionListener {
	private ModeleScenarioDeMenacesTypes modele ;
	private JTable tableau ;
	
	public ControlJCheckBox(ModeleScenarioDeMenacesTypes modele, JTable tableau) {
		this.modele=modele;
		this.tableau=tableau;
	}

	public void actionPerformed(ActionEvent e) {
		int row = this.tableau.getSelectedRow();
		String id = ((JCheckBox) e.getSource()).getText();
		
		SourceDeMenace sourceAssocie = this.modele.getSourcesDeMenaces().getSourceDeMenace(id);
		this.modele.getModuleCourant().getScenarioType(row).getMenaces().add(sourceAssocie);
	}

}
