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

import javax.swing.BorderFactory;
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

import presentation.FenetreBiensEssentiels.ModeleDynamiqueObjet;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.MappingDesBiens;

public class FenetreMappingDesBiens extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
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
		for (int i = 1; i < table.getColumnCount(); i++) {
			table.getColumn("" + table.getColumnName(i)).setCellEditor(
					new DefaultCellEditor(comboBox));
			;
		}
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JScrollPane(table));
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
		
		table.setDefaultRenderer(Object.class, new Renderer());
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		table.setRowHeight(50);
		table.setRowHeight(50);
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
		private BiensEssentiels biensEssentiels = mappingDesBiens
				.getBiensEssentiels();
		private BiensSupports biensSupports = mappingDesBiens
				.getBiensSupports();

		public ModeleDynamiqueObjet() {
			super();
			entetes.add("Biens Essentiels   \\   Biens Supports");
			for (int i = 0; i < biensSupports.getLesBiens().size(); i++) {
				entetes.add(biensSupports.getBien(i).getIntitule());
			}
		}

		public int getRowCount() {
			return biensEssentiels.nombreDeBiens();
		}

		public int getColumnCount() {
			return biensSupports.nombreDeBiens() + 1;
		}

		public String getColumnName(int columnIndex) {
			return entetes.get(columnIndex);
		}

		public boolean isCellEditable(int row, int col) {
			return true;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return biensEssentiels.getBien(rowIndex).getIntitule();
			default:
				return mappingDesBiens.getMappingDesBiens().get(rowIndex)
						.getValueAt(columnIndex - 1);
			}
		}

		public Class getColumnClass(int columnIndex) {
			return String.class;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (aValue != null) {
				switch (columnIndex) {
				case 0:
					break;
				default:
					mappingDesBiens.getMappingDesBiens().get(rowIndex)
							.setValueAt((String) aValue, columnIndex - 1);
					break;
				}
			}
		}
	}

	class Renderer extends DefaultTableCellRenderer {

		public Renderer() {
			super();
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component component = super.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, column);

			if (component instanceof JComponent) {
				((JComponent) component).setToolTipText(value.toString());
			}
			return component;
		}
	}
}
