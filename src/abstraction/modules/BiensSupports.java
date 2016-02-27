package abstraction.modules;
import java.util.ArrayList;
import java.util.LinkedList;





import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import abstraction.Etude;
import abstraction.autres.*;

/**
 * Cette classe correspond � la mod�lisation de la base de donn�es pr�existante
 * de biens supports augment�e de nouveaux biens supports que l'on peut rajouter
 * 
 * @author Francois Adam
 */

public class BiensSupports extends Module{

	// ---Variables d'instance

	private LinkedList<Bien> lesBiens;
	private TypologieDesBiensSupports typologie;
	private LinkedList<String> nomColonnesSup;

	// ---Constructeurs---

	public BiensSupports(Etude etude) {
		super("BiensSupports");
		this.etude=etude;
		
		this.lesBiens = new LinkedList<Bien>();
		this.nomColonnesSup = new LinkedList<String>();

		this.predecesseurs.add(this.getEtude().getModule("TypologieDesBiensSupports"));
		
		typologie = (TypologieDesBiensSupports) this.getEtude().getModule("TypologieDesBiensSupports");
		
		this.cree = false;
		this.coherent = false;
		this.disponible = false;
	}

	// ---Getters et setters---

	public LinkedList<Bien> getLesBiens() {
		return lesBiens;
	}

	public void setLesBiens(LinkedList<Bien> lesBiens) {
		this.lesBiens = lesBiens;
	}

	public Bien getBien (String intitule){
		int index = 0;
		while (index<this.nombreDeBiens() && !this.getBien(index).getIntitule().equals(intitule)){
			index++;
		}
		if (index<this.nombreDeBiens()){
			return this.getBien(index);
		}
		else{
			return null;
		}
		
	}
	
	public Bien getBien(int index){
		return this.lesBiens.get(index);
	}
	
	public TypologieDesBiensSupports getTypologie(){
		return typologie;
	}
	
	public LinkedList<String> getNomColonnesSup(){
		return this.nomColonnesSup;
	}
	
	public void setNomColonnesSup(LinkedList<String> nomColonnesSup){
		this.nomColonnesSup=nomColonnesSup;
	}

	// ---Services---
	
	public void ajouterColonne(String nomColonne){
		this.getNomColonnesSup().addFirst(nomColonne);
	}
	
	public void enleverPremiereColonne(){
		this.getNomColonnesSup().removeFirst();
	}
	
	public void modifierColonne(String nomColonne, String nouvelleColonne){
		for (int i=0; i<this.getNomColonnesSup().size(); i++){
			if (this.getNomColonnesSup().get(i).equals(nomColonne)){
				this.getNomColonnesSup().set(i, nouvelleColonne);
			}
		}
	}

	public int nombreDeBiens(){
		return lesBiens.size();
	}
	
	public void ajouterBien(Bien bien) {
		this.getLesBiens().add(bien);
	}

	public void supprimerBien(String nomBien) {
		this.getLesBiens().remove(this.getBien(nomBien));
	}

	public LinkedList<Bien> getBiensRetenus() {
		LinkedList<Bien> resultat = new LinkedList<Bien>();
		for (Bien bien : this.getLesBiens()) {
			if (bien.isRetenu()) {
				resultat.add(bien);
			}
		}
		return resultat;
	}

	public void retenirBien(String intituleBien) {
		this.getBien(intituleBien).setRetenu(true);
	}

	public void retenirBien(Bien bien) {
		this.retenirBien(bien.getIntitule());
	}

	public void actualiserBiens() {
		Object[] types = this.typologie.getIntituleTypeBiensRetenus();
		for (Bien bien : this.lesBiens){
			if (!this.typologie.isTypeBienRetenu(bien.getType())){
				String type = null;
				do{
					type = (String)JOptionPane.showInputDialog(null, "Quel nouveau type de bien support voulez vous mettre à la place de "+bien.getType()+" pour le bien "+bien.getIntitule()+"?","Type de bien", JOptionPane.QUESTION_MESSAGE,new ImageIcon(),types,types[0]);
				} while (type==null);
				bien.setType(type);
				bien.setRetenu(false);
			}	
		}
	}
	
	public String toString(){
		return "Biens supports";
	}
	
	public boolean estCoherent(){
		boolean resultat = true;
		this.erreurs = new ArrayList<String>();
		for(Bien b : this.getLesBiens()){
			if(!b.estComplet()){
				this.erreurs.add("bien support \" " + b.getIntitule() + " \" incomplet");
				resultat = false;
			}
		}
		if(this.getBiensRetenus().size() <= 0){
			this.erreurs.add("Aucun bien support retenu");
			resultat = false;
		}	
		return resultat;
	}
}
