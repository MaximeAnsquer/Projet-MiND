package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JTable;

import presentation.ModeleScenarioDeMenacesGeneriques;

public class ControlJButtonSuppressionScenario implements Observer,ActionListener {
	private ModeleScenarioDeMenacesGeneriques modele ;
	private JTable tableau ;
	private JButton supprimerLigne;
	
	public ControlJButtonSuppressionScenario(
			ModeleScenarioDeMenacesGeneriques modele, JTable tableau,
			JButton supprimerLigne) {
		this.modele = modele;
		this.tableau = tableau;
		this.supprimerLigne = supprimerLigne;
	}

	public void actionPerformed(ActionEvent e) {
		if (this.tableau.getSelectedRow()!=-1){
			this.modele.removeScenarioGenerique(this.tableau.getSelectedRow());
		}
	}

	public void update(Observable o, Object arg) {
		if (this.modele.getRowCount()!=0 && this.tableau.getSelectedRow()!=-1){
			this.supprimerLigne.setEnabled(true);
		}
		else{
			this.supprimerLigne.setEnabled(false);
		}
	}

}
