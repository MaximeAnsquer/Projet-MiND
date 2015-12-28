package presentation;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import abstraction.modules.ScenariosDeMenacesTypes;

public class ModeleScenarioDeMenacesTypes extends AbstractTableModel {
	private ScenariosDeMenacesTypes moduleCourant ;
	private LinkedList<String> entetes = new LinkedList<String>();
	
	public ModeleScenarioDeMenacesTypes() {
		super();
		entetes.add("Bien support");
		entetes.add("Type(s)");
		entetes.add("Scenario generique");
		entetes.add("Scenario concret");
		entetes.add("Sources de menace");
	}
	
	public ScenariosDeMenacesTypes getModuleCourant(){
		return this.moduleCourant;
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.moduleCourant.getSize();
	}

	public int getColumnCount() {
		return this.entetes.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
