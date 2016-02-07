package controle.ScenariosDeMenacesTypes;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import presentation.ModeleScenarioDeMenacesTypes;

public class ControlJTable implements MouseListener,Observer {
	private ModeleScenarioDeMenacesTypes modele ;
	private JTable tableau ;
	private JFrame listeSourcesMenaces;
	
	public ControlJTable(ModeleScenarioDeMenacesTypes modele, JTable tableau, JFrame fen) {
		this.modele=modele;
		this.tableau=tableau;
		this.listeSourcesMenaces=fen;
	}

	public void mouseClicked(MouseEvent e) {
		if (this.tableau.getSelectedRow() != -1) {
			this.modele.setScenarioCourant(this.tableau.getSelectedRow());
		}
	}

	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			// On sélectionne la case correspondante
			Point p = e.getPoint();
			int rowNumber = this.tableau.rowAtPoint(p);
			int colNumber = this.tableau.columnAtPoint(p);
			this.tableau.changeSelection(rowNumber, colNumber, false, true);
			
			// On affiche la fenêtre si la colonne est celle des sources de menaces
			if (colNumber==ModeleScenarioDeMenacesTypes.COLONNE_SOURCES_MENACES){
				this.listeSourcesMenaces.setVisible(true);
				
				// On actualise la fenêtre pour qu'elle corresponde à ce qui est dans la case
				int row = this.tableau.getSelectedRow();
				int col = this.tableau.getSelectedColumn();
				if(row != -1 && col != -1){
					String idsSourcesMenaces = (String) this.modele.getValueAt(row, col);
					for (int i = 0 ; i<this.listeSourcesMenaces.getContentPane().getComponentCount() ; i++){
						JCheckBox checkbox = ((JCheckBox) this.listeSourcesMenaces.getContentPane().getComponent(i));
						String idSDM = checkbox.getText();
						if (idsSourcesMenaces.contains(idSDM)){
							checkbox.setSelected(true);
						}
						else{
							checkbox.setSelected(false);
						}
					}
				}
			}
		}
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

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
