package presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Metrique;
import abstraction.autres.NiveauDeMetrique;
import abstraction.modules.Metriques;

/**
 * Source : http://baptiste-wicht.developpez.com/tutoriels/java/swing/jtable/
 * @author Maxime Ansquer
 *
 */
public class FenetreMetriques extends JFrame {

	private ArrayList<JPanel> lesTableaux = new ArrayList<JPanel>();
	private JComboBox comboBox;
	private JPanel metriqueEnCours;
	private Etude etude = Main.etude;
	private Metriques metriques = (Metriques) etude.getModule("Metriques");
	

	public FenetreMetriques(){
		super("Metriques");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		int nombreDeMetriques = metriques.nombreDeMetriques();
		Container contentPane = this.getContentPane();
				
		/**
		 * On cree tous les tableaux, que l'on range dans la variable " les tableaux "
		 */
		for(Metrique m : metriques.getLesMetriques().values()){			
			ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet(m);
			JTable table = new JTable(modele);
			lesTableaux.add(tableau(table, m.getCritere().getIntitule()));	
		}
		
		/**
		 * S'il y a au moins une metrique, on affiche la premiere
		 */
		if(lesTableaux.size()!=0){
			contentPane.add(lesTableaux.get(0), BorderLayout.CENTER);		}
		
		/**
		 * On ajoute la comboBox et les boutons
		 */
		contentPane.add(partieDuBas(), BorderLayout.SOUTH);		
		
		pack();
	}

	private JPanel partieDuBas() {
		JPanel jp = new JPanel();
		jp.add(comboBox());
		return jp;
	}

	private JComboBox comboBox() {
		comboBox = new JComboBox(lesTableaux.toArray());
		comboBox.getS
		return comboBox;
	}

	private JPanel tableau(JTable table, String intitule) {
		JPanel j = new JPanel();
		j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
		j.add(new JLabel(intitule));
		j.add(new JScrollPane(table));
		return j;
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {

		private final String[] entetes = {"#", "Intitulé", "Description"};
		private Metrique metrique;

		public ModeleDynamiqueObjet(Metrique metrique) {
			super();
			this.metrique = metrique;
		}

		public int getRowCount() {
			return metrique.nombreDeNiveaux();
		}

		public int getColumnCount() {
			return entetes.length;
		}

		public String getColumnName(int columnIndex) {
			return entetes[columnIndex];
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex){
			case 0:
				return metrique.getNiveau(rowIndex+1).getNumero();
			case 1:
				return metrique.getNiveau(rowIndex+1).getIntitule();
			case 2:
				return metrique.getNiveau(rowIndex+1).getDescription();
			default:
				return null; //Ne devrait jamais arriver
			}
		}

		public boolean isCellEditable(int row, int col){
			return true; 
		}

		public Class getColumnClass(int columnIndex){
			switch(columnIndex){
			case 0:
				return Integer.class;
			default:
				return String.class; 
			}
		}
		
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		    if(aValue != null){
		        NiveauDeMetrique niveau = metrique.getNiveau(rowIndex+1);
		 
		        switch(columnIndex){
		            case 0:
		            	niveau.setNumero((Integer)aValue);
		                break;
		            case 1:
		            	niveau.setIntitule((String)aValue);
		                break;
		            case 2:
		            	niveau.setDescription((String)aValue);
		                break;
		        }
		    }
		}
	}



}
