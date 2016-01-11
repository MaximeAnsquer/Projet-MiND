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
			switch(row){
			default: 
				Random rand = new Random();
				float r = rand.nextFloat();
				float g = rand.nextFloat() / 2f;
				float b = rand.nextFloat() / 2f;
				component.setBackground(new Color(r,g,b));
		}
		} else {
			Color clr = new Color(255, 255, 255);
			component.setBackground(clr);
		}
		return component;
	}

}
