package abstraction.autres;

/**
 * Classe representant les scénarios de menaces typés. Cette classe étend la
 * classe ScenarioGenerique : un scénario typé n'est qu'un cas particulier de
 * scénario générique auquel on ajoute des précisions. 
 * La classe représente plus généralement une ligne du tableau généré dans
 * "Scenario De Menaces Typés"
 * 
 * @author Belghiti Ali
 */

public class ScenarioType extends ScenarioGenerique {
	private Bien bienSupport ; // Il s'agit d'un bien support ici
	private String intituleConcret ;
	private int vraisemblanceIntrinseque ;
	private int vraisemblanceReelle ;
	
	
}
