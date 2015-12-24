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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import abstraction.autres.Critere;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.Metriques;

/**
 * Fonctionnel, mais le code est relativement degueulasse.
 * Source : http://baptiste-wicht.developpez.com/tutoriels/java/swing/jtable/
 * @author Maxime Ansquer *
 */
public class FenetreCriteresDeSecurite extends JPanel {

	private JTable table;	
	private JTextArea zoneDescription;
	private JButton boutonModifierDescription;
	private JButton boutonSupprimer;
	private CriteresDeSecurite cds;

	public FenetreCriteresDeSecurite(CriteresDeSecurite cds){

		this.cds = cds;

		this.setVisible(true);

		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//On redimensionne les colonnes 
		TableColumnModel columnModel = table.getColumnModel();		
		columnModel.getColumn(0).setMaxWidth(30);
		columnModel.getColumn(1).setMaxWidth(200);
		columnModel.getColumn(1).setMinWidth(200);
		columnModel.getColumn(3).setMaxWidth(50);

		//On definit le Renderer (pour les tooltips)
		table.setDefaultRenderer(Object.class, new Renderer());

		table.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {}
			
			public void mousePressed(MouseEvent e) {
//				zoneDescription.setText(getCritereSelectionne().getDescription());
//				boutonModifierDescription.setEnabled(false);
				boutonSupprimer.setEnabled(true);
			}
			public void mouseReleased(MouseEvent e) {
//				zoneDescription.setText(getCritereSelectionne().getDescription());
//				boutonModifierDescription.setEnabled(false);
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}			
		});

		table.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
//				zoneDescription.setText(getCritereSelectionne().getDescription());
				boutonSupprimer.setEnabled(true);
			}			
		});

		table.setFont(new Font("Arial", Font.PLAIN, 16));
		table.setRowHeight(50);

		this.setLayout(new BorderLayout());

//		this.add(zoneDescription());
		this.add(partieDuHaut(), BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);	

	}

	private JScrollPane zoneDescription() {

		zoneDescription = new JTextArea();
		zoneDescription.setLineWrap(true);
		zoneDescription.setWrapStyleWord(true);
		zoneDescription.setFont(new Font("Arial", Font.PLAIN, 18));

		zoneDescription.addKeyListener(new KeyListener(){

			public void keyTyped(KeyEvent e) {
				if(table.getSelectedRow()>-1){
//					boutonModifierDescription.setEnabled(true);
				}

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
								BorderFactory.createTitledBorder("Description du critere"),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								areaScrollPane.getBorder()));
		return areaScrollPane;
	}

	private JPanel partieDuHaut() {
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAjouter());
		jpanel.add(boutonSupprimer());
//		jpanel.add(boutonModifierDescription());
		return jpanel;
	}

	private JButton boutonModifierDescription() {
		this.boutonModifierDescription = new JButton("Modifier la description");
		boutonModifierDescription.setEnabled(false);

		boutonModifierDescription.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
//				String nouvelleDescription = zoneDescription.getText();
//				getCritereSelectionne().setDescription(nouvelleDescription);
				table.validate();
				table.repaint();
				boutonModifierDescription.setEnabled(false);
			}

		});
		return boutonModifierDescription;
	}

	private JButton boutonSupprimer() {
		boutonSupprimer = new JButton("Supprimer un critere");
		boutonSupprimer.setEnabled(false);
		boutonSupprimer.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerCritere(ligneSelectionnee);
				boutonSupprimer.setEnabled(false);
			}

		});
		return boutonSupprimer;
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

	private Critere getCritereSelectionne(){
		Critere c;
		try{
			c = cds.getCritere(table.getSelectedRow());
		}
		catch(ArrayIndexOutOfBoundsException e){
			c = cds.getCritere(0);
		}
		return c;
	}

	//---Modele---

	class ModeleDynamiqueObjet extends AbstractTableModel {

		private final String[] entetes = {"Id", "Intitule", "Description", "Retenu"};

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
			String Id = JOptionPane.showInputDialog("Veuillez entrer l'id du critere.");
			String Intitule = JOptionPane.showInputDialog("Veuillez entrer l'intitule du critere.");
			if(cds.getCritere(Intitule) != null){
				JOptionPane.showMessageDialog(null, "Le critere existe deja.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			else{
				String Description = JOptionPane.showInputDialog("Veuillez entrer la description du critere.");
				Critere critere = new Critere(Id, Intitule, Description);
				cds.ajouterCritere(critere);
				fireTableRowsInserted(cds.nombreDeCriteres() -1, cds.nombreDeCriteres() -1);
			}			
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
					String ancienIntitule = critere.getIntitule();
					critere.setIntitule((String)aValue);
					((Metriques) cds.getEtude().getModule("Metriques")).supprimerMetrique(ancienIntitule);
					((Metriques) cds.getEtude().getModule("Metriques")).ajouterMetrique(critere);
					break;
				case 2:
					critere.setDescription((String)aValue);
					break;
				case 3:
					critere.setRetenu((Boolean)aValue);
					break;
				}
			}
//			zoneDescription.setText(getCritereSelectionne().getDescription());
		}
	}

	//---Renderer---

	class Renderer extends DefaultTableCellRenderer {

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
