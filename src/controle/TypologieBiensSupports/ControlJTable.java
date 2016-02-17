package controle.TypologieBiensSupports;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import abstraction.autres.ScenarioGenerique;
import abstraction.autres.ScenarioType;
import presentation.ModeleTypologieBiensSupports;

public class ControlJTable implements MouseListener,KeyListener {
	private ModeleTypologieBiensSupports modele;
	private JTable tableau ;
	private String idCourant ;
	private String intituleCourant ;
	private JFrame fenetreDescription;
	
	public ControlJTable(ModeleTypologieBiensSupports modele, JTable tableau, JFrame fen) {
		this.modele=modele;
		this.tableau=tableau;
		this.idCourant="";
		this.intituleCourant="";
		this.fenetreDescription=fen;
	}

	public void mouseClicked(MouseEvent e) {
		if (this.tableau.getSelectedRow() != -1) {
			this.modele.setTypeBienCourant(this.tableau.getSelectedRow());
			this.idCourant=(String) this.tableau.getValueAt(this.tableau.getSelectedRow(), 1);
			this.intituleCourant = (String) this.tableau.getValueAt(this.tableau.getSelectedRow(), 2);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			// On sélectionne la case correspondante
			Point p = e.getPoint();
			int rowNumber = this.tableau.rowAtPoint(p);
			int colNumber = this.tableau.columnAtPoint(p);
			this.tableau.changeSelection(rowNumber, colNumber, false, true);
			
			this.fenetreDescription.setVisible(true);
			
			// On actualise la fenêtre pour qu'elle corresponde à ce qui est dans la case
			int row = this.tableau.getSelectedRow();
			int col = this.tableau.getSelectedColumn();
			if(row != -1 && col != -1){
				String description = (String) this.modele.getValueAt(row, col);
				((JTextArea) this.fenetreDescription.getContentPane().getComponent(0)).setText(description);
				
				Point positionSouris = MouseInfo.getPointerInfo().getLocation();
				int xSouris = (int) positionSouris.getX();
				int ySouris = (int) positionSouris.getY();
				Point positionDeLaFenetre = new Point(xSouris - 1, ySouris + 1);
				this.fenetreDescription.setLocation(positionDeLaFenetre);
				
				this.fenetreDescription.pack();
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

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		if (this.tableau.getSelectedRow() != -1 && this.modele.getScenariosGeneriques()!=null){
			if (this.tableau.getSelectedColumn()==1){
				for (ScenarioGenerique sGene : this.modele.getScenariosGeneriques().getTableau()){
					if (sGene.getTypeBienSupport().getId().equals(idCourant)){
						String newId = (String) this.modele.getValueAt(this.tableau.getSelectedRow(), 1);
						sGene.setId(newId);
					}
				}
			}
			else{
				if (this.tableau.getSelectedColumn()==2){
					for (ScenarioGenerique sGene : this.modele.getScenariosGeneriques().getTableau()){
						if (sGene.getTypeBienSupport().getIntitule().equals(intituleCourant)){
							String newIntitule = (String) this.tableau.getValueAt(this.tableau.getSelectedRow(), 2);
							sGene.setIntitule(newIntitule);
						}
					}
				}
			}
		}	
	}

}
