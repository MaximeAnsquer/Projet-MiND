package controle.TypologieBiensSupports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JTable;

import presentation.ModeleTypologieBiensSupports;

public class ControlJButtonSuppressionTypeBien implements Observer,
		ActionListener {
	private ModeleTypologieBiensSupports modele;
	private JTable tableau;
	private JButton supprimer;

	public ControlJButtonSuppressionTypeBien(
			ModeleTypologieBiensSupports modele, JTable tableau,
			JButton supprimer) {
		this.modele = modele;
		this.tableau = tableau;
		this.supprimer = supprimer;
	}

	public void actionPerformed(ActionEvent e) {
		int[] selection = tableau.getSelectedRows();
		
		for(int i = selection.length - 1; i >= 0; i--){
			modele.removeTypeBien(selection[i]);
		} 
	}

	public void update(Observable o, Object arg) {
		if (this.modele.getRowCount()==0 || this.tableau.getSelectedColumn()==-1){
			supprimer.setEnabled(false);
		}
		else{
			supprimer.setEnabled(true);
		}
	}

}
