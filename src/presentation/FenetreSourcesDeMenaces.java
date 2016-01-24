package presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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

import presentation.FenetreMetriques.Renderer;

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
		columnModel.getColumn(0).setMaxWidth(40);
		columnModel.getColumn(3).setMaxWidth(50);

		//On change le mode de selection
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//On change la police et la hauteur des lignes
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		table.setRowHeight(50);

		//On definit le renderer (pour les tooltips)
		table.setDefaultRenderer(Object.class, new Renderer());

		table.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {			
				boutonSupprimer.setEnabled(true);
			}
			public void mouseReleased(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}			
		});

		table.addKeyListener(new KeyListener(){

			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {		
				boutonSupprimer.setEnabled(true);
			}			
		});

		this.setLayout(new BorderLayout());

		this.add(partieDuHaut(), BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);			
	}

	private JScrollPane zoneIntitule() {

		zoneIntitule = new JTextArea();
		zoneIntitule.setLineWrap(true);
		zoneIntitule.setWrapStyleWord(true);

		zoneIntitule.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e) {
				if(table.getSelectedRow()>-1){
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
								BorderFactory.createTitledBorder("Intitule de la source de menace"),
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

	private JPanel partieDuHaut() {
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAjouter());
		jpanel.add(boutonSupprimer());
		jpanel.add(boutonAide());
		return jpanel;
	}
	
	private JButton boutonAide() {
		JButton bouton = new JButton("Aide");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Double-cliquez sur une cellule pour la modifier.", "Aide", JOptionPane.INFORMATION_MESSAGE);
				
			}
			
		});
		return bouton;
	}

	private JButton boutonSupprimer() {
		boutonSupprimer = new JButton("Supprimer une source de menace");
		boutonSupprimer.setEnabled(false);
		boutonSupprimer.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerSource(ligneSelectionnee);
				boutonSupprimer.setEnabled(false);
			}

		});
		return boutonSupprimer;
	}

	private JButton boutonAjouter() {
		JButton bouton = new JButton("Ajouter une source de menace");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
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
			String id = JOptionPane.showInputDialog("Veuillez saisir un id.");
			if(sdm.getSourceDeMenace(id) != null){
				JOptionPane.showMessageDialog(null, "Cet id est déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else if(id != null){
				String intitule = JOptionPane.showInputDialog("Veuillez saisir un intitulé.");
				if(intitule != null){
					String exemple = JOptionPane.showInputDialog("Veuillez saisir un exemple.");
					if(exemple != null){
						SourceDeMenace source = new SourceDeMenace(id, intitule, exemple);
						source.setRetenu(true);
						sdm.ajouterSourceDeMenace(source);						
						fireTableRowsInserted(sdm.nombreDeSources() -1, sdm.nombreDeSources() -1);
					}
				}
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

	//---Renderer---

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

	//---Fin du Renderer---

}
