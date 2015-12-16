package presentation;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import abstraction.modules.MatriceDesRisques;

public class ModeleMatriceDesRisques extends AbstractTableModel{
    private ArrayList<String>[][] data;
    private MatriceDesRisques matrice;
    
    
    public ModeleMatriceDesRisques(){
    	this.data=this.matrice.getMatrice();
    	
    	}
    
	
	public int getColumnCount() {
		
		return this.data[1].length;
		
	}


	public int getRowCount() {
		
		return this.data.length;
	}

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
		}
		
	

}
