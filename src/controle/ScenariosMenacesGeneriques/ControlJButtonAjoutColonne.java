package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import presentation.ModeleScenarioDeMenacesGeneriques;

public class ControlJButtonAjoutColonne implements Observer,ActionListener {
	private ModeleScenarioDeMenacesGeneriques modele;
	private JButton ajouterCritere ;
	
	public ControlJButtonAjoutColonne(ModeleScenarioDeMenacesGeneriques modele, JButton ajouterCritere) {
		this.modele=modele;
		this.ajouterCritere=ajouterCritere;
	}

	public void actionPerformed(ActionEvent e) {
		String critere = JOptionPane.showInputDialog("Intitule du critere ?");
		if (critere!=null){
			this.modele.addCritere(critere);
		}
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
