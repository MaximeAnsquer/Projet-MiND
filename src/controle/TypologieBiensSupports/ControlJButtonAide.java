package controle.TypologieBiensSupports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ControlJButtonAide implements ActionListener {
	private JButton aide;

	public ControlJButtonAide(JButton aide) {
		this.aide = aide;
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane
				.showMessageDialog(
						null,
						"Pour modifier la description, vous pouvez écrire directement dans la zone prévue à cet effet, en haut des boutons"
						+ "\nDouble-cliquez sur une cellule pour modifier le champ de texte ",
						"Aide", JOptionPane.INFORMATION_MESSAGE);
	}

}
