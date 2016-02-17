package presentation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRendererToolTip extends DefaultTableCellRenderer {
	ModeleScenarioDeMenacesTypes modeleType ;
	ModeleScenarioDeMenacesGeneriques modeleGene ;
	ModeleTypologieBiensSupports modeleTypologie;

	public CellRendererToolTip(ModeleScenarioDeMenacesTypes modele) {
		super();
		this.modeleType=modele;
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public CellRendererToolTip(ModeleTypologieBiensSupports modele) {
		super();
		this.modeleTypologie=modele;
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public CellRendererToolTip(ModeleScenarioDeMenacesGeneriques modele) {
		super();
		this.modeleGene=modele;
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component component = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		
		if (component instanceof JComponent) {
			if (modeleType!=null){
				String contenuCase = (String) this.modeleType.getValueAt(row, column);
				((JComponent) component).setToolTipText(contenuCase);
			}
			if (modeleGene!=null){
				String contenuCase = (String) this.modeleGene.getValueAt(row, column);
				((JComponent) component).setToolTipText(contenuCase);
			}	
		}
		
		if (value.equals("") || value == null) {
			Color clr = Color.yellow;
			component.setBackground(clr);
		}
		return component;
	}
}
