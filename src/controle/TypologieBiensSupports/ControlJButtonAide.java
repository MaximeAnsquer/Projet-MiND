package controle.TypologieBiensSupports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ControlJButtonAide implements ActionListener {
	private JButton aide ;
	private JFrame fen ;
	
	public ControlJButtonAide(JButton aide, JFrame fen) {
		this.aide=aide;
		this.fen=fen;
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane
				.showMessageDialog(
						this.fen,
						"Pour modifier la description, vous pouvez écrire directement dans la zone prévue à cet effet, en haut des boutons ",
						"Aide", JOptionPane.INFORMATION_MESSAGE);
	}

}
