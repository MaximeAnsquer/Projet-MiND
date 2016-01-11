package presentation;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import abstraction.modules.TypologieDesBiensSupports;

public class CellRendererBiensSupports extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	TypologieDesBiensSupports modele ;
	public CellRendererBiensSupports(TypologieDesBiensSupports modele) {
		super();
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.modele=modele;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component component = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if(component instanceof JComponent){
			String toolTip = "";
			for (int i=0; i<this.modele.getIntituleTypeBiensRetenus().length;i++){
				toolTip += "\n"+this.modele.getIntituleTypeBiensRetenus()[i]+" : \n"+this.modele.getTypeBiensRetenus().get(i).getDescription();
			}
			((JComponent)component).setToolTipText(toolTip);
		}	
		return component;
	}

}
