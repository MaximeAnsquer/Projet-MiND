package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import abstraction.Etude;
import abstraction.autres.Critere;
import abstraction.autres.Evenement;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.EvenementsRedoutes;
import abstraction.modules.Metriques;

public class FenetreEvenementsRedoutes extends JPanel{
	/*Cette classe fait appel aux variables d'instance suivantes pour afficher le tableau des Evenements Redout�s
	 * 
	 */
	
	
	
	private ModeleEvenementsRedoutes modele;
	private EvenementsRedoutes evenements;
	private JTableX tableau;
	private Etude etude;
	private CriteresDeSecurite criteres;
	private ArrayList<TableCellEditor> editors=new ArrayList<TableCellEditor>();

	
	FenetreEvenementsRedoutes(EvenementsRedoutes evenements){
		 super(new GridLayout(1,0));
		 /* On recr�e un module pour �tre sur que les donn�es sont � jour*/
		 
		
		this.modele=new ModeleEvenementsRedoutes(evenements);
		
		this.etude=evenements.getEtude();
		
	
		
		this.criteres=(CriteresDeSecurite) this.etude.getModule("CriteresDeSecurite");
		
		this.evenements=evenements;
		
		
		this.setUpComBo();
		
		
		tableau.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tableau.setFillsViewportHeight(true);
		
        JScrollPane scrollPane = new JScrollPane(tableau);
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
		
		add(scrollPane);

		
		CellRendererEv renderer=new CellRendererEv();
		
		this.tableau.getColumnModel().getColumn(tableau.getColumnCount()-1).setCellRenderer( renderer);
		this.tableau.getColumnModel().getColumn(tableau.getColumnCount()-2).setCellRenderer( renderer);
		this.tableau.getColumnModel().getColumn(tableau.getColumnCount()-5).setCellRenderer( renderer);
		
		
		this.setVisible(true);
		
		this.evenements.setCree(true);
		
		
		
	    
		
		
		
	}
	
	
	/*Methode qui permet de construire le tableau ainsi que les Jcombobox associ�es � la Gravit� et � l'Exigence*/
	
	public void setUpComBo() {
 
		
		this.tableau=new JTableX(modele);
		
		
		
		tableau.setRowSelectionAllowed(false);
        tableau.setColumnSelectionAllowed(false);
		
		RowEditorModel rm = new RowEditorModel();
		
		tableau.setRowEditorModel(rm);
	
	/*On construit la JCombobox associ�e � l'exigence de chaque crit�re du tableau*/
		
		int u=this.criteres.getCriteresRetenus().size();
		
		
		for(int i=0;i<u;i++){
			
			ArrayList<Critere> tableaucriteres=new ArrayList<Critere>(this.criteres.getCriteresRetenus());
			String nomcritere=tableaucriteres.get(i).getIntitule();	
			
			
			int b=((Metriques)this.etude.getModule("Metriques")).getMetrique(nomcritere).nombreDeNiveaux();
			String[] listebis=new String[b];
	    
			for(int j=1;j<=b;j++){
	    	
	    	
			listebis[j-1]=""+j;
		}
	    JComboBox comboBoxex=new JComboBox(listebis);
	    DefaultCellEditor ed = new DefaultCellEditor(comboBoxex);
	    for (int k=0;k<modele.getRowCount();k++){
	    	if(evenements.getEvenementsRedoutes().get(k).getNomCritere()==nomcritere){
	    		rm.addEditorForRow(k,ed);
	    	}
	    }
	    
		
		}
	
		this.tableau.getColumnModel().getColumn(modele.getColumnCount()-3).setPreferredWidth(150);
		this.tableau.getColumnModel().getColumn(modele.getColumnCount()-4).setPreferredWidth(200);
       
		/*Puis on construit la JCombobox associ�e � la Gravit�*/
		
       int a=((Metriques) this.etude.getModule("Metriques")).getMetrique("Gravite").nombreDeNiveaux();
		
		String[] liste=new String[a];
		
		for(int i=1;i<=a;i++){
			
			liste[i-1]=""+i;
		}
        
	
	TableColumn gravColumn =this.tableau.getColumnModel().getColumn(modele.getColumnCount()-1);
	final JComboBox comboBoxgrav=new JComboBox(liste);
	
	gravColumn.setCellEditor(new DefaultCellEditor(comboBoxgrav));
	
	
	gravColumn.setPreferredWidth(200);
	gravColumn.setMaxWidth(250);
	
	/*TableColumn nameColumn =this.tableau.getColumnModel().getColumn(modele.getColumnCount()-5);
	 DefaultTableCellRenderer renderer =
             new DefaultTableCellRenderer();
     renderer.setToolTipText("Appuyez sur entr�e pour confirmer le nom de l'�v�nement une fois qu'il a �t� �crit");
     nameColumn.setCellRenderer(renderer);*/
	
	}
	
	private Evenement getEvenementSelectionne(){
		Evenement e;
		try{
			e = evenements.getEvenementsRedoutes().get(tableau.getSelectedRow());
		}
		catch(ArrayIndexOutOfBoundsException h){
			e = evenements.getEvenementsRedoutes().get(0);
		}
		return e;
	}
		
		
	
	 
	    
	        
	   
	
	
	    
	
}

