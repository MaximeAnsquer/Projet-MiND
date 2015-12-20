package controle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import presentation.ModeleTypologieBiensSupports;

public class ControlJTable implements MouseListener {
	private ModeleTypologieBiensSupports modele;
	private JTable tableau ;
	
	public ControlJTable(ModeleTypologieBiensSupports modele, JTable tableau) {
		this.modele=modele;
		this.tableau=tableau;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.tableau.getSelectedRow() != -1) {
			this.modele.setTypeBienCourant(this.tableau.getSelectedRow());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
