package presentation;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.modules.EvenementsRedoutes;
import abstraction.modules.MatriceDesRisques;
import abstraction.modules.Metriques;

public class ModeleMatriceDesRisques extends AbstractTableModel{
    private ArrayList<String>[][] data;
    private MatriceDesRisques matrice;
    private Etude etude;
    private ArrayList<String> entetes;
    
    
    public ModeleMatriceDesRisques(MatriceDesRisques mat){
    	this.etude=mat.getEtude();
		this.matrice=mat;
    	this.data=this.matrice.getMatrice();
    	
    
    	
    	this.entetes=new ArrayList<String>();

		this.entetes.add("Gravité-Vraisemblance");
		int a=this.matrice.nombre();
		
		for (int i=1;i<=a;i++){
			this.entetes.add(""+i);
		}
		
    	
    	}
    
	
	public int getColumnCount() {
		
		return this.entetes.size();
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
		
		String risques="";
	
		if (columnIndex!=0){
			
		if(data[rowIndex][columnIndex].size()!=0){
			
		for (int i=0;i<data[rowIndex][columnIndex].size();i++){
		 risques= risques+"["+data[rowIndex][columnIndex].get(i)+"]";
		
	}}
		}
		else{
			risques=data[rowIndex][columnIndex].get(0);
		}
	
	return risques;
	
	}
	
	public String getColumnName(int column) {
		  return entetes.get(column);
		}
}
	
	
		
	


