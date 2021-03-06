package controle.ScenariosMenacesGeneriques;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
		//ArrayList<Critere> criteresRetenus = this.modele.getCriteresDeSecurite().getCriteresRetenus();
		ArrayList<String> intitulesCriteresRetenus = this.modele.getCriteresDeSecurite().getIntitulesCriteresRetenus();
		
		if (this.modele.getModuleCourant().getNomColonneSup()!=null){
			for (String nomCritere : this.modele.getModuleCourant().getNomColonneSup()){
				intitulesCriteresRetenus.remove(nomCritere);
			}
		}
		
		Object[] tabCriteresRetenus = intitulesCriteresRetenus.toArray();
		String critere = (String) JOptionPane.showInputDialog(null,
				"Quel critère voulez-vous rajouter ?", "Choix du critère",
				JOptionPane.QUESTION_MESSAGE, null, tabCriteresRetenus, tabCriteresRetenus[0]);
		if (critere!=null){
			this.modele.addCritere(critere);
		}
	}

	public void update(Observable o, Object arg) {
		if (this.modele.getModuleCourant().getNomColonneSup().size()==this.modele.getCriteresDeSecurite().nombreDeCriteres()){
			this.ajouterCritere.setEnabled(false);
		}
		else{
			this.ajouterCritere.setEnabled(true);
		}
	}
}
