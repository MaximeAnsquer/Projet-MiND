package presentation;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

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
	
	 
	    
	        
	   
	
	
	    
	
}
