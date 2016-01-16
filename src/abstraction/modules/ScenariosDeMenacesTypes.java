package abstraction.modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import abstraction.Etude;
import abstraction.autres.Bien;
import abstraction.autres.ScenarioGenerique;
import abstraction.autres.ScenarioType;

public class ScenariosDeMenacesTypes extends Module {

	// Represente la bdc
	private static ArrayList<ScenarioType> bdcScenariosMenacesTypes;

	// Variable d'instance
	ArrayList<ScenarioType> tableau;
	private ScenarioType scenarioTypeCourant ;
	// On liste les critères de sécurité retenus dans le module Scénarios de menaces génériques
	private ArrayList<String> nomColonneSup ;
	private Hashtable<String, Bien> biensRetenus ;
	private Hashtable<String, Bien> biensModifies ;

	public ScenariosDeMenacesTypes(Etude etude) {
		super("ScenariosDeMenacesTypes");
		this.etude=etude;
		this.biensRetenus=new Hashtable<String,Bien>();
		this.biensModifies= new Hashtable<String,Bien>();
		
		this.tableau = new ArrayList<ScenarioType>();
		this.nomColonneSup=new ArrayList<String>();
		
		this.predecesseurs.add(this.getEtude().getModule("ScenariosDeMenacesGeneriques"));
		this.predecesseurs.add(this.getEtude().getModule("BiensSupports"));
		this.predecesseurs.add(this.getEtude().getModule("Metriques"));
		this.predecesseurs.add(this.getEtude().getModule("SourcesDeMenaces"));
		
		// this.successeurs.add(this.getEtude().getModule("AnalyseDesRisques"));
		this.scenarioTypeCourant=new ScenarioType();
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
		this.importerBDC();
	}
	
	public int getSize(){
		return this.tableau.size();
	}
	
	public ScenarioType getScenarioTypeCourant() {
		return scenarioTypeCourant;
	}

	public void setScenarioTypeCourant(ScenarioType scenarioTypeCourant) {
		this.scenarioTypeCourant = scenarioTypeCourant;
		this.setChanged();         // PAC
		this.notifyObservers();    // PAC
	}

	public ArrayList<String> getNomColonneSup() {
		return nomColonneSup;
	}

	public void setNomColonneSup(ArrayList<String> nomColonneSup) {
		this.nomColonneSup = nomColonneSup;
	}

	public ArrayList<ScenarioType> getTableau() {
		return tableau;
	}

	public void setTableau(ArrayList<ScenarioType> tableau) {
		this.tableau = tableau;
	}
	
	public Hashtable<String, Bien> getBiensRetenus() {
		return biensRetenus;
	}

	public void setBiensRetenus(Hashtable<String, Bien> biensRetenus) {
		this.biensRetenus = biensRetenus;
	}
	
	public ScenarioType getScenarioType (int i){
		return this.tableau.get(i);
	}
	
	public void addScenarioType(ScenarioType scenario,int indiceInsertion){
		this.tableau.add(indiceInsertion,scenario);
	}
	
	public void removeScenariosTypes(Integer index){
		this.tableau.remove(index);
	}

	public static ArrayList<ScenarioType> getBDC() {
		return bdcScenariosMenacesTypes;
	}
	
	public ArrayList<ScenarioType> getScenariosTypesRetenus() {
		ArrayList<ScenarioType> resultat = new ArrayList<ScenarioType>();
		for (ScenarioType scenario : this.getTableau()) {
			if (scenario.isRetenu()) {
				resultat.add(scenario);
			}
		}
		return resultat;
	}
	
	// Vérifie si le Bien b est présent dans le tableau
	public Boolean contientBien(Bien b){
		Boolean resultat = false ;
		for (ScenarioType scenario : this.tableau) {
			if (scenario.getBienSupport().getIntitule().equals(b.getIntitule())) {
				resultat=true;
			}
		}
		return resultat ;
	}
	
	// Vérifie si le Bien b est retenu dans le module BiensEssentiels
	public Boolean estRetenu (Bien b){
		Boolean res = false ;
		BiensSupports biensSupports = (BiensSupports) this.etude.getModule("BiensSupports");
		for (Bien bien : biensSupports.getBiensRetenus()){
			if (bien.getIntitule().equals(b.getIntitule())){
				res=true;
			}
		}
		return res;
	}
	
	
	// Retourne le premier et le dernier indice du tableau où les scenarios
	// types ont pour bien b
	public ArrayList<Integer> getIndicesBien (Bien b){
		ArrayList<Integer> listeIndices = new ArrayList<Integer>();
		for (int i = 0 ; i<this.tableau.size() ; i++){
			if (this.tableau.get(i).getBienSupport().equals(b)){
				listeIndices.add(i);
			}
		}
		return listeIndices ;
	}
	
	// Suppression des scenarios types dont le Bien Support n'est plus retenu
	public void supprimerScenariosTypes() {
		int j = 0;
		while (j < this.tableau.size() && this.tableau.get(j) != null) {
			while (j < this.tableau.size() && !estRetenu(this.tableau.get(j).getBienSupport())) {
				ScenarioType scenario = this.tableau.get(j);
				this.biensRetenus.remove(this.tableau.get(j).getBienSupport().getIntitule());
				this.tableau.remove(scenario);
			}
			j++;
		}
	}
	
	// Suppression des scenarios types dont le type de Bien dans l'onglet Bien
	// support n'est pas celui du scénario générique
	public void coherenceTypologie() {
		int j = 0;
		while (j < this.tableau.size() && this.tableau.get(j) != null) {
			while (j < this.tableau.size()
					&& !this.tableau
							.get(j)
							.getTypeBienSupport()
							.contains(
									this.tableau.get(j).getBienSupport()
											.getType())) {
				this.tableau.remove(this.tableau.get(j));
			}
			j++;
		}
	}
	
	public void biensNonCoherent() {
		for (ScenarioType scenario : this.tableau) {
			if (!scenario.getTypeBienSupport().contains(
					scenario.getBienSupport().getType())) {
				this.biensModifies.put(scenario.getBienSupport().getType(),scenario.getBienSupport());
			}
		}
	}
	
	public void actualiserDonnees() {

		ScenariosDeMenacesGeneriques moduleScenarioGene = (ScenariosDeMenacesGeneriques) this.etude
				.getModule("ScenariosDeMenacesGeneriques");
		
		SourcesDeMenaces SourcesDeMenaces = (SourcesDeMenaces) this.etude.getModule("SourcesDeMenaces");
		
		// Actualisation des types de biens supports que l'on modifie dans biens supports
		this.biensNonCoherent();
		this.coherenceTypologie();
		for (Bien b : this.biensModifies.values()) {
			for (ScenarioGenerique sGene : moduleScenarioGene.getTableau()) {
				ScenarioType scenario = new ScenarioType(
						sGene.getTypeBienSupport(), sGene.getId(),
						sGene.getIntitule(), sGene.getCriteresSup(),
						SourcesDeMenaces.getSourcesDeMenacesRetenues(), null,
						true);
				if (sGene.getTypeBienSupport().contains(b.getType())
						&& scenario.getBienSupport() == null) {
					scenario.setBienSupport(b);
					this.tableau.add(scenario);
				}
			}
		}
		// On réinitialise la liste des biens modifiés
		this.biensModifies= new Hashtable<String, Bien>();
		
		// On actualise les sources de menaces retenus dans l'onglet correspondant
		SourcesDeMenaces sourcesDeMenaces = (SourcesDeMenaces) this.etude.getModule("SourcesDeMenaces");
		for (ScenarioType scenario : this.tableau){
			scenario.setMenaces(sourcesDeMenaces.getSourcesDeMenacesRetenues());
		}
	}
	
	public void importerDonnees() {
		System.out.println("ENTREE DANS LA METHODE importDonnees");
		
		BiensSupports biensSupports = (BiensSupports) this.etude.getModule("BiensSupports");
		ScenariosDeMenacesGeneriques scenarioDeMenacesGeneriques = (ScenariosDeMenacesGeneriques) this.etude
				.getModule("ScenariosDeMenacesGeneriques");
		SourcesDeMenaces sourcesDeMenaces = (SourcesDeMenaces) this.etude.getModule("SourcesDeMenaces");
		
		// Cas où on remplit le tableau pour la première fois
		if (this.tableau.size() == 0) {
			System.out.println("ENTREE DANS Le 1er cas");
			this.importerBDC();
		}
		// On actualise les valeurs du tableau
		else {
			if (this.biensRetenus.size() < biensSupports
					.getBiensRetenus().size()) {
				System.out.println("Cas intermediaire");
				for (Bien b : biensSupports.getBiensRetenus()) {
					if (!this.contientBien(b)) {
						for (ScenarioGenerique sGene : scenarioDeMenacesGeneriques
								.getScenariosGeneriquesRetenus()) {
							ScenarioType scenario = new ScenarioType(
									sGene.getTypeBienSupport(), sGene.getId(),
									sGene.getIntitule(),
									sGene.getCriteresSup(),
									sourcesDeMenaces.getSourcesDeMenacesRetenues(),
									null, true);
							if (sGene.getTypeBienSupport()
									.contains(b.getType())
									&& scenario.getBienSupport() == null) {
								scenario.setBienSupport(b);
								scenario.setTypeBienSupport(b.getType());
								this.biensRetenus.put(b.getIntitule(), b);
								this.tableau.add(scenario);
								System.out.println("scénario ajouté");
								System.out.println(tableau.size());
							}
						}
					}
				}
			}
			// Sinon on supprime les scénarios correspondants aux biens qui 
			// ne sont plus retenus
			else {
				this.supprimerScenariosTypes();
				System.out.println("Dernier cas !");
			}
		}
	}

	public void importerBDC() {
		bdcScenariosMenacesTypes = new ArrayList<ScenarioType>();
		
		BiensSupports biensSupports = (BiensSupports) this.etude.getModule("BiensSupports");
		
		ScenariosDeMenacesGeneriques moduleScenarioGene = (ScenariosDeMenacesGeneriques) this.etude
				.getModule("ScenariosDeMenacesGeneriques");
		
		SourcesDeMenaces SourcesDeMenaces = (SourcesDeMenaces) this.etude.getModule("SourcesDeMenaces");
		
		
		for (Bien b : biensSupports.getBiensRetenus()) {
			for (ScenarioGenerique sGene : moduleScenarioGene.getTableau()) {
				ScenarioType scenario = new ScenarioType(
						sGene.getTypeBienSupport(), sGene.getId(),
						sGene.getIntitule(), sGene.getCriteresSup(),
						SourcesDeMenaces.getSourcesDeMenacesRetenues(), null,
						true);
				if (sGene.getTypeBienSupport().contains(b.getType())
						&& scenario.getBienSupport() == null) {
					scenario.setBienSupport(b);
					this.biensRetenus.put(b.getIntitule(), b);
					bdcScenariosMenacesTypes.add(scenario);
				}
			}
		}
		this.tableau=bdcScenariosMenacesTypes;
	}
	
	public boolean estCoherent() {
		boolean resultat = true ;
		this.problemesDeCoherence = new ArrayList<String>();
		for (ScenarioType scenario : this.tableau) {
			if (scenario.isIncompleteType()) {
				String s = "Scenario type \" " + scenario.getIntitule()
						+ " \" incomplet";
				this.problemesDeCoherence.add(s);
				resultat = false;
			}
		}
		if (this.getScenariosTypesRetenus().size() < 1) {
			String s = "Aucun scenario type retenu";
			this.problemesDeCoherence.add(s);
			resultat = false;
		}
		return resultat;
	}
	
	public String toString(){
		return "Scenarios de menaces types";
	}
}
