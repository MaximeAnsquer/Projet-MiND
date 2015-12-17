package abstraction.autres;

/**
 * Classe representant les scénarios de menaces typés. Cette classe étend la
 * classe ScenarioGenerique : un scénario typé n'est qu'un cas particulier de
 * scénario générique auquel on ajoute des précisions. La classe représente plus
 * généralement une ligne du tableau généré dans "Scenario De Menaces Typés"
 * 
 * @author Belghiti Ali
 */

public class ScenarioType extends ScenarioGenerique {
	private Bien bienSupport; // Il s'agit d'un bien support ici
	private String intituleConcret;
	private int vraisemblanceIntrinseque;
	private int vraisemblanceReelle;

	public Bien getBienSupport() {
		return bienSupport;
	}

	public void setBienSupport(Bien bienSupport) {
		this.bienSupport = bienSupport;
	}

	public String getIntituleConcret() {
		return intituleConcret;
	}

	public void setIntituleConcret(String intituleConcret) {
		this.intituleConcret = intituleConcret;
	}

	public int getVraisemblanceIntrinseque() {
		return vraisemblanceIntrinseque;
	}

	public void setVraisemblanceIntrinseque(int vraisemblanceIntrinseque) {
		this.vraisemblanceIntrinseque = vraisemblanceIntrinseque;
	}

	public int getVraisemblanceReelle() {
		return vraisemblanceReelle;
	}

	public void setVraisemblanceReelle(int vraisemblanceReelle) {
		this.vraisemblanceReelle = vraisemblanceReelle;
	}

}
