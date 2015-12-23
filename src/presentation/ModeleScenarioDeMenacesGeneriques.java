package presentation;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import abstraction.autres.Critere;
import abstraction.autres.ScenarioGenerique;
import abstraction.autres.TypeBien;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.TypologieDesBiensSupports;

public class ModeleScenarioDeMenacesGeneriques extends AbstractTableModel {
	
	private TypologieDesBiensSupports typologieDesBiensSupports = new TypologieDesBiensSupports();
	private CriteresDeSecurite criteresDeSecurite = new CriteresDeSecurite();
	private ScenariosDeMenacesGeneriques moduleCourant = new ScenariosDeMenacesGeneriques();
	private LinkedList<String> entetes = new LinkedList<String>();
	// private LinkedList<ArrayList<Boolean>> colonnesSup = new LinkedList<ArrayList<Boolean>>();
	
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
		
		// On ajoute un scénario générique à partir des types définis dans Typologie des Biens Supports
		int i = 1 ;
		for(TypeBien type : this.typologieDesBiensSupports.getTypeBiensRetenus()){
			
			// La clé est l'intitulé du TYPE !! (il ne faudra pas avoir plusieurs clés identiques)
			ScenarioGenerique scenario = new ScenarioGenerique(type.getIntitule(),"toto " + i,"Intitule " + i);
			this.moduleCourant.getTableau().put(scenario.getIntitule(),scenario );
			i++ ;
		}
		
		// On ajoute les colonnes supplémentaires représentant les critères de sécurités retenus
		/*
		for (Critere c : this.criteresDeSecurite.getCriteresRetenus().values()){
			this.moduleCourant.getTableau().setTa
		}
		//*/
	}
	
	public ScenariosDeMenacesGeneriques getModuleCourant(){
		return this.moduleCourant;
	}
	
	public TypologieDesBiensSupports getTypologieBiensSupports(){
		return this.typologieDesBiensSupports;
	}
	
	public CriteresDeSecurite getCriteresDeSecurite(){
		return this.criteresDeSecurite;
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
			String intituleTypeBien = this.moduleCourant.getScenarioGenerique(rowIndex).getTypeBienSupport();
			return intituleTypeBien;
		case COLONNE_ID:
			return this.moduleCourant.getScenarioGenerique(rowIndex).getId();
		case COLONNE_INTITULE:
			return this.moduleCourant.getScenarioGenerique(rowIndex).getIntitule();
		case COLONNE_RETENU:
			return this.moduleCourant.getScenarioGenerique(rowIndex).isRetenuScenario();
		default:
			/*
			int indice = this.getColumnCount() - columnIndex - 1 ;
			if(colonnesSup.get(indice)!=null){
				return colonnesSup.get(indice).get(rowIndex);
			}
			else{
				return false;
			}
			*/
			int indice = columnIndex - 4 ;
			if (this.moduleCourant.getNomColonneSup()!=null){
				String critereAssocie = this.moduleCourant.getNomColonneSup().get(indice);
				return this.moduleCourant.getScenarioGenerique(rowIndex).getCriteresSup().get(critereAssocie);
			}
			else{
				return false ;
			}
		}
	}
	
	public boolean isCellEditable(int row, int col){
		return true; 
	}

	public Class getColumnClass(int columnIndex){
		switch(columnIndex){
		case COLONNE_TYPEBIENSUPPORT:
			return String.class;
		case COLONNE_ID:
			return String.class ;
		case COLONNE_INTITULE:
			return String.class ;
		default:
			return Boolean.class;
		}
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue != null) {
			ScenarioGenerique scenario = this.moduleCourant
					.getScenarioGenerique(rowIndex);

			switch (columnIndex) {

			case COLONNE_TYPEBIENSUPPORT:
				if (this.typologieDesBiensSupports
						.isTypeBienRetenu((String) aValue)) {
					scenario.setTypeBienSupport((String) aValue);
				}
				break;
			case COLONNE_ID:
				scenario.setId((String) aValue);
				break;
			case COLONNE_INTITULE:
				scenario.setIntitule((String) aValue);
				break;
			case COLONNE_RETENU:
				scenario.setRetenuScenario((Boolean) aValue);
				break;
			default:
				/*
				 * int indice = this.getColumnCount() - columnIndex - 1 ;
				 * colonnesSup.get(indice).set(rowIndex, ((Boolean)aValue)); //
				 */
				int indice = columnIndex - 4;
				if (this.moduleCourant.getNomColonneSup() != null) {
					String critereAssocie = this.moduleCourant
							.getNomColonneSup().get(indice);
					this.moduleCourant.getScenarioGenerique(rowIndex)
							.getCriteresSup()
							.replace(critereAssocie, (Boolean) aValue);
					break;
				}
			}
		}
	}
	
	public void addCritere (String critere){
		/*
		this.colonnesSup.addFirst(new ArrayList<Boolean>(this.getRowCount()));
		for (int i=0; i<this.getRowCount(); i++){
			colonnesSup.getFirst().add(i, false);
		}
		//*/
		this.moduleCourant.addCritere(critere);
		// On ajoute la colonne du critère à gauche de la colonne "Retenu"
		this.entetes.add(this.getColumnCount()-1, critere);
		fireTableStructureChanged();
	}
	
	public void removeCritere (String critere){
		/*
		if (colonnesSup.size()!=0){
			this.entetes.remove(this.getColumnCount()-2);
			colonnesSup.removeLast();
			fireTableStructureChanged();
		}
		//*/
		this.entetes.remove(this.getColumnCount()-2);
		fireTableStructureChanged();
	}
	
	// Ajout d'un scénario
	public void addScenarioGenerique (ScenarioGenerique scenario, int indiceInsertion){
		this.moduleCourant.addScenarioGenerique(scenario);
		fireTableRowsInserted(indiceInsertion, indiceInsertion);
	}
	
	public void removeScenarioGenerique (int indiceSuppression){
		this.moduleCourant.removeScenarioGenerique(this.moduleCourant.getScenarioGenerique(indiceSuppression));
		fireTableRowsDeleted(indiceSuppression, indiceSuppression);
	}

}
