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
import abstraction.modules.CriteresDeSecurite;

/**
 * Fonctionnel, mais le code est relativement degueulasse.
 * Source : http://baptiste-wicht.developpez.com/tutoriels/java/swing/jtable/
 * @author Maxime Ansquer
 *
 */
public class FenetreCriteresDeSecurite2 extends JFrame {

	private JTable table;	

	public FenetreCriteresDeSecurite2(){
		super("Criteres de securite 2");
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
		JButton bouton = new JButton("Supprimer un critere");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerCritere(ligneSelectionnee);
			}

		});
		return bouton;
	}

	private JButton boutonAjouter() {
		JButton bouton = new JButton("Ajouter un critere");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.ajouterCritere();
			}

		});
		return bouton;
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private Etude etude = MainMaximeAnsquer.etude;
		private CriteresDeSecurite cds = (CriteresDeSecurite) etude.getModule("CriteresDeSecurite");

		private final String[] entetes = {"Id", "Intitulé", "Description", "Retenu"};

		public ModeleDynamiqueObjet() {
			super();
		}

		public int getRowCount() {
			return cds.nombreDeCriteres();
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
				return cds.getCritere(rowIndex).getId();
			case 1:
				return cds.getCritere(rowIndex).getIntitule();
			case 2:
				return cds.getCritere(rowIndex).getDescription();
			case 3:
				return cds.getCritere(rowIndex).isRetenu();
			default:
				return null; //Ne devrait jamais arriver
			}
		}

		public void ajouterCritere() {
			String Id = JOptionPane.showInputDialog("Id ?");
			String Intitule = JOptionPane.showInputDialog("Intitule ?");
			String Description = JOptionPane.showInputDialog("Description ?");
			Critere critere = new Critere(Id, Intitule, Description);
			cds.ajouterCritere(critere);

			fireTableRowsInserted(cds.nombreDeCriteres() -1, cds.nombreDeCriteres() -1);
		}

		public void supprimerCritere(int rowIndex) {
			Critere c = cds.getCritere(rowIndex);
			cds.supprimerCritere(c.getIntitule());

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
		        Critere critere = cds.getCritere(rowIndex);
		 
		        switch(columnIndex){
		            case 0:
		            	critere.setId((String)aValue);
		                break;
		            case 1:
		            	critere.setIntitule((String)aValue);
		                break;
		            case 2:
		            	critere.setDescription((String)aValue);
		                break;
		            case 3:
		            	critere.setRetenu((Boolean)aValue);
		                break;
		        }
		    }
		}
	}



}
