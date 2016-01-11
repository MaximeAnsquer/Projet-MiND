package presentation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRendererScenario extends DefaultTableCellRenderer {
	ModeleScenarioDeMenacesTypes modele ;
	
	public CellRendererScenario(ModeleScenarioDeMenacesTypes modele){
		super();
		this.modele=modele;
		this.setHorizontalAlignment( SwingConstants.CENTER );
	}
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		
	
		if(component instanceof JComponent){
			String descriptionNiveauMetrique = this.modele.getMetriques()
					.getMetrique("Vraisemblance")
					.getNiveau(Integer.parseInt(value.toString()) - 1)
					.getDescription();
			((JComponent)component).setToolTipText(descriptionNiveauMetrique);
		}	
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
