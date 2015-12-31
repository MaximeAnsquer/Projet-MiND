package presentation;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.ScenarioType;
import abstraction.modules.BiensSupports;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;

public class ModeleScenarioDeMenacesTypes extends AbstractTableModel {
	private SourcesDeMenaces sourcesDeMenaces = new SourcesDeMenaces();
	private BiensSupports biensSupports = new BiensSupports(new Etude());
	private ScenariosDeMenacesGeneriques scenarioDeMenacesGeneriques = new ScenariosDeMenacesGeneriques(new Etude());
	private ScenariosDeMenacesTypes moduleCourant = new ScenariosDeMenacesTypes() ;
	private LinkedList<String> entetes = new LinkedList<String>();
	
	public static int COLONNE_BIEN_SUPPORT = 0;
	public static int COLONNE_TYPE= 1;
	public static int COLONNE_ID= 2;
	public static int COLONNE_SCENARIO_GENERIQUE = 3;
	public static int COLONNE_SCENARIO_CONCRET= 4;
	public static int COLONNE_SOURCES_MENACES = 5;
	
	/*
	public static final int COLONNE_CRITERE_D = 6;
	public static final int COLONNE_CRITERE_I = 7;
	public static final int COLONNE_CRITERE_C = 8;
	public static final int COLONNE_CRITERE_T = 9;
	*/
	public static int COLONNE_VRAISEMBLANCE_I=6 ;
	public static int COLONNE_VRAISEMBLANCE_R=7 ;
	public static int COLONNE_RETENU=8 ;
	
	public ModeleScenarioDeMenacesTypes() {
		super();
		
		// Ajout des colonnes supplémentaires provenant du module "Biens Supports"
		if (this.biensSupports.getNomColonnesSup()!=null){
			for (int i=0; i<this.biensSupports.getNomColonnesSup().size();i++){
				entetes.add(this.biensSupports.getNomColonnesSup().get(i));
			}
		}
		entetes.add("Bien support");
		entetes.add("Type(s)");
		entetes.add("Id");
		entetes.add("Scénario générique");
		entetes.add("Scénario concret");
		entetes.add("Sources de menace");
		
		// Ajout des colonnes supplémentaires provenant du module "Scenarios de menaces generiques"
		if (this.scenarioDeMenacesGeneriques.getNomColonneSup()!=null){
			for (int i=0; i<this.scenarioDeMenacesGeneriques.getNomColonneSup().size();i++){
				
				entetes.add(this.scenarioDeMenacesGeneriques.getNomColonneSup().get(i));
				this.moduleCourant.getNomColonneSup().add(this.scenarioDeMenacesGeneriques.getNomColonneSup().get(i));
			}
		}
		
		entetes.add("Vraisemblance intrinsèque");
		entetes.add("Vraisemblance réelle");
		entetes.add("Retenu");
		
		this.updateIndice();
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
	
	public void updateIndice(){
		if (this.biensSupports.getNomColonnesSup()!=null){
			int nbColonnesSup = this.biensSupports.getNomColonnesSup().size();
			COLONNE_BIEN_SUPPORT += nbColonnesSup;
			COLONNE_TYPE += nbColonnesSup;
			COLONNE_ID += nbColonnesSup;
			COLONNE_SCENARIO_GENERIQUE += nbColonnesSup;
			COLONNE_SCENARIO_CONCRET += nbColonnesSup;
			COLONNE_SOURCES_MENACES += nbColonnesSup;
			COLONNE_VRAISEMBLANCE_I+=nbColonnesSup;
			COLONNE_VRAISEMBLANCE_R +=nbColonnesSup;
			COLONNE_RETENU += nbColonnesSup;
		}
		if (this.scenarioDeMenacesGeneriques.getNomColonneSup()!=null){
			int nbCriteresSup=this.scenarioDeMenacesGeneriques.getNomColonneSup().size();
			COLONNE_VRAISEMBLANCE_I+=nbCriteresSup;
			COLONNE_VRAISEMBLANCE_R+=nbCriteresSup;
			COLONNE_RETENU+=nbCriteresSup;
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		ScenarioType scenarioType = this.moduleCourant.getScenarioType(rowIndex);
		Object resultat = new Object();
		if (this.biensSupports.getNomColonnesSup() != null
				&& columnIndex < this.biensSupports.getNomColonnesSup().size()) {
			
			// Bien du module BiensSupports ou du module ScenarioType ??
			resultat = this.biensSupports.getBien(rowIndex).getContenuColonnesSup().get(columnIndex);
		}
		if (columnIndex==COLONNE_BIEN_SUPPORT){
			resultat=scenarioType.getBienSupport().getIntitule();
		}
		if(columnIndex==COLONNE_TYPE){
			resultat=scenarioType.getTypeBienSupport();
		}
		if(columnIndex==COLONNE_ID){
			resultat=scenarioType.getId();
		}
		if(columnIndex==COLONNE_SCENARIO_GENERIQUE){
			resultat=scenarioType.getId();
		}
		if (columnIndex==COLONNE_SCENARIO_CONCRET){
			resultat=scenarioType.getIntituleConcret();
		}
		if (columnIndex==COLONNE_SOURCES_MENACES){
			resultat=scenarioType.listeSourcesMenaces();
		}
		if(this.scenarioDeMenacesGeneriques.getNomColonneSup()!=null){
			if (columnIndex>COLONNE_SOURCES_MENACES && columnIndex<COLONNE_VRAISEMBLANCE_I){
				// indice du critère dans l'ArrayList
				int indice = columnIndex-COLONNE_SOURCES_MENACES; 
				String critere = this.moduleCourant.getNomColonneSup().get(indice);
				resultat=this.moduleCourant.getScenarioType(rowIndex).getCriteresSup().get(critere);
			}
		}
		if(columnIndex==COLONNE_VRAISEMBLANCE_I){
			resultat=scenarioType.getVraisemblanceIntrinseque();
		}
		if(columnIndex==COLONNE_VRAISEMBLANCE_R){
			resultat=scenarioType.getVraisemblanceReelle();
		}
		if(columnIndex==COLONNE_RETENU){
			resultat=scenarioType.isRetenu();
		}
		return resultat;
		/*
		switch(this.getColumnCount()-columnIndex-1){
		case 12 :
			return scenarioType.getBienSupport().getIntitule();
		case 11 :
			return scenarioType.getTypeBienSupport();
		case 10 :
			return scenarioType.getId();
		case 9 :
			return scenarioType.getIntitule();
		case 8 :
			return scenarioType.getIntituleConcret();
		case 7 :
			return scenarioType.listeSourcesMenaces();
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
			if(biensSupports.getNomColonnesSup().get(columnIndex)!=null){
				return biensSupports.getBien(rowIndex).getContenuColonnesSup().get(columnIndex);
			}
			else{
				return "";
			}
		}
		*/
	}
	
	public boolean isCellEditable(int row, int col){
		return true; 
	}
	
	public Class getColumnClass(int columnIndex){
		switch(this.getColumnCount()-columnIndex-1){
		case 6 :
			return Boolean.class;
		case 5 :
			return Boolean.class;
		case 4 :
			return Boolean.class;
		case 3 :
			return Boolean.class;
		case 2 :
			return Integer.class;
		case 1 :
			return Integer.class;
		case 0 :
			return Boolean.class;
		default:
			return String.class;
		}
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue != null) {
			ScenarioType scenarioType = this.moduleCourant.getScenarioType(rowIndex);

			switch (this.getColumnCount() - columnIndex - 1) {
			case 12:
				if (!aValue.equals("")) {
					scenarioType.getBienSupport().setIntitule(((String) aValue));
				}
				break;
			case 11:
				if (!aValue.equals("")) {
					scenarioType.setTypeBienSupport((String) aValue);
				}
				break;
			case 10:
				if (!aValue.equals("")) {
					scenarioType.setId((String) aValue);
				}
				break;
			case 9:
				scenarioType.setRetenu((Boolean) aValue);
				break;
			default:
				if (!aValue.equals("")) {
					biensSupports.getBien(rowIndex).getContenuColonnesSup()
							.set(columnIndex, (String) aValue);
				}
				break;

			}
		}
	}
	
	

}
