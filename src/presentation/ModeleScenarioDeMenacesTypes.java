package presentation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.ScenarioGenerique;
import abstraction.autres.ScenarioType;
import abstraction.autres.SourceDeMenace;
import abstraction.modules.BiensSupports;
import abstraction.modules.Metriques;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;

public class ModeleScenarioDeMenacesTypes extends AbstractTableModel {
	private Metriques metriques ;
	private SourcesDeMenaces sourcesDeMenaces ;
	private BiensSupports biensSupports ;
	private ScenariosDeMenacesGeneriques scenarioDeMenacesGeneriques ;
	private ScenariosDeMenacesTypes moduleCourant ;
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
	
	public ModeleScenarioDeMenacesTypes(ScenariosDeMenacesTypes moduleCourant) {
		super();
		this.moduleCourant=moduleCourant;
		this.metriques= (Metriques) this.moduleCourant.getEtude().getModule("Metriques");
		this.sourcesDeMenaces = (SourcesDeMenaces) this.moduleCourant.getEtude().getModule("SourcesDeMenaces");
		this.biensSupports = (BiensSupports) this.moduleCourant.getEtude().getModule("BiensSupports");
		this.scenarioDeMenacesGeneriques= (ScenariosDeMenacesGeneriques) this.moduleCourant.getEtude().getModule("ScenariosDeMenacesGeneriques");
		
		// On reinitialise les indices à leurs valeurs initiales (pas de colonnes supplémentaires)
		this.setIndices();
		
		// Ajout des colonnes supplémentaires provenant du module "Biens Supports"
		if (this.biensSupports.getNomColonnesSup()!=null){
			for (int i=0; i<this.biensSupports.getNomColonnesSup().size();i++){
				entetes.add(this.biensSupports.getNomColonnesSup().get(i));
				this.updateIndicesTab1();
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
				this.updateIndicesTab2();
			}
		}
		
		entetes.add("Vraisemblance intrinsèque");
		entetes.add("Vraisemblance réelle");
		entetes.add("Retenu");
		

		this.moduleCourant.importerDonnees();
	}
	
	public void setIndices(){
		COLONNE_BIEN_SUPPORT = 0;
		COLONNE_TYPE = 1;
		COLONNE_ID = 2;
		COLONNE_SCENARIO_GENERIQUE = 3;
		COLONNE_SCENARIO_CONCRET = 4 ;
		COLONNE_SOURCES_MENACES = 5;
		COLONNE_VRAISEMBLANCE_I = 6;
		COLONNE_VRAISEMBLANCE_R = 7;
		COLONNE_RETENU = 8;
	}
	
	// On incrémente d'un pas les indices de la première partie du tableau 
	// (indices des colonnes situées à gauche des critères de sécurités) 
	public void updateIndicesTab1(){
		COLONNE_BIEN_SUPPORT += 1;
		COLONNE_TYPE += 1;
		COLONNE_ID += 1;
		COLONNE_SCENARIO_GENERIQUE += 1;
		COLONNE_SCENARIO_CONCRET += 1;
		COLONNE_SOURCES_MENACES += 1;
		COLONNE_VRAISEMBLANCE_I+=1;
		COLONNE_VRAISEMBLANCE_R +=1;
		COLONNE_RETENU += 1;
	}
	
	// On incrémente d'un pas les indices de la deuxième partie du tableau 
	// (indices des colonnes situées à droite des critères de sécurités) 
	public void updateIndicesTab2(){
		COLONNE_VRAISEMBLANCE_I+=1;
		COLONNE_VRAISEMBLANCE_R+=1;
		COLONNE_RETENU+=1;
	}
	
	public ScenariosDeMenacesTypes getModuleCourant(){
		return this.moduleCourant;
	}
	
	public SourcesDeMenaces getSourcesDeMenaces(){
		return this.sourcesDeMenaces;
	}
	
	public Metriques getMetriques(){
		return this.metriques;
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
	
	//*
	public void importerDonnees() {
		
		// Cas où on remplit le tableau pour la première fois
		if (this.moduleCourant.getTableau().size()==0){
			this.moduleCourant.importerBDC();
		}
		// On actualise les valeurs du tableau selon les biens 
		else{
			if (this.moduleCourant.getBiensRetenus().size()<this.biensSupports.getBiensRetenus().size()){
				for (Bien b : this.biensSupports.getBiensRetenus()){
					if (!this.moduleCourant.contientBien(b)){
						for (ScenarioGenerique sGene : this.scenarioDeMenacesGeneriques.getScenariosGeneriquesRetenus()) {
							ScenarioType scenario = new ScenarioType(
									sGene.getTypeBienSupport(), sGene.getId(),
									sGene.getIntitule(), this.moduleCourant.getCriteres(sGene),
									this.moduleCourant.getSourcesMenaces(), null,
									true);
							if (sGene.getTypeBienSupport().getIntitule().contains(b.getType()) && scenario.getBienSupport()==null) {
								scenario.setBienSupport(b);
								this.moduleCourant.getBiensRetenus().put(b.getIntitule(),b);
								this.moduleCourant.getTableau().add(scenario);
							}
						}
					}
				}
			}
			// Sinon on supprime les scénarios correspondants aux biens qui ne sont plus retenus
			else{
				this.moduleCourant.supprimerScenariosTypes();
			}
		}
	}
	//*/

	public Object getValueAt(int rowIndex, int columnIndex) {
		ScenarioType scenarioType = this.moduleCourant.getScenarioType(rowIndex);
		Object resultat = new Object();
		if (this.biensSupports.getNomColonnesSup() != null
				&& columnIndex < this.biensSupports.getNomColonnesSup().size()) {
			
			resultat = scenarioType.getBienSupport().getContenuColonnesSup().get(columnIndex);
		}
		if (columnIndex==COLONNE_BIEN_SUPPORT && scenarioType.getBienSupport()!=null){
			resultat=scenarioType.getBienSupport().getIntitule();
		}
		if(columnIndex==COLONNE_TYPE){
			String intituleTypeBien = scenarioType.getTypeBienSupport().getIntitule();
			String idTypeBien = scenarioType.getTypeBienSupport().getId();
			resultat=intituleTypeBien + " (" + idTypeBien + ")";
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
				int indice = columnIndex-COLONNE_SOURCES_MENACES-1; 
				String critere = this.moduleCourant.getNomColonneSup().get(indice);
				resultat=this.moduleCourant.getScenarioType(rowIndex).getCriteresTypes().get(critere);
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
				//scenarioType.getBienSupport().setIntitule((String) aValue);
			}
			if(columnIndex==COLONNE_TYPE){
				// scenarioType.setTypeBienSupport((String) aValue);
			}
			if(columnIndex==COLONNE_ID){
				scenarioType.setId((String) aValue);
			}
			if(columnIndex==COLONNE_SCENARIO_GENERIQUE){
				// scenarioType.setIntitule((String) aValue);
			}
			if (columnIndex==COLONNE_SCENARIO_CONCRET){
				scenarioType.setIntituleConcret((String) aValue);
			}
			if (columnIndex==COLONNE_SOURCES_MENACES){
				/*
				SourceDeMenace source = this.sourcesDeMenaces.getSourceDeMenace((String) aValue);
				if (source!=null){
					scenarioType.getMenaces().put((String) aValue, source);
				}
				*/
			}
			if(this.scenarioDeMenacesGeneriques.getNomColonneSup()!=null){
				
				if (columnIndex>COLONNE_SOURCES_MENACES && columnIndex<COLONNE_VRAISEMBLANCE_I){
					
					// indice du critère dans l'ArrayList
					int indice = columnIndex-COLONNE_SOURCES_MENACES-1; 
					String critere = this.moduleCourant.getNomColonneSup().get(indice);
					this.moduleCourant.getScenarioType(rowIndex).getCriteresTypes().put(critere, (Boolean) aValue);
				}
			}
			if(columnIndex==COLONNE_VRAISEMBLANCE_I){
				scenarioType.setVraisemblanceIntrinseque(Integer.parseInt((String) aValue));
			}
			if(columnIndex==COLONNE_VRAISEMBLANCE_R){
				scenarioType.setVraisemblanceReelle(Integer.parseInt((String) aValue));
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
		this.moduleCourant.addScenarioType(scenario, indiceInsertion);
		fireTableRowsInserted(indiceInsertion, indiceInsertion);
	}
	
	public void removeScenariosTypes(Integer index) {
		this.moduleCourant.removeScenariosTypes(index);
		fireTableRowsDeleted(index, index);
	}
	
	

}
