package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import abstraction.autres.Critere;
import presentation.ModeleScenarioDeMenacesGeneriques;

public class ControlJButtonAjoutColonne implements Observer,ActionListener {
	private ModeleScenarioDeMenacesGeneriques modele;
	private JButton ajouterCritere ;
	
	public ControlJButtonAjoutColonne(ModeleScenarioDeMenacesGeneriques modele, JButton ajouterCritere) {
		this.modele=modele;
		this.ajouterCritere=ajouterCritere;
	}

	public void actionPerformed(ActionEvent e) {
		Hashtable<String, Critere> criteresRetenus = this.modele.getCriteresDeSecurite().getCriteresRetenus();
		
		if (this.modele.getModuleCourant().getNomColonneSup()!=null){
			for (String nomCritere : this.modele.getModuleCourant().getNomColonneSup()){
				criteresRetenus.remove(nomCritere);
			}
		}
		
		Object[] tabCriteresRetenus = criteresRetenus.keySet().toArray();
		String critere = (String) JOptionPane.showInputDialog(null,
				"Quelle critère voulez-vous rajoutez ?", "Choix du critère",
				JOptionPane.QUESTION_MESSAGE, null, tabCriteresRetenus, tabCriteresRetenus[0]);
		if (critere!=null){
			this.modele.addCritere(critere);
		}
	}

	public void update(Observable o, Object arg) {
		if (this.modele.getModuleCourant().getNomColonneSup().size()==4){
			this.ajouterCritere.setEnabled(false);
		}
		else{
			this.ajouterCritere.setEnabled(true);
		}
	}
}
