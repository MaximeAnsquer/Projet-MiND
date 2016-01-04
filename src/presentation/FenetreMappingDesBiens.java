package presentation;

import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.MappingDesBiens;

public class FenetreMappingDesBiens extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private MappingDesBiens mappingDesBiens;
	JComboBox comboBox = new JComboBox();

	public FenetreMappingDesBiens(MappingDesBiens mappingDesBiens){
		this.setVisible(true);
		this.mappingDesBiens=mappingDesBiens;
		this.mappingDesBiens.actualiserMapping();
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		comboBox.addItem("");
		comboBox.addItem("x");
		comboBox.addItem("o");
		for (int i=1; i<table.getColumnCount();i++){
			table.getColumn(""+table.getColumnName(i)).setCellEditor(new DefaultCellEditor(comboBox));;
		}
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JScrollPane(table));	
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final LinkedList<String> entetes = new LinkedList<String>();
		private BiensEssentiels biensEssentiels = mappingDesBiens.getBiensEssentiels();
		private BiensSupports biensSupports = mappingDesBiens.getBiensSupports();

		
		public ModeleDynamiqueObjet() {
			super();
			entetes.add("Biens Essentiels");
			for (int i=0; i<biensSupports.getLesBiens().size(); i++){
				entetes.add(biensSupports.getBien(i).getIntitule());
			}
		}

		public int getRowCount() {
			return biensEssentiels.nombreDeBiens();
		}

		public int getColumnCount() {
			return biensSupports.nombreDeBiens()+1;
		}
		
		public String getColumnName(int columnIndex) {
			return entetes.get(columnIndex);
		}
		
		public boolean isCellEditable(int row, int col){
			return true; 
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex){
			case 0:
				return biensEssentiels.getBien(rowIndex).getIntitule();	
			default:
				return mappingDesBiens.getMappingDesBiens().get(rowIndex).getValueAt(columnIndex-1);
			}
		}

		public Class getColumnClass(int columnIndex){
			return String.class;
		}
		
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		    if(aValue != null){
		    	switch(columnIndex){
		    	case 0 :
		    		break;
				default:
					mappingDesBiens.getMappingDesBiens().get(rowIndex).setValueAt((String)aValue, columnIndex-1);
					break;
				}
		    }
		}
	}
}
