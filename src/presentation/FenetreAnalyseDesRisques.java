package presentation;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import abstraction.autres.Bien;
import abstraction.autres.Evenement;
import abstraction.autres.ScenarioType;
import abstraction.modules.AnalyseDesRisques;
import abstraction.modules.EvenementsRedoutes;

public class FenetreAnalyseDesRisques extends JPanel{
	private ModeleAnalyseDesRisques modele=new ModeleAnalyseDesRisques();
	private JTable tableau;
	private AnalyseDesRisques analyse;
		
	
	public FenetreAnalyseDesRisques(AnalyseDesRisques analyse){
		
		
		 super(new GridLayout(1,0));
		 
		 AnalyseDesRisques nouvellean=new AnalyseDesRisques(analyse.getEtude());
		 
		 
		 
		 nouvellean.setCree(true);
			
			/*La boucle va servir à mettre à conserver les valeurs modifiées de l'ancien tableau et de les mettre
			 * dans le nouveau
			 */
			
			int a=nouvellean.getAnalyseDesRisques().size();
			int b=analyse.getAnalyseDesRisques().size();
			
			for (int i=0;i<a;i++){
				for(int j=0;j<b;j++){
					Evenement evnouveau=nouvellean.getAnalyseDesRisques().get(i).getEvenementRedoute();
					Evenement evancien=analyse.getAnalyseDesRisques().get(j).getEvenementRedoute();
					
					/*int nouveaugrav=nouvellean.getAnalyseDesRisques().get(i).getNiveauGravite();
					int anciengrav=analyse.getAnalyseDesRisques().get(j).getNiveauGravite();
					int nouveauvrai=nouvellean.getAnalyseDesRisques().get(i).getNiveauVraisemblance();
					int ancienvrai=analyse.getAnalyseDesRisques().get(j).getNiveauVraisemblance();*/
					
					Bien biennouveau=nouvellean.getAnalyseDesRisques().get(i).getBienSupport();
					Bien bienancien=analyse.getAnalyseDesRisques().get(j).getBienSupport();
					
					ScenarioType scnouveau=	nouvellean.getAnalyseDesRisques().get(i).getScenarioConcret();
					ScenarioType scancien= analyse.getAnalyseDesRisques().get(j).getScenarioConcret();
				
					
					if(evnouveau==evancien&&biennouveau==bienancien&&scnouveau==scancien){
						
						nouvellean.getAnalyseDesRisques().get(i).setIntitule(analyse.getAnalyseDesRisques().get(j).getIntitule());;
						nouvellean.getAnalyseDesRisques().get(i).setRetenu(analyse.getAnalyseDesRisques().get(j).getRetenu());
						
						
						
						
					}
					
				}
			}
			
			analyse=nouvellean;
			analyse.getEtude().addModule(nouvellean);
		 
		 
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