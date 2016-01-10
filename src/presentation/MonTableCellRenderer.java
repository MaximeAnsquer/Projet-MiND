package presentation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;



public class MonTableCellRenderer extends DefaultTableCellRenderer {
	
	

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			Color clr=Color.white;
			if(column==0){
			switch(row){
			case 0: clr = Color.green;
			break;
			case 1: clr = Color.yellow;
			break;
			case 2: clr = Color.orange;
			break;
			case 3: clr = Color.red;
			break;
			default: clr = Color.magenta;
			}
		}
			
	cell.setBackground(clr);
	return cell;

}
}