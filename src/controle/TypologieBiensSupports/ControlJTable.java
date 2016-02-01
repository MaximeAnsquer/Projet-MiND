package controle.TypologieBiensSupports;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import abstraction.autres.ScenarioGenerique;
import abstraction.autres.ScenarioType;
import presentation.ModeleTypologieBiensSupports;

public class ControlJTable implements MouseListener,KeyListener {
	private ModeleTypologieBiensSupports modele;
	private JTable tableau ;
	private String idCourant ;
	private String intituleCourant ;
	
	public ControlJTable(ModeleTypologieBiensSupports modele, JTable tableau) {
		this.modele=modele;
		this.tableau=tableau;
		this.idCourant="";
		this.intituleCourant="";
	}

	public void mouseClicked(MouseEvent e) {
		if (this.tableau.getSelectedRow() != -1) {
			this.modele.setTypeBienCourant(this.tableau.getSelectedRow());
			this.idCourant=(String) this.tableau.getValueAt(this.tableau.getSelectedRow(), 1);
			this.intituleCourant = (String) this.tableau.getValueAt(this.tableau.getSelectedRow(), 2);
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
