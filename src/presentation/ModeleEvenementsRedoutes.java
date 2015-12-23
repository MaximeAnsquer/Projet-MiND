package presentation;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import abstraction.Etude;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.modules.EvenementsRedoutes;

public class ModeleEvenementsRedoutes extends AbstractTableModel{
	private Etude etude;
	private EvenementsRedoutes evenements;
	ArrayList<Object> data;
	ArrayList<String> entetes=new ArrayList<String>();
	
	
	
	public ModeleEvenementsRedoutes(){
		this.etude=MainMaximeEtienne.etude;
		this.evenements=(EvenementsRedoutes)etude.getModule("EvenementsRedoutes");
		int a=this.evenements.getEvenementsRedoutes().size();
		
		System.out.println(a+"");
		this.data=new ArrayList<Object>();
			
				for(int j=0;j<a;j++){
					System.out.println(this.evenements.getEvenementsRedoutes().get(j).GetNomEvenement());
					this.data.add(this.evenements.getEvenementsRedoutes().get(j));
					
				}
				
				this.entetes.add("Nom evênement");
				this.entetes.add("Bien essentiel");
				this.entetes.add("Critère");
				this.entetes.add("Exigence");
				this.entetes.add("Gravité");
			
				
				if(this.evenements.getEvenementsRedoutes().get(0).getNomGroupes()!=null){
					int b=this.evenements.getEvenementsRedoutes().get(0).getNomGroupes().size();
				for(int i=0;i<b;i++){
				this.entetes.add(this.evenements.getEvenementsRedoutes().get(0).getNomGroupes().get(i));
				
				}
				}
				
			}
	public String getColumnName(int columnIndex) {
		return entetes.get(columnIndex);
	}
			
		

	public int getColumnCount() {
		if(this.evenements.getEvenementsRedoutes().get(0).getNomGroupes()!=null){
		return 5+this.evenements.getEvenementsRedoutes().get(0).getNomGroupes().size();
		}
		else{
			return 5;
		}
		
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
			return evenements.getEvenementsRedoutes().get(rowIndex).getNiveauExigence();
		case 0:
			return evenements.getEvenementsRedoutes().get(rowIndex).getNiveauGravite();
		default:
			if(this.evenements.getEvenementsRedoutes().get(0).getNomGroupes().size()!=0){
				return this.evenements.getEvenementsRedoutes().get(rowIndex).getContenuGroupes().get(columnIndex);
			}
			else{
				return "";
			}
		}
		
	}
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	public boolean isCellEditable(int row, int col){
		if(this.getColumnCount()-col-1==4||this.getColumnCount()-col-1==0||this.getColumnCount()-col-1==1){
			return true;
		}
		else{
		return false; 
		}
	}
	 
		
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if(aValue != null){
				
				Evenement ev = evenements.getEvenementsRedoutes().get(rowIndex);
				
				switch(this.getColumnCount()-columnIndex-1){
				case 4:
					ev.setNomEvenement((String)aValue);
					break;
				case 1:
					/*ev.getComboExigence().getSelectedItem();
					ev.getComboExigence().setSelectedIndex(ev.getComboExigence().getSelectedIndex());*/
					
				ev.setNiveauExigence(Integer.parseInt((String)aValue));
				fireTableCellUpdated(rowIndex, columnIndex);
					break;
				case 0:
					ev.setNiveauGravite(Integer.parseInt((String)aValue));;
					fireTableCellUpdated(rowIndex,columnIndex);
					break;

				}
			}
		
		
	}
		
		

}
 
