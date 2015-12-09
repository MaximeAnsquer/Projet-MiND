package presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.Critere;
import abstraction.modules.BiensEssentiels;

/**
 * Presentation du module Biens Essentiels
 * @author francois
 *
 */

public class FenetreBiensEssentiels extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTable table;

	public FenetreBiensEssentiels(){
		super("Criteres de securite 2");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		table = new JTable(new ModeleDynamiqueObjet());
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);	
		getContentPane().add(partieBoutons(), BorderLayout.SOUTH);
		pack();
	}
	
	private JPanel partieBoutons() {
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAjouterLigne());
		jpanel.add(boutonSupprimerLigne());
		jpanel.add(boutonAjouterColonne());
		jpanel.add(boutonSupprimerColonne());
		return jpanel;
	}

	private JButton boutonSupprimerColonne() {
		JButton bouton = new JButton("Supprimer une categorie");
		bouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerCategorie(ligneSelectionnee);
			}
		});
		return bouton;
	}

	private JButton boutonSupprimerLigne() {
		JButton bouton = new JButton("Supprimer un bien essentiel");
		bouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerBienEssentiel(ligneSelectionnee);
			}
		});
		return bouton;
	}

	private JButton boutonAjouterColonne() {
		JButton bouton = new JButton("Ajouter une catégorie");
		bouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.ajouterCategorie();
			}
		});
		return bouton;
	}

	private JButton boutonAjouterLigne() {
		JButton bouton = new JButton("Ajouter un bien essentiel");
		bouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.ajouterBienEssentiel();
			}
		});
		return bouton;
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private Etude etude = MainFrancois.etude;
		private BiensEssentiels biens = (BiensEssentiels) etude.getModule("BiensEssentiels");
		private final LinkedList<String> entetes = new LinkedList<String>();

		
		public ModeleDynamiqueObjet() {
			super(); 
			entetes.add("Intitulé");
			entetes.add("Description");
			entetes.add("Retenu");
		}
		
		public void supprimerCategorie(int ligneSelectionnee) {
			//TODO
		}

		public void supprimerBienEssentiel(int ligneSelectionnee) {
			Bien bien = biens.getBien(ligneSelectionnee);
			biens.supprimerBien(bien.getIntitule());

			fireTableRowsDeleted(ligneSelectionnee, ligneSelectionnee);
		}

		public void ajouterCategorie() {
			String categorie = JOptionPane.showInputDialog("Intitule de la categorie ?");
			
			entetes.addFirst(categorie);
			fireTableStructureChanged();	
			
		}

		public void ajouterBienEssentiel() {
			ArrayList<String> nomColonneSup = new ArrayList<String>();
			ArrayList<String> contenuColonneSup = new ArrayList<String>();
			String Intitule = JOptionPane.showInputDialog("Intitule ?");
			String type = "";
			String Description = JOptionPane.showInputDialog("Description ?");
			Bien bien = new Bien(Description, Intitule, type, nomColonneSup, contenuColonneSup);
			biens.ajouterBien(bien);

			fireTableRowsInserted(biens.nombreDeBiens() -1, biens.nombreDeBiens() -1);
		}

		public int getRowCount() {
			return biens.nombreDeBiens();
		}

		public int getColumnCount() {
			return entetes.size();
		}

		public String getColumnName(int columnIndex) {
			return entetes.get(columnIndex);
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(this.getColumnCount()-columnIndex-1){
			case 2:
				return biens.getBien(rowIndex).getIntitule();
			case 1:
				return biens.getBien(rowIndex).getDescription();
			case 0:
				return biens.getBien(rowIndex).isRetenu();
			default:
				return "";
			}
		}

		public boolean isCellEditable(int row, int col){
			return true; 
		}

		public Class getColumnClass(int columnIndex){
			switch(this.getColumnCount()-columnIndex-1){
			case 0:
				return Boolean.class;
			default:
				return String.class;
			}
		}
		
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		    if(aValue != null){
		        Bien bien = biens.getBien(rowIndex);
		 
		        switch(this.getColumnCount()-columnIndex-1){
		            case 2:
		            	bien.setIntitule((String)aValue);
		                break;
		            case 1:
		            	bien.setDescription((String)aValue);
		                break;
		            case 0:
		            	bien.setRetenu((Boolean)aValue);
		                break;
		        }
		    }
		}
	}
}
