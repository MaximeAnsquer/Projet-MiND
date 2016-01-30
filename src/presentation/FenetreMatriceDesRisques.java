package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;



import abstraction.modules.MatriceDesRisques;
import controle.TypologieBiensSupports.ControlJTextArea;

public class FenetreMatriceDesRisques extends JPanel{
	
	private JTable tableau;
	private ModeleMatriceDesRisques modele;
	private MatriceDesRisques matrice;
	private JTextArea zoneDescription;
	
	public FenetreMatriceDesRisques(MatriceDesRisques matrice){
		
		
this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	
		
		
		
this.modele=new ModeleMatriceDesRisques(matrice);		
		
		this.matrice=matrice;

		
		this.tableau=new JTable(modele);
		System.out.println(this.tableau.getColumnCount());
		
		tableau.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tableau.setFillsViewportHeight(true);
		
        JScrollPane scrollPane = new JScrollPane(tableau);
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//this.add(partieDuHaut(), BorderLayout.NORTH);
		
		add(scrollPane,BorderLayout.NORTH);
		
		tableau.setDefaultRenderer(Object.class, new MonTableCellRenderer());
		
		
		DefaultTableCellRenderer headerRenderer1 = new DefaultTableCellRenderer();
		Color clr1=Color.green;
		headerRenderer1.setBackground(clr1);
		
		tableau.getColumnModel().getColumn(1).setHeaderRenderer(headerRenderer1);
		

		if(tableau.getModel().getColumnCount()>=3){DefaultTableCellRenderer headerRenderer2= new DefaultTableCellRenderer();
		Color clr2=Color.yellow;
		headerRenderer2.setBackground(clr2);
		
		tableau.getColumnModel().getColumn(2).setHeaderRenderer(headerRenderer2);
		}
		
		if(tableau.getModel().getColumnCount()>=4){ DefaultTableCellRenderer headerRenderer3= new DefaultTableCellRenderer();
		Color clr3=Color.orange;
		headerRenderer3.setBackground(clr3);
		
		tableau.getColumnModel().getColumn(3).setHeaderRenderer(headerRenderer3);
		}
		
		if(tableau.getModel().getColumnCount()>=5){DefaultTableCellRenderer headerRenderer4= new DefaultTableCellRenderer();
		Color clr4=Color.red;
		headerRenderer4.setBackground(clr4);
		
		tableau.getColumnModel().getColumn(4).setHeaderRenderer(headerRenderer4);
		}
		
		
		if(tableau.getModel().getColumnCount()>=6){DefaultTableCellRenderer headerRenderer5= new DefaultTableCellRenderer();
		Color clr5=Color.magenta;
		headerRenderer5.setBackground(clr5);
		
		tableau.getColumnModel().getColumn(5).setHeaderRenderer(headerRenderer5);
		}
		
		if (tableau.getModel().getColumnCount()>6){
			for (int i=6;i<tableau.getModel().getColumnCount();i++){
				DefaultTableCellRenderer headerRenderer6= new DefaultTableCellRenderer();
				Color clr6=new Color(150, 0, 150);
				headerRenderer6.setBackground(clr6);
				
				tableau.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer6);
			}
		}
		
		
		
		this.creerZoneDescription();
		
		tableau.setRowHeight(50);
		
		
		tableau.setFont(new Font("Arial", Font.PLAIN, 15));
		
		
		
		this.setVisible(true);
	
	
	}
	
	public void creerZoneDescription(){
		JLabel label = new JLabel("");
		this.zoneDescription= new JTextArea("Cliquez sur la cellule que vous souhaitez afficher");
		
		Font font=new Font("Verdana",Font.BOLD,12);
		this.zoneDescription.setFont(font);
		
		this.zoneDescription.setLineWrap(true); // On passe à la ligne 
		this.zoneDescription.setWrapStyleWord(true);
		
		tableau.addMouseListener(new MouseAdapter() {
			  public void mouseClicked(MouseEvent e) {
			    
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
			      int column = target.getSelectedColumn();
			     
			      zoneDescription.setText((String)tableau.getValueAt(row, column));
			    
			  }
			});
		
		JScrollPane areaScrollPane = new JScrollPane(this.zoneDescription);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(200, 100));
		areaScrollPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder("Détail des risques contenus dans la cellule"),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								areaScrollPane.getBorder()));
		
		this.add(label,BorderLayout.SOUTH);
		this.add(areaScrollPane,BorderLayout.SOUTH);
	}
	
	private JButton boutonModifierCellule() {
		 final JButton bouton = new JButton("Modifier la couleur de la cellule");
		bouton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				((FenetreMatriceDesRisques)bouton.getParent().getParent()).ChoixCouleur();
				
			}
			});
		
		return bouton;
	}
	private JColorChooser ChoixCouleur(){
		JColorChooser choix=new JColorChooser();
		Color couleur=choix.showDialog(this, "Choix couleur cellule", tableau.getSelectionBackground());
		if(couleur!=null){
			
			tableau.setSelectionBackground(couleur);
			
		}
		return choix;
		}
	
	
	
	private JPanel partieDuHaut() {
		JPanel jpanel = new JPanel();
		jpanel.setPreferredSize(new Dimension(50,150));
		jpanel.add(boutonModifierCellule());
		
		//		jpanel.add(boutonModifierDescription());
		return jpanel;
	}
	 
}
