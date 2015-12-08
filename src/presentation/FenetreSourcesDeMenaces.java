package presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Critere;
import abstraction.autres.SourceDeMenace;
import abstraction.modules.SourcesDeMenaces;

/**
 * Fonctionnel, mais le code est relativement degueulasse.
 * Source : http://baptiste-wicht.developpez.com/tutoriels/java/swing/jtable/
 * @author Maxime Ansquer
 *
 */
public class FenetreSourcesDeMenaces extends JFrame {

	private JTable table;	

	public FenetreSourcesDeMenaces(){
		super("Sources de menaces");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		table = new JTable(new ModeleDynamiqueObjet());
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);	
		getContentPane().add(partieDuBas(), BorderLayout.SOUTH);
		pack();

	}

	private JPanel partieDuBas() {
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAjouter());
		jpanel.add(boutonSupprimer());
		return jpanel;
	}

	private Component boutonSupprimer() {
		JButton bouton = new JButton("Supprimer une source de menace");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerSource(ligneSelectionnee);
			}

		});
		return bouton;
	}

	private JButton boutonAjouter() {
		JButton bouton = new JButton("Ajouter une source de menace");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.ajouterSource();
			}

		});
		return bouton;
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private Etude etude = MainMaximeAnsquer.etude;
		private SourcesDeMenaces sdm = (SourcesDeMenaces) etude.getModule("SourcesDeMenaces");

		private final String[] entetes = {"Id", "Intitulé", "Exemple", "Retenu"};

		public ModeleDynamiqueObjet() {
			super();		}

		public int getRowCount() {
			return sdm.nombreDeSources();
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
				return sdm.getSource(rowIndex).getId();
			case 1:
				return sdm.getSource(rowIndex).getIntitule();
			case 2:
				return sdm.getSource(rowIndex).getExemple();
			case 3:
				return sdm.getSource(rowIndex).isRetenu();
			default:
				return null; //Ne devrait jamais arriver
			}
		}

		public void ajouterSource() {
			String id = JOptionPane.showInputDialog("Id ?");
			String intitule = JOptionPane.showInputDialog("Intitule ?");
			String exemple = JOptionPane.showInputDialog("Exemple ?");
			SourceDeMenace source = new SourceDeMenace(id, intitule, exemple);
			sdm.ajouterSourceDeMenace(source);

			fireTableRowsInserted(sdm.nombreDeSources() -1, sdm.nombreDeSources() -1);
		}

		public void supprimerSource(int rowIndex) {
			SourceDeMenace source = sdm.getSource(rowIndex);
			sdm.supprimerSourceDeMenace(source);

			fireTableRowsDeleted(rowIndex, rowIndex);
		}

		public boolean isCellEditable(int row, int col){
			return true; 
		}

		public Class getColumnClass(int columnIndex){
			switch(columnIndex){
			case 3:
				return Boolean.class;
			default:
				return String.class; 
			}
		}
		
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		    if(aValue != null){
		        SourceDeMenace source = sdm.getSource(rowIndex);
		 
		        switch(columnIndex){
		            case 0:
		            	source.setId((String)aValue);
		                break;
		            case 1:
		            	source.setIntitule((String)aValue);
		                break;
		            case 2:
		            	source.setExemple((String)aValue);
		                break;
		            case 3:
		            	source.setRetenu((Boolean)aValue);
		                break;
		        }
		    }
		}
	}



}
