package presentation;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import abstraction.autres.Evenement;
import abstraction.modules.EvenementsRedoutes;

public class ModeleEvenementsRedoutes extends AbstractTableModel{
	private EvenementsRedoutes evenements;
	ArrayList<Object> data;
	ArrayList<String> entetes;
	
	
	
	public ModeleEvenementsRedoutes(){
		int a=this.evenements.getEvenementsRedoutes().size();
			
				for(int j=0;j<a;j++){
					this.data.add(this.evenements.getEvenementsRedoutes().get(j));
				}
				
			}
			
		

	public int getColumnCount() {
		return 5+this.evenements.getEvenementsRedoutes().get(0).getNomGroupes().size();
		
	}

	public int getRowCount() {
		return this.evenements.getEvenementsRedoutes().size();
	}




	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(this.getColumnCount()-columnIndex-1){
		case 4:
			return evenements.getEvenementsRedoutes().get(rowIndex).GetNomEvenement();
		case 3:
			return evenements.getEvenementsRedoutes().get(rowIndex).GetNomBien();
		case 2:
			return evenements.getEvenementsRedoutes().get(rowIndex).getNomCritere();
		case 1:
			return evenements.getEvenementsRedoutes().get(rowIndex).getComboExigence();
		case 0:
			return evenements.getEvenementsRedoutes().get(rowIndex).getComboGravite();
		default:
			if(this.evenements.getEvenementsRedoutes().get(0).getNomGroupes().size()!=0){
				return this.evenements.getEvenementsRedoutes().get(rowIndex).getContenuGroupes().get(columnIndex);
			}
			else{
				return "";
			}
		}
		
		
	}

}
