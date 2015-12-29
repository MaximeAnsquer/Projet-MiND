package presentation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import abstraction.autres.Bien;
import abstraction.modules.BiensSupports;

/**
 * Presentation du module Biens Essentiels
 * @author francois
 *
 */

public class FenetreBiensSupports extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextArea zoneDescription;
	private JButton boutonModifierDescription;
	private JButton boutonSupprimerColonne;
	private JButton boutonSupprimerLigne;
	private BiensSupports biensSupports;
	JComboBox comboBox = new JComboBox();

	public FenetreBiensSupports(BiensSupports biensSupports){
		this.setVisible(true);
		this.biensSupports=biensSupports;
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
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JScrollPane(table));	
		this.add(zoneDescription());
		this.add(partieBoutons());
		this.boutonSupprimerColonne.setEnabled(false);
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
		Bien b;
		try{
			b = biensSupports.getBien(table.getSelectedRow());
		}
		catch(ArrayIndexOutOfBoundsException e){
			b = biensSupports.getBien(0);
		}
		return b;
	}

	private JButton boutonSupprimerColonne() {
		this.boutonSupprimerColonne = new JButton("Supprimer la premiere categorie");
		boutonSupprimerColonne.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerCategorie(ligneSelectionnee);
			}
		});
		return boutonSupprimerColonne;
	}

	private JButton boutonSupprimerLigne() {
		this.boutonSupprimerLigne = new JButton("Supprimer un bien support");
		boutonSupprimerLigne.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerBienEssentiel(ligneSelectionnee);
			}
		});
		return boutonSupprimerLigne;
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
		private final LinkedList<String> entetes = new LinkedList<String>();

		
		public ModeleDynamiqueObjet() {
			super(); 
			entetes.add("Intitule");
			entetes.add("Description");
			entetes.add("Type");
			entetes.add("Retenu");
		}
		
		public void supprimerCategorie(int ligneSelectionnee) {
			if (biensSupports.getNomColonnesSup().size()!=0){
				entetes.removeFirst();
				for (int i=0; i<biensSupports.nombreDeBiens();i++){
					biensSupports.getBien(i).enleverPremiereColonne();
				}
				biensSupports.getNomColonnesSup().removeFirst();
				if (biensSupports.getNomColonnesSup().size()==1){
					boutonSupprimerColonne.setEnabled(false);
				}
				fireTableStructureChanged();
			}
		}

		public void supprimerBienEssentiel(int ligneSelectionnee) {
			if (biensSupports.getLesBiens().size()!=0){
				if (biensSupports.getLesBiens().size()==1){
					Bien bien = biensSupports.getBien(ligneSelectionnee);
					biensSupports.supprimerBien(bien.getIntitule());
					boutonSupprimerLigne.setEnabled(false);
					fireTableRowsDeleted(ligneSelectionnee, ligneSelectionnee);
				}
				else{
					Bien bien = biensSupports.getBien(ligneSelectionnee);
					biensSupports.supprimerBien(bien.getIntitule());
					fireTableRowsDeleted(ligneSelectionnee, ligneSelectionnee);
				}
			}
		}

		public void ajouterCategorie() {
			String categorie = "";
			do{
				categorie = JOptionPane.showInputDialog("Intitule de la categorie ?");
			} while (categorie.equals("") || categorie.equals("Intitule") || categorie.equals("Description") || categorie.equals("Type") || categorie.equals("Retenu"));			
			biensSupports.getNomColonnesSup().addFirst("categorie");
			for (int i=0; i<biensSupports.nombreDeBiens();i++){
				biensSupports.getBien(i).ajouterColonne("");
			}
			entetes.addFirst(categorie);
			boutonSupprimerColonne.setEnabled(true);
			fireTableStructureChanged();	
		}

		public void ajouterBienEssentiel() {
			LinkedList<String> contenuColonneSup = new LinkedList<String>();
			for (int i=0; i<entetes.size()-3;i++){
				contenuColonneSup.add("");
			}
			String intitule = "";
			do{
				intitule = JOptionPane.showInputDialog("Intitule ?");
			} while (intitule.equals(""));
			String type = "";
			do{
				type = JOptionPane.showInputDialog("Type ?");
			} while (type.equals(""));
			String description = "";
			do{
				description = JOptionPane.showInputDialog("Description ?");
			} while (description.equals(""));
			Bien bien = new Bien(description, intitule, type, contenuColonneSup);
			biensSupports.ajouterBien(bien);
			boutonSupprimerLigne.setEnabled(true);
			fireTableRowsInserted(biensSupports.nombreDeBiens() -1, biensSupports.nombreDeBiens() -1);
		}

		public int getRowCount() {
			return biensSupports.nombreDeBiens();
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
				return biensSupports.getBien(rowIndex).getIntitule();
			case 2:
				return biensSupports.getBien(rowIndex).getDescription();
			case 1:
				return biensSupports.getBien(rowIndex).getType();
			case 0:
				return biensSupports.getBien(rowIndex).isRetenu();
			default:
				if(biensSupports.getNomColonnesSup().get(columnIndex)!=null){
					return biensSupports.getBien(rowIndex).getContenuColonnesSup().get(columnIndex);
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
		        Bien bien = biensSupports.getBien(rowIndex);
		 
		        switch(this.getColumnCount()-columnIndex-1){
		            case 3:
		            	if (!aValue.equals("")){
		            		bien.setIntitule((String)aValue);
		            	}
		                break;
		            case 2:
		            	if (!aValue.equals("")){
		            		bien.setDescription((String)aValue);
		            	}
		                break;
		            case 1:
		            	if (!aValue.equals("")){
		            		bien.setType((String)aValue);
		            	}
		                break;
		            case 0:
		            	bien.setRetenu((Boolean)aValue);
		                break;
		            default:
		            	if (!aValue.equals("")){
		            		biensSupports.getBien(rowIndex).getContenuColonnesSup().set(columnIndex, (String)aValue);
		            	}
		            	break;
		            	
		        }
		    }
		}
	}
}