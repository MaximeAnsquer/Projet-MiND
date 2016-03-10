package presentation;

import java.awt.Color;
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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.MappingDesBiens;

public class FenetreMappingDesBiens extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private MappingDesBiens mappingDesBiens;
	JComboBox comboBox = new JComboBox();
	private JFrame petiteFenetre;
	private JTextArea textAreaPetiteFenetre;
	public static final String stringAide = "- Double-cliquez sur une cellule pour la modifier. " +
			"\n- Faites un clic-droit sur une cellule pour afficher son contenu en entier." +
			"\n  (La fenêtre qui apparaît alors se ferme par un clic-gauche dans une zone quelconque du tableau.)"+
			"\n- Une case renseignée avec un ‘X’ (lettre ‘x’ majuscule) indique que le bien essentiel "+
			"\n  repose sur le bien support, ce qui se traduit par le fait que les scénarios de menaces associés à ce"+
			"\n  bien support sont susceptibles de provoquer les événements redoutés associés à ce bien essentiel : "+
			"\n  cette information sera donc exploitée lors des croisements automatiques pour la constitution des risques. "+
			"\n- Une case renseignée avec un ‘O’ (lettre ‘o’ majuscule) indique que le bien support est concerné "+
			"\n  par le bien essentiel correspondant, mais que les scénarios de menaces qui lui sont associés ne peuvent pas "+
			"\n  compromettre le bien essentiel dans son ensemble.";

	public FenetreMappingDesBiens(MappingDesBiens mappingDesBiens) {
		this.setVisible(true);
		this.mappingDesBiens = mappingDesBiens;
		this.mappingDesBiens.actualiserMapping();
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);			
		this.creerPetiteFenetre();
		comboBox.addItem("");
		comboBox.addItem("x");
		comboBox.addItem("o");
		
		//TODO régler le problème de cellEditor (l'appliquer que pour les bonnes cases (combo column+row))
		TableColumnModel columnModel = table.getColumnModel();
		ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
		for (int i = 0; i < table.getColumnCount(); i++) {
			columnModel.getColumn(i).setCellEditor(new DefaultCellEditor(comboBox));
		}
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);
		this.fixerPremiereColonneEtPouvoirScrollerLesAutres();	
		JPanel jpanel = new JPanel();
		jpanel.add(boutonAide());
		this.add(jpanel);

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
		
		table.setDefaultRenderer(Object.class, new Renderer1());
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		table.setRowHeight(50);
		table.setRowHeight(50);
	}
	
	private void fixerPremiereColonneEtPouvoirScrollerLesAutres() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		resizeColumnWidth(table);
		FixedColumnTable fct = new FixedColumnTable(1, scrollPane);		
		JTable fixedTable = fct.getFixedTable();
		fixedTable.setDefaultRenderer(Object.class, new Renderer2());
		fixedTable.setFont(new Font("Arial", Font.PLAIN, 15));
		fixedTable.setRowHeight(50);
		fixedTable.setRowHeight(50);
	}

	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(2*comp.getMinimumSize().width + 1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
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
	
	private JButton boutonAide() {
		JButton bouton = new JButton("Aide");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, stringAide, "Aide", JOptionPane.INFORMATION_MESSAGE);

			}

		});
		return bouton;
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final LinkedList<String> entetes = new LinkedList<String>();
		private BiensEssentiels biensEssentiels = mappingDesBiens.getBiensEssentiels();
		private BiensSupports biensSupports = mappingDesBiens.getBiensSupports();

		public ModeleDynamiqueObjet() {
			super();
		}

		public int getRowCount() {
			return biensEssentiels.getBiensRetenus().size() + biensSupports.getNomColonnesSup().size() + 1;
		}

		public int getColumnCount() {
			return biensSupports.getBiensRetenus().size() + biensEssentiels.getNomColonnesSup().size() + 1;
		}

		public String getColumnName(int columnIndex) {
			return "";
		}

		public boolean isCellEditable(int row, int col) {
			if (row > biensSupports.getNomColonnesSup().size() && col > biensEssentiels.getNomColonnesSup().size()){
				return true;
			}
			else{
				return false;
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if (rowIndex < biensSupports.getNomColonnesSup().size() +1){
				if (rowIndex == biensSupports.getNomColonnesSup().size()){
					if (columnIndex < biensEssentiels.getNomColonnesSup().size() + 1){
						if (columnIndex == biensEssentiels.getNomColonnesSup().size()){
							return "Biens Essentiels   \\   Biens Supports";
						}
						else {
							return biensEssentiels.getNomColonnesSup().get(columnIndex);
						}
					}
					else{
						return biensSupports.getBiensRetenus().get(columnIndex-biensEssentiels.getNomColonnesSup().size() - 1).getIntitule();
						//return biensSupports.getBien(columnIndex-biensEssentiels.getNomColonnesSup().size() - 1).getIntitule();
					}
				}
				else{
					if (columnIndex < biensEssentiels.getNomColonnesSup().size() + 1){
						if (columnIndex == biensEssentiels.getNomColonnesSup().size()){
							return biensSupports.getNomColonnesSup().get(rowIndex);
						}
						else {
							return "";
						}
					}
					else{
						return biensSupports.getBiensRetenus().get(columnIndex-biensEssentiels.getNomColonnesSup().size() - 1).getContenuColonnesSup().get(rowIndex);
						//return biensSupports.getBien(columnIndex-biensEssentiels.getNomColonnesSup().size() - 1).getContenuColonnesSup().get(rowIndex);
					}
				}
			}
			else{
				if (columnIndex < biensEssentiels.getNomColonnesSup().size() + 1){
					if (columnIndex == biensEssentiels.getNomColonnesSup().size()){
						return biensEssentiels.getBiensRetenus().get(rowIndex-biensSupports.getNomColonnesSup().size() - 1).getIntitule();
						//return biensEssentiels.getBien(rowIndex-biensSupports.getNomColonnesSup().size() - 1).getIntitule();
					}
					else{
						return biensEssentiels.getBiensRetenus().get(rowIndex-biensSupports.getNomColonnesSup().size() - 1).getContenuColonnesSup().get(columnIndex);
						//return biensEssentiels.getBien(rowIndex-biensSupports.getNomColonnesSup().size() - 1).getContenuColonnesSup().get(columnIndex);
					}
				}
				else{
					return mappingDesBiens.getMappingDesBiens().get(rowIndex-biensSupports.getNomColonnesSup().size()-1).getValueAt(columnIndex - biensEssentiels.getNomColonnesSup().size()-1);
				}
			}
		}

		public Class getColumnClass(int columnIndex) {
			return String.class;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (aValue != null && rowIndex >= biensSupports.getNomColonnesSup().size() + 1 && columnIndex >= biensEssentiels.getNomColonnesSup().size() + 1) {
				mappingDesBiens.getMappingDesBiens().get(rowIndex - biensSupports.getNomColonnesSup().size() - 1).setValueAt((String) aValue, columnIndex - biensEssentiels.getNomColonnesSup().size() - 1);
			}
		}
	}

	class Renderer1 extends DefaultTableCellRenderer {

		public Renderer1() {
			super();
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row,int column) {
			Component component = super.getTableCellRendererComponent(table,value, isSelected, hasFocus, row, column);
			ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
			
			if (component instanceof JComponent) {
				((JComponent) component).setToolTipText(value.toString());
			}

			return component;
		}
	}
	
	class Renderer2 extends DefaultTableCellRenderer {

		public Renderer2() {
			super();
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row,int column) {
			Component component = super.getTableCellRendererComponent(table,value, isSelected, hasFocus, row, column);
			ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
			
			if (component instanceof JComponent) {
				((JComponent) component).setToolTipText(value.toString());
			}
			
//			if (row < modele.biensSupports.getNomColonnesSup().size() +1){
//				if (row == modele.biensSupports.getNomColonnesSup().size()){
//					if (column < modele.biensEssentiels.getNomColonnesSup().size() + 1){
//						component.setBackground(new Color(200, 200, 200));
//					}
//					else{
//						component.setBackground(new Color(222, 222, 222));
//					}
//				}
//				else{
//					if (column < modele.biensEssentiels.getNomColonnesSup().size() + 1){
//						if (column == modele.biensEssentiels.getNomColonnesSup().size()){
//							component.setBackground(new Color(200, 200, 200));
//						}
//						else {
//							component.setBackground(new Color(255, 255, 255));
//						}
//					}
//					else{
//						component.setBackground(new Color(222, 222, 222));
//					}
//				}
//			}
//			else{
//				if (column < modele.biensEssentiels.getNomColonnesSup().size() + 1){
//					component.setBackground(new Color(222, 222, 222));
//				}
//				else{
//					component.setBackground(new Color(255, 255, 255));
//				}
//			}

			return component;
		}
	}
	
	
}
