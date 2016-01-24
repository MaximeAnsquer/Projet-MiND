package controle.ScenariosDeMenacesTypes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JTable;

import abstraction.autres.ScenarioType;
import presentation.ModeleScenarioDeMenacesTypes;

public class ControlJButtonAjoutLigne implements Observer,ActionListener{
	private ModeleScenarioDeMenacesTypes modele;
	private JTable tableau;
	private JButton ajouterLigne;

	public ControlJButtonAjoutLigne(ModeleScenarioDeMenacesTypes modele,
			JTable tableau, JButton ajouterLigne) {
		this.modele = modele;
		this.tableau = tableau;
		this.ajouterLigne = ajouterLigne;
	}

	public void actionPerformed(ActionEvent e) {
		ScenarioType sType = new ScenarioType(this.modele.getScenarioCourant());
		this.modele.addScenarioType(sType, this.tableau.getSelectedRow()+1);
	}

	public void update(Observable o, Object arg) {
		if (this.tableau.getSelectedRow()!=-1 && this.modele.getRowCount()!=0){
			this.ajouterLigne.setEnabled(true);
		}
		else{
			this.ajouterLigne.setEnabled(false);
		}
	}
}
