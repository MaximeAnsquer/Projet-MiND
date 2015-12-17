package presentation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import abstraction.autres.TypeBien;
import abstraction.modules.TypologieDesBiensSupports;

public class ModeleTypologieBiensSupports extends AbstractTableModel{
	// private final List<TypeBien> typesBien = new ArrayList<TypeBien>();
	private TypologieDesBiensSupports moduleCourant = new TypologieDesBiensSupports();
	private final String[] entetes = {"Id", "Intitulé", "Description", "Retenu"} ;
	
	public ModeleTypologieBiensSupports() {
		super();

		this.moduleCourant
				.addTypeBienSupport(new TypeBien(
						"MAT",
						"Ce type de biens supports est constitué de l’ensemble des éléments physiques d'un système informatique (hardware) et des supports de données électroniques) participant au stockage et au traitement de tout ou partie des biens essentiels.",
						"Matériels", true));
		this.moduleCourant
				.addTypeBienSupport(new TypeBien(
						"LOG",
						"Logiciels",
						"Ce type de biens supports est constitué de l'ensemble des programmes participant au traitement de tout ou partie des biens essentiels (software).",
						true));
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
		case 0:
			return this.moduleCourant.getTypeBien(rowIndex).getId();
		case 1:
			return this.moduleCourant.getTypeBien(rowIndex).getIntitule();
		case 2:
			return this.moduleCourant.getTypeBien(rowIndex).getDescription();
		case 3:
			return this.moduleCourant.getTypeBien(rowIndex).isRetenu();
		default:
			return null; // Ne devrait jamais arriver
		}
	}
	
	public void addTypeBien(TypeBien type){
		this.moduleCourant.addTypeBienSupport(type);
		fireTableRowsInserted(this.moduleCourant.getSize() - 1,this.moduleCourant.getSize() - 1);
	}
	
	public void removeTypeBien(int rowIndex){
		this.moduleCourant.removeTypeBienSupport(this.moduleCourant.getTypeBien(rowIndex));
		fireTableRowsDeleted(this.moduleCourant.getSize() - 1,this.moduleCourant.getSize() - 1);
	}
	
}
