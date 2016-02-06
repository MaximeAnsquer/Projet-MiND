package presentation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import presentation.FenetreBiensEssentiels.ModeleDynamiqueObjet;
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
	private JButton boutonModifierColonne;
	private JButton boutonSupprimerColonne;
	private JButton boutonSupprimerLigne;
	private BiensSupports biensSupports;
	private JComboBox comboBox = new JComboBox();
	private JFrame petiteFenetre;
	private JTextArea textAreaPetiteFenetre;
	
	public static String stringAide = "- Double-cliquez sur une cellule pour la modifier. " +
			"\n- Faites un clic-droit sur une cellule pour afficher son contenu en entier." +
			"\n  (La fenêtre qui apparaît alors se ferme par un clic-gauche dans une zone quelconque du tableau.)";

	public FenetreBiensSupports(BiensSupports biensSupports){
		this.setVisible(true);
		this.biensSupports=biensSupports;
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		for (int i=0; i<biensSupports.getTypologie().getIntituleTypeBiensRetenus().length;i++){
			comboBox.addItem((String)biensSupports.getTypologie().getIntituleTypeBiensRetenus()[i]);
		}
		stringAide += this.descriptionTypesBiens();
		table.getColumn("Type").setCellEditor(new DefaultCellEditor(comboBox));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.creerPetiteFenetre();
		this.add(new JScrollPane(table));
		this.add(partieBoutons());
		if (biensSupports.getNomColonnesSup().size()>0){
			boutonSupprimerColonne.setEnabled(true);
			boutonModifierColonne.setEnabled(true);
		}
		else{
			boutonSupprimerColonne.setEnabled(false);
			boutonModifierColonne.setEnabled(false);
		}
		if (biensSupports.nombreDeBiens()==0){
			boutonSupprimerLigne.setEnabled(false);
		}
		else{
			boutonSupprimerLigne.setEnabled(true);
		}
		
		table.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {
				petiteFenetre.setVisible(false);
				if(SwingUtilities.isRightMouseButton(e)){
					selectionnerLaLigne(e);
					setPetiteFenetre();
				}
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}			
		});
		
		table.getColumnModel().getColumn(table.getColumnModel().getColumnCount()-1).setMaxWidth(50);
		table.setDefaultRenderer(Object.class, new Renderer());
		table.setFont(new Font("Arial", Font.PLAIN, 15)); table.setRowHeight(50);
		table.setRowHeight(50);
		
		this.biensSupports.actualiserBiens();
			
	}
	
	protected void selectionnerLaLigne(MouseEvent e) {
		Point p = e.getPoint();
		int rowNumber = table.rowAtPoint(p);
		int colNumber = table.columnAtPoint(p);
		table.changeSelection(rowNumber, colNumber, false, true);		
	}
	
	protected void setPetiteFenetre() {
		int row = this.table.getSelectedRow();
		int col = this.table.getSelectedColumn();
		if(row != -1 && col != -1){
			ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
			String contenuCellule = modele.getValueAt(row, col).toString();
			this.textAreaPetiteFenetre.setText(contenuCellule);
			Point positionSouris = MouseInfo.getPointerInfo().getLocation();
			int xSouris = (int) positionSouris.getX();
			int ySouris = (int) positionSouris.getY();
			Point positionDeLaFenetre = new Point(xSouris - 1, ySouris + 1);
			petiteFenetre.setLocation(positionDeLaFenetre);
			petiteFenetre.pack();
			petiteFenetre.pack();
			petiteFenetre.setVisible(true);	
		}			
	}

	private void creerPetiteFenetre() {
		this.petiteFenetre = new JFrame("Détails de la cellule");
		this.creerTextAreaPetiteFenetre();
		petiteFenetre.add(textAreaPetiteFenetre);	
		petiteFenetre.setMaximumSize(new Dimension(1000,1000));
		petiteFenetre.setMinimumSize(new Dimension(300,0));
		petiteFenetre.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}

	private void creerTextAreaPetiteFenetre() {
		this.textAreaPetiteFenetre = new JTextArea("Laul");		
		textAreaPetiteFenetre.setEditable(false);
		textAreaPetiteFenetre.setFont(new Font("Arial", Font.PLAIN, 17));
		textAreaPetiteFenetre.setLineWrap(true);
		textAreaPetiteFenetre.setWrapStyleWord(true);
	}
	
	private String descriptionTypesBiens() {
		String valeurInitiale = "\n\n\n";
		String stringDeBase = "";
		String stringRaccourcie = "";
		int index = 0;
		int nombreLignes = 0;
		boolean raccourcie = false;
		for (int i=0; i<biensSupports.getTypologie().getIntituleTypeBiensRetenus().length;i++){
			valeurInitiale += biensSupports.getTypologie().getIntituleTypeBiensRetenus()[i];
			stringDeBase = biensSupports.getTypologie().getTypeBiensRetenus().get(i).getDescription();
			while (!raccourcie && index<stringDeBase.length()){
				stringRaccourcie += stringDeBase.charAt(index);
				if (index == 120*(1+nombreLignes)){
					stringRaccourcie += "\n";
					nombreLignes++;
				}
				index++;
			}
			valeurInitiale +=" : \n"+stringRaccourcie+"\n\n";
		}
		return valeurInitiale;
	}
	
	private JPanel partieBoutons() {
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAjouterLigne());
		jpanel.add(boutonSupprimerLigne());
		jpanel.add(boutonAjouterColonne());
		jpanel.add(boutonSupprimerColonne());
		jpanel.add(boutonModificationColonne());
		jpanel.add(boutonAide());
		return jpanel;
	}

	private JButton boutonModificationColonne(){
		boutonModifierColonne = new JButton("Modifier categorie");
		boutonModifierColonne.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				LinkedList<String> listeCategorie = biensSupports.getNomColonnesSup();
				String categorie = null;
				categorie = (String)JOptionPane.showInputDialog(null,"Choississez une categorie","Categorie",JOptionPane.QUESTION_MESSAGE, null, listeCategorie.toArray(), listeCategorie.get(0));
				if (categorie != null){
					String nouvelleCategorie = "";
					nouvelleCategorie = JOptionPane.showInputDialog(null, "Nouveau nom de la categorie "+categorie+" ?");
					for (int i=0; i<listeCategorie.size();i++){
						if (listeCategorie.get(i).equals(nouvelleCategorie)){
							nouvelleCategorie = "";
						}
					}
					if (nouvelleCategorie.equals("Intitule") || nouvelleCategorie.equals("Description") || nouvelleCategorie.equals("Retenu") || nouvelleCategorie.equals("Type")){
						nouvelleCategorie = "";
					}
					if (!nouvelleCategorie.equals("")){
						biensSupports.modifierColonne(categorie, nouvelleCategorie);
						ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
						for (int i=0; i<modele.entetes.size(); i++){
							if (modele.entetes.get(i).equals(categorie)){
								modele.entetes.set(i, nouvelleCategorie);
							}
						}
						modele.fireTableStructureChanged();
					}
				}
			}
		});
		return boutonModifierColonne;
	}
	
	private JButton boutonAide() {
		JButton bouton = new JButton("Aide");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, stringAide, "Aide", JOptionPane.INFORMATION_MESSAGE);

			}

		});
		return bouton;
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
					boutonModifierColonne.setEnabled(false);
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
			boutonModifierColonne.setEnabled(true);
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
			String description = "";
			do{
				description = JOptionPane.showInputDialog("Description ?");
			} while (description.equals(""));
			Object[] possibilities = biensSupports.getTypologie().getIntituleTypeBiensRetenus();
			String type = "";
			do{
				type = (String)JOptionPane.showInputDialog(null, "Quel type de bien support ?","Type de bien", JOptionPane.QUESTION_MESSAGE,new ImageIcon(),possibilities,possibilities[0]);
			} while (type.equals(""));
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