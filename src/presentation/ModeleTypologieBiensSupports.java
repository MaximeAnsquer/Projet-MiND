package presentation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import abstraction.autres.TypeBien;
import abstraction.modules.TypologieDesBiensSupports;

public class ModeleTypologieBiensSupports extends AbstractTableModel{
	
	private TypologieDesBiensSupports moduleCourant = new TypologieDesBiensSupports();
	private final String[] entetes = {"Id", "Intitulé", "Description", "Retenu"} ;
	
	public static final int COLONNE_ID = 0;
	public static final int COLONNE_INTITULE = 1;
	public static final int COLONNE_DESCRIPTION = 2;
	public static final int COLONNE_RETENUE = 3;
	
	public ModeleTypologieBiensSupports() {
		super();
	}
	
	public TypologieDesBiensSupports getModuleCourant(){
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
		case COLONNE_ID:
			return this.moduleCourant.getTypeBien(rowIndex).getId();
		case COLONNE_INTITULE:
			return this.moduleCourant.getTypeBien(rowIndex).getIntitule();
		case COLONNE_DESCRIPTION:
			return this.moduleCourant.getTypeBien(rowIndex).getDescription();
		case COLONNE_RETENUE:
			return this.moduleCourant.getTypeBien(rowIndex).isRetenu();
		default:
			return null; // Ne devrait jamais arriver
		}
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	    return true; //Toutes les cellules sont éditables
	}
	// PAC
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue!=null){
			switch(columnIndex){
			case COLONNE_ID:
				this.moduleCourant.getTypeBien(rowIndex).setId((String) aValue);
				break;
			case COLONNE_INTITULE:
				this.moduleCourant.getTypeBien(rowIndex).setIntitule((String) aValue);
				break;
			case COLONNE_DESCRIPTION:
				this.moduleCourant.getTypeBien(rowIndex).setDescription((String) aValue);
				break;
			case COLONNE_RETENUE:
				this.moduleCourant.getTypeBien(rowIndex).setRetenu((Boolean) aValue);
				break;
			}
		}
	}
	
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 3:
			return Boolean.class;
		default:
			return Object.class;
		}
	}
	
	// PAC 
	public void addTypeBien(TypeBien type){
		this.moduleCourant.addTypeBienSupport(type);
		fireTableRowsInserted(this.moduleCourant.getSize() - 1,this.moduleCourant.getSize() - 1);
	}
	// PAC 
	public void removeTypeBien(int rowIndex){
		this.moduleCourant.removeTypeBienSupport(this.moduleCourant.getTypeBien(rowIndex));
		fireTableRowsDeleted(this.moduleCourant.getSize() - 1,this.moduleCourant.getSize() - 1);
	}
	
	public TypeBien getTypeBienCourant(){
		return this.moduleCourant.getTypeBienCourant();
	}
	// PAC 
	public void setTypeBienCourant(int rowIndex){
		this.moduleCourant.setTypeBienCourant(this.moduleCourant.getTypeBien(rowIndex));
	}
	
}
