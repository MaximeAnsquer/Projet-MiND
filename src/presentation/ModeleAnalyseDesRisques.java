package presentation;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import abstraction.modules.AnalyseDesRisques;

public class ModeleAnalyseDesRisques extends AbstractTableModel{
	private AnalyseDesRisques analyse;
	private ArrayList<Object> data;
	private ArrayList<String> entetes;
	
	
	
	public ModeleAnalyseDesRisques(){
		int a=this.analyse.getAnalyseDesRisques().size();
		
		for (int i=0;i<a;i++){
			this.data.add(this.analyse.getAnalyseDesRisques().get(i));
		}
	
	}
	public int getColumnCount() {
		return 7;
		
	}

	public int getRowCount() {
		return this.analyse.getAnalyseDesRisques().size();
	}




	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			analyse.getAnalyseDesRisques().get(rowIndex).getEvenementRedoute();
		case 1:
			return analyse.getAnalyseDesRisques().get(rowIndex).getNiveauGravite();
		
		case 2:
			return analyse.getAnalyseDesRisques().get(rowIndex).getBienSupport();
		case 3:
			return analyse.getAnalyseDesRisques().get(rowIndex).getScenarioConcret().getIntitule();
		case 4:
			return analyse.getAnalyseDesRisques().get(rowIndex).getNiveauVraisemblance();
		case 5:
			return analyse.getAnalyseDesRisques().get(rowIndex).getJCheckBox();
		case 6:
			return analyse.getAnalyseDesRisques().get(rowIndex).getIntitule();
		default:
			
				return "";
			}
		
		
		
	}

}



	

