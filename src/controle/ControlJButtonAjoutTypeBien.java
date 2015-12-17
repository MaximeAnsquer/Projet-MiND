package controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import abstraction.autres.TypeBien;
import presentation.ModeleTypologieBiensSupports;

public class ControlJButtonAjoutTypeBien implements ActionListener  {
	private ModeleTypologieBiensSupports modele ;
	
	public ControlJButtonAjoutTypeBien(ModeleTypologieBiensSupports modele) {
		this.modele=modele;
	}

	public void actionPerformed(ActionEvent e) {
		// La modification du modele va entrainer la modif de l'abstraction
		modele.addTypeBien(new TypeBien("yo", "pour le style", "wesh", true));
	}

}
