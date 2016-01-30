package presentation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.MappingDesBiens;

public class FenetreMappingDesBiens extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextArea zoneDescription;
	private MappingDesBiens mappingDesBiens;
	JComboBox comboBox = new JComboBox();

	public FenetreMappingDesBiens(MappingDesBiens mappingDesBiens) {
		this.setVisible(true);
		this.mappingDesBiens = mappingDesBiens;
		this.mappingDesBiens.actualiserMapping();
		table = new JTable(new ModeleDynamiqueObjet());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		this.add(zoneDescription());

		table.setDefaultRenderer(Object.class, new Renderer());
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		table.setRowHeight(50);
		table.setRowHeight(50);
	}

	private JScrollPane zoneDescription() {
		String valeurInitiale = "Une case renseignée avec un ‘X’ (lettre ‘x’ majuscule) indique que le bien essentiel "
				+"repose sur le bien support, ce qui se traduit par le fait que les scénarios de menaces associés à ce"
				+"bien support sont susceptibles de provoquer les événements redoutés associés à ce bien essentiel : "
				+"cette information sera donc exploitée lors des croisements automatiques pour la constitution des risques. "
				+"\n \n Une case renseignée avec un ‘O’ (lettre ‘o’ majuscule) indique que le bien support est concerné "
				+"par le bien essentiel correspondant, mais que les scénarios de menaces qui lui sont associés ne peuvent pas "
				+"compromettre le bien essentiel dans son ensemble.";
		zoneDescription = new JTextArea(valeurInitiale);
		zoneDescription.setLineWrap(true);
		zoneDescription.setWrapStyleWord(true);
		zoneDescription.setFont(new Font("Arial", Font.PLAIN, 15));

		JScrollPane areaScrollPane = new JScrollPane(zoneDescription);
		areaScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(400, 150));
		areaScrollPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory
						.createTitledBorder("Aide mapping des biens"),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)),
				areaScrollPane.getBorder()));
		return areaScrollPane;
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
