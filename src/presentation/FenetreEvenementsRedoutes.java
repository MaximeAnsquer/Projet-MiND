package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.modules.EvenementsRedoutes;

public class FenetreEvenementsRedoutes extends JPanel{
	
	private ModeleEvenementsRedoutes modele;
	private EvenementsRedoutes evenements;
	private JTable tableau;

	
	FenetreEvenementsRedoutes(EvenementsRedoutes evenements){
		 super(new GridLayout(1,0));
		this.modele=new ModeleEvenementsRedoutes(evenements);
		this.evenements=evenements;
		
		this.tableau=new JTable(modele);
		tableau.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tableau.setFillsViewportHeight(true);
		
        JScrollPane scrollPane = new JScrollPane(tableau);
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setUpComBo();
		
		add(scrollPane);
		
		
		
		this.setVisible(true);
		
		
	}
	
	public void setUpComBo() {
	TableColumn exColumn = this.tableau.getColumnModel().getColumn(modele.getColumnCount()-2);
	
	final JComboBox comboBoxex=this.evenements.getEvenementsRedoutes().get(0).getComboExigence();
	exColumn.setCellEditor(new DefaultCellEditor(comboBoxex));
	DefaultTableCellRenderer renderer =
            new DefaultTableCellRenderer();
    renderer.setToolTipText("Cliquez pour sélectionner un niveau");
    exColumn.setCellRenderer(renderer);
	
	exColumn.setPreferredWidth(200);
	
	this.tableau.getColumnModel().getColumn(modele.getColumnCount()-3).setPreferredWidth(150);
	this.tableau.getColumnModel().getColumn(modele.getColumnCount()-4).setPreferredWidth(200);
	
	
	TableColumn gravColumn =this.tableau.getColumnModel().getColumn(modele.getColumnCount()-1);
	final JComboBox comboBoxgrav=this.evenements.getEvenementsRedoutes().get(0).getComboGravite();
	
	gravColumn.setCellEditor(new DefaultCellEditor(comboBoxgrav));
	gravColumn.setCellRenderer(renderer);
	
	gravColumn.setPreferredWidth(200);
	gravColumn.setMaxWidth(250);
	
	/*gravColumn.setCellRenderer(new DefaultTableCellRenderer());
	exColumn.setCellRenderer(new DefaultTableCellRenderer());*/
	
	
	
	
	}
	
	private Evenement getEvenementSelectionne(){
		Evenement e;
		try{
			e = evenements.getEvenementsRedoutes().get(tableau.getSelectedRow());
		}
		catch(ArrayIndexOutOfBoundsException h){
			e = evenements.getEvenementsRedoutes().get(0);
		}
		return e;
	}
		
		
	
	 
	    
	        
	   
	
	
	    
	
}

