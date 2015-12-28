package presentation;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;

public class ModeleScenarioDeMenacesTypes extends AbstractTableModel {
	private SourcesDeMenaces sourcesDeMenaces = new SourcesDeMenaces();
	private ScenariosDeMenacesTypes moduleCourant = new ScenariosDeMenacesTypes() ;
	private LinkedList<String> entetes = new LinkedList<String>();
	
	public static final int COLONNE_SYSTEME = 0;
	public static final int COLONNE_BIEN_SUPPORT = 1;
	public static final int COLONNE_TYPE= 2;
	public static final int COLONNE_ID= 3;
	public static final int COLONNE_SCENARIO_GENERIQUE = 4;
	public static final int COLONNE_SCENARIO_CONCRET= 5;
	public static final int COLONNE_SOURCES_MENACES = 6;
	
	public static final int COLONNE_CRITERE_D = 7;
	public static final int COLONNE_CRITERE_I = 8;
	public static final int COLONNE_CRITERE_C = 9;
	public static final int COLONNE_CRITERE_T = 10;
	
	public static final int COLONNE_VRAISEMBLANCE_I = 11;
	public static final int COLONNE_VRAISEMBLANCE_R = 12;
	public static final int COLONNE_RETENU= 13;
	
	public ModeleScenarioDeMenacesTypes() {
		super();
		entetes.add("Système");
		entetes.add("Bien support");
		entetes.add("Type(s)");
		entetes.add("Scénario générique");
		entetes.add("Scénario concret");
		entetes.add("Sources de menace");
		
		entetes.add("D");
		entetes.add("I");
		entetes.add("C");
		entetes.add("T");
		
		entetes.add("Vraisemblance intrinsèque");
		entetes.add("Vraisemblance réelle");
		entetes.add("Retenu");
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
