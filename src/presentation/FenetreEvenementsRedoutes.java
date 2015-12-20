package presentation;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class FenetreEvenementsRedoutes extends JPanel{
	private ModeleEvenementsRedoutes modele=new ModeleEvenementsRedoutes();
	private JTable tableau;

	
	FenetreEvenementsRedoutes(){
		
		
		this.tableau=new JTable(modele);
		
		this.setVisible(true);
		
	}
	
	 public static void main(String[] args) {
	        new FenetreEvenementsRedoutes();
	    }
	 
	    
	        
	   
	
	
	    
	
}
