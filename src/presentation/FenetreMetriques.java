package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
	private JPanel metriqueEnCours;
	private Metrique metriqueCourante;
	private Metriques metriques;
	private JPanel jpanel = new JPanel(new BorderLayout());
	private JTable table;
	private JTextArea zoneDescription;
	private JButton boutonModifierDescription;
	private JButton boutonSupprimer;

	public FenetreMetriques(Metriques metriques){
		this.metriques = metriques;

		this.setVisible(true);
		this.setLayout(new BorderLayout());		


		//		this.add(zoneDescription());		
		this.add(partieDuBas(), BorderLayout.NORTH);			
		this.add(jpanel, BorderLayout.CENTER);

		setTableau();

	}	

	private void setTableau() {
		jpanel.removeAll();
		metriqueCourante = getMetriqueCourante();
		ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet(metriqueCourante);
		table = new JTable(modele);

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
				//				zoneDescription.setText(getNiveauSelectionne().getDescription());
				//				boutonModifierDescription.setEnabled(false);
				boutonSupprimer.setEnabled(true);
			}
			public void mouseReleased(MouseEvent e) {
				//				zoneDescription.setText(getNiveauSelectionne().getDescription());
				//				boutonModifierDescription.setEnabled(false);
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

	private NiveauDeMetrique getNiveauSelectionne() {
		NiveauDeMetrique n;
		try{
			n = ( (ModeleDynamiqueObjet)table.getModel() ).metrique.getNiveau(table.getSelectedRow());
		}
		catch(ArrayIndexOutOfBoundsException e){
			n = ( (ModeleDynamiqueObjet)table.getModel() ).metrique.getNiveau(table.getSelectedRow());
		}
		return n;
	}

	private Metrique getMetriqueCourante() {
		Metrique m;
		if(comboBox == null){
			if(metriques.getMetriquesDesCriteresRetenus().size() != 0){
				m = metriques.getMetriquesDesCriteresRetenus().get(0);
			}
			else{
				m = new Metrique();
			}
		}
		else{
			if(comboBox.getSelectedItem() == null){
				m = new Metrique();
			}
			else{
				m = metriques.getMetrique(  ((Metrique) comboBox.getSelectedItem()).getCritere().getIntitule()   );
			}			
		}
		return m;
	}

	private JPanel partieDuBas() {
		JPanel jp = new JPanel();
		jp.add(comboBox());
		jp.add(boutonAjouter());
		jp.add(boutonSupprimer());
		//		jp.add(boutonModifier());
		return jp;
	}

	private JButton boutonModifier() {
		this.boutonModifierDescription = new JButton("Modifier la description");
		boutonModifierDescription.setEnabled(false);

		boutonModifierDescription.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//				String nouvelleDescription = zoneDescription.getText();
				//				getNiveauSelectionne().setDescription(nouvelleDescription);
				table.validate();
				table.repaint();
				boutonModifierDescription.setEnabled(false);
			}

		});
		return boutonModifierDescription;
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
				//				boutonModifierDescription.setEnabled(false);
				//				zoneDescription.setText(null);
				boutonSupprimer.setEnabled(false);
			}

		});
		return comboBox;
	}

	private JPanel tableau(JTable table, String intitule) {

		JPanel j = new JPanel();
		j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
		j.add(new JLabel(intitule));
		j.add(new JScrollPane(table));
		return j;
	}

	private JScrollPane zoneDescription() {

		zoneDescription = new JTextArea();
		zoneDescription.setLineWrap(true);
		zoneDescription.setWrapStyleWord(true);

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
								BorderFactory.createTitledBorder("Description du niveau"),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								areaScrollPane.getBorder()));
		return areaScrollPane;
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
			String intitule = JOptionPane.showInputDialog("Intitule ?");
			String description = JOptionPane.showInputDialog("Description ?");
			try{ 
				NiveauDeMetrique niveau = new NiveauDeMetrique(numero, intitule, description);

				getMetriqueCourante().ajouterNiveau(niveau);
			}
			catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "La premiere valeur doit etre un entier.");
			}

			fireTableRowsInserted(getMetriqueCourante().nombreDeNiveaux() -1, getMetriqueCourante().nombreDeNiveaux() -1);
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
						JOptionPane.showMessageDialog(null, "Veuillez saisir un entier superieur a 0.");
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
				((JComponent)component).setToolTipText(value.toString());
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
			return component;
		}
	}

	//---Fin du Renderer---

}
