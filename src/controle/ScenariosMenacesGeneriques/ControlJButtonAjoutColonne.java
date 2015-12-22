package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import presentation.ModeleScenarioDeMenacesGeneriques;

public class ControlJButtonAjoutColonne implements Observer, ActionListener {
	private ModeleScenarioDeMenacesGeneriques modele;
	private JButton ajouterCritere ;
	
	public ControlJButtonAjoutColonne(ModeleScenarioDeMenacesGeneriques modele, JButton ajouterCritere) {
		this.modele=modele;
		this.ajouterCritere=ajouterCritere;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String critere = JOptionPane.showInputDialog("Intitulé du critère ?");
		this.modele.addCritere(critere);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
