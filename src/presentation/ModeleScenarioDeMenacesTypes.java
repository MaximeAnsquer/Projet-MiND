package presentation;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import abstraction.autres.ScenarioType;
import abstraction.modules.BiensSupports;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;

public class ModeleScenarioDeMenacesTypes extends AbstractTableModel {
	private SourcesDeMenaces sourcesDeMenaces = new SourcesDeMenaces();
	private BiensSupports biensSupports = new BiensSupports();
	private ScenariosDeMenacesGeneriques scenarioDeMenacesGeneriques = new ScenariosDeMenacesGeneriques();
	private ScenariosDeMenacesTypes moduleCourant = new ScenariosDeMenacesTypes() ;
	private LinkedList<String> entetes = new LinkedList<String>();
	
	public static final int COLONNE_BIEN_SUPPORT = 0;
	public static final int COLONNE_TYPE= 1;
	public static final int COLONNE_ID= 2;
	public static final int COLONNE_SCENARIO_GENERIQUE = 3;
	public static final int COLONNE_SCENARIO_CONCRET= 4;
	public static final int COLONNE_SOURCES_MENACES = 5;
	
	public static final int COLONNE_CRITERE_D = 6;
	public static final int COLONNE_CRITERE_I = 7;
	public static final int COLONNE_CRITERE_C = 8;
	public static final int COLONNE_CRITERE_T = 9;
	
	public static final int COLONNE_VRAISEMBLANCE_I = 10;
	public static final int COLONNE_VRAISEMBLANCE_R = 11;
	public static final int COLONNE_RETENU= 12;
	
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
	
	public SourcesDeMenaces getSourcesDeMenaces(){
		return this.sourcesDeMenaces;
	}

	public int getRowCount() {
		return this.moduleCourant.getSize();
	}

	public int getColumnCount() {
		return this.entetes.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		ScenarioType scenarioType = this.moduleCourant.getScenarioType(rowIndex);
		switch(this.getColumnCount()-columnIndex-1){
		case 12 :
			return scenarioType.getBienSupport();
		case 11 :
			return scenarioType.getVraisemblanceReelle();
		case 10 :
			return scenarioType.getId();
		case 9 :
			return scenarioType.getIntitule();
		case 8 :
			return scenarioType.getIntituleConcret();
		case 7 :
			return null;
		case 6 :
			return scenarioType.getCriteresSup().get("Disponibilité");	
		case 5 :
			return scenarioType.getCriteresSup().get("Intégrité");
		case 4 :
			return scenarioType.getCriteresSup().get("Confidentialité");
		case 3 :
			return scenarioType.getCriteresSup().get("Traçabilité");
		case 2 :
			return scenarioType.getVraisemblanceIntrinseque();
		case 1 :
			return scenarioType.getVraisemblanceReelle();
		case 0 :
			return scenarioType.isRetenuScenarioType();
		default :
			return null;
		}
	}
	
	

}
