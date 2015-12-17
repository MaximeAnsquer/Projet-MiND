package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controle.ControlJButtonAjoutTypeBien;
import controle.ControlJButtonSuppressionTypeBien;
import abstraction.modules.TypologieDesBiensSupports;

public class FenetreTypologieBiensSupports extends JFrame {

	private TypologieDesBiensSupports moduleCourant;
	private ModeleTypologieBiensSupports modeleTableau ;
	private JTable tableau ;
	private JLabel labelDescription ;
	private JButton supprimerLigne ;
	private JButton ajouterLigne ;

	public FenetreTypologieBiensSupports() {
		super("Typologie des Biens Supports");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		this.getContentPane().setLayout(new BorderLayout());
		
		this.creerTableau();
		this.creerDroite();
		this.creerBas();
		this.pack();
	}

	public void creerTableau() {
		this.modeleTableau=new ModeleTypologieBiensSupports();
		this.moduleCourant=this.modeleTableau.getModuleCourant();
		this.tableau = new JTable(this.modeleTableau);
		
		//tableau.getColumnModel().getColumn(2).setPreferredWidth(1000);
		this.getContentPane().add(tableau.getTableHeader(), BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
	}
	
	public void creerDroite(){
		this.labelDescription= new JLabel("Cliquer sur la description que vous souhaitez afficher");
		this.getContentPane().add(this.labelDescription,BorderLayout.EAST);
	}
	
	public void creerBas() {
		JPanel panelBas = new JPanel() ;
		panelBas.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.ajouterLigne = new JButton("Ajouter un type de bien support");
		
		ControlJButtonAjoutTypeBien ControlAjoutTypeBien = new ControlJButtonAjoutTypeBien(this.modeleTableau);
		this.ajouterLigne.addActionListener(ControlAjoutTypeBien );
		
		this.supprimerLigne = new JButton("Supprimer un type de bien support"); 
		
		ControlJButtonSuppressionTypeBien controlSuppressionTypeBien = new ControlJButtonSuppressionTypeBien(
				modeleTableau, tableau, this.supprimerLigne);                                                 // PAC
		this.moduleCourant.addObserver(controlSuppressionTypeBien);                                           // PAC
		this.supprimerLigne.addActionListener(controlSuppressionTypeBien);                                    // PAC
		
		panelBas.add(ajouterLigne);
		panelBas.add(supprimerLigne);
		this.getContentPane().add(panelBas,BorderLayout.SOUTH);
	}
}
