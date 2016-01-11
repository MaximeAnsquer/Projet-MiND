package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableColumn;

import controle.TypologieBiensSupports.ControlJButtonAide;
import controle.TypologieBiensSupports.ControlJButtonAjoutTypeBien;
import controle.TypologieBiensSupports.ControlJButtonSuppressionTypeBien;
import controle.TypologieBiensSupports.ControlJTable;
import controle.TypologieBiensSupports.ControlJTextArea;
import abstraction.modules.TypologieDesBiensSupports;

public class FenetreTypologieBiensSupports extends JPanel {

	private TypologieDesBiensSupports moduleCourant;
	private ModeleTypologieBiensSupports modeleTableau ;
	private JTable tableau ;
	private JTextArea zoneDescription ;
	private JButton supprimerLigne ;
	private JButton ajouterLigne ;
	private JButton aide ;

	public FenetreTypologieBiensSupports(TypologieDesBiensSupports module) {
		
		this.moduleCourant=module;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.creerTableau();
		this.creerCouleurs();
		this.creerZoneDescription();
		this.creerBoutonsBas();
	}

	public void creerTableau() {
		this.modeleTableau=new ModeleTypologieBiensSupports(this.moduleCourant);
		this.tableau = new JTable(this.modeleTableau);
		
		ControlJTable control = new ControlJTable(modeleTableau, tableau); // PAC
		this.tableau.addMouseListener(control);                            // PAC
		
		//tableau.getColumnModel().getColumn(2).setPreferredWidth(1000);
		this.add(tableau.getTableHeader());
        this.add(new JScrollPane(tableau));
	}
	
	public void creerCouleurs(){
		TableColumn colonneId = this.tableau.getColumnModel().getColumn(ModeleTypologieBiensSupports.COLONNE_ID);
		CellRendererTypesBiens rendererTypeBien = new CellRendererTypesBiens();
		colonneId.setCellRenderer(rendererTypeBien);
	}
	
	public void creerZoneDescription(){
		JLabel label = new JLabel("");
		this.zoneDescription= new JTextArea("Cliquer sur la description que vous souhaitez afficher");
		this.zoneDescription.setLineWrap(true); // On passe à la ligne 
		this.zoneDescription.setWrapStyleWord(true);
		
		ControlJTextArea controlTextArea = new ControlJTextArea(modeleTableau, tableau, zoneDescription); // PAC
		this.moduleCourant.addObserver(controlTextArea);                                                  // PAC
		this.zoneDescription.addKeyListener(controlTextArea);
		
		JScrollPane areaScrollPane = new JScrollPane(this.zoneDescription);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(400, 150));
		areaScrollPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder("Description du type de bien support"),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								areaScrollPane.getBorder()));
		
		this.add(label);
		this.add(areaScrollPane);
	}
	
	public void creerBoutonsBas() {
		JPanel panelBas = new JPanel() ;
		panelBas.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.ajouterLigne = new JButton("Ajouter un type de bien support");
		
		ControlJButtonAjoutTypeBien ControlAjoutTypeBien = new ControlJButtonAjoutTypeBien(this.modeleTableau,null);
		this.ajouterLigne.addActionListener(ControlAjoutTypeBien );
		
		this.supprimerLigne = new JButton("Supprimer un type de bien support"); 
		
		ControlJButtonSuppressionTypeBien controlSuppressionTypeBien = new ControlJButtonSuppressionTypeBien(
				modeleTableau, tableau, this.supprimerLigne);                                                 // PAC
		this.moduleCourant.addObserver(controlSuppressionTypeBien);                                           // PAC
		this.supprimerLigne.addActionListener(controlSuppressionTypeBien);                                    // PAC
		
		this.aide = new JButton("?");
		
		ControlJButtonAide controlAide = new ControlJButtonAide(
				this.aide);
		this.aide.addActionListener(controlAide);
		
		panelBas.add(ajouterLigne);
		panelBas.add(supprimerLigne);
		panelBas.add(aide);
		this.add(panelBas);
	}
}
