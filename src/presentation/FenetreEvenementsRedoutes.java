package presentation;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTable;

public class FenetreEvenementsRedoutes extends JFrame{
	private ModeleEvenementsRedoutes modele=new ModeleEvenementsRedoutes();
	private JTable tableau;

	
	FenetreEvenementsRedoutes(){
		super("Evenements Redoutés");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.tableau=new JTable(modele);
		
		this.setVisible(true);
		this.pack();
	}
	
	 public static void main(String[] args) {
	        new FenetreEvenementsRedoutes();
	    }
	 
	    
	        
	   
	
	
	    
	
}
