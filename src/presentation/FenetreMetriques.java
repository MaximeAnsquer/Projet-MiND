package presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import presentation.FenetreCriteresDeSecurite2.ModeleDynamiqueObjet;

import abstraction.Etude;
import abstraction.autres.Metrique;
import abstraction.autres.NiveauDeMetrique;
import abstraction.modules.Metriques;

/**
 * Source : http://baptiste-wicht.developpez.com/tutoriels/java/swing/jtable/
 * @author Maxime Ansquer
 *
 */
public class FenetreMetriques extends JFrame {

	private ArrayList<JPanel> lesTableaux = new ArrayList<JPanel>();
	private JComboBox comboBox;
	private JPanel metriqueEnCours;
	private Etude etude = MainMaximeAnsquer.etude;
	private Metriques metriques = (Metriques) etude.getModule("Metriques");
	private Metrique metriqueCourante;
	private JPanel jpanel = new JPanel(new BorderLayout());
	JTable table;
	

	public FenetreMetriques(){
		super("Metriques");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		int nombreDeMetriques = metriques.nombreDeMetriques();
		Container contentPane = this.getContentPane();
				
				
		/**
		 * On ajoute la comboBox et les boutons
		 */
		contentPane.add(partieDuBas(), BorderLayout.SOUTH);		
		
		/**
		 * On affiche la metrique courante
		 */
		contentPane.add(jpanel);
		setTableau();
		
		pack();
	}

	private void setTableau() {
		jpanel.removeAll();
		metriqueCourante = getMetriqueCourante();
		ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet(metriqueCourante);
		table = new JTable(modele);
		jpanel.add(new JLabel(metriqueCourante.getIntitule()), BorderLayout.NORTH);		
		jpanel.add(new JScrollPane(table), BorderLayout.CENTER);
		jpanel.validate();
	}

	private Metrique getMetriqueCourante() {
		Metrique m = metriques.getMetrique(  ((Metrique) comboBox.getSelectedItem()).getCritere().getIntitule()   );
		return m;
	}

	private JPanel partieDuBas() {
		JPanel jp = new JPanel();
		jp.add(comboBox());
		jp.add(boutonAjouter());
		jp.add(boutonSupprimer());
		return jp;
	}

	private JButton boutonSupprimer() {
		JButton bouton = new JButton("Supprimer un niveau");
		bouton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int niveauSelectionne = table.getSelectedRow();
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.supprimerNiveau(niveauSelectionne);				
			}
		});
		return bouton;
	}

	private JButton boutonAjouter() {
		JButton bouton = new JButton("Ajouter un niveau");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				ModeleDynamiqueObjet modele = (ModeleDynamiqueObjet) table.getModel();
				modele.ajouterNiveau();				
			}
			
		});
		return bouton;
	}

	private JComboBox comboBox() {
		comboBox = new JComboBox(metriques.getLesMetriques().values().toArray());
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setTableau();	
			}
			
		});
		return comboBox;
	}

	private JPanel tableau(JTable table, String intitule) {
		JPanel j = new JPanel();
		j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
		j.add(new JLabel(intitule));
		j.add(new JScrollPane(table));
		return j;
	}

	class ModeleDynamiqueObjet extends AbstractTableModel {

		private final String[] entetes = {"#", "Intitulé", "Description"};
		private Metrique metrique;

		public ModeleDynamiqueObjet(Metrique metrique) {
			super();
			this.metrique = metrique;
		}

		public int getRowCount() {
			return metrique.nombreDeNiveaux();
		}

		public int getColumnCount() {
			return entetes.length;
		}

		public String getColumnName(int columnIndex) {
			return entetes[columnIndex];
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex){
			case 0:
				return metrique.getNiveau(rowIndex).getNumero();
			case 1:
				return metrique.getNiveau(rowIndex).getIntitule();
			case 2:
				return metrique.getNiveau(rowIndex).getDescription();
			default:
				return null; 
			}
		}

		public boolean isCellEditable(int row, int col){
			return true; 
		}

		public Class getColumnClass(int columnIndex){
			switch(columnIndex){
			case 0:
				return Integer.class;
			default:
				return String.class; 
			}
		}
		
		public void ajouterNiveau() {
			String numero = JOptionPane.showInputDialog("# ?");
			String Intitule = JOptionPane.showInputDialog("Intitule ?");
			String Description = JOptionPane.showInputDialog("Description ?");
			NiveauDeMetrique niveau = new NiveauDeMetrique(Integer.parseInt(numero), Intitule, Description);
			getMetriqueCourante().ajouterNiveau(niveau);

			fireTableRowsInserted(getMetriqueCourante().nombreDeNiveaux() -1, getMetriqueCourante().nombreDeNiveaux() -1);
		}

		public void supprimerNiveau(int rowIndex) {
			getMetriqueCourante().supprimerNiveau(rowIndex);

			fireTableRowsDeleted(rowIndex, rowIndex);
		}
		
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		    if(aValue != null){
		        NiveauDeMetrique niveau = metrique.getNiveau(rowIndex);
		 
		        switch(columnIndex){
		            case 0:
		            	niveau.setNumero((Integer)aValue);
		                break;
		            case 1:
		            	niveau.setIntitule((String)aValue);
		                break;
		            case 2:
		            	niveau.setDescription((String)aValue);
		                break;
		        }
		    }
		}
	}



}
