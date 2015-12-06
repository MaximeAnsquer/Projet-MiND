package presentation;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTable;

import abstraction.modules.TypologieDesBiensSupports;

public class FenetreTypologieBiensSupports extends JFrame {

	private TypologieDesBiensSupports moduleCourant;

	public FenetreTypologieBiensSupports() {
		super("Typologie des Biens Supports");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.getContentPane().setLayout(new BorderLayout());
		
		this.creerTableau();
		this.pack();
	}

	public void creerTableau() {
		
		Object[][] donnees = {
				{"MAT", "Matériels", "Ce type de biens supports est constitué de l’ensemble des éléments physiques d'un système informatique (hardware) et des supports de données électroniques) participant au stockage et au traitement de tout ou partie des biens essentiels.", "X"}, 
				{"LOG", "Logiciels", "Ce type de biens supports est constitué de l'ensemble des programmes participant au traitement de tout ou partie des biens essentiels (software).", "X"}
		};
		
		String[] titres = {"Id", "Intitulé", "Description", "Retenu"} ;
		
		JTable tableau = new JTable(donnees,titres);
		//tableau.getColumnModel().getColumn(2).setPreferredWidth(1000);
		this.getContentPane().add(tableau.getTableHeader(), BorderLayout.NORTH);
        this.getContentPane().add(tableau, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		FenetreTypologieBiensSupports fen = new FenetreTypologieBiensSupports();
		fen.setVisible(true);
	}
}
