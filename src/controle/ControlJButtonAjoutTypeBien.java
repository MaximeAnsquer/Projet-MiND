package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import abstraction.autres.TypeBien;
import presentation.ModeleTypologieBiensSupports;

public class ControlJButtonAjoutTypeBien implements ActionListener  {
	private ModeleTypologieBiensSupports modele ;
	private JFrame fen ;
	
	public ControlJButtonAjoutTypeBien(ModeleTypologieBiensSupports modele,JFrame fen) {
		this.modele=modele;
		this.fen=fen;
	}

	public void actionPerformed(ActionEvent e) {
		// La modification du modele va entrainer la modif de l'abstraction
		String Id = JOptionPane.showInputDialog("Id du type de bien support ?");

		if (Id != null && !Id.equals("")) {
			String intitule = JOptionPane
					.showInputDialog("Intitulé du type de bien support ?");

			if (intitule != null && !intitule.equals("")) {
				String description = JOptionPane
						.showInputDialog("Description du type de bien support ?");

				if (description != null && !description.equals("")) {
					modele.addTypeBien(new TypeBien(Id, description, intitule,
							true));
				} else {
					JOptionPane.showMessageDialog(this.fen,
							"Le champ description est vide !",
							"Echec de la création du type de bien support",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this.fen,
						"Le champ intitulé est vide !",
						"Echec de la création du type de bien support",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this.fen, "Le champ Id est vide !",
					"Echec de la création du type de bien support",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
