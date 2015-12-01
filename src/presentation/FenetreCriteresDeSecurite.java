package presentation;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import abstraction.Etude;
import abstraction.autres.Critere;
import abstraction.modules.CriteresDeSecurite;

public class FenetreCriteresDeSecurite extends JFrame {
	
	private JTable table;;	
	private Etude etude = Main.etude;
	private CriteresDeSecurite cds = (CriteresDeSecurite) etude.getModule("CriteresDeSecurite"); 
	
	public FenetreCriteresDeSecurite(){
		super("Fenetre de test");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		Container contentPane = this.getContentPane();
		
		Object[][] data = new Object[cds.getLesCriteres().size()][100];
		int numeroLigne=0;
		for(Critere c : cds.getLesCriteres().values()){
			data[numeroLigne][0] = c.getId();
			data[numeroLigne][1] = c.getIntitule();
			data[numeroLigne][2] = c.getDescription();
			data[numeroLigne][3] = c.isRetenu();			
			numeroLigne++;
		}
		
		Object[] columnNames = {"Id", "Intitulé", "Description", "Retenu"};
		
		table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		contentPane.add(scrollPane);
		
		this.pack();
	}

	

}
