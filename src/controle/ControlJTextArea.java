package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.JTextArea;

import presentation.ModeleTypologieBiensSupports;

public class ControlJTextArea implements Observer,KeyListener {
	private ModeleTypologieBiensSupports modele;
	private JTable tableau ;
	private JTextArea zoneDescription;
	
	public ControlJTextArea(ModeleTypologieBiensSupports modele, JTable tableau, JTextArea zoneDescription) {
		this.modele=modele;
		this.tableau=tableau;
		this.zoneDescription=zoneDescription;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (this.tableau.getSelectedRow() == -1 || this.modele.getRowCount() == 0 ) {
			this.zoneDescription.setText("");
		} else {
			this.zoneDescription.setText((String) this.modele.getTypeBienCourant().getDescription());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
