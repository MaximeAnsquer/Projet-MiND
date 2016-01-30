package presentation;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Evenement;
import abstraction.autres.Risque;
import abstraction.modules.AnalyseDesRisques;
import abstraction.modules.EvenementsRedoutes;

public class ModeleAnalyseDesRisques extends AbstractTableModel{
	private Etude etude;
	private AnalyseDesRisques analyse;
	private ArrayList<Object> data=new ArrayList<Object>();
	private ArrayList<String> entetes=new ArrayList<String>();
	
	
	
	public ModeleAnalyseDesRisques(AnalyseDesRisques analyse){
		this.etude=analyse.getEtude();
		this.analyse=analyse;
		
		int a=this.analyse.getAnalyseDesRisques().size();
		System.out.println(a+"");
		
		for (int i=0;i<a;i++){
			this.data.add(this.analyse.getAnalyseDesRisques().get(i));
		}
	this.entetes.add("Evenement redouté");
	this.entetes.add("Niveau gravité");
	this.entetes.add("Bien support");
	this.entetes.add("Scenario concret");
	this.entetes.add("Niveau vraisemblance");
	this.entetes.add("Retenu");
	this.entetes.add("Intitulé du risque");
		
	
	}
	public int getColumnCount() {
		return 7;
		
	}
	
	public String getColumnName(int columnIndex) {
		return entetes.get(columnIndex);
	}
	

	public int getRowCount() {
		return this.analyse.getAnalyseDesRisques().size();
	}
	

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return analyse.getAnalyseDesRisques().get(rowIndex).getEvenementRedoute().GetNomEvenement();
		case 1:
			return analyse.getAnalyseDesRisques().get(rowIndex).getNiveauGravite();
		
		case 2:
			return analyse.getAnalyseDesRisques().get(rowIndex).getBienSupport().getIntitule();
		case 3:
			return analyse.getAnalyseDesRisques().get(rowIndex).getScenarioConcret().getIntitule();
		case 4:
			return analyse.getAnalyseDesRisques().get(rowIndex).getNiveauVraisemblance();
		case 5:
			return analyse.getAnalyseDesRisques().get(rowIndex).getRetenu();
		case 6:
			return analyse.getAnalyseDesRisques().get(rowIndex).getIntitule();
		default:
			
				return "";
			}
				
	}

	
	 public void setValueAt(Object aValue, int row, int col) {
		 if(aValue != null){
				
				Risque r = this.analyse.getAnalyseDesRisques().get(row);
				
				switch(col){
				case 6:
					r.setIntitule((String)aValue);
					break;
				case 5:
				    r.setRetenu((Boolean) aValue);
				    break;
				 default :   
				
				
	 }

}
	 }
	 
	 public boolean isCellEditable(int row,int col){
		 if(col==6||col==5){
			 return true;
		 }
			 else{
				 return false;
			 }
		 }
		 
	 
}
