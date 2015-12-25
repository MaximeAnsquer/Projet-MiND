package presentation;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import abstraction.Etude;
import abstraction.modules.AnalyseDesRisques;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.EvenementsRedoutes;
import abstraction.modules.MappingDesBiens;
import abstraction.modules.MatriceDesRisques;
import abstraction.modules.Metriques;
import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;

public class MainMaximeEtienne extends JFrame {
	
	public static Etude etude;

	
	public MainMaximeEtienne(){
		super("Main Maxime Etienne");
		
		etude = new Etude();
		etude.addModule(new CriteresDeSecurite());
		BiensEssentiels be=new BiensEssentiels();
		BiensSupports bs=new BiensSupports();
		etude.addModule(be);
		etude.addModule(new Metriques(etude));
		etude.addModule(new SourcesDeMenaces());
		EvenementsRedoutes ev=new EvenementsRedoutes(etude);
		etude.addModule(ev);
		etude.addModule(new ScenariosDeMenacesTypes());
		etude.addModule(new MappingDesBiens(bs,be));
		AnalyseDesRisques an=new AnalyseDesRisques(etude);
		etude.addModule(an);
		MatriceDesRisques ma=new MatriceDesRisques(etude);
		
		
		
		
		
		
		/*FenetreEvenementsRedoutes f= new FenetreEvenementsRedoutes(ev);*/
		/*FenetreAnalyseDesRisques f=new FenetreAnalyseDesRisques(an);*/
		FenetreMatriceDesRisques f=new FenetreMatriceDesRisques(ma);
		
		this.add(f);
		
		this.setVisible(true);
		this.pack();
		
	}
	public static void main(String[] args){
		
	MainMaximeEtienne main=new MainMaximeEtienne();
}
	
}
