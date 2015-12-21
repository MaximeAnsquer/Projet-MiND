package presentation;

import javax.swing.table.AbstractTableModel;

import abstraction.modules.ScenariosDeMenacesGeneriques;

public class ModeleScenarioDeMenacesGeneriques extends AbstractTableModel {
	
	private ScenariosDeMenacesGeneriques moduleCourant = new ScenariosDeMenacesGeneriques();
	private final String[] entetes = {"Id", "Intitulé", "Description", "Retenu"} ;
	
	public ModeleScenarioDeMenacesGeneriques(){
		super();
	}
	
	public ScenariosDeMenacesGeneriques getModuleCourant(){
		return this.moduleCourant;
	}

	public int getRowCount() {
		return this.moduleCourant.getSize();
	}

	public int getColumnCount() {
		return this.entetes.length;
	}
	
	public String getColumnName(int columnIndex) {
		return this.entetes[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		
		default:
			return null; // Ne devrait jamais arriver
		}
	}

}
