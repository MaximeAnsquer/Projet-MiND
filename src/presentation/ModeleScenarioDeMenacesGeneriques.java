package presentation;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import abstraction.autres.ScenarioGenerique;
import abstraction.autres.TypeBien;
import abstraction.modules.ScenariosDeMenacesGeneriques;

public class ModeleScenarioDeMenacesGeneriques extends AbstractTableModel {
	
	private ScenariosDeMenacesGeneriques moduleCourant = new ScenariosDeMenacesGeneriques();
	private LinkedList<String> entetes = new LinkedList<String>();
	private LinkedList<ArrayList<String>> colonnesSup = new LinkedList<ArrayList<String>>();
	
	public static final int COLONNE_TYPEBIENSUPPORT = 0;
	public static final int COLONNE_ID = 1;
	public static final int COLONNE_INTITULE= 2;
	public static final int COLONNE_RETENU= 3;
	public static int COLONNE_CRITERE_D = 4;
	public static int COLONNE_CRITERE_I= 5;
	public static int COLONNE_CRITERE_C = 6;
	
	public ModeleScenarioDeMenacesGeneriques(){
		super();
		entetes.add("Type de bien support");
		entetes.add("Id");
		entetes.add("Scénario générique");
		entetes.add("Retenu");
	}
	
	public ScenariosDeMenacesGeneriques getModuleCourant(){
		return this.moduleCourant;
	}

	public int getRowCount() {
		return this.moduleCourant.getSize();
	}

	public int getColumnCount() {
		return this.entetes.size();
	}
	
	public String getColumnName(int columnIndex) {
		return this.entetes.get(columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case COLONNE_TYPEBIENSUPPORT:
			TypeBien typeScenario = this.moduleCourant.getScenarioGenerique(rowIndex).getType();
			return typeScenario.getIntitule() + " (" + typeScenario.getId() + ")";
		case COLONNE_ID:
			return this.moduleCourant.getScenarioGenerique(rowIndex).getId();
		case COLONNE_INTITULE:
			return this.moduleCourant.getScenarioGenerique(rowIndex).getIntitule();
		case COLONNE_RETENU:
			return this.moduleCourant.getScenarioGenerique(rowIndex).isRetenuScenario();
		default:
			return null; // Ne devrait jamais arriver
		}
	}

}
