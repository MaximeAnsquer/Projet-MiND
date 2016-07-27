package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import abstraction.Etude;
import abstraction.modules.AnalyseDesRisques;
import abstraction.modules.BiensEssentiels;
import abstraction.modules.BiensSupports;
import abstraction.modules.CriteresDeSecurite;
import abstraction.modules.EvenementsRedoutes;
import abstraction.modules.MappingDesBiens;
import abstraction.modules.MatriceDesRisques;
import abstraction.modules.Metriques;
import abstraction.modules.Module;
import abstraction.modules.ScenariosDeMenacesGeneriques;
import abstraction.modules.ScenariosDeMenacesTypes;
import abstraction.modules.SourcesDeMenaces;
import abstraction.modules.TypologieDesBiensSupports;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

//Copyright (c) 2003-2006, Joe Walnes
//Copyright (c) 2006-2009, 2011 XStream Committers
//All rights reserved.

/**
 * 
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
	private JButton boutonAnnuler;
	private JFrame fenetreChoisirEtude; 
	private JButton boutonWorkflow;
	private JButton boutonVerifier;
	private JList jListAvertissements;
	private DefaultListModel listModelAvertissements;
	private JList jListErreurs;
	private DefaultListModel listModelErreurs;

	public MainMaximeAnsquer(){

		super("Outil d'analyse de risques");
		this.setPreferredSize(new Dimension(largeurEcran, (int) (0.95*hauteurEcran)));
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);	
		this.contentPane = this.getContentPane();
		this.partieDuCentre = new JPanel();
		this.partieDuCentre.setLayout(new BorderLayout());
		this.partieDeGauche = new JPanel();
		partieDeGauche.setLayout(new GridLayout(2,1));
		creerPartieDuBas();		
		this.lesJpanels = new Hashtable<String, JPanel>(); //les différents tableaux affichés selon le module
		creerBoutonWorkflow();
		creerBoutonVerifierCoherence();
		ajouterListenerFermetureFenetre();
		setJMenuBar(new BarreMenu(this));
		pack();

		if(this.existeAuMoinsUneEtude()){
			this.demanderEtude();			
		}
		else{
			JOptionPane.showMessageDialog(this, "Aucune étude enregistrée !");
			this.nouvelleEtude();
		}
	}

	private void ajouterListenerFermetureFenetre() {		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(etudeEnCours != null){
					int confirmation = JOptionPane.showConfirmDialog(null, "Enregistrer l'étude en cours avant de quitter ?");
					switch(confirmation){
					case JOptionPane.YES_OPTION:
						enregistrerEtude();		 
						System.exit(0);
						break;
					case JOptionPane.NO_OPTION:
						System.exit(0);
						break;
					default:;
					}	    	
				}
			}
		});		
	}

	private void creerBoutonVerifierCoherence() {
		this.boutonVerifier = new JButton("Vérifier la cohérence");
		boutonVerifier.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String nom = moduleEnCours.getNom();
				setContenu(nom);
			}				
		});
	}

	private void creerBoutonWorkflow() {
		this.boutonWorkflow = new JButton("Menu principal"); 
		boutonWorkflow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				moduleEnCours = new Module("Workflow");
				setContenu("Workflow");				
			}				
		});		
	}

	private void creerPartieDuBas() {
		partieDuBas = new JPanel();
		partieDuBas.setLayout(new BoxLayout(partieDuBas, BoxLayout.X_AXIS));
		Font font = new Font("Arial", Font.PLAIN, 18);

		//JList des avertissements
		listModelAvertissements = new DefaultListModel();	
		jListAvertissements = new JList(listModelAvertissements);		
		jListAvertissements.setVisibleRowCount(6);
		jListAvertissements.setFont(font);
		jListAvertissements.setForeground(new Color(255,65,0));

		//JList des erreurs
		listModelErreurs = new DefaultListModel();	
		jListErreurs = new JList(listModelErreurs);		
		jListErreurs.setVisibleRowCount(6);
		jListErreurs.setFont(font);
		jListErreurs.setForeground(Color.red);

		partieDuBas.add(new JScrollPane(jListAvertissements));
		partieDuBas.add(new JScrollPane(jListErreurs));
	}

	private boolean existeAuMoinsUneEtude() {
		String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
		File dossierEtude = new File(urlEtudes);
		File[] listOfFiles = dossierEtude.listFiles();
		return listOfFiles.length > 0;
	}

	private void demanderEtude() {
		Object[] choix = {"Créer une nouvelle étude", "Ouvrir une étude existante"};
		Object reponse =  JOptionPane.showOptionDialog(this,  "Que souhaitez-vous faire ?", null, JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);	
		if (reponse.equals(0)){
			this.nouvelleEtude();
		}
		else if(reponse.equals(1)){
			this.choisirEtude();
		}	
		else{
			demanderEtude();
		}
	}

	/** 
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
			else if(nom.equals("ScenariosDeMenacesTypes")){
				this.lesJpanels.put(nom, new FenetreScenarioDeMenacesTypes( (ScenariosDeMenacesTypes) etudeEnCours.getModule(nom)) ) ;
			}
			else if(nom.equals("AnalyseDesRisques")){
				this.lesJpanels.put(nom, new FenetreAnalyseDesRisques( (AnalyseDesRisques) etudeEnCours.getModule(nom)) ) ;
			}
			else if(nom.equals("MatriceDesRisques")){
				this.lesJpanels.put(nom, new FenetreMatriceDesRisques(  (MatriceDesRisques) etudeEnCours.getModule(nom)) ) ;
			}

			this.partieDuCentre.add(label, BorderLayout.NORTH);
			this.partieDuCentre.add(lesJpanels.get(nom), BorderLayout.CENTER);
		}				

		this.validate();
		this.repaint();

	}

	private void setPartieDuBas() {

		//On genere les eventuels avertissements et erreurs
		moduleEnCours.estCoherent();

		//On vide les JList
		listModelAvertissements.removeAllElements();
		listModelErreurs.removeAllElements();

		//S'il n'y a pas d'avertissement :
		if(moduleEnCours.getAvertissements().size() == 0){
			jListAvertissements.setVisibleRowCount(1);		
			jListAvertissements.setForeground(new Color(34,139,34));
			listModelAvertissements.addElement("Aucun avertissement.");
		}
		//s'il y a des avertissements :
		else{
			for(String avertissement : moduleEnCours.getAvertissements()){
				listModelAvertissements.addElement("Avertissement : " + avertissement);
			}
			int nbLignesAffichees;
			if(listModelAvertissements.size() > 5){
				nbLignesAffichees = 6;
			}
			else{
				nbLignesAffichees = listModelErreurs.size();
			}
			jListAvertissements.setVisibleRowCount(nbLignesAffichees);
			jListAvertissements.setForeground(new Color(255,65,0)); 
		}

		//S'il n'y a pas de problème de cohérence :
		if(moduleEnCours.getErreurs().size() == 0){
			jListErreurs.setVisibleRowCount(1);		
			jListErreurs.setForeground(new Color(34,139,34));
			listModelErreurs.addElement("Aucune erreur.");
		}
		//s'il y a des problèmes de cohérence :
		else{
			for(String probleme : moduleEnCours.getErreurs()){
				listModelErreurs.addElement("Erreur : " + probleme);
			}
			int nbLignesAffichees;
			if(listModelErreurs.size() > 5){
				nbLignesAffichees = 6;
			}
			else{
				nbLignesAffichees = listModelErreurs.size();
			}
			jListErreurs.setVisibleRowCount(nbLignesAffichees);
			jListErreurs.setForeground(Color.red);
		}

		partieDuBas.validate();
		partieDuBas.repaint();
	}

	private void setPartieDeGauche() {

		this.partieDeGauche.removeAll();

		/*
		 * Si le moduleEnCours est veritablement un module (et pas le workflow), on ajoute les boutons
		 * " Workflow " et " Verifier la coherence " a la partie de gauche (qui sinon est vide)
		 */
		String nom = moduleEnCours.getNom();

		if(!nom.equals("Workflow")){			
			partieDeGauche.add(this.boutonWorkflow);
			partieDeGauche.add(this.boutonVerifier);

			partieDeGauche.validate();
			partieDeGauche.repaint();
		}		
	}

	/**
	 * Cree une nouvelle etude et la definie comme etude courante
	 * @return la nouvelle etude cree
	 */
	public void nouvelleEtude(){	
		//permet d'eviter un bug graphique
		if(etudeEnCours != null){
			setContenu("Workflow");
		}

		int decision = -2;
		if(etudeEnCours != null){
			decision = JOptionPane.showConfirmDialog(null, 
					"Enregistrer l'étude en cours avant de créer une nouvelle etude ?", "Enregistrer l'étude en cours ?", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			switch(decision){
			case JOptionPane.YES_OPTION:
				enregistrerEtude();		
				break;
			case JOptionPane.NO_OPTION:
				break;
			default:
				decision = -1;
				nouvelleEtude();
			}	
		}
		if(decision != -1 ){
			//On demande le nom a attribuer a l'etude
			String nomEtude = "";
			while( (nomEtude != null && nomEtude.equals("")) || !nomEstValide(nomEtude) ){
				nomEtude = JOptionPane.showInputDialog("Veuillez saisir un nom pour la nouvelle étude." +
						"\n(Un nom d'étude ne peut pas contenir les caractères suivants : \\ / * ? \" < > | )");	
				if(nomEtude == null){
					if(this.existeAuMoinsUneEtude()){
						this.demanderEtude();			
					}
					else{
						JOptionPane.showMessageDialog(this, "Aucune étude enregistrée !");
						this.nouvelleEtude();
					}
				}
			}
			if(nomEtude != null){
				Etude nouvelleEtude = new Etude();

				if(nomEtudeDejaUtilise(nomEtude)){
					JOptionPane.showMessageDialog(this, "Ce nom est déjà utilisé pour une autre étude, veuillez en choisir un autre.");
					nouvelleEtude();
				}
				else{
					nouvelleEtude.setNom(nomEtude);
					this.etudeEnCours = nouvelleEtude;
					this.setTitle("Outil d'analyse de risques - Etude en cours : " + nomEtude);			

					this.moduleEnCours = new Module("Workflow");

					this.setContenu("Workflow");					
				}					
			}
		}
	}

	private boolean nomEstValide(String nomEtude) {
		boolean valide = true;
		if(nomEtude != null){
			String[] caracteresInterdits = {"\\","/",":","*","?","\"","<",">","|"};
			for(String c: caracteresInterdits){
				if(nomEtude.contains(c)){
					valide = false;
				}
			}		
		}
		return valide;
	}

	private boolean nomEtudeDejaUtilise(String nomEtude) {
		boolean resultat = false;

		String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
		File dossierEtude = new File(urlEtudes);
		File[] listOfFiles = dossierEtude.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String nomFichier = listOfFiles[i].getName();
				if(nomFichier.equals(nomEtude + ".xml")){
					resultat = true;
				}
			}
		}			
		return resultat;
	}

	public void enregistrerEtude(){

		long t0 = System.currentTimeMillis();

		//On supprime les observeurs (posent problemes pour la serialisation, sont recrees en meme temps que la fenetre)
		((TypologieDesBiensSupports) etudeEnCours.getModule("TypologieDesBiensSupports") ).deleteObservers();
		((ScenariosDeMenacesGeneriques) etudeEnCours.getModule("ScenariosDeMenacesGeneriques") ).deleteObservers();
		((ScenariosDeMenacesTypes) etudeEnCours.getModule("ScenariosDeMenacesTypes") ).deleteObservers();

		//Necessaire etant donne que l'on supprime les observeurs (il faut recreer les fenetres pour que leurs boutons etc refonctionnent)
		setContenu("Workflow");

		((TypologieDesBiensSupports) etudeEnCours.getModule("TypologieDesBiensSupports") ).deleteObservers();
		try {
			// Instanciation de la classe XStream
			XStream xstream = new XStream(new DomDriver("UTF-8"));		    
			// Instanciation d'un fichier
			File fichier = new File(System.getProperty("user.dir") + File.separator + "etudes" + File.separator + etudeEnCours.getNom()+".xml");
			// Instanciation d'un flux de sortie fichier
			FileOutputStream fos = new FileOutputStream(fichier);
			try {
				// Sérialisation de l'objet 
				xstream.toXML(etudeEnCours, fos);
				long t1 = System.currentTimeMillis();
				System.out.println("Temps mis pour enregistrer l'étude : " + (t1-t0)/1000.0 + "s");
				JOptionPane.showMessageDialog(null, "Étude enregistrée avec succès");
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
		System.out.println("Choix d'une étude...");
		if(this.etudeEnCours != null){
			System.out.println("Il y a une étude en cours... : " + etudeEnCours.getNom());
			int decision = JOptionPane.showConfirmDialog(null, 
					"Enregistrer l'étude en cours avant d'en ouvrir une autre ?", null, 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if(decision == JOptionPane.YES_OPTION ){
				enregistrerEtude();
			}  
		}
		if(this.existeAuMoinsUneEtude()){
			System.out.println("Il n'y a pas d'étude en cours...");
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
			this.setVisible(false);
			JFrame fenetreChoixFichier = fenetreChoixFichier(listeFichiers);			
		}
		else{
			JOptionPane.showMessageDialog(null, "Désolé, il n'existe aucune étude enregistrée.");
		}
	}

	public Etude ouvrirEtude(String urlEtude){
		long tempsAvantOuverture = System.currentTimeMillis();
		System.out.println("Ouverture de l'étude : " + urlEtude);
		Etude etudeOuverte = new Etude();
		try {
			// Instanciation de la classe XStream
			XStream xstream = new XStream(new DomDriver());
			// Redirection du fichier vers un flux d'entrée fichier
			FileInputStream fis = new FileInputStream(new File(urlEtude));			
			// Désérialisation du fichier vers un nouvel objet article
			etudeOuverte = (Etude) xstream.fromXML(fis);
			long tempsApresOuverture = System.currentTimeMillis();
			System.out.println("Temps mis pour ouvrir l'étude : " + (tempsApresOuverture - tempsAvantOuverture)/1000.0 + "s"  );
		} catch (Exception e) {	
			JOptionPane.showMessageDialog(this, "Ce fichier ne contient pas d'étude valide.\nUne nouvelle étude \" " + extraireNom(urlEtude) + " \" a été créée à la place.", "Fichier invalide", JOptionPane.ERROR_MESSAGE, null );
		}
		this.etudeEnCours = etudeOuverte;
		this.moduleEnCours = new Module("Workflow");
		this.setContenu("Workflow");	

		//Si le nom du fichier a été renommé manuellement, on renomme l'étude de façon appropriée
		String nomDuFichier = extraireNom(urlEtude);
		if(!nomDuFichier.equals(etudeOuverte.getNom())){
			etudeOuverte.setNom(nomDuFichier);
		}
		this.setTitle("Outil d'analyse de risques - Etude en cours : " + etudeOuverte.getNom());


		return etudeOuverte;
	}

	private String extraireNom(String urlEtude) {
		String resultat = "";
		int taille = urlEtude.length();
		int debut = 0;
		int i = taille - 1;
		boolean trouve = false;
		while( i > -1 && !trouve){
			if( (urlEtude.charAt(i)+"").equals(File.separator)){
				trouve = true;
				debut = i+1;
			}
			i--;
		}
		resultat = urlEtude.substring(i+2, taille - 4);
		return resultat;
	}

	private JFrame fenetreChoixFichier(JList jlist) {
		fenetreChoisirEtude = new JFrame("Veuillez choisir une étude.");
		fenetreChoisirEtude.setVisible(true);
		fenetreChoisirEtude.setMinimumSize(new Dimension(400,0));
		fenetreChoisirEtude.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		fenetreChoisirEtude.getContentPane().add(new JScrollPane(jlist), BorderLayout.CENTER);

		boutonOk = new JButton("Ouvrir l'étude");
		boutonOk.setEnabled(false);
		boutonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String urlEtude = System.getProperty("user.dir") + File.separator + "etudes" + File.separator + listeFichiers.getSelectedValue() + ".xml";
				ouvrirEtude(urlEtude);
				fenetreChoisirEtude.dispose();	
				setVisible(true);
			}
		});

		boutonAnnuler = new JButton("Annuler");
		boutonAnnuler.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fenetreChoisirEtude.dispose();
				MainMaximeAnsquer.this.setVisible(true);
				demanderEtude();
			}			
		});

		JPanel lesBoutons = new JPanel();
		lesBoutons.setLayout(new BorderLayout());
		lesBoutons.add(boutonOk, BorderLayout.CENTER);
		lesBoutons.add(boutonAnnuler, BorderLayout.EAST);
		fenetreChoisirEtude.getContentPane().add(lesBoutons, BorderLayout.SOUTH);
		fenetreChoisirEtude.setLocationRelativeTo(null);
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
		//On sauvegarde le nom actuel, et on verifie s'il existe une sauvegarde de l'etude actuelle
		String nomEtudeASupprimer = etudeEnCours.getNom();

		boolean existaitSauvegarde = false;
		String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
		File dossierEtude = new File(urlEtudes);
		File[] listOfFiles = dossierEtude.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String nomFichier = listOfFiles[i].getName();
				if(nomFichier.equals(etudeEnCours.getNom()+".xml")){
					existaitSauvegarde = true;
				}
			}
		}

		//On change le nom de l'etude		
		String nouveauNom = "";
		while( (nouveauNom != null && nouveauNom.equals("")) || !nomEstValide(nouveauNom)){
			nouveauNom = JOptionPane.showInputDialog("Veuillez saisir le nouveau nom de l'étude." +
					"\n(Un nom d'étude ne peut pas contenir les caractères suivants : \\ / * ? \" < > | )");
			if(nouveauNom != null && nomEstValide(nouveauNom)){
				nouveauNom.replace('"', '\"');
				this.etudeEnCours.setNom(nouveauNom);
				this.setTitle("Outil d'analyse de risques - Etude en cours : " + nouveauNom);

				//On enregistre l'etude (si elle etait deja enregistree)
				if(existaitSauvegarde){
					this.enregistrerEtude();
				}
				//On supprime l'ancienne etude (si elle etait enregistree)
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						String nomFichier = listOfFiles[i].getName();
						if(nomFichier.equals(nomEtudeASupprimer + ".xml")){
							listOfFiles[i].delete();
						}
					}
				}
			}
		}	
	}

	/**
	 * supprime l'etude en cours
	 */
	public void supprimerEtude() {

		int confirmation = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer l'étude en cours ?", "Suppression de l'étude", JOptionPane.YES_NO_OPTION);
		if(confirmation == JOptionPane.YES_OPTION){
			String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
			File dossierEtude = new File(urlEtudes);
			File[] listOfFiles = dossierEtude.listFiles();
			int i = 0;
			boolean trouve = false;
			int tailleListe = listOfFiles.length;
			while (i < tailleListe && !trouve) {
				if (listOfFiles[i].isFile()) {
					String nomFichier = listOfFiles[i].getName();
					if(nomFichier.equals(etudeEnCours.getNom()+".xml")){
						trouve = true;
						listOfFiles[i].delete();
						JOptionPane.showMessageDialog(this, "Etude supprimée avec succès.");
						etudeEnCours = null;
						if(this.existeAuMoinsUneEtude()){
							this.demanderEtude();			
						}
						else{
							this.nouvelleEtude();
						}
					}
				}
				i++;
			}		
			if(i == tailleListe && !trouve){
				JOptionPane.showMessageDialog(this, "Etude supprimée avec succès.");
				etudeEnCours = null;
				if(this.existeAuMoinsUneEtude()){
					this.demanderEtude();			
				}
				else{
					this.nouvelleEtude();
				}		
			}		
		}
	}

	public static void main(String[] args) {
		//---La fenetre principale---
		MainMaximeAnsquer fenetrePrincipale = new MainMaximeAnsquer(); 
	}

}

