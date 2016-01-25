package presentation;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import abstraction.Etude;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.modules.EvenementsRedoutes;

public class ModeleEvenementsRedoutes extends AbstractTableModel{
	
	/* les varialbes d'instance sont l'�tude consid�r�e, les evenements redoutes qui permettent d'�tablir le tableau*/
	private Etude etude;
	private EvenementsRedoutes evenements;
	
	/*mais �galement les �l�ments du tableau lui m�me, c'est � dire une liste d'Objects qui seront en fait des evenements
	 * et les ent�tes du tableau Evenements Redout�s
	 */
	ArrayList<Object> data;
	ArrayList<String> entetes=new ArrayList<String>();
	
	
	
	public ModeleEvenementsRedoutes(EvenementsRedoutes evenements){
		this.etude=evenements.getEtude();
		this.evenements=evenements;
		int a=this.evenements.getEvenementsRedoutes().size();
		
		this.data=new ArrayList<Object>();
			
				for(int j=0;j<a;j++){
					
					this.data.add(this.evenements.getEvenementsRedoutes().get(j));
					
				}
				
				this.entetes.add("Nom événement");
				this.entetes.add("Bien essentiel");
				this.entetes.add("Critère");
				this.entetes.add("Exigence");
				this.entetes.add("Gravité");
			
				
				if(this.evenements.getEvenementsRedoutes().get(0).getNomGroupes()!=null){
					int b=this.evenements.getEvenementsRedoutes().get(0).getNomGroupes().size();
				for(int i=0;i<b;i++){
				this.entetes.add(0,this.evenements.getEvenementsRedoutes().get(0).getNomGroupes().get(i));
				
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
					fireTableCellUpdated(rowIndex, columnIndex);
					break;
				case 1:
					
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
 
