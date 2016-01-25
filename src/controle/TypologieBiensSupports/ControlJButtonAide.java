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
						"1) Pour modifier la description, vous pouvez écrire directement dans la zone prévue à cet effet, en haut des boutons"
								+ "\n2) Double-cliquez sur une cellule pour modifier le champ de texte "
								+ "\n3) Une case vide a une couleur jaune",
						"Aide", JOptionPane.INFORMATION_MESSAGE);
	}

}
