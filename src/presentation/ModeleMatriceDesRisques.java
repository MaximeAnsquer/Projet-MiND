package presentation;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.modules.EvenementsRedoutes;
import abstraction.modules.MatriceDesRisques;

public class ModeleMatriceDesRisques extends AbstractTableModel{
    private ArrayList<String>[][] data;
    private MatriceDesRisques matrice;
    private Etude etude;
    
    
    public ModeleMatriceDesRisques(){
    	this.etude=MainMaximeEtienne.etude;
		this.matrice=(MatriceDesRisques)etude.getModule("MatriceDesRisques");
    	this.data=this.matrice.getMatrice();
    	
    	}
    
	
	public int getColumnCount() {
		
		return this.data[0].length;
		
	}


	public int getRowCount() {
		
		return this.data.length;
	}

	  public Class getColumnClass(int c) {
          return getValueAt(0, c).getClass();
      }
	
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
		}
	
	
	
	
	
	
		
	

}
