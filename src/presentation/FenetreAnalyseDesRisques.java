package presentation;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import abstraction.autres.Bien;
import abstraction.autres.Evenement;
import abstraction.autres.ScenarioType;
import abstraction.modules.AnalyseDesRisques;
import abstraction.modules.EvenementsRedoutes;

public class FenetreAnalyseDesRisques extends JPanel{
	private ModeleAnalyseDesRisques modele;
	private JTable tableau;
	private AnalyseDesRisques analyse;
	private JFrame petiteFenetre;
	private JTextArea textAreaPetiteFenetre; 
	
		
	
	public FenetreAnalyseDesRisques(AnalyseDesRisques analyse){
		
		
		 super(new GridLayout(1,0));
			
			

			
			this.modele=new ModeleAnalyseDesRisques(analyse);
		 
		 
			this.analyse=analyse;
			
			this.tableau=new JTable(modele);
			
			this.creerPetiteFenetre();
			
			
			this.tableau.addMouseListener(new MouseListener(){
				
				public void mousePressed(MouseEvent e) {
					
					
					petiteFenetre.setVisible(false);
					if(SwingUtilities.isRightMouseButton(e)){
						selectionnerLaLigne(e);
						setPetiteFenetre();
					}
				}
				public void mouseReleased(MouseEvent e) {
					
				}
				public void mouseClicked(MouseEvent arg0) {
					
					
				}
				public void mouseEntered(MouseEvent arg0) {
					
					
				}
				public void mouseExited(MouseEvent arg0) {
					
					
				}});
			
			tableau.setPreferredScrollableViewportSize(new Dimension(900, 400));
	        tableau.setFillsViewportHeight(true);
			
	        JScrollPane scrollPane = new JScrollPane(tableau);
			this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			add(scrollPane);
			
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-3).setPreferredWidth(400);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-4).setPreferredWidth(600);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-2).setPreferredWidth(250);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-1).setPreferredWidth(400);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-5).setPreferredWidth(400);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-6).setPreferredWidth(300);
			this.tableau.getColumnModel().getColumn(modele.getColumnCount()-7).setPreferredWidth(800);
			
			
			CellRendererEv renderer=new CellRendererEv();
			
			this.tableau.getColumnModel().getColumn(1).setCellRenderer( renderer);
			this.tableau.getColumnModel().getColumn(4).setCellRenderer( renderer);
			this.tableau.getColumnModel().getColumn(6).setCellRenderer( renderer);
			
			tableau.setRowHeight(50);
			
			tableau.setFont(new Font("Arial", Font.PLAIN, 15));
			
			this.setVisible(true);
		
		this.analyse.setCree(true);
		
		
		
	}
	
	protected void selectionnerLaLigne(MouseEvent e) {
		// get the coordinates of the mouse click
		Point p = e.getPoint();

		// get the row and column indexes that contains that coordinate
		int rowNumber = tableau.rowAtPoint(p);
		int colNumber = tableau.columnAtPoint(p);

		tableau.changeSelection(rowNumber, colNumber, false, true);

	}

	private void creerPetiteFenetre() {
		this.petiteFenetre = new JFrame("Détails de la cellule");
		this.creerTextAreaPetiteFenetre();
		petiteFenetre.add(textAreaPetiteFenetre);	
		petiteFenetre.setMaximumSize(new Dimension(1000,1000));
		petiteFenetre.setMinimumSize(new Dimension(300,0));		
	}

	private void creerTextAreaPetiteFenetre() {
		this.textAreaPetiteFenetre = new JTextArea("Laul");		
		textAreaPetiteFenetre.setEditable(false);
		textAreaPetiteFenetre.setFont(new Font("Arial", Font.PLAIN, 17));
		textAreaPetiteFenetre.setLineWrap(true);
		textAreaPetiteFenetre.setWrapStyleWord(true);		
	}

	protected void setPetiteFenetre() {
		int row = this.tableau.getSelectedRow();
		int col = this.tableau.getSelectedColumn();
		if(row != -1 && col != -1){
			ModeleAnalyseDesRisques modele = (ModeleAnalyseDesRisques) tableau.getModel();
			String contenuCellule = modele.getValueAt(row, col).toString();
			System.out.println(contenuCellule);
			this.textAreaPetiteFenetre.setText(contenuCellule);
			petiteFenetre.pack();
			petiteFenetre.pack();
			Point positionSouris = MouseInfo.getPointerInfo().getLocation();
			int xSouris = (int) positionSouris.getX();
			int ySouris = (int) positionSouris.getY();
			Point positionDeLaFenetre = new Point(xSouris - 1, ySouris + 1);
			petiteFenetre.setLocation(positionDeLaFenetre);
			petiteFenetre.setVisible(true);	
		}			
	}

	
	
}