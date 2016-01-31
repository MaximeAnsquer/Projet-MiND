package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import abstraction.autres.Metrique;
import abstraction.autres.NiveauDeMetrique;
import abstraction.modules.Metriques;

/**
 * Source : http://baptiste-wicht.developpez.com/tutoriels/java/swing/jtable/
 * @author Maxime Ansquer
 *
 */
public class FenetreMetriques extends JPanel {

	private JComboBox comboBox;
	private Metrique metriqueCourante;
	private Metriques metriques;
	private JPanel jpanel = new JPanel(new BorderLayout());
	private JTable table;
	private JButton boutonSupprimer;
	private JFrame petiteFenetre;
	private JTextArea textAreaPetiteFenetre;
	public static final String stringAide = "- Double-cliquez sur une cellule pour la modifier." +
			"\n- Utilisez le menu déroulant en haut à gauche pour sélectionner un autre critère de sécurité." +
			"\n- Faites un clic-droit sur une cellule pour afficher son contenu en entier.";

	public FenetreMetriques(Metriques metriques){
		this.metriques = metriques;

		this.setVisible(true);
		this.setLayout(new BorderLayout());		

		this.add(partieDuBas(), BorderLayout.NORTH);			
		this.add(jpanel, BorderLayout.CENTER);

		setTableau();

	}	

	private void setTableau() {
		jpanel.removeAll();
		metriqueCourante = getMetriqueCourante();
		ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet(metriqueCourante);
		table = new JTable(modele);

		this.creerPetiteFenetre();

		//On n'autorise la selection que d'une seule ligne a la fois
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//On change la police et la hauteur des lignes
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		table.setRowHeight(50);

		//On ajoute un listener qui joue sur l'affichage de la description d'une part, et sur le bouton " modifier " d'autre part
		table.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				boutonSupprimer.setEnabled(true);
				if(SwingUtilities.isRightMouseButton(e)){
					selectionnerLaLigne(e);
					setPetiteFenetre();
				}
			}
			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)){
					petiteFenetre.setVisible(false);
				}
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}			
		});

		table.addKeyListener(new KeyListener(){

			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				//				zoneDescription.setText(getNiveauSelectionne().getDescription());
				boutonSupprimer.setEnabled(true);
			}

		});

		//On redimensionne les colonnes 
		TableColumnModel columnModel = table.getColumnModel();		
		columnModel.getColumn(0).setMaxWidth(30);
		columnModel.getColumn(1).setMaxWidth(150);
		columnModel.getColumn(1).setMinWidth(150);

		//On met colorie le tableau
		table.setDefaultRenderer(Object.class, new Renderer());

		JLabel label = new JLabel(metriqueCourante.getIntitule());
		label.setOpaque(true);
		label.setBackground(Color.black);
		label.setForeground(Color.white);
		label.setFont(new Font("Arial", Font.BOLD, 15));
		jpanel.add(label, BorderLayout.NORTH);		
		jpanel.add(new JScrollPane(table), BorderLayout.CENTER);
		jpanel.validate();
	}	

	protected void selectionnerLaLigne(MouseEvent e) {
		// get the coordinates of the mouse click
		Point p = e.getPoint();

		// get the row and column indexes that contains that coordinate
		int rowNumber = table.rowAtPoint(p);
		int colNumber = table.columnAtPoint(p);

		table.changeSelection(rowNumber, colNumber, false, true);

	}

	private void creerPetiteFenetre() {
		this.petiteFenetre = new JFrame("Détails de la cellule");
		this.creerTextAreaPetiteFenetre();
		petiteFenetre.add(textAreaPetiteFenetre);	
		petiteFenetre.setMaximumSize(new Dimension(1000,1000));
		petiteFenetre.setMinimumSize(new Dimension(300,0));		
	}

	private void creerTextAreaPetiteFenetre() {
		this.textAreaPetiteFenetre = new JTextArea("Laul");		
		textAreaPetiteFenetre.setEditable(false);
		textAreaPetiteFenetre.setFont(new Font("Arial", Font.PLAIN, 17));
		textAreaPetiteFenetre.setLineWrap(true);
		textAreaPetiteFenetre.setWrapStyleWord(true);		
	}

	protected void setPetiteFenetre() {
		int row = this.table.getSelectedRow();
		int col = this.table.getSelectedColumn();
		if(row != -1 && col != -1){
			ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
			String contenuCellule = modele.getValueAt(row, col).toString();
			this.textAreaPetiteFenetre.setText(contenuCellule);
			petiteFenetre.pack();
			petiteFenetre.pack();
			Point positionSouris = MouseInfo.getPointerInfo().getLocation();
			int xSouris = (int) positionSouris.getX();
			int ySouris = (int) positionSouris.getY();
			Point positionDeLaFenetre = new Point(xSouris - 1, ySouris + 1);
			petiteFenetre.setLocation(positionDeLaFenetre);
			petiteFenetre.setVisible(true);	
		}			
	}

	private Metrique getMetriqueCourante() {
		Metrique m;		
		if(comboBox.getSelectedItem() == null){
			//Metrique fictive et inutile, cree juste pour eviter un bug
			m = new Metrique();
		}
		else{
			m = (Metrique) comboBox.getSelectedItem();
		}		
		return m;
	}

	private JPanel partieDuBas() {
		JPanel jp = new JPanel();
		jp.add(comboBox());
		jp.add(boutonAjouter());
		jp.add(boutonSupprimer());
		jp.add(boutonAide());
		return jp;
	}

	private JButton boutonAide() {
		JButton bouton = new JButton("Aide");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String aide = stringAide;
				JOptionPane.showMessageDialog(null, aide, "Aide", JOptionPane.INFORMATION_MESSAGE);

			}

		});
		return bouton;
	}

	private JButton boutonSupprimer() {
		boutonSupprimer = new JButton("Supprimer un niveau");
		boutonSupprimer.setEnabled(false);
		boutonSupprimer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int niveauSelectionne = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerNiveau(niveauSelectionne);		
				boutonSupprimer.setEnabled(false);
			}
		});
		return boutonSupprimer;
	}

	private JButton boutonAjouter() {
		JButton bouton = new JButton("Ajouter un niveau");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.ajouterNiveau();			
			}

		});
		return bouton;
	}

	private JComboBox comboBox() {
		comboBox = new JComboBox(metriques.getMetriquesDesCriteresRetenus().toArray());
		comboBox.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setTableau();	
				boutonSupprimer.setEnabled(false);
			}

		});
		return comboBox;
	}
	//---Modele---

	class ModeleDynamiqueObjet extends AbstractTableModel {

		private final String[] entetes = {"#", "Intitule", "Description"};
		private Metrique metrique;

		public ModeleDynamiqueObjet(Metrique metrique) {
			super();
			this.metrique = metrique;
		}

		public int getRowCount() {
			return metrique.nombreDeNiveaux();
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
				return metrique.getNiveau(rowIndex).getNumero();
			case 1:
				return metrique.getNiveau(rowIndex).getIntitule();
			case 2:
				return metrique.getNiveau(rowIndex).getDescription();
			default:
				return null; 
			}
		}

		public boolean isCellEditable(int row, int col){
			if(col == 0){
				return false;
			}
			else{
				return true;
			}
		}

		public Class getColumnClass(int columnIndex){
			switch(columnIndex){
			case 0:
				return Object.class;
			default:
				return String.class; 
			}
		}

		public void ajouterNiveau() {
			int numero = getMetriqueCourante().nombreDeNiveaux() + 1;
			String intitule = JOptionPane.showInputDialog("Veuillez saisir un intitulé.");
			if(intitule != null){
				String description = JOptionPane.showInputDialog("Veuillez saisir une description.");
				if(description != null){
					try{ 
						NiveauDeMetrique niveau = new NiveauDeMetrique(numero, intitule, description);

						getMetriqueCourante().ajouterNiveau(niveau);
					}
					catch(NumberFormatException e){
						JOptionPane.showMessageDialog(null, "La première valeur doit être un entier.");
					}

					fireTableRowsInserted(getMetriqueCourante().nombreDeNiveaux() -1, getMetriqueCourante().nombreDeNiveaux() -1);							
				}
			}
		}

		public void supprimerNiveau(int rowIndex) {
			getMetriqueCourante().supprimerNiveau(rowIndex);

			fireTableRowsDeleted(rowIndex, rowIndex);
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if(aValue != null && rowIndex >= 0){
				NiveauDeMetrique niveau = metrique.getNiveau(rowIndex);

				switch(columnIndex){
				case 0:
					try{
						niveau.setNumero(Integer.parseInt((String) aValue));
					}
					catch(NumberFormatException e){
						JOptionPane.showMessageDialog(null, "Veuillez saisir un entier supérieur a 0.");
					}
					break;
				case 1:
					niveau.setIntitule((String)aValue);
					break;
				case 2:
					niveau.setDescription((String)aValue);
					break;
				}
			}
			//			zoneDescription.setText(getNiveauSelectionne().getDescription());
		}
	}

	//---Fin du modele	

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
				if(value != null){
					((JComponent)component).setToolTipText(value.toString());					
				}
			}	
			if (value.equals("") || value == null) {
				Color clr = Color.yellow;
				component.setBackground(clr);
			} 
			else if(value instanceof Integer){
				int numero = (Integer) value;
				Color clr;
				switch(numero){
				case 1: clr = Color.green;
				break;
				case 2: clr = Color.yellow;
				break;
				case 3: clr = Color.orange;
				break;
				case 4: clr = Color.red;
				break;
				case 5: clr = Color.magenta;
				break;
				default: clr = new Color(150, 0, 150);
				}
				component.setBackground(clr);
			}
			else {
				Color clr = new Color(255, 255, 255);
				component.setBackground(clr);
			}
			if(isSelected && column != 0){
				component.setBackground(new Color(184, 207, 229));
			}
			return component;
		}
	}

	//---Fin du Renderer---

}
