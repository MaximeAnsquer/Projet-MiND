package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ControlJButtonAide implements ActionListener {
	private JButton aide ;
	
	public ControlJButtonAide(JButton aide) {
		this.aide=aide;
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane
				.showMessageDialog(
						null,
						"Pour supprimer un critère ou un scénario, sélectionner une ligne du tableau "
							+ "\nPour ajouter une nouvelle ligne, sélectionner la ligne du tableau en dessous de laquelle le scénario sera crée",
						"Aide", JOptionPane.INFORMATION_MESSAGE);
	}

}

