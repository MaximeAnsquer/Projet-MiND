package controle.ScenariosMenacesGeneriques;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import abstraction.autres.ScenarioType;
import presentation.ModeleScenarioDeMenacesGeneriques;

public class ControlJtable implements MouseListener, KeyListener {
	private ModeleScenarioDeMenacesGeneriques modele ;
	private JTable tableau ;
	private String idCourant ;
	private String intituleCourant ;
	
	public ControlJtable(ModeleScenarioDeMenacesGeneriques modele , JTable tableau) {
		this.modele=modele;
		this.tableau=tableau;
		this.idCourant="";
		this.intituleCourant="";
	}

	public void mouseClicked(MouseEvent e) {
		if (this.tableau.getSelectedRow() != -1) {
			this.modele.setScenarioCourant(this.tableau.getSelectedRow());
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
		/*
		if (this.tableau.getSelectedRow() != -1 && this.tableau.getSelectedColumn()==2 && this.modele.getScenarioTypes()!=null){
			for (ScenarioType sType : this.modele.getScenarioTypes().getTableau()){
				if (sType.getIntitule().equals(intituleCourant)){
					System.out.println("ENTREE dans le KeyPressed");
					String newIntitule = (String) this.tableau.getValueAt(this.tableau.getSelectedRow(), 2);
					sType.setIntitule(newIntitule);
				}
			}
		}
		*/
	}
	
	
	public void keyReleased(KeyEvent e) {
		if (this.tableau.getSelectedRow() != -1 && this.modele.getScenarioTypes()!=null){
			if (this.tableau.getSelectedColumn()==1){
				for (ScenarioType sType : this.modele.getScenarioTypes().getTableau()){
					if (sType.getId().equals(idCourant)){
						String newId = (String) this.modele.getValueAt(this.tableau.getSelectedRow(), 1);
						sType.setId(newId);
					}
				}
			}
			else{
				if (this.tableau.getSelectedColumn()==2){
					for (ScenarioType sType : this.modele.getScenarioTypes().getTableau()){
						if (sType.getIntitule().equals(intituleCourant)){
							String newIntitule = (String) this.tableau.getValueAt(this.tableau.getSelectedRow(), 2);
							sType.setIntitule(newIntitule);
						}
					}
				}
			}
		}	
	}
}
