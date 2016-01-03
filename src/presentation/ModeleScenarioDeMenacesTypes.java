package presentation;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.ScenarioGenerique;
import abstraction.autres.ScenarioType;
import abstraction.autres.SourceDeMenace;
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
		this.importerDonnees();
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
	
	public String getColumnName(int columnIndex) {
		return this.entetes.get(columnIndex);
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
	
	public void importerDonnees(){
		// On ajoute des biens supports pour tester
		Bien bien1 = new Bien("description bien 1", "BS 1", "Matériel", new LinkedList<String>() );
		bien1.setRetenu(true);
		this.biensSupports.ajouterBien(bien1);
		
		Bien bien2 = new Bien("description bien 2", "BS 2", "Logiciel", new LinkedList<String>() );
		bien2.setRetenu(true);
		this.biensSupports.ajouterBien(bien2);
		
		for (ScenarioGenerique sGene : this.scenarioDeMenacesGeneriques.getTableau().values()){
			ScenarioType scenario = new ScenarioType(sGene.getTypeBienSupport(), sGene.getId(), sGene.getIntitule(), sGene.getCriteresSup(), this.sourcesDeMenaces.getSourcesDeMenacesRetenues(), null, true);
			for (Bien b : this.biensSupports.getBiensRetenus().values()){
				if (sGene.getTypeBienSupport().contains(b.getType())){
					scenario.setBienSupport(b);
				}
			}
			this.moduleCourant.getTableau().put(sGene.getIntitule(), scenario);
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		ScenarioType scenarioType = this.moduleCourant.getScenarioType(rowIndex);
		Object resultat = new Object();
		if (this.biensSupports.getNomColonnesSup() != null
				&& columnIndex < this.biensSupports.getNomColonnesSup().size()) {
			
			// Bien du module BiensSupports ou du module ScenarioType ??
			resultat = scenarioType.getBienSupport().getContenuColonnesSup().get(columnIndex);
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
			resultat=scenarioType.getIntitule();
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
	}
	
	public boolean isCellEditable(int row, int col){
		return true; 
	}
	
	public Class getColumnClass(int columnIndex) {
		if (columnIndex == COLONNE_VRAISEMBLANCE_I
				|| columnIndex == COLONNE_VRAISEMBLANCE_R) {
			return Integer.class;
		} else {
			if (columnIndex == COLONNE_RETENU
					|| (columnIndex > COLONNE_SOURCES_MENACES && columnIndex < COLONNE_VRAISEMBLANCE_I)) {
				return Boolean.class;
			} else {
				return String.class;
			}
		}
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue != null) {
			ScenarioType scenarioType = this.moduleCourant.getScenarioType(rowIndex);
			
			if (columnIndex==COLONNE_BIEN_SUPPORT){
				scenarioType.getBienSupport().setIntitule((String) aValue);
			}
			if(columnIndex==COLONNE_TYPE){
				scenarioType.setTypeBienSupport((String) aValue);
			}
			if(columnIndex==COLONNE_ID){
				scenarioType.setId((String) aValue);
			}
			if(columnIndex==COLONNE_SCENARIO_GENERIQUE){
				scenarioType.setIntitule((String) aValue);
			}
			if (columnIndex==COLONNE_SCENARIO_CONCRET){
				scenarioType.setIntituleConcret((String) aValue);
			}
			if (columnIndex==COLONNE_SOURCES_MENACES){
				SourceDeMenace source = this.sourcesDeMenaces.getSourceDeMenace((String) aValue);
				if (source!=null){
					scenarioType.getMenaces().put((String) aValue, source);
				}
			}
			if(this.scenarioDeMenacesGeneriques.getNomColonneSup()!=null){
				
				if (columnIndex>COLONNE_SOURCES_MENACES && columnIndex<COLONNE_VRAISEMBLANCE_I){
					
					// indice du critère dans l'ArrayList
					int indice = columnIndex-COLONNE_SOURCES_MENACES; 
					String critere = this.moduleCourant.getNomColonneSup().get(indice);
					this.moduleCourant.getScenarioType(rowIndex).getCriteresSup().put(critere, (Boolean) aValue);
				}
			}
			if(columnIndex==COLONNE_VRAISEMBLANCE_I){
				scenarioType.setVraisemblanceIntrinseque((Integer) aValue);
			}
			if(columnIndex==COLONNE_VRAISEMBLANCE_R){
				scenarioType.setVraisemblanceReelle((Integer) aValue);
			}
			if(columnIndex==COLONNE_RETENU){
				scenarioType.setRetenu((Boolean) aValue);
			}
		}
	}
	
	// SERVICES 
	
	public ScenarioType getScenarioCourant(){
		return this.moduleCourant.getScenarioTypeCourant();
	}
	
	public void setScenarioCourant(int rowIndex){
		this.moduleCourant.setScenarioTypeCourant(this.moduleCourant.getScenarioType(rowIndex));
	}
	
	public void addScenarioType(ScenarioType scenario, int indiceInsertion){
		this.moduleCourant.addScenarioType(scenario);
		fireTableRowsInserted(indiceInsertion, indiceInsertion);
	}
	
	

}
