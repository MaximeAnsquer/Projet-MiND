package presentation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import abstraction.autres.TypeBien;

public class ModeleTypologieBiensSupports extends AbstractTableModel{
	private final List<TypeBien> typesBien = new ArrayList<TypeBien>();
	private final String[] entetes = {"Id", "Intitulé", "Description", "Retenu"} ;
	
	public ModeleTypologieBiensSupports() {
		super();

		this.typesBien
				.add(new TypeBien(
						"MAT",
						"Ce type de biens supports est constitué de l’ensemble des éléments physiques d'un système informatique (hardware) et des supports de données électroniques) participant au stockage et au traitement de tout ou partie des biens essentiels.",
						"Matériels", true));
		this.typesBien
				.add(new TypeBien(
						"LOG",
						"Logiciels",
						"Ce type de biens supports est constitué de l'ensemble des programmes participant au traitement de tout ou partie des biens essentiels (software).",
						true));
	}
	
	public int getRowCount() {
		return this.typesBien.size();
	}

	public int getColumnCount() {
		return this.entetes.length;
	}
	
	public String getColumnName(int columnIndex) {
		return this.entetes[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this.typesBien.get(rowIndex).getId();
		case 1:
			return this.typesBien.get(rowIndex).getIntitule();
		case 2:
			return this.typesBien.get(rowIndex).getDescription();
		case 3:
			return this.typesBien.get(rowIndex).isRetenu();
		default:
			return null; // Ne devrait jamais arriver
		}
	}
	
	public void addTypeBien(TypeBien type){
		this.typesBien.add(type);
		fireTableRowsInserted(this.typesBien.size() - 1,this.typesBien.size() - 1);
	}
	
	public void removeTypeBien(int rowIndex){
		this.typesBien.remove(rowIndex);
		fireTableRowsDeleted(this.typesBien.size() - 1,this.typesBien.size() - 1);
	}

}
