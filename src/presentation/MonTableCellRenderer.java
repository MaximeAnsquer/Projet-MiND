package presentation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;



public class MonTableCellRenderer extends DefaultTableCellRenderer {
	
	

		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			Component cell = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			
			TableModel modele=table.getModel();
			int nombrelignes=modele.getRowCount();
			
			Color clr=Color.white;
			if(column==0){
		
			
			switch(nombrelignes-row){
			case 1: clr = Color.green;
			break;
			case 2: clr = Color.yellow;
			break;
			case 3: clr = Color.orange;
			break;
			case 4: clr = Color.red;
			break;
			default: clr = Color.magenta;
			}
		}
			
			
	cell.setBackground(clr);
	return cell;

}
}