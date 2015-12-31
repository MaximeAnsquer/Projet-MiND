package presentation;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import abstraction.modules.MatriceDesRisques;

public class FenetreMatriceDesRisques extends JPanel{
	
	private JTable tableau;
	private ModeleMatriceDesRisques modele= new ModeleMatriceDesRisques();
	private MatriceDesRisques matrice;
	
	public FenetreMatriceDesRisques(MatriceDesRisques matrice){
		
		
		super(new GridLayout(1,0));
		
		MatriceDesRisques mat=new MatriceDesRisques(matrice.getEtude());
		matrice=mat;		
		
		this.matrice=matrice;
		this.tableau=new JTable(modele);
		tableau.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tableau.setFillsViewportHeight(true);
		
        JScrollPane scrollPane = new JScrollPane(tableau);
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		add(scrollPane);
		
		
		
		this.setVisible(true);
	
	
	
	this.tableau=new JTable(modele);
	
	this.setVisible(true);
		
	}
	
	 
}
