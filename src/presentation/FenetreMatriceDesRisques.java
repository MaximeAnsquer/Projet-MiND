package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class FenetreMatriceDesRisques extends JPanel{
	
	private JTable tableau;
	private ModeleMatriceDesRisques modele;
	
	public FenetreMatriceDesRisques(){
		
		
		this.tableau=new JTable(modele);
		
		this.setVisible(true);
		
	}
	
	 
}
