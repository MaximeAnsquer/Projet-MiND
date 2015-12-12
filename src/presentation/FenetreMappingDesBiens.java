package presentation;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;

public class FenetreMappingDesBiens extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTable table;


	public FenetreMappingDesBiens(){
		super("Mapping des Biens");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(new JScrollPane(table));	
		pack();
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private Etude etude = MainFrancois.etude;
		private BiensEssentiels biensEssentiels = (BiensEssentiels) etude.getModule("BiensEssentiels");
		private BiensSupports biensSupports = (BiensSupports) etude.getModule("BiensSupports");
		private LinkedList<ArrayList<String>> mapping = new LinkedList<ArrayList<String>>();

		
		public ModeleDynamiqueObjet() {
			super();
			for (int j=0; j<biensEssentiels.nombreDeBiens();j++){
				mapping.add(new ArrayList<String>(biensSupports.nombreDeBiens())) ;
				for (int i=0; i<biensSupports.nombreDeBiens();i++){
					mapping.get(j).add("");
				}
			}
		}

		public int getRowCount() {
			return biensSupports.nombreDeBiens()-1;
		}

		public int getColumnCount() {
			return biensEssentiels.nombreDeBiens()-1;
		}
		
		public boolean isCellEditable(int row, int col){
			return true; 
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex){
			case 0:
				if (rowIndex==0){
					return "";
				}
				else{
					return biensEssentiels.getBien(rowIndex).getIntitule();	
				}
			default:
				if (rowIndex==0){
					return biensSupports.getBien(columnIndex-1).getIntitule();
				}
				else{
					return mapping.get(columnIndex-1).get(rowIndex-1);
				}
			}
		}

		public Class getColumnClass(int columnIndex){
			return String.class;
		}
		
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		    if(aValue != null){
		    	switch(columnIndex){
				case 0:
					break;
				default:
					if (rowIndex!=0 && ((String)aValue=="" || (String)aValue=="x" || (String)aValue=="o")){
						mapping.get(columnIndex-1).set(rowIndex-1,(String)aValue);
					}
					break;
				}
		    }
		}
	}
}
