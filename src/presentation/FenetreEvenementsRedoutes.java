package presentation;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import abstraction.modules.EvenementsRedoutes;

public class FenetreEvenementsRedoutes extends JPanel{
	
	private ModeleEvenementsRedoutes modele=new ModeleEvenementsRedoutes();
	private EvenementsRedoutes evenements;
	private JTable tableau;

	
	FenetreEvenementsRedoutes(EvenementsRedoutes evenements){
        
		this.evenements=evenements;
		
		this.tableau=new JTable(modele);
		this.add(this.tableau);
		
		this.setVisible(true);
		
	}
	
	public void setUpSportColumn() {
	TableColumn exColumn = tableau.getColumnModel().getColumn(modele.getColumnCount()-2);
	
	JComboBox comboBoxex=this.evenements.getEvenementsRedoutes().get(0).getComboExigence();
	exColumn.setCellEditor(new DefaultCellEditor(comboBoxex));
	
	TableColumn gravColumn = tableau.getColumnModel().getColumn(modele.getColumnCount()-1);
	JComboBox comboBoxgrav=this.evenements.getEvenementsRedoutes().get(0).getComboGravite();
	gravColumn.setCellEditor(new DefaultCellEditor(comboBoxgrav));
	
	 }
		
		
	
	 
	    
	        
	   
	
	
	    
	
}
