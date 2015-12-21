package presentation;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.MappingDesBiens;

public class FenetreMappingDesBiens extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable table;


	public FenetreMappingDesBiens(){
		this.setVisible(true);
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JScrollPane(table));	
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final LinkedList<String> entetes = new LinkedList<String>();
		private Etude etude = MainFrancois.etude;
		private BiensEssentiels biensEssentiels = (BiensEssentiels) etude.getModule("BiensEssentiels");
		private BiensSupports biensSupports = (BiensSupports) etude.getModule("BiensSupports");
		private MappingDesBiens mapping = new MappingDesBiens(biensSupports,biensEssentiels);

		
		public ModeleDynamiqueObjet() {
			super();			
			
			entetes.add("Biens Essentiels");
			for (int i=0; i<biensSupports.getLesBiens().size(); i++){
				entetes.add(biensSupports.getBien(i).getIntitule());
			}
		}

		public int getRowCount() {
			return biensSupports.nombreDeBiens();
		}

		public int getColumnCount() {
			return biensEssentiels.nombreDeBiens()+1;
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
				return mapping.getMappingDesBiens().get(rowIndex).getValueAt(columnIndex-1);
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
					//System.out.println(""+aValue);
					mapping.getMappingDesBiens().get(rowIndex).setValueAt((String)aValue, columnIndex-1);
					//System.out.println("value : "+mapping.getMappingDesBiens().get(rowIndex).getValueAt(columnIndex-1));
					break;
				}
		    }
		}
	}
}
