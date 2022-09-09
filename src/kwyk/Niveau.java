package kwyk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import core.operations.collections.Block;
import kwyk.affichage.Environnement;
import core.Operation;
import machines.Furnace;
import machines.Machine;
import machines.NightGemImport;
import machines.SunGemImport;

public class Niveau implements Serializable {
	private static final long serialVersionUID = 1L;
	public transient Environnement envi;
	
	//Constantes à sauvegarder
	public final int num;
	public final int[] starScore = {4000, 6000, 7000};
	
	//Variables propre à la partie en cours
	public Plateau currentPlat;
	public transient Runner runner;
	public boolean modified = false;
	// Pour choisir le code de la machine à modifier
	public int currentx = 0;
	public int currenty = 0;
	
	//Maybe useful
	public transient int totalMachine;
	public transient int gameState;
	
	
	public Niveau(int i) {
		num = i;
		switch (i) {
		case 1 :
			currentPlat = new Plateau(3, 3);
			Machine m = new NightGemImport();
			this.addMachine(m);
			this.selectionner(1, 0);
			this.addMachine(new SunGemImport());
			this.selectionner(1, 2);
			this.addMachine(new Furnace ());
			break;
		case 2 :
			currentPlat = new Plateau(3, 3);
			break ;
		case 3 :
			currentPlat = new Plateau(3, 3);
			break;
		case 4 :
			currentPlat = new Plateau(3, 3);
			break ;
		case 5 :
			currentPlat = new Plateau(4, 4);
			break;
		default :
			break ;
			
		}
	}
	
	public void initTransients(Environnement e) {
		gameState = 0;
		totalMachine = 0;
		envi = e;
	}
	
	@Override
	public String toString() {
		String s = "Level " + num + "\n";
		s += "Current selection : " + this.currentx + " " + this.currenty + "\n";
		s += this.currentPlat.toString();
		for (Machine m : this.currentPlat)
		{
			s += m.toString() + "\n";
		}
		return s;
	}
	
	public void jouer() {
		// Just scanning System.in and calling functions accordingly
		// Later will be done via buttons.
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		while (!line.equals("stop"))
		{
			switch (line) {
				case "run" :
					submit();
					break;
				case "runOnce" :
					runOnce();
					break;
				case "print" :
					System.out.println(this);
					break;
				default :
					break;			
			}
			line = sc.nextLine();
		}
		sc.close();
	}
	
	public void selectionner(int a, int b) {
		if (!currentPlat.isWithinBounds(a, b))
		{
			System.out.println("Vous séléctionnez hors des limites du plateau !");
			return;
		}
		currentx = a;
		currenty = b;
	}
	
	public void addMachine(Machine m) {
		if (this.currentx == -1)
			return;
		this.currentPlat.grid[currentx][currenty] = m;
		this.modified = true;
	}
	
	public void suppMachine() {
		this.currentPlat.suppMachine(this.currentx, this.currenty);
	}
	
	public void addScript(Script s) {
		if (this.currentx == -1)
			return;
		this.currentPlat.grid[currentx][currenty].addScript(s);
		this.modified = true;
	}

	public <T> void addBlock(Block<T> b) {
		this.currentPlat.grid[currentx][currenty].getScript().getRoot().append(b);
		this.modified = true;
	}

	public <T> void addOp(Operation<T> o) {
		this.currentPlat.grid[currentx][currenty].getScript().getRoot().append(o);
		this.modified = true;
	}

	public void runOnce() {
		if (this.modified || runner == null)
			try {
				runner = new Runner(this.currentPlat.clone());
				this.modified = false;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return;
			}
		runner.run();
	}
	
	public void submit() {
		runner = new Runner(this.currentPlat);//.clone());
		System.out.println("Running level "+this.num+" : " );
		runner.run();
		//See if materials are produced
		if (gameState > 1)
			retour(true);
	}
	
	// à appeler quand le niveau est fini et qu'on veux envoyer des informations
	// à l'environnement
	public void retour(boolean save) {
		if (save) {
			File file = new File("saves/Niveau" + this.num + ".niv");
			try {
				file.createNewFile();
			} catch (IOException e1) {
				File dir = new File("saves/");
				dir.mkdir();
			}
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(this);
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		this.envi.niveauFini();
	}
}
