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
		Object[] criteresRetenus = this.modele.getCriteresDeSecurite().getCriteresRetenus().keySet().toArray();
		String critere = (String) JOptionPane.showInputDialog(null,
				"Quelle critère voulez-vous rajoutez ?", "Choix du critère",
				JOptionPane.QUESTION_MESSAGE, null, criteresRetenus, criteresRetenus[0]);
		if (critere!=null){
			this.modele.addCritere(critere);
		}
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
