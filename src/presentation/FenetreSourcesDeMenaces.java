package presentation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import abstraction.autres.SourceDeMenace;
import abstraction.modules.SourcesDeMenaces;

/**
 * Source : http://baptiste-wicht.developpez.com/tutoriels/java/swing/jtable/
 * @author Maxime Ansquer
 *
 */
public class FenetreSourcesDeMenaces extends JPanel {

	private JTable table;	
	private JTextArea zoneIntitule;
	private JButton boutonModifierIntitule;
	private JButton boutonSupprimer;
	private SourcesDeMenaces sdm;

	public FenetreSourcesDeMenaces(SourcesDeMenaces sdm){
		this.sdm = sdm;
		this.setVisible(true);

		table = new JTable(new ModeleDynamiqueObjet());

		//On redimensionne les colonnes 
		TableColumnModel columnModel = table.getColumnModel();		
		columnModel.getColumn(0).setMaxWidth(30);
		columnModel.getColumn(2).setMaxWidth(200);
		columnModel.getColumn(2).setMinWidth(200);
		columnModel.getColumn(3).setMaxWidth(50);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {
				zoneIntitule.setText(getSourceSelectionnee().getIntitule());
				boutonModifierIntitule.setEnabled(false);				
				boutonSupprimer.setEnabled(true);
			}
			public void mouseReleased(MouseEvent e) {
				zoneIntitule.setText(getSourceSelectionnee().getIntitule());
				boutonModifierIntitule.setEnabled(false);
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}			
		});

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(zoneIntitule());
		this.add(partieDuBas());
		this.add(new JScrollPane(table));			
	}

	private JScrollPane zoneIntitule() {

		zoneIntitule = new JTextArea();
		zoneIntitule.setLineWrap(true);
		zoneIntitule.setWrapStyleWord(true);

		zoneIntitule.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e) {
				if(table.getSelectedRow()>-1){
					boutonModifierIntitule.setEnabled(true);
				}				
			}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
		});

		JScrollPane areaScrollPane = new JScrollPane(zoneIntitule);
		areaScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(400, 150));
		areaScrollPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder("Intitulé de la source de menace"),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								areaScrollPane.getBorder()));
		return areaScrollPane;
	}

	protected SourceDeMenace getSourceSelectionnee() {
		SourceDeMenace c;
		try{
			c = sdm.getSource(table.getSelectedRow());
		}
		catch(ArrayIndexOutOfBoundsException e){
			c = sdm.getSource(table.getSelectedRow());
		}
		return c;
	}

	private JPanel partieDuBas() {
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAjouter());
		jpanel.add(boutonSupprimer());
		jpanel.add(boutonModifierIntitule());
		return jpanel;
	}

	private JButton boutonModifierIntitule() {
		this.boutonModifierIntitule = new JButton("Modifier l'intitule");
		boutonModifierIntitule.setEnabled(false);

		boutonModifierIntitule.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String nouvelIntitule = zoneIntitule.getText();
				getSourceSelectionnee().setIntitule(nouvelIntitule);
				table.validate();
				table.repaint();
				boutonModifierIntitule.setEnabled(false);
			}

		});
		return this.boutonModifierIntitule;
	}

	private JButton boutonSupprimer() {
		boutonSupprimer = new JButton("Supprimer une source de menace");
		boutonSupprimer.setEnabled(false);
		boutonSupprimer.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerSource(ligneSelectionnee);
			}

		});
		return boutonSupprimer;
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
			if(sdm.getSourceDeMenace(id) != null){
				JOptionPane.showMessageDialog(null, "Cet id est déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else{
				String intitule = JOptionPane.showInputDialog("Intitule ?");
				String exemple = JOptionPane.showInputDialog("Exemple ?");
				SourceDeMenace source = new SourceDeMenace(id, intitule, exemple);
				sdm.ajouterSourceDeMenace(source);

				fireTableRowsInserted(sdm.nombreDeSources() -1, sdm.nombreDeSources() -1);
			}			
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
