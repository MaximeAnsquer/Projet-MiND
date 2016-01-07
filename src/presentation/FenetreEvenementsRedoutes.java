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
	/*Cette classe fait appel aux variables d'instance suivantes pour afficher le tableau des Evenements Redoutés
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
		 /* On recrée un module pour être sur que les données sont à jour*/
		 
		EvenementsRedoutes nouveauev=new EvenementsRedoutes(evenements.getEtude());
		
		nouveauev.setCree(true);
		
		/*La boucle va servir à mettre à conserver les valeurs modifiées de l'ancien tableau et de les mettre
		 * dans le nouveau
		 */
		
		int a=nouveauev.getEvenementsRedoutes().size();
		int b=evenements.getEvenementsRedoutes().size();
		
		for (int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				String biennouveau=nouveauev.getEvenementsRedoutes().get(i).GetNomBien();
				String bienancien=evenements.getEvenementsRedoutes().get(j).GetNomBien();
				
				String nouveaucrit=nouveauev.getEvenementsRedoutes().get(i).GetCritere();
				String anciencrit=evenements.getEvenementsRedoutes().get(j).GetCritere();
				
				if(anciencrit==nouveaucrit&&biennouveau==bienancien){
					
					nouveauev.getEvenementsRedoutes().get(i).setNomEvenement(evenements.getEvenementsRedoutes().get(j).GetNomEvenement());
					nouveauev.getEvenementsRedoutes().get(i).setNiveauExigence(evenements.getEvenementsRedoutes().get(j).getNiveauExigence());
					nouveauev.getEvenementsRedoutes().get(i).setNiveauGravite(evenements.getEvenementsRedoutes().get(j).getNiveauGravite());
					if(evenements.getEvenementsRedoutes().get(0).getNomGroupes()!=null){
						for(int k=0;k<evenements.getEvenementsRedoutes().get(0).getNomGroupes().size();k++){
						nouveauev.getEvenementsRedoutes().get(i).getContenuGroupes().addLast(evenements.getEvenementsRedoutes().get(j).getContenuGroupes().get(k));
						}
					}
					
				}
				
			}
		}
		
		evenements=nouveauev;
		evenements.getEtude().addModule(nouveauev);
		
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
		
		tableau.setDefaultRenderer(Object.class, new CellRendererEv());
		
		this.setVisible(true);
		
		
	}
	
	
	/*Methode qui permet de construire le tableau ainsi que les Jcombobox associées à la Gravité et à l'Exigence*/
	
	public void setUpComBo() {
 
		
		this.tableau=new JTableX(modele);
		tableau.setRowSelectionAllowed(false);
        tableau.setColumnSelectionAllowed(false);
		
		RowEditorModel rm = new RowEditorModel();
		
		tableau.setRowEditorModel(rm);
	
	/*On construit la JCombobox associée à l'exigence de chaque critère du tableau*/
		
		int u=this.criteres.nombreDeCriteres();
		
		
		for(int i=0;i<u;i++){
			
			ArrayList<Critere> tableaucriteres=new ArrayList<Critere>(this.criteres.getCriteresRetenus().values());
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
       
		/*Puis on construit la JCombobox associée à la Gravité*/
		
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
	
	TableColumn nameColumn =this.tableau.getColumnModel().getColumn(modele.getColumnCount()-5);
	 DefaultTableCellRenderer renderer =
             new DefaultTableCellRenderer();
     renderer.setToolTipText("Appuyez sur entrée pour confirmer le nom de l'évènement une fois qu'il a été écrit");
     nameColumn.setCellRenderer(renderer);
	
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

