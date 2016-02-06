package presentation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRendererToolTip extends DefaultTableCellRenderer {
	ModeleScenarioDeMenacesTypes modele ;

	public CellRendererToolTip(ModeleScenarioDeMenacesTypes modele) {
		super();
		this.modele=modele;
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component component = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		
		if (component instanceof JComponent) {
			String contenuCase = (String) this.modele.getValueAt(row, column);
			((JComponent) component).setToolTipText(contenuCase);
		}
		
		if (value.equals("") || value == null) {
			Color clr = Color.yellow;
			component.setBackground(clr);
		}
		return component;
	}
}
