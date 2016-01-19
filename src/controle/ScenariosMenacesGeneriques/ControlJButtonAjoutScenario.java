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

public class ControlJButtonAjoutScenario implements ActionListener {
	private ModeleScenarioDeMenacesGeneriques modele;
	private JTable tableau;
	private JButton ajouterLigne;

	public ControlJButtonAjoutScenario(
			ModeleScenarioDeMenacesGeneriques modele, JTable tableau,
			JButton ajouterLigne) {
		this.modele = modele;
		this.tableau=tableau;
		this.ajouterLigne = ajouterLigne;
	}

	public void actionPerformed(ActionEvent e) {
		if (this.tableau.getSelectedRow() == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Pour ajouter un scénario, vous devez sélectionner la ligne sous laquelle ce scénario sera créé ",
							"Erreur ajout de scénario", JOptionPane.ERROR_MESSAGE);
		}
		else{
			Object[] typesBiens = this.modele.getTypologieBiensSupports().getIntituleTypeBiensRetenus();
			
			String typeBienSupport = (String) JOptionPane.showInputDialog(null,
					"Quel type de bien support voulez vous choisir ?", "Choix du Type de bien support",
					JOptionPane.QUESTION_MESSAGE, null, typesBiens, typesBiens[0]);
			
			if (typeBienSupport != null) {
				String Id = JOptionPane.showInputDialog("Id du scénario ?");

				if (Id != null) {
					while (Id.equals("")) {
						Id = JOptionPane.showInputDialog("Id du scénario ?");
					}

					String intituleScenario = JOptionPane
							.showInputDialog("Intitulé du scénario ?");

					if (intituleScenario != null) {
						while (intituleScenario.equals("")) {
							intituleScenario = JOptionPane
									.showInputDialog("Intitulé du scénario ?");
						}
						this.modele.addScenarioGenerique(new ScenarioGenerique(
								typeBienSupport, Id, intituleScenario),
								this.tableau.getSelectedRow() + 1);
					}
				}
			}
		}
		
	}
}
