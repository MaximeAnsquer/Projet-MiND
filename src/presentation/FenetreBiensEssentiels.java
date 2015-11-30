package presentation;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTable;

public class FenetreBiensEssentiels extends JFrame{

	private static final long serialVersionUID = 1L;

	public FenetreBiensEssentiels(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		JTable table = new JTable(2,3);
		table.setValueAt("Intitulé", 0, 0);
		table.setValueAt("Description", 0, 1);
		table.setValueAt("Retenu", 0, 2);
		table.setValueAt("", 1, 0);
		table.setValueAt("", 1, 1);
		table.setValueAt("X", 1, 2);
		table.setDefaultRenderer(Object.class, new JTableRender());
		table.setRowHeight(0, 75);
		table.setRowHeight(1, 200);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		 
		contentPane.add(table, BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args) {
		FenetreBiensEssentiels fenetre = new FenetreBiensEssentiels();
	}
}
