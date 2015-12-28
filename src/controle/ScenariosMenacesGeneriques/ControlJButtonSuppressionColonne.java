package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import presentation.ModeleScenarioDeMenacesGeneriques;

public class ControlJButtonSuppressionColonne implements Observer, ActionListener {
	private ModeleScenarioDeMenacesGeneriques modele;
	private JTable tableau ;
	private JButton supprimerCritere ;
	
	public ControlJButtonSuppressionColonne(ModeleScenarioDeMenacesGeneriques modele, JButton supprimerCritere) {
		this.modele=modele;
		this.supprimerCritere=supprimerCritere;
	}

	public void actionPerformed(ActionEvent e) {
		// Faire plutôt une JComboBox avec des choix 
		// UTILISER UN CRITERE COURANT ????
		
		if (this.modele.getModuleCourant().getNomColonneSup() != null) {
			Object[] criteres = this.modele.getModuleCourant()
					.getNomColonneSup().toArray();

			String critereAsupprimer = (String) JOptionPane.showInputDialog(
					null, "Quelle critère voulez-vous supprimer ?",
					"Choix du critère à supprimer",
					JOptionPane.QUESTION_MESSAGE, null, criteres, criteres[0]);
			
			this.modele.removeCritere(critereAsupprimer);
		}
	}

	public void update(Observable o, Object arg) {
		if (this.modele.getColumnCount() == 4) {
			this.supprimerCritere.setEnabled(false);
		} else {
			this.supprimerCritere.setEnabled(true);
		}
	}

}
