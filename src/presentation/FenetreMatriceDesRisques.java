package presentation;

import javax.swing.JFrame;
import javax.swing.JTable;

public class FenetreMatriceDesRisques extends JFrame{
	
	private JTable tableau;
	private ModeleMatriceDesRisques modele;
	
	public FenetreMatriceDesRisques(){
		super("Matrice des Risques");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.tableau=new JTable(modele);
		
		this.setVisible(true);
		this.pack();
	}
	
	 public static void main(String[] args) {
	        new FenetreEvenementsRedoutes();
	    }

}
