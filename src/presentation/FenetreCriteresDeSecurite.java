package presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
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
import javax.swing.table.TableColumnModel;

import abstraction.autres.Critere;
import abstraction.autres.Metrique;
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
	private JFrame petiteFenetre;
	private JTextArea textAreaPetiteFenetre;
	public static final String stringAide = "- Double-cliquez sur une cellule pour la modifier. " +
			"\n- Faites un clic-droit sur une cellule pour afficher son contenu en entier." +
			"\n  (La fenêtre qui apparaît alors se ferme par un clic-gauche dans une zone quelconque du tableau.)";

	public FenetreCriteresDeSecurite(CriteresDeSecurite cds){
		//On set le module
		this.cds = cds;

		//On rend le fenêtre visible
		this.setVisible(true);

		//On crée la fenêtre qui détaille les cellules lors d'un clic droit
		this.creerPetiteFenetre();

		//On crée le tableau des sources de menaces
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//On redimensionne les colonnes 
		TableColumnModel columnModel = table.getColumnModel();		
		columnModel.getColumn(0).setMaxWidth(60);
		columnModel.getColumn(1).setMaxWidth(200);
		columnModel.getColumn(1).setMinWidth(200);
		columnModel.getColumn(3).setMaxWidth(50);		

		//On definit le Renderer (pour les tooltips)
		table.setDefaultRenderer(Object.class, new Renderer());		

		table.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {}

			public void mousePressed(MouseEvent e) {
				boutonSupprimer.setEnabled(true);
				petiteFenetre.setVisible(false);
				if(SwingUtilities.isRightMouseButton(e)){
					selectionnerLaLigne(e);
					setPetiteFenetre();
				}
			}
			public void mouseReleased(MouseEvent e) {
				//				if(SwingUtilities.isRightMouseButton(e)){
				//					petiteFenetre.setVisible(false);
				//				}
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

		table.setFont(new Font("Arial", Font.PLAIN, 16));
		table.setRowHeight(50);

		this.setLayout(new BorderLayout());

		//		this.add(zoneDescription());
		this.add(partieDuHaut(), BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);	

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
				JOptionPane.showMessageDialog(null, stringAide, "Aide", JOptionPane.INFORMATION_MESSAGE);

			}

		});
		return bouton;
	}

	private JButton boutonSupprimer() {
		boutonSupprimer = new JButton("Supprimer un critère");
		boutonSupprimer.setEnabled(false);
		boutonSupprimer.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int ligneSelectionnee = table.getSelectedRow();
				System.out.println("ligneSelectionnee : " + ligneSelectionnee);
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerCritere(ligneSelectionnee);
				boutonSupprimer.setEnabled(false);
			}

		});
		return boutonSupprimer;
	}

	private JButton boutonAjouter() {
		JButton bouton = new JButton("Ajouter un critère");
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
			String id = JOptionPane.showInputDialog("Veuillez saisir un id.");
			if(id != null){
				String intitule = JOptionPane.showInputDialog("Veuillez saisir un intitulé.");
				if(cds.getCritere(intitule) != null){
					JOptionPane.showMessageDialog(null, "Le critère existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				else if(intitule != null){
					String description = JOptionPane.showInputDialog("Veuillez entrer la description du critère.");
					if(description != null){
						Critere critere = new Critere(id, intitule, description);
						cds.ajouterCritere(critere);
						fireTableRowsInserted(cds.nombreDeCriteres() -1, cds.nombreDeCriteres() -1);						
					}
				}				
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
					((Metriques) cds.getEtude().getModule("Metriques")).getMetrique(ancienIntitule).setCritere(critere);
					Metrique metriqueARemettreDansLaListe = ((Metriques) cds.getEtude().getModule("Metriques")).getMetrique(ancienIntitule);
					((Metriques) cds.getEtude().getModule("Metriques")).supprimerMetrique(ancienIntitule);
					((Metriques) cds.getEtude().getModule("Metriques")).ajouterMetrique(metriqueARemettreDansLaListe);
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

	//---Renderer---

	class Renderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;		

		public Renderer(){
			super();
			this.setHorizontalAlignment( SwingConstants.CENTER );
		}

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
