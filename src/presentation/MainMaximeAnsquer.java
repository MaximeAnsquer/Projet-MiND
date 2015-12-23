package presentation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BoxLayout;
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
import abstraction.modules.SourcesDeMenaces;

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
	private JPanel contenuPrincipal;
	private Hashtable<String, JPanel> lesJpanels;
	private Workflow workflow;
	private Module moduleEnCours;
	private JLabel label;
	private Container contentPane;
	private int largeurEcran = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int hauteurEcran = Toolkit.getDefaultToolkit().getScreenSize().height;
	private JList listeFichiers;
	private JButton boutonOk;

	public MainMaximeAnsquer(){

		super("Outil d'analyse de risques");
		this.setPreferredSize(new Dimension(largeurEcran, (int) (0.95*hauteurEcran)));

		this.setJMenuBar(new BarreMenu(this));
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.contentPane = this.getContentPane();
		this.contenuPrincipal = new JPanel();
		this.contenuPrincipal.setLayout(new BorderLayout());
		this.partieDeGauche = new JPanel();
		this.lesJpanels = new Hashtable<String, JPanel>();
		this.pack();

		this.demanderEtude();	
	}

	private void demanderEtude() {
		String[] choix = {"Creer une nouvelle etude", "Ouvrir une etude existante"};
		String reponse = (String) JOptionPane.showInputDialog(null,	null, null,	JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);		
		if (reponse==choix[0]){
			this.nouvelleEtude();
		}
		if (reponse==choix[1]){
			this.choisirEtude();
		}		
	}
	/**
	 * 
	 * @param nom le nom du module a afficher, ou bien " workflow " si on veut afficher le workflow
	 */
	public void setContenu(String nom) {					

		contentPane.add(contenuPrincipal, BorderLayout.CENTER);

		contentPane.remove(partieDeGauche);

		if(nom.equals("Workflow")){					
			this.moduleEnCours = new Module("Workflow");
			this.contenuPrincipal.removeAll();
			this.workflow = new Workflow(etudeEnCours, this);
			this.contenuPrincipal.add(workflow, BorderLayout.CENTER);
		}
		else{
			this.moduleEnCours = etudeEnCours.getModule(nom);
			setPartieDeGauche();
			contentPane.add(partieDeGauche, BorderLayout.WEST);
			this.contenuPrincipal.removeAll();	
			this.label = new JLabel(moduleEnCours.toString(), SwingConstants.CENTER);
			this.label.setFont(new Font("Arial", Font.PLAIN, 25));

			if(nom.equals("CriteresDeSecurite")){
				this.lesJpanels.put(nom, new FenetreCriteresDeSecurite((CriteresDeSecurite) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("Metriques")){
				this.lesJpanels.put(nom, new FenetreMetriques((Metriques) etudeEnCours.getModule(nom)));
			}
			else if(nom.equals("SourcesDeMenaces")){
				System.out.println("lesJpanels : "+lesJpanels);
				System.out.println("etudeEnCours.getModule(nom) : "+etudeEnCours.getModule(nom));
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
				this.lesJpanels.put(nom, new FenetreEvenementsRedoutes( (EvenementsRedoutes) etudeEnCours.getModule(nom)));
			}

			this.contenuPrincipal.add(label, BorderLayout.NORTH);
			this.contenuPrincipal.add(lesJpanels.get(nom), BorderLayout.CENTER);
		}				

		this.validate();
		this.repaint();

	}

	private void setPartieDeGauche() {

		partieDeGauche = new JPanel();
		partieDeGauche.setLayout(new BoxLayout(partieDeGauche, BoxLayout.Y_AXIS));
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

			for(JLabel label : moduleEnCours.getProblemes()){
				partieDeGauche.add(label);
			}

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
		while(nomEtude.equals("")){
			nomEtude = JOptionPane.showInputDialog("Veuillez saisir un nom pour la nouvelle etude.");			
			if(nomEtude == null){
				System.exit(0);
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
		ArrayList<Object> data = new ArrayList<Object>();
		String urlEtudes = System.getProperty("user.dir") + File.separator + "etudes";
		File dossierEtude = new File(urlEtudes);
		File[] listOfFiles = dossierEtude.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				data.add(listOfFiles[i].getName());
			}
		}
		listeFichiers = new JList(data.toArray());
		listeFichiers.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				boutonOk.setEnabled(true);				
			}
		});
		JFrame fenetreChoixFichier = fenetreChoixFichier(listeFichiers);		
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
		final JFrame fenetre = new JFrame();
		fenetre.setVisible(true);
		fenetre.getContentPane().add(new JLabel("Veuillez choisir l'etude a ouvrir"), BorderLayout.NORTH);
		fenetre.getContentPane().add(jlist, BorderLayout.CENTER);
		boutonOk = new JButton("OK");
		boutonOk.setEnabled(false);
		boutonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String urlEtude = System.getProperty("user.dir") + File.separator + "etudes" + File.separator + listeFichiers.getSelectedValue();
				ouvrirEtude(urlEtude);
				fenetre.dispose();				
			}
		});
		fenetre.getContentPane().add(boutonOk, BorderLayout.SOUTH);
		fenetre.pack();
		return fenetre;
	}
	public Etude getEtude(){
		return etudeEnCours;
	}
	public void setModuleEnCours(Module m){
		this.moduleEnCours = m;
	}
	public void modifierNomEtude() {
		String nouveauNom = "";
		while(nouveauNom != null && nouveauNom.equals("")){
			nouveauNom = JOptionPane.showInputDialog("Veuillez saisir le nouveau nom de l'etude");
			if(nouveauNom != null){
				this.etudeEnCours.setNom(nouveauNom);
				this.setTitle("Outil d'analyse de risques - Etude en cours : " + nouveauNom);
			}
		}		
	}
	public static void main(String[] args) {
		//---La fenetre principale---
		MainMaximeAnsquer fenetrePrincipale = new MainMaximeAnsquer(); 
	}
}

