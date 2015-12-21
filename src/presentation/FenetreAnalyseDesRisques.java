package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class FenetreAnalyseDesRisques extends JPanel{
	private ModeleEvenementsRedoutes modele=new ModeleEvenementsRedoutes();
	private JTable tableau;
	

	
	public FenetreAnalyseDesRisques(){
		
		
		
		this.tableau=new JTable(modele);
		
		this.setVisible(true);
		
	}
	
	
}
