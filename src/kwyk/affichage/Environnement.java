package kwyk.affichage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import kwyk.Niveau;

public class Environnement implements Serializable{
	private static final long serialVersionUID = 3968155942580492870L;
	public int maxNiv;
	public int cursorNiv;
	transient Ecran screen;
	transient Niveau current;
	

	public Environnement () {
		maxNiv = 1;
		cursorNiv = 1;
	}
	
	// Pour avoir accès à l'écran à partir de l'environnement
	public void setScreen(Ecran e) {
		screen = e;
	}
	
	//format des fichiers niveaux : NiveauX.niv avec X = son numéro, exemple : Niveau4.niv
	public void chargerNiveau() {
		//System.out.println("Chargement du niveau");
		File niveau = new File("saves/Niveau"+cursorNiv+".niv");
		System.out.println("Chargement du niveau");
		if (niveau.exists()) {
			try { //chargement du fichier de niveau sauvegardé
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(niveau));
				current = (Niveau)ois.readObject();
				ois.close();
				current.initTransients(this);
				AffichageNiv panelNiv= new AffichageNiv(current);
				this.screen.setContentPane(panelNiv);
				this.screen.setVisible(true);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		} else { // Tant qu'on a pas de fichier sauvegardés pour les niveaux
			System.out.println("Fichier pas encore créé. Chargement du niveau par défaut.");
			current = new Niveau(cursorNiv);
			current.initTransients(this);
			AffichageNiv panelNiv= new AffichageNiv(current);
			this.screen.setContentPane(panelNiv);
			this.screen.setVisible(true);
		}
		
	}
	
	public void save() {
		File save = new File("saves/Firstsave.ser");
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(save));
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	// Le niveau a été quitté donc le score ou nombre de niveau disponibles ont peut être changer
	public void niveauFini() {
		if (this.cursorNiv == maxNiv)
			maxNiv++;
		System.out.println(cursorNiv + " " + maxNiv);
		this.screen.select();
	}
}
