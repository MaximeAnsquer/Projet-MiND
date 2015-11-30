package presentation;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
 
public class JTableRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        /**
         * Fixer la couleur de fond de la première colonne en jaune
         */
        if (row == 1 && (column == 0 || column == 1)) {
            Color clr = new Color(255,255,0);
            component.setBackground(clr);
        } else {
            Color clr = new Color(255, 255, 255);
            component.setBackground(clr);
        }
        return component;
    }
}