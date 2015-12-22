package presentation;

import java.awt.Color;
import java.awt.Component;
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
	
	private ModeleEvenementsRedoutes modele=new ModeleEvenementsRedoutes();
	private EvenementsRedoutes evenements;
	private JTable tableau;

	
	FenetreEvenementsRedoutes(EvenementsRedoutes evenements){
        
		this.evenements=evenements;
		
		this.tableau=new JTable(modele);
		System.out.println(modele.getColumnCount());
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setUpComBo();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(this.tableau);
		
		JPanel paneau=new JPanel();
		paneau.add(evenements.getEvenementsRedoutes().get(0).getComboExigence());
		this.add(paneau);
		
		this.setVisible(true);
		
		
	}
	
	public void setUpComBo() {
	TableColumn exColumn = this.tableau.getColumnModel().getColumn(modele.getColumnCount()-2);
	
	JComboBox comboBoxex=this.evenements.getEvenementsRedoutes().get(0).getComboExigence();
	exColumn.setCellEditor(new DefaultCellEditor(comboBoxex));
	exColumn.setPreferredWidth(200);
	
	this.tableau.getColumnModel().getColumn(modele.getColumnCount()-3).setPreferredWidth(150);
	this.tableau.getColumnModel().getColumn(modele.getColumnCount()-4).setPreferredWidth(200);
	
	
	TableColumn gravColumn =this.tableau.getColumnModel().getColumn(modele.getColumnCount()-1);
	JComboBox comboBoxgrav=this.evenements.getEvenementsRedoutes().get(0).getComboGravite();
	System.out.println(comboBoxgrav.getItemAt(0));
	System.out.println(comboBoxgrav.getItemAt(1));
	gravColumn.setCellEditor(new DefaultCellEditor(comboBoxgrav));
	gravColumn.setPreferredWidth(200);
	
	/*gravColumn.setCellRenderer(new DefaultTableCellRenderer());
	exColumn.setCellRenderer(new DefaultTableCellRenderer());*/
	
	
	JScrollPane scroll=new JScrollPane(this.tableau);
	this.add(scroll);
	
	comboBoxex.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int d=getEvenementSelectionne().getComboExigence().getSelectedIndex();
			getEvenementSelectionne().getComboExigence().setSelectedIndex(d);;
			tableau.validate();
			
		}});
			
	comboBoxgrav.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					tableau.repaint();
		}
	});
	
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

