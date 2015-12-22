package presentation;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class BarreMenu extends JMenuBar {

	public BarreMenu(){
		super();
		JMenu fichier = new JMenu("Fichier");
		JMenuItem nouveau = new JMenuItem("Nouveau");
		nouveau.setMnemonic('n');
		fichier.add(nouveau);
		JMenuItem enregistrer = new JMenuItem("Enregistrer");
		fichier.add(enregistrer);
		fichier.addSeparator();
		JMenu preferences = new JMenu("Préférences");
		preferences.add(new JMenuItem("Config. éditeur"));
		preferences.add(new JCheckBoxMenuItem("Son"));
		JRadioButtonMenuItem b219 = new JRadioButtonMenuItem("21:9");
		preferences.add(b219);
		JRadioButtonMenuItem b169 = new JRadioButtonMenuItem("16:9");
		preferences.add(b169);
		JRadioButtonMenuItem b43 = new JRadioButtonMenuItem("4:3");
		preferences.add(b43);
		ButtonGroup groupeFormat = new ButtonGroup();
		groupeFormat.add(b219);
		groupeFormat.add(b169);
		groupeFormat.add(b43);
		fichier.add(preferences);
		this.add(fichier);
	}
	
}
