package presentation;

import java.awt.Color;
import java.awt.Component;
import java.util.Random;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRendererTypesBiens extends DefaultTableCellRenderer {
	
	public CellRendererTypesBiens(){
		super();
		this.setHorizontalAlignment( SwingConstants.CENTER );
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component component = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (value.equals("") || value == null) {
			Color clr = Color.yellow;
			component.setBackground(clr);
		} 
		else if (value instanceof String) {
			Color clr;
			switch(row){
			case 0 : clr=new Color(198, 224, 180);
			break;
			case 1 : clr=new Color(248, 203, 173);
			break;
			case 2 : clr=new Color(255, 230, 153);
			break;
			case 3 : clr=new Color(250, 172, 207);
			break;
			case 4 : clr=new Color(189, 215, 238);
			break;
			case 5 : clr=new Color(172, 185, 202);
			break;
			case 6 : clr=new Color(219, 219, 219);
			break;
			default: 
				clr=new Color((255/(row+1))*(row),(255/(row+1))*(row),(255/(row+1))*(row));
				component.setBackground(clr);
		}
		component.setBackground(clr);
		} else {
			Color clr = new Color(255, 255, 255);
			component.setBackground(clr);
		}
		return component;
	}

}
