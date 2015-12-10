package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import abstraction.modules.TypologieDesBiensSupports;

public class FenetreTypologieBiensSupports extends JFrame {

	private TypologieDesBiensSupports moduleCourant;
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
		
		Object[][] donnees = {
				{"MAT", "Matériels", "Ce type de biens supports est constitué de l’ensemble des éléments physiques d'un système informatique (hardware) et des supports de données électroniques) participant au stockage et au traitement de tout ou partie des biens essentiels.", "X"}, 
				{"LOG", "Logiciels", "Ce type de biens supports est constitué de l'ensemble des programmes participant au traitement de tout ou partie des biens essentiels (software).", "X"}
		};
		
		String[] titres = {"Id", "Intitulé", "Description", "Retenu"} ;
		
		JTable tableau = new JTable(donnees,titres);
		// tableau.getColumnModel().getColumn(2).setPreferredWidth(1000);
		this.getContentPane().add(tableau.getTableHeader(), BorderLayout.NORTH);
        this.getContentPane().add(tableau, BorderLayout.CENTER);
	}
	
	public void creerDroite(){
		this.labelDescription= new JLabel("Cliquer sur la description que vous souhaitez afficher");
		this.getContentPane().add(this.labelDescription,BorderLayout.EAST);
	}
	
	public void creerBas() {
		JPanel panelBas = new JPanel() ;
		panelBas.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.ajouterLigne = new JButton("Ajout d'un type de bien support");
		this.supprimerLigne = new JButton("Suppression d'un type de bien support"); 
		
		panelBas.add(ajouterLigne);
		panelBas.add(supprimerLigne);
		this.getContentPane().add(panelBas,BorderLayout.SOUTH);
	}
}
