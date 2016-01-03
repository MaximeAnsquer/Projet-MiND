package controle.ScenariosDeMenacesTypes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import presentation.ModeleScenarioDeMenacesTypes;

public class ControlJTable implements MouseListener  {
	private ModeleScenarioDeMenacesTypes modele ;
	private JTable tableau ;
	
	public ControlJTable(ModeleScenarioDeMenacesTypes modele, JTable tableau) {
		this.modele=modele;
		this.tableau=tableau;
	}

	public void mouseClicked(MouseEvent e) {
		if (this.tableau.getSelectedRow() != -1) {
			this.modele.setScenarioCourant(this.tableau.getSelectedRow());
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
