package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;



import abstraction.modules.MatriceDesRisques;

public class FenetreMatriceDesRisques extends JPanel{
	
	private JTable tableau;
	private ModeleMatriceDesRisques modele;
	private MatriceDesRisques matrice;
	
	public FenetreMatriceDesRisques(MatriceDesRisques matrice){
		
		
		super(new GridLayout(1,0));
		
		MatriceDesRisques mat=new MatriceDesRisques(matrice.getEtude());
		mat.setCree(true);
		
		this.modele=new ModeleMatriceDesRisques(mat);
		matrice.getEtude().addModule(mat);
		matrice=mat;
		
this.modele=new ModeleMatriceDesRisques(matrice);		
		
		this.matrice=matrice;

		
		this.tableau=new JTable(modele);
		tableau.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tableau.setFillsViewportHeight(true);
		
        JScrollPane scrollPane = new JScrollPane(tableau);
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		add(scrollPane);
		
		tableau.setDefaultRenderer(Object.class, new MonTableCellRenderer());
		
		
		DefaultTableCellRenderer headerRenderer1 = new DefaultTableCellRenderer();
		Color clr1=Color.green;
		headerRenderer1.setBackground(clr1);
		
		tableau.getColumnModel().getColumn(1).setHeaderRenderer(headerRenderer1);
		

		DefaultTableCellRenderer headerRenderer2= new DefaultTableCellRenderer();
		Color clr2=Color.yellow;
		headerRenderer2.setBackground(clr2);
		
		tableau.getColumnModel().getColumn(2).setHeaderRenderer(headerRenderer2);
		
		DefaultTableCellRenderer headerRenderer3= new DefaultTableCellRenderer();
		Color clr3=Color.orange;
		headerRenderer3.setBackground(clr3);
		
		tableau.getColumnModel().getColumn(3).setHeaderRenderer(headerRenderer3);
		
		DefaultTableCellRenderer headerRenderer4= new DefaultTableCellRenderer();
		Color clr4=Color.red;
		headerRenderer4.setBackground(clr4);
		
		tableau.getColumnModel().getColumn(4).setHeaderRenderer(headerRenderer4);
		
		if (tableau.getModel().getColumnCount()>5){
			for (int i=5;i<tableau.getModel().getColumnCount();i++){
				clr4=Color.magenta;
				headerRenderer4.setBackground(clr4);
				
				tableau.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer4);
			}
		}
		
		
		
		
		this.setVisible(true);
	
	

	
	
		
	}
	
	 
}
