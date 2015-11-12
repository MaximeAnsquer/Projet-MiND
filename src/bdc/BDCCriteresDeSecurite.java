package bdc;
import java.util.ArrayList;

public class BDCCriteresDeSecurite{
	
	//---Variables d'instance
	
	private ArrayList<String> colonneId = new ArrayList<>();
	private ArrayList<String> colonneIntitule = new ArrayList<>();
	private ArrayList<String> colonneDescription = new ArrayList<>();
	private ArrayList<Boolean> colonneRetenu = new ArrayList<>();
	
	//---Constructeurs---

	public BDCCriteresDeSecurite() {
		
	}
	
	//---Getters et setters---

	public ArrayList<String> getColonneId() {
		return colonneId;
	}

	public void setColonneId(ArrayList<String> colonneId) {
		this.colonneId = colonneId;
	}

	public ArrayList<String> getColonneIntitule() {
		return colonneIntitule;
	}

	public void setColonneIntitule(ArrayList<String> colonneIntitule) {
		this.colonneIntitule = colonneIntitule;
	}

	public ArrayList<String> getColonneDescription() {
		return colonneDescription;
	}

	public void setColonneDescription(ArrayList<String> colonneDescription) {
		this.colonneDescription = colonneDescription;
	}

	public ArrayList<Boolean> getColonneRetenu() {
		return colonneRetenu;
	}

	public void setColonneRetenu(ArrayList<Boolean> colonneRetenu) {
		this.colonneRetenu = colonneRetenu;
	}

	public int getNbLigne() {
		if(this.colonneDescription.size() == this.colonneId.size() 
			&& this.colonneId.size() == this.colonneIntitule.size()
			&& this.colonneIntitule.size() == this.colonneRetenu.size())
		{
			return this.colonneDescription.size();
		}
		else{
			System.out.println("Erreur, les colonnes de BDCCriteresDeSecurite n'ont pas toutes la même taille.");
			return 0;
		}
	}	

}
