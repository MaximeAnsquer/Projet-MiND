package presentation;

import java.awt.Component;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
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
	private JTextArea zoneDescription;
	private JButton boutonModifierDescription;

	public FenetreCriteresDeSecurite2(){
		super("Criteres de securite 2");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				zoneDescription.setText(getCritereSelectionne().getDescription());
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
		getContentPane().add(partieDuBas());
		pack();

	}

	private JScrollPane zoneDescription() {
		
		String valeurInitiale = ( (ModeleDynamiqueObjet)table.getModel() ).cds.getCritere(0).getDescription();		
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
								BorderFactory.createTitledBorder("Description du critere"),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								areaScrollPane.getBorder()));
		return areaScrollPane;
	}

	private JPanel partieDuBas() {
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAjouter());
		jpanel.add(boutonSupprimer());
		jpanel.add(boutonModifierDescription());
		return jpanel;
	}

	private JButton boutonModifierDescription() {
		this.boutonModifierDescription = new JButton("Modifier la description");
		boutonModifierDescription.setEnabled(false);
		
		boutonModifierDescription.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String nouvelleDescription = zoneDescription.getText();
				getCritereSelectionne().setDescription(nouvelleDescription);
				boutonModifierDescription.setEnabled(false);
			}
			
		});
		return boutonModifierDescription;
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
	
	private Critere getCritereSelectionne(){
		Critere c;
		try{
			c = ( (ModeleDynamiqueObjet)table.getModel() ).cds.getCritere(table.getSelectedRow());
		}
		catch(ArrayIndexOutOfBoundsException e){
			c = ( (ModeleDynamiqueObjet)table.getModel() ).cds.getCritere(0);
		}
		return c;
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private Etude etude = MainMaximeAnsquer.etude;
		private CriteresDeSecurite cds = (CriteresDeSecurite) etude.getModule("CriteresDeSecurite");

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
