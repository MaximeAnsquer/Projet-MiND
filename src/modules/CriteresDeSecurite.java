package modules;
import java.util.ArrayList;
import bdc.*; 

public class CriteresDeSecurite extends Module {
	
	//---Variables d'instance---
	
	private ArrayList<String> colonneId = new ArrayList<>();
	private ArrayList<String> colonneIntitule = new ArrayList<>();
	private ArrayList<String> colonneDescription = new ArrayList<>();
	private ArrayList<Boolean> colonneRetenu = new ArrayList<>();
	private int nbLigne;
	
	//---Constructeurs---

	public CriteresDeSecurite() {
		super("Critères de sécurité");
		this.disponible = true;
		this.coherent = false;
		this.cree = false;
		//On importe les valeurs de la BDC :
		BDCCriteresDeSecurite bdc = new BDCCriteresDeSecurite(); 		
		this.colonneId = bdc.getColonneId();
		this.colonneIntitule = bdc.getColonneIntitule();
		this.colonneRetenu = bdc.getColonneRetenu();
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
				System.out.println("Erreur, les colonnes de CriteresDeSecurite n'ont pas toutes la même taille.");
				return 0;
			}
	}	

}
