package presentation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

import abstraction.Etude;
import abstraction.autres.Critere;
import abstraction.modules.CriteresDeSecurite;

/**
 * 
 * @author Maxime Ansquer
 *
 */
public class FenetreCriteresDeSecurite extends JFrame {
	
	private JTable table;;	
	private Etude etude = Main.etude;
	private CriteresDeSecurite cds = (CriteresDeSecurite) etude.getModule("CriteresDeSecurite"); 
	private Object[][] data;
	
	public FenetreCriteresDeSecurite(){
		super("Criteres de securite");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		Container contentPane = this.getContentPane();
		
		this.updateData();
		
		String[] columnNames = {"Id", "Intitulé", "Description", "Retenu"};
		
		table = new JTable(new MonModel(data, columnNames));
		table.setFillsViewportHeight(true);  //je sais pas ce que ca fait mais apparemment c'est cool
		
		contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
		contentPane.add(partieDuBas(), BorderLayout.SOUTH);
		contentPane.add(test(), BorderLayout.EAST);
		
		this.pack();
	}
	
	/**
	 * Inspire d'un exemple de la Javadoc sur les JTextArea
	 * @return
	 */
	private JScrollPane test() {
		JTextArea textArea = new JTextArea(
                "Cette zone servirait a afficher la description"
                + " du critere selectionne. Qu'en dites-vous ?"
                + " On enleverait alors la colonne \" description \" dans"
                + " la JTable de gauche."
        );
        textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(400, 250));
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Description du critere"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));
        return areaScrollPane;
	}

	private JPanel partieDuBas() {
		JPanel jp = new JPanel();
		jp.add(boutonAjouter());
		jp.add(boutonSupprimer());
		return jp;
	}

	private JButton boutonSupprimer() {
		JButton b = new JButton("Supprimer le critere selectionne");
		return b;
	}

	private JButton boutonAjouter() {
		JButton b = new JButton("Ajouter un nouveau critere");
		return b;
	}

	/**
	 * Actualise la variable data conformement aux donnes du package abstraction 
	 */
	private void updateData() {
		this.data = new Object[cds.getLesCriteres().size()][4];
		int numeroLigne=0;
		for(Critere c : cds.getLesCriteres().values()){
			data[numeroLigne][0] = c.getId();
			data[numeroLigne][1] = c.getIntitule();
			data[numeroLigne][2] = c.getDescription();
			data[numeroLigne][3] = c.isRetenu();			
			numeroLigne++;
		}
		
	}

	//Classe modèle personnalisée
	  class MonModel extends AbstractTableModel{
	    private Object[][] data;
	    private String[] title;

	    //Constructeur
	    public MonModel(Object[][] data, String[] title){
	      this.data = data;
	      this.title = title;
	    }

	    //Retourne le nombre de colonnes
	    public int getColumnCount() {
	      return this.title.length;
	    }

	    //Retourne le nombre de lignes
	    public int getRowCount() {
	      return this.data.length;
	    }

	    //Retourne la valeur à l'emplacement spécifié
	    public Object getValueAt(int row, int col) {
	      return this.data[row][col];
	    }  
	    
	    //Retourne le titre de la colonne à l'indice spécifié
	    public String getColumnName(int col) {
	      return this.title[col];
	    }
	    
	    //Retourne la classe de la donnée de la colonne
	    public Class getColumnClass(int col){
	      return this.data[0][col].getClass();
	    }	
	    
	    //Retourne vrai si la cellule est éditable : celle-ci sera donc éditable
	    public boolean isCellEditable(int row, int col){
	      return true; 
	    }
	    
	  }

}
