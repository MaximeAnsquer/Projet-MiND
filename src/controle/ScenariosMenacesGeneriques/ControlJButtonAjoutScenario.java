package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import abstraction.autres.ScenarioGenerique;
import presentation.ModeleScenarioDeMenacesGeneriques;

public class ControlJButtonAjoutScenario implements Observer, ActionListener {
	private ModeleScenarioDeMenacesGeneriques modele;
	private JTable tableau;
	private JButton ajouterLigne;

	public ControlJButtonAjoutScenario(
			ModeleScenarioDeMenacesGeneriques modele, JTable tableau,
			JButton ajouterLigne) {
		this.modele = modele;
		this.ajouterLigne = ajouterLigne;
	}

	public void actionPerformed(ActionEvent e) {
		Object[] typesBiens = this.modele.getTypologieBiensSupports().getIntituleTypeBiensRetenus();
		
		String typeBienSupport = (String) JOptionPane.showInputDialog(null,
				"Quelle type de bien support voulez vous choisir ?", "Choix du Type de bien support",
				JOptionPane.QUESTION_MESSAGE, null, typesBiens, typesBiens[0]);
		
		if (typeBienSupport!=null){
			String Id = JOptionPane.showInputDialog("Id du scénario ?");
			
			if (Id != null && !Id.equals("")) {
				String intituleScenario = JOptionPane.showInputDialog("Intitulé du scénario ?");
				
				if (intituleScenario != null && !intituleScenario.equals("")){
					this.modele.addScenarioGenerique(new ScenarioGenerique(typeBienSupport, Id, intituleScenario), 0);
				}
			}
		}
		
	}

	public void update(Observable o, Object arg) {
		if (this.tableau.getSelectedRow() != -1) {
			this.ajouterLigne.setEnabled(true);
		} else {
			this.ajouterLigne.setEnabled(false);
		}
	}
}
