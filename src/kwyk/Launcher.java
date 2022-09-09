package kwyk;

import kwyk.affichage.Ecran;
import kwyk.affichage.Environnement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Launcher {
	
	public static void main (String[] args) {
		Scanner toanalyze;
		File arg;
		Environnement envi = null;
		
		if (args.length == 0) {
			//Default Procedure
			//Run all
			
			
			// création de la fenêtre sans sauvegarde
			envi = new Environnement();
			// sauvegarde
//			File save = new File("Firstsave.ser");
//			ObjectOutputStream oos;
//			try {
//				oos = new ObjectOutputStream(new FileOutputStream(save));
//				oos.writeObject(envi);
//				oos.close();
				afficher(envi);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return;
//			}
		}
		else {
			if (args[0].equals("debug")) {
				//Debug Procedure
				System.out.println("Debug procedure.");
			}
			else if (args[0].equals("manual")) {
				//Manual Input, via System.in
				toanalyze = new Scanner(System.in);

				toanalyze.close();
			}
			else if (args.length == 1)
			{
				// taking first argument as a file to analyze
				arg = new File(args[0]);
				try { //chargement du fichier sauvegardé
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arg));
					envi = (Environnement)ois.readObject();
					ois.close();
					afficher(envi);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ReflectiveOperationException e) {
					e.printStackTrace();
				}
			}
						
		}
	}
	
	static void exitDisplay(String s)
	{
		System.out.println("Le programme a dû quitter.");
		System.out.println(s);
		System.exit(0);
	}

	static void afficher (Environnement e) {
		Ecran window = new Ecran(e);
		java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
	        	window.setVisible(true);
	        }
		});
	}
}


