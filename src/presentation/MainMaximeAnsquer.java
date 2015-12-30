package presentation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import abstraction.Etude;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.EvenementsRedoutes;
import abstraction.modules.MappingDesBiens;
import abstraction.modules.Metriques;
import abstraction.modules.Module;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.SourcesDeMenaces;
import abstraction.modules.TypologieDesBiensSupports;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Classe brouillon pour faire des tests
 * @author Maxime Ansquer
 *
 */

public class MainMaximeAnsquer extends JFrame {

	public Etude etudeEnCours;
	private JPanel partieDeGauche;
	private JPanel partieDuBas;
	private JPanel partieDuCentre;
	private Hashtable<String, JPanel> lesJpanels;
	private Workflow workflow;
	private Module moduleEnCours;
	private JLabel label;
	private Container contentPane;
	private int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int hauteurEcran = Toolkit.getDefaultToolkit().getScreenSize().height;
	private JList listeFichiers;
	private JButton boutonOk;
	private JFrame fenetreChoisirEtude; 

	public MainMaximeAnsquer(){


		super("Outil d'analyse de risques");
		this.setPreferredSize(new Dimension(largeurEcran, (int) (0.95*hauteurEcran)));

		//Permet de reagir a la fermeture de la fenetre
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(etudeEnCours != null){
					int decision = JOptionPane.showConfirmDialog(null, 
							"Enregistrer l'etude en cours avant de fermer le programme ?", "Fermeture du programme", 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					switch(decision){
					case JOptionPane.YES_OPTION:
						enregistrerEtude();		 
						System.exit(0);
						break;
					case JOptionPane.NO_OPTION:
						System.exit(0);
						break;
					}	    	
				}
			}
		});
		
		//La barre de menu en haut
		this.setJMenuBar(new BarreMenu(this));
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.contentPane = this.getContentPane();
		this.partieDuCentre = new JPanel();
		this.partieDuCentre.setLayout(new BorderLayout());
		this.partieDeGauche = new JPanel();
		creerPartieDuBas();		
		this.lesJpanels = new Hashtable<String, JPanel>();
		this.pack();

		if(this.existeAuMoinsUneEtude()){
			this.demanderEtude();			
		}
		else{
			JOptionPane.showMessageDialog(this, "Aucune etude enregistreee !");
			this.nouvelleEtude();
		}
	}

	private void creerPartieDuBas() {
		partieDuBas = new JPanel();
		partieDuBas.setLayout(new BoxLayout(partieDuBas, BoxLayout.Y_AXIS));		
	}

	private boolean existeAuMoinsUneEtude() {
		String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
		File dossierEtude = new File(urlEtudes);
		File[] listOfFiles = dossierEtude.listFiles();
		return listOfFiles.length > 0;
	}

	private void demanderEtude() {
		Object[] choix = {"Creer une nouvelle etude", "Ouvrir une etude existante"};
		Object reponse =  JOptionPane.showOptionDialog(this,  "Que souhaitez-vous faire ?", null, JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);	
		if (reponse.equals(0)){
			this.nouvelleEtude();
		}
		else{
			this.choisirEtude();
		}	
	}
	/**
	 * 
	 * @param nom le nom du module a afficher, ou bien " Workflow " si on veut afficher le workflow
	 */
	public void setContenu(String nom) {					

		contentPane.add(partieDuCentre, BorderLayout.CENTER);

		contentPane.remove(partieDeGauche);

		if(nom.equals("Workflow")){					
			this.moduleEnCours = new Module("Workflow");
			this.partieDuCentre.removeAll();
			this.workflow = new Workflow(etudeEnCours, this);
			this.partieDuCentre.add(workflow, BorderLayout.CENTER);
			partieDuBas.removeAll();
		}
		else{
			this.moduleEnCours = etudeEnCours.getModule(nom);
			setPartieDeGauche();
			this.partieDuCentre.removeAll();	
			contentPane.add(partieDeGauche, BorderLayout.WEST);
			this.label = new JLabel(moduleEnCours.toString(), SwingConstants.CENTER);
			this.label.setFont(new Font("Arial", Font.PLAIN, 25));
			partieDuCentre.add(partieDuBas, BorderLayout.SOUTH);
			setPartieDuBas();

			if(nom.equals("CriteresDeSecurite")){
				this.lesJpanels.put(nom, new FenetreCriteresDeSecurite((CriteresDeSecurite) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("Metriques")){
				this.lesJpanels.put(nom, new FenetreMetriques((Metriques) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("SourcesDeMenaces")){
				this.lesJpanels.put(nom, new FenetreSourcesDeMenaces((SourcesDeMenaces) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("BiensSupports")){
				this.lesJpanels.put(nom, new FenetreBiensSupports((BiensSupports) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("BiensEssentiels")){
				this.lesJpanels.put(nom, new FenetreBiensEssentiels((BiensEssentiels) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("MappingDesBiens")){
				this.lesJpanels.put(nom, new FenetreMappingDesBiens((MappingDesBiens) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("EvenementsRedoutes")){
				this.lesJpanels.put(nom, new FenetreEvenementsRedoutes( (EvenementsRedoutes) etudeEnCours.getModule(nom)) );
			}
			else if(nom.equals("TypologieDesBiensSupports")){
				this.lesJpanels.put(nom, new FenetreTypologieBiensSupports( (TypologieDesBiensSupports) etudeEnCours.getModule(nom)) ) ;
			}
			else if(nom.equals("ScenariosDeMenacesGeneriques")){
				this.lesJpanels.put(nom, new FenetreScenariosDeMenacesGeneriques( (ScenariosDeMenacesGeneriques) etudeEnCours.getModule(nom)) ) ;
			}

			this.partieDuCentre.add(label, BorderLayout.NORTH);
			this.partieDuCentre.add(lesJpanels.get(nom), BorderLayout.CENTER);
		}				

		this.validate();
		this.repaint();

	}

	private void setPartieDuBas() {
		
		partieDuBas.removeAll();
		
		for(JLabel label : moduleEnCours.getProblemes()){
			label.setFont(new Font("Arial", Font.PLAIN, 22));
			partieDuBas.add(label);
		}
		
		partieDuBas.validate();
		partieDuBas.repaint();
				
	}

	private void setPartieDeGauche() {

		partieDeGauche = new JPanel();
		partieDeGauche.setLayout(new GridLayout(2,1));
		String nom = moduleEnCours.getNom();

		if(nom.equals("Workflow")){
		}		
		else{			
			JButton boutonWorkflow = new JButton("Workflow"); 
			boutonWorkflow.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					moduleEnCours = new Module("Workflow");
					setContenu("Workflow");				
				}				
			});
			partieDeGauche.add(boutonWorkflow);

			JButton boutonVerifier = new JButton("Verifier la coherence");
			boutonVerifier.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					String nom = moduleEnCours.getNom();
					setContenu("Workflow");
					setContenu(nom);
				}				
			});
			partieDeGauche.add(boutonVerifier);

			partieDeGauche.validate();
			partieDeGauche.repaint();
		}		
	}

	/**
	 * Cree une nouvelle etude et la definie comme etude courante
	 * @return la nouvelle etude cree
	 */
	public Etude nouvelleEtude(){	
		//permet d'eviter un bug graphique
		if(etudeEnCours != null){
			setContenu("Workflow");
		}
		String nomEtude = "";
		while(nomEtude.equals("") ){
			nomEtude = JOptionPane.showInputDialog("Veuillez saisir un nom pour la nouvelle etude.");	
			if(nomEtude == null){
				if(this.existeAuMoinsUneEtude()){
					this.demanderEtude();			
				}
				else{
					JOptionPane.showMessageDialog(this, "Aucune etude enregistreee !");
					this.nouvelleEtude();
				}
			}
		}
		Etude nouvelleEtude = new Etude(nomEtude);
		this.etudeEnCours = nouvelleEtude;
		this.setTitle("Outil d'analyse de risques - Etude en cours : " + nomEtude);			

		this.moduleEnCours = new Module("Workflow");

		this.setContenu("Workflow");		

		return nouvelleEtude;
	}
	public void enregistrerEtude(){
		try {
			// Instanciation de la classe XStream
			XStream xstream = new XStream(new DomDriver("UTF-8"));		    
			// Instanciation d'un fichier c:/temp/article.xml
			File fichier = new File(System.getProperty("user.dir") + File.separator + "etudes" + File.separator + etudeEnCours.getNom()+".xml");
			// Instanciation d'un flux de sortie fichier
			FileOutputStream fos = new FileOutputStream(fichier);
			try {
				// Sérialisation de l'objet article dans c:/temp/article.xml
				xstream.toXML(etudeEnCours, fos);
				JOptionPane.showMessageDialog(null, "Etude enregistree avec succes");
			} finally {
				// On s'assure de fermer le flux quoi qu'il arrive
				fos.close();
			}	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	/**
	 * Permet a l'utilisateur de choisir parmi une liste d'etudes sauvegardees, et definit l'etude choisie comme etude courante
	 * @return l'etude choisie
	 */
	public void choisirEtude(){
		if(this.existeAuMoinsUneEtude()){
			ArrayList<Object> data = new ArrayList<Object>();
			String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
			File dossierEtude = new File(urlEtudes);
			File[] listOfFiles = dossierEtude.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String nomFichier = listOfFiles[i].getName();
					int longueurFichier = nomFichier.length();
					String nomFichierSansExtension = nomFichier.substring(0, longueurFichier - 4);				
					data.add(nomFichierSansExtension);
				}
			}
			listeFichiers = new JList(data.toArray());
			listeFichiers.setFont(new Font("Arial", Font.PLAIN, 20));
			((DefaultListCellRenderer) listeFichiers.getCellRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

			listeFichiers.addKeyListener(new KeyListener(){
				public void keyTyped(KeyEvent e) {}			
				public void keyPressed(KeyEvent e) {
					int keyCode = e.getKeyCode();
					if(keyCode == KeyEvent.VK_ENTER){
						String urlEtude = System.getProperty("user.dir") + File.separator + "etudes" + File.separator + listeFichiers.getSelectedValue() + ".xml";
						ouvrirEtude(urlEtude);
						fenetreChoisirEtude.dispose();
					}
				}			
				public void keyReleased(KeyEvent e) {}			
			});

			listeFichiers.addListSelectionListener(new ListSelectionListener(){
				public void valueChanged(ListSelectionEvent e) {
					boutonOk.setEnabled(true);				
				}
			});
			JFrame fenetreChoixFichier = fenetreChoixFichier(listeFichiers);			
		}
		else{
			JOptionPane.showMessageDialog(null, "Desole, il n'existe aucune etude enregistree.");
		}
	}

	public Etude ouvrirEtude(String urlEtude){
		Etude etudeOuverte = new Etude();
		try {
			// Instanciation de la classe XStream
			XStream xstream = new XStream(new DomDriver());
			// Redirection du fichier vers un flux d'entrée fichier
			FileInputStream fis = new FileInputStream(new File(urlEtude));			
			// Désérialisation du fichier vers un nouvel objet article
			etudeOuverte = (Etude) xstream.fromXML(fis);
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		this.etudeEnCours = etudeOuverte;
		this.setTitle("Outil d'analyse de risques - Etude en cours : " + etudeOuverte.getNom());
		this.moduleEnCours = new Module("Workflow");
		this.setContenu("Workflow");	
		return etudeOuverte;
	}
	private JFrame fenetreChoixFichier(JList jlist) {
		fenetreChoisirEtude = new JFrame();
		fenetreChoisirEtude.setVisible(true);
		fenetreChoisirEtude.getContentPane().add(jlist, BorderLayout.CENTER);
		boutonOk = new JButton("Ouvrir l'etude");
		boutonOk.setEnabled(false);
		boutonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String urlEtude = System.getProperty("user.dir") + File.separator + "etudes" + File.separator + listeFichiers.getSelectedValue() + ".xml";
				ouvrirEtude(urlEtude);
				fenetreChoisirEtude.dispose();				
			}
		});
		fenetreChoisirEtude.setLocationRelativeTo(null);
		fenetreChoisirEtude.getContentPane().add(boutonOk, BorderLayout.SOUTH);
		fenetreChoisirEtude.pack();
		return fenetreChoisirEtude;
	}
	public Etude getEtude(){
		return etudeEnCours;
	}
	public void setModuleEnCours(Module m){
		this.moduleEnCours = m;
	}
	public void modifierNomEtude() {		
		//On supprimer le fichier de sauvegarde de l'etude en cours s'il existe		
		boolean existaitSauvegarde = false;
		String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
		File dossierEtude = new File(urlEtudes);
		File[] listOfFiles = dossierEtude.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String nomFichier = listOfFiles[i].getName();
				if(nomFichier.equals(etudeEnCours.getNom()+".xml")){
					existaitSauvegarde = true;
					listOfFiles[i].delete();
				}
			}
		}
		//On change le nom de l'etude		
		String nouveauNom = "";
		while(nouveauNom != null && nouveauNom.equals("")){
			nouveauNom = JOptionPane.showInputDialog("Veuillez saisir le nouveau nom de l'etude");
			if(nouveauNom != null){
				this.etudeEnCours.setNom(nouveauNom);
				this.setTitle("Outil d'analyse de risques - Etude en cours : " + nouveauNom);
			}
		}		
		//On enregistre l'etude (si elle etait deja enregistree)
		if(existaitSauvegarde){
			this.enregistrerEtude();
		}
	}

	public static void main(String[] args) {
		//---La fenetre principale---
		MainMaximeAnsquer fenetrePrincipale = new MainMaximeAnsquer(); 
	}

	public void supprimerEtude() {
		System.out.println("supprimerEtude");
		String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
		File dossierEtude = new File(urlEtudes);
		File[] listOfFiles = dossierEtude.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String nomFichier = listOfFiles[i].getName();
				if(nomFichier.equals(etudeEnCours.getNom()+".xml")){
					listOfFiles[i].delete();
					JOptionPane.showMessageDialog(this, "Etude supprimee avec succes");
					if(this.existeAuMoinsUneEtude()){
						this.demanderEtude();			
					}
					else{
						this.nouvelleEtude();
					}
				}
			}
		}		
	}
}

