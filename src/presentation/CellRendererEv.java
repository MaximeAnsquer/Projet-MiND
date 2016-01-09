package presentation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class CellRendererEv extends DefaultTableCellRenderer{
	
	
	public CellRendererEv(){
		super();
		this.setHorizontalAlignment( SwingConstants.CENTER );
	}

	private static final long serialVersionUID = 1L;
	
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		
	
		
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
			default: clr = Color.magenta;
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