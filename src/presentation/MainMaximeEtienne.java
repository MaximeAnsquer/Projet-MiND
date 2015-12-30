package presentation;

import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.ScenarioType;
import abstraction.autres.SourceDeMenace;
import abstraction.modules.AnalyseDesRisques;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.EvenementsRedoutes;
import abstraction.modules.MappingDesBiens;
import abstraction.modules.MatriceDesRisques;
import abstraction.modules.Metriques;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;

public class MainMaximeEtienne extends JFrame {
	
	public static Etude etude;

	
	public MainMaximeEtienne(){
		super("Main Maxime Etienne");
		
		etude = new Etude("nouvelleetude");
		/*Hashtable<String,Boolean> criteres=new Hashtable<String,Boolean>();
		criteres.put("Confidentialite", true);
		criteres.put("Integrite",true);
		
		
		Hashtable<String,SourceDeMenace> menaces=new Hashtable<String,SourceDeMenace>();
		SourceDeMenace menace=new SourceDeMenace("Moi","ouiooui","tapacompri");
 		menaces.put("Moi",menace);
		
 		
 		BiensSupports bs=new BiensSupports(etude);
		
		Bien bien1=bs.getBien("Disponibilite");
		System.out.println(bien1.getIntitule());
		
		
		ScenarioType sc1=new ScenarioType("couco", "1", "J'ai parlé du projet à un ami", criteres,menaces, bien1,
				 "J'ai parlé du projet et tout à foiré", 1, 2,true) ;
		
		ScenariosDeMenacesTypes sc=new ScenariosDeMenacesTypes();
		sc.getTableau().put("ligne1", sc1);*/
		
		ScenariosDeMenacesTypes sc=new ScenariosDeMenacesTypes();
		etude.addModule(new CriteresDeSecurite());
		BiensEssentiels be=new BiensEssentiels(etude);
		
		etude.addModule(be);
		etude.addModule(new Metriques(etude));
		etude.addModule(new SourcesDeMenaces());
		/*etude.addModule(new ScenariosDeMenacesGeneriques());*/
		EvenementsRedoutes ev=new EvenementsRedoutes(etude);
		etude.addModule(ev);
		etude.addModule(sc);
		MappingDesBiens mapping=new MappingDesBiens(etude);
		mapping.getMappingDesBiens().get(0).setValueAt("x",0);
		mapping.getMappingDesBiens().get(1).setValueAt("x",1);
		/*mapping.getMappingDesBiens().get(2).setValueAt("x",2);*/
		
		
		etude.addModule(mapping);
		AnalyseDesRisques an=new AnalyseDesRisques(etude);
		etude.addModule(an);
		etude.addModule(new ScenariosDeMenacesTypes());
		/*etude.addModule(new MappingDesBiens(bs,be));
		AnalyseDesRisques an=new AnalyseDesRisques(etude);
		etude.addModule(an);
		/*MatriceDesRisques ma=new MatriceDesRisques(etude);*/
		
		
		
		
		
		
		FenetreEvenementsRedoutes f= new FenetreEvenementsRedoutes(ev);
		/*FenetreAnalyseDesRisques f=new FenetreAnalyseDesRisques(an);*/
		/*FenetreMatriceDesRisques f=new FenetreMatriceDesRisques(ma);*/
		/*FenetreAnalyseDesRisques f=new FenetreAnalyseDesRisques(an);*/
		
		this.add(f);
		
		this.setVisible(true);
		this.pack();
		
	}
	public static void main(String[] args){
		
	MainMaximeEtienne main=new MainMaximeEtienne();
}
	
}
