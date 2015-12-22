package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import presentation.ModeleScenarioDeMenacesGeneriques;

public class ControlJButtonSuppressionColonne implements Observer, ActionListener {
	private ModeleScenarioDeMenacesGeneriques modele;
	private JButton supprimerCritere ;
	
	public ControlJButtonSuppressionColonne(ModeleScenarioDeMenacesGeneriques modele, JButton supprimerCritere) {
		this.modele=modele;
		this.supprimerCritere=supprimerCritere;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Faire plutôt une JComboBox avec des choix 
		// UTILISER UN CRITERE COURANT ????
		this.modele.removeCritere();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}

}
