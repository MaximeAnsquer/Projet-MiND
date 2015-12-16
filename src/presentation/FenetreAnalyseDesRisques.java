package presentation;

import javax.swing.JFrame;
import javax.swing.JTable;

public class FenetreAnalyseDesRisques extends JFrame{
	private ModeleEvenementsRedoutes modele=new ModeleEvenementsRedoutes();
	private JTable tableau;

	
	public FenetreAnalyseDesRisques(){
		super("Analyse des risques");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.tableau=new JTable(modele);
		
		this.setVisible(true);
		this.pack();
	}
	
	 public static void main(String[] args) {
	        new FenetreEvenementsRedoutes();
	    }
}
