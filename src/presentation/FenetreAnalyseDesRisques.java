package presentation;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import abstraction.modules.AnalyseDesRisques;
import abstraction.modules.EvenementsRedoutes;

public class FenetreAnalyseDesRisques extends JPanel{
	private ModeleAnalyseDesRisques modele=new ModeleAnalyseDesRisques();
	private JTable tableau;
	private AnalyseDesRisques analyse;
		
	
	public FenetreAnalyseDesRisques(AnalyseDesRisques analyse){
		
		
		 super(new GridLayout(1,0));
		 
		 AnalyseDesRisques an=new AnalyseDesRisques(analyse.getEtude());
		 analyse=an;
			this.analyse=analyse;
			
			this.tableau=new JTable(modele);
			tableau.setPreferredScrollableViewportSize(new Dimension(900, 400));
	        tableau.setFillsViewportHeight(true);
			
	        JScrollPane scrollPane = new JScrollPane(tableau);
			this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			add(scrollPane);
			
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-3).setPreferredWidth(400);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-4).setPreferredWidth(400);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-2).setPreferredWidth(250);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-1).setPreferredWidth(300);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-5).setPreferredWidth(600);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-6).setPreferredWidth(700);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-7).setPreferredWidth(800);
			
			this.setVisible(true);
		
		
		
		
		
	}
	
	
}
