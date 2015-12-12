package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import presentation.FenetreBiensEssentiels.ModeleDynamiqueObjet;
import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.modules.BiensSupports;

/**
 * Presentation du module Biens Essentiels
 * @author francois
 *
 */

public class FenetreBiensSupports extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextArea zoneDescription;
	private JButton boutonModifierDescription;

	public FenetreBiensSupports(){
		super("Biens Supports");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				zoneDescription.setText(getBienSelectionne().getDescription());
				boutonModifierDescription.setEnabled(false);
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}			
		});
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(new JScrollPane(table));	
		getContentPane().add(zoneDescription());
		getContentPane().add(partieBoutons());
		pack();
	}
	
	private JScrollPane zoneDescription() {
		String valeurInitiale = "";		
		zoneDescription = new JTextArea(valeurInitiale);
		zoneDescription.setLineWrap(true);
		zoneDescription.setWrapStyleWord(true);
		
		zoneDescription.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e) {
				boutonModifierDescription.setEnabled(true);
			}
			public void keyPressed(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
			}
		});
		
		JScrollPane areaScrollPane = new JScrollPane(zoneDescription);
		areaScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(400, 150));
		areaScrollPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder("Description du Bien"),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								areaScrollPane.getBorder()));
		return areaScrollPane;
	}
	
	private JPanel partieBoutons() {
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAjouterLigne());
		jpanel.add(boutonSupprimerLigne());
		jpanel.add(boutonAjouterColonne());
		jpanel.add(boutonSupprimerColonne());
		jpanel.add(boutonModifierDescription());
		return jpanel;
	}
	
	private JButton boutonModifierDescription() {
		this.boutonModifierDescription = new JButton("Modifier la description");
		boutonModifierDescription.setEnabled(false);
		
		boutonModifierDescription.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String nouvelleDescription = zoneDescription.getText();
				getBienSelectionne().setDescription(nouvelleDescription);
				boutonModifierDescription.setEnabled(false);
			}
		});
		return boutonModifierDescription;
	}
	
	private Bien getBienSelectionne(){
		Bien c;
		try{
			c = ( (ModeleDynamiqueObjet)table.getModel() ).biens.getBien(table.getSelectedRow());
		}
		catch(ArrayIndexOutOfBoundsException e){
			c = ( (ModeleDynamiqueObjet)table.getModel() ).biens.getBien(0);
		}
		return c;
	}

	private JButton boutonSupprimerColonne() {
		JButton bouton = new JButton("Supprimer la premiere categorie");
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
		JButton bouton = new JButton("Supprimer un bien support");
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
		JButton bouton = new JButton("Ajouter une categorie");
		bouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.ajouterCategorie();
			}
		});
		return bouton;
	}

	private JButton boutonAjouterLigne() {
		JButton bouton = new JButton("Ajouter un bien support");
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
		private BiensSupports biens = (BiensSupports) etude.getModule("BiensSupports");
		private final LinkedList<String> entetes = new LinkedList<String>();
		private LinkedList<ArrayList<String>> colonnesSup = new LinkedList<ArrayList<String>>();

		
		public ModeleDynamiqueObjet() {
			super(); 
			entetes.add("Intitule");
			entetes.add("Description");
			entetes.add("Type");
			entetes.add("Retenu");
		}
		
		public void supprimerCategorie(int ligneSelectionnee) {
			entetes.removeFirst();
			colonnesSup.removeLast();
			fireTableStructureChanged();
		}

		public void supprimerBienEssentiel(int ligneSelectionnee) {
			Bien bien = biens.getBien(ligneSelectionnee);
			biens.supprimerBien(bien.getIntitule());
			fireTableRowsDeleted(ligneSelectionnee, ligneSelectionnee);
		}

		public void ajouterCategorie() {
			String categorie = JOptionPane.showInputDialog("Intitule de la categorie ?");
			colonnesSup.addFirst(new ArrayList<String>(this.getRowCount()));
			for (int i=0; i<this.getRowCount(); i++){
				colonnesSup.getFirst().add(i, "");
			}
			entetes.addFirst(categorie);
			fireTableStructureChanged();	
		}

		public void ajouterBienEssentiel() {
			ArrayList<String> nomColonneSup = new ArrayList<String>();
			ArrayList<String> contenuColonneSup = new ArrayList<String>();
			String Intitule = JOptionPane.showInputDialog("Intitule ?");
			String type = JOptionPane.showInputDialog("Type ?");
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
			case 3:
				return biens.getBien(rowIndex).getIntitule();
			case 2:
				return biens.getBien(rowIndex).getDescription();
			case 1:
				return biens.getBien(rowIndex).getType();
			case 0:
				return biens.getBien(rowIndex).isRetenu();
			default:
				if(colonnesSup.get(columnIndex)!=null){
					return colonnesSup.get(columnIndex).get(rowIndex);
				}
				else{
					return "";
				}
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
		            case 3:
		            	bien.setIntitule((String)aValue);
		                break;
		            case 2:
		            	bien.setDescription((String)aValue);
		                break;
		            case 1:
		            	bien.setType((String)aValue);
		                break;
		            case 0:
		            	bien.setRetenu((Boolean)aValue);
		                break;
		            default:
		            	colonnesSup.get(columnIndex).set(rowIndex, ((String)aValue));
		            	break;
		            	
		        }
		    }
		}
	}
}