package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;

import presentation.ModeleTypologieBiensSupports;

public class ControlJButtonModifierDescription implements Observer, ActionListener {
	private ModeleTypologieBiensSupports modele;
	private JTable tableau;
	private JTextArea description ;
	private JButton modifierDescription;
	
	public ControlJButtonModifierDescription(
			ModeleTypologieBiensSupports modele, JTable tableau,
			JTextArea description, JButton modifierDescription) {
		this.modele = modele;
		this.tableau = tableau;
		this.description=description;
		this.modifierDescription = modifierDescription;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.modele.setValueAt(this.description.getText(),
				this.tableau.getSelectedRow(),
				ModeleTypologieBiensSupports.COLONNE_DESCRIPTION);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (this.modele.getRowCount() == 0 || this.tableau.getSelectedRow()==-1) {
			this.modifierDescription.setEnabled(false);
		}
		else{
			this.modifierDescription.setEnabled(true);
		}
	}

}
