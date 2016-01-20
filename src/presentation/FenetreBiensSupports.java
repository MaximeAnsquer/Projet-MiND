package presentation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

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
	private JTextArea descriptionTypesBiens;
	private JButton boutonModifierDescription;
	private JButton boutonSupprimerColonne;
	private JButton boutonSupprimerLigne;
	private BiensSupports biensSupports;
	private JComboBox comboBox = new JComboBox();

	public FenetreBiensSupports(BiensSupports biensSupports){
		this.setVisible(true);
		this.biensSupports=biensSupports;
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		for (int i=0; i<biensSupports.getTypologie().getIntituleTypeBiensRetenus().length;i++){
			comboBox.addItem((String)biensSupports.getTypologie().getIntituleTypeBiensRetenus()[i]);
		}
		table.getColumn("Type").setCellEditor(new DefaultCellEditor(comboBox));
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
		this.add(descriptionTypesBiens());
		this.add(zoneDescription());
		this.add(partieBoutons());
		if (biensSupports.getNomColonnesSup().size()>0){
			boutonSupprimerColonne.setEnabled(true);
		}
		else{
			boutonSupprimerColonne.setEnabled(false);
		}
		if (biensSupports.nombreDeBiens()==0){
			boutonSupprimerLigne.setEnabled(false);
		}
		else{
			boutonSupprimerLigne.setEnabled(true);
		}
		
		table.getColumnModel().getColumn(table.getColumnModel().getColumnCount()-1).setMaxWidth(50);
		table.setDefaultRenderer(Object.class, new Renderer());
		table.setFont(new Font("Arial", Font.PLAIN, 15)); table.setRowHeight(50);
		table.setRowHeight(50);
	}
	
	private Component descriptionTypesBiens() {
		String valeurInitiale = ""+biensSupports.getTypologie().getIntituleTypeBiensRetenus()[0];
		valeurInitiale+=" : \n"+biensSupports.getTypologie().getTypeBiensRetenus().get(0).getDescription()+"\n";
		for (int i=1; i<biensSupports.getTypologie().getIntituleTypeBiensRetenus().length;i++){
			valeurInitiale += "\n"+biensSupports.getTypologie().getIntituleTypeBiensRetenus()[i];
			valeurInitiale +=" : \n"+biensSupports.getTypologie().getTypeBiensRetenus().get(i).getDescription()+"\n";
		}
		descriptionTypesBiens = new JTextArea(valeurInitiale);
		descriptionTypesBiens.setLineWrap(true);
		descriptionTypesBiens.setWrapStyleWord(true);
		descriptionTypesBiens.setFont(new Font("Arial", Font.PLAIN, 15));
		
		JScrollPane areaScrollPane = new JScrollPane(descriptionTypesBiens);
		areaScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(400, 150));
		areaScrollPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder("Description des types de biens"),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								areaScrollPane.getBorder()));
		return areaScrollPane;
	}

	private JScrollPane zoneDescription() {
		String valeurInitiale = "";		
		zoneDescription = new JTextArea(valeurInitiale);
		zoneDescription.setLineWrap(true);
		zoneDescription.setWrapStyleWord(true);
		zoneDescription.setFont(new Font("Arial", Font.PLAIN, 15));
		
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
								BorderFactory.createTitledBorder("Description du bien selectionne"),
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
				modele.supprimerBienSupport(ligneSelectionnee);
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
				modele.ajouterBienSupport();
			}
		});
		return bouton;
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final LinkedList<String> entetes = new LinkedList<String>();

		
		public ModeleDynamiqueObjet() {
			super();
			for (int i=0; i<biensSupports.getNomColonnesSup().size();i++){
				entetes.add(biensSupports.getNomColonnesSup().get(i));
			}
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
				table.getColumnModel().getColumn(table.getColumnModel().getColumnCount()-1).setMaxWidth(50);
			}
		}

		public void supprimerBienSupport(int ligneSelectionnee) {
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
				for (int i=0; i<this.entetes.size();i++){
					if (this.entetes.get(i).equals(categorie)){
						categorie = "";
					}
				}
			} while (categorie.equals(""));			
			biensSupports.getNomColonnesSup().addFirst(categorie);
			for (int i=0; i<biensSupports.nombreDeBiens();i++){
				biensSupports.getBien(i).ajouterColonne("");
			}
			entetes.addFirst(categorie);
			boutonSupprimerColonne.setEnabled(true);
			fireTableStructureChanged();
			table.getColumnModel().getColumn(table.getColumnModel().getColumnCount()-1).setMaxWidth(50);
		}

		public void ajouterBienSupport() {
			LinkedList<String> contenuColonneSup = new LinkedList<String>();
			for (int i=0; i<entetes.size()-4;i++){
				contenuColonneSup.add("");
			}
			String intitule = "";
			do{
				intitule = JOptionPane.showInputDialog("Intitule ?");
				for (int i=0; i<biensSupports.nombreDeBiens();i++){
					if (biensSupports.getBien(i).getIntitule().equals(intitule)){
						intitule = "";
					}
				}
			} while (intitule.equals(""));
			Object[] possibilities = biensSupports.getTypologie().getIntituleTypeBiensRetenus();
			String type = (String)possibilities[0];
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
	
	class Renderer extends DefaultTableCellRenderer {

		public Renderer(){
			super();
			this.setHorizontalAlignment( SwingConstants.CENTER );
		}

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if(component instanceof JComponent){
				((JComponent)component).setToolTipText(value.toString());
			}
			return component;
		}
	}
}