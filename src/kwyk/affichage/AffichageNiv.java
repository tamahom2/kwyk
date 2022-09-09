package kwyk.affichage;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import org.w3c.dom.Text;

import kwyk.BlockType;
import kwyk.Niveau;
import kwyk.Plateau;
import machines.*;

public class AffichageNiv extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Niveau modele;
	Machine machineCurrent;
	JPanel jplat, mid, addMns = new JPanel();
	AffichageScript script;
	JTabbedPane creeBlocks = new JTabbedPane();
	int width;
	int height;
	static ImageIcon blanc = 	null;
	static ImageIcon bleu = null;
	static ImageIcon jaune = null;
	static ImageIcon marron = null;
	static ImageIcon orange = null;
	static ImageIcon rose =null;
	static ImageIcon rouge = null;
	static ImageIcon turquoise =null;
	static ImageIcon verdatre = null;
	static ImageIcon vert = null;
	//new ImageIcon(ImageIO.read(new File("images/gemmeBlanc.png")).getScaledInstance(61, 42, Image.SCALE_DEFAULT));


	public AffichageNiv (Niveau n) {
		this.setLayout(null);
		modele=n;
		width = n.envi.screen.width;
		height = n.envi.screen.height;
		machineCurrent = n.currentPlat.grid[n.currentx][n.currenty];
		AddBlockButton.setAffichage(this);
		mid = new JPanel();
		mid.setLayout(new CardLayout());
		mid.setBounds(width / 3 + 10, 80, width / 3, height - 180);
		this.add(mid);

		if(blanc==null) {
			try {
				blanc =new ImageIcon(ImageIO.read(new File("images/gemmeBlanc.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				bleu =new ImageIcon(ImageIO.read(new File("images/gemmeBleu.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				jaune =new ImageIcon(ImageIO.read(new File("images/gemmeJaune.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				marron =new ImageIcon(ImageIO.read(new File("images/gemmeMarron.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				orange =new ImageIcon(ImageIO.read(new File("images/gemmeOrange.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				rose =new ImageIcon(ImageIO.read(new File("images/gemmeRose.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				rouge =new ImageIcon(ImageIO.read(new File("images/gemmeRouge.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				turquoise =new ImageIcon(ImageIO.read(new File("images/gemmeTurquoise.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				verdatre =new ImageIcon(ImageIO.read(new File("images/gemmeVerdatre.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
				vert =new ImageIcon(ImageIO.read(new File("images/gemmeVert.png")).getScaledInstance(50,34, Image.SCALE_DEFAULT));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Script Panel create
		script = new AffichageScript(n, width / 3, height - 180);
		script.setBounds(width / 3 + 10, 80, width / 3, height - 180);
		JButton delete = new JButton("Supprimer la machine");
		delete.setBackground(Color.red);
		delete.setBounds(0, height - 220, width / 3 - 20, 40);
		delete.addActionListener((event) -> {
			int a = JOptionPane.showConfirmDialog(delete, "Voulez vous vraiment supprimer la machine ?");
			if (a==JOptionPane.YES_OPTION) {
				modele.suppMachine();actualiser();}
		});
		script.add(delete);
		mid.add(script);

		//Affichage du plateau central
		jplat = affichagePlateau(n.currentPlat);

		this.add(jplat);



		//Panel pour faire les nouveaux blocks 
		for (int i = 0; i < 3; i++) {
			JPanel inside = new JPanel();
			inside.setBounds(0, 0, width / 3, height - 180);
			String label = "";
			switch (i) {
			case 0 : // Rajouter différents blocks selon leur type
				label = "Actions";
				inside.setBackground(Color.blue);
				break;
			case 1 :
				label = "Opérations";
				inside.setBackground(Color.green);
				break;
			case 2 :
				label = "Blocks logiques";
				inside.setBackground(Color.orange);
				break;
			default :
				break;
			}
			this.creeBlocks.addTab(label, inside);
		}
		creeBlocks.setBounds(width * 2 / 3 + 10, 80, width / 3 - 15, height - 180);
		this.add(creeBlocks);

		//Panel pour ajouter les machines
		this.addMns.setLayout(new GridLayout(6,1));
		addMns.setBounds(width / 3 + 10, 80, width / 3 - 20, height - 180);
		for (int i = 0; i < 5; i++) {
			JButton machine = new JButton();
			machine.setBackground(Color.green);
			// Créations des boutons pour ajouter les machines
			switch (i) {
			case 0 :
				machine.setText("Ajouter un Four");
				machine.addActionListener((event) -> {modele.addMachine(new Furnace());actualiser();});
				break;
			case 1 :
				machine.setText("Ajouter un convoyeur");
				machine.addActionListener((event) -> {modele.addMachine(new Conveyer());actualiser();});
				break;
			case 2 :
				machine.setText("Ajouter une mine de gemmes lunaires");
				machine.addActionListener((event) -> {modele.addMachine(new MoonGemImport());actualiser();});
				break;
			case 3 :
				machine.setText("Ajouter une mine de gemmes nocturnes");
				machine.addActionListener((event) -> {modele.addMachine(new NightGemImport());actualiser();});
				break;
			case 4 :
				machine.setText("Ajouter une mine de gemmes solaires");
				machine.addActionListener((event) -> {modele.addMachine(new SunGemImport());actualiser();});
				break;
			default:
				break;
			}

			// Ajout des boutons selon les machines autorisées dans le niveau
			addMns.add(machine);
			mid.add(addMns);
		}
		//Add Level rules
		JLabel l= new JLabel();
		JLabel l1= new JLabel();
		JLabel l2= new JLabel();
		JLabel l3= new JLabel();
		switch(n.num) {
		case 1 :
			l.setText("Gemme Solaire + 230° --> Gemme Crépuscule");
			l.setBounds(5, height - 90, width - 30, 60);
			l.setVisible(true);
			l.setOpaque(true);
			this.add(l);

			l1.setIcon(orange);
			l1.setText("4");
			l1.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l1.setBounds(width-width/3, 10, 80, 80);;
			l1.setVisible(true);
			l1.setOpaque(true);
			this.add(l1);

			l2.setIcon(bleu);
			l2.setText("2");
			l2.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l2.setBounds((width-width/3)+120, 10, 80, 80);;
			l2.setVisible(true);
			l2.setOpaque(true);
			this.add(l2);


			break;
		case 2 :
			l.setText("Gemme Ciel + Gemme Vert + 150° --> Gemme Turquoise");
			l.setBounds(100, height - 90, width - 30, 100);
			l.setVisible(true);
			l.setOpaque(true);
			this.add(l);


			l1.setIcon(turquoise);
			l1.setText("4");
			l1.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l1.setBounds(width-width/3, 10, 80, 80);;
			l1.setVisible(true);
			l1.setOpaque(true);
			this.add(l1);

			l2.setIcon(rouge);
			l2.setText("2");
			l2.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l2.setBounds((width-width/3)+120, 10, 80, 80);;
			l2.setVisible(true);
			l2.setOpaque(true);
			this.add(l2);

			break;
		case 3 :
			l.setText("Gemme Rouge + Gemme Vert + 165° --> Gemme Marron\t Gemme Rouge + Gemme Argentée + 150° --> Gemme Rose");
			l.setBounds(5, height - 90, width - 30, 60);
			l.setVisible(true);
			l.setOpaque(true);
			this.add(l);


			l1.setIcon(marron);
			l1.setText("8");
			l1.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l1.setBounds(width-width/3, 10, 80, 80);;
			l1.setVisible(true);
			l1.setOpaque(true);
			this.add(l1);

			l2.setIcon(rose);
			l2.setText("12");
			l2.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l2.setBounds((width-width/3)+120, 10, 80, 80);;
			l2.setVisible(true);
			l2.setOpaque(true);
			this.add(l2);



			break;
		case 4 :
			l.setText(" Gemme Vert + Gemme Argentée + 125° --> Gemme Verdatre\tGemme Rouge + Gemme Solaire + 150° --> Gemme Crépuscule");
			l.setBounds(5, height - 90, width - 30, 60);
			l.setVisible(true);
			l.setOpaque(true);
			this.add(l);


			l1.setIcon(orange);
			l1.setText("10");
			l1.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l1.setBounds(width-width/3, 10, 80, 80);;
			l1.setVisible(true);
			l1.setOpaque(true);
			this.add(l1);

			l2.setIcon(verdatre);
			l2.setText("15");
			l2.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l2.setBounds((width-width/3)+120, 10, 80, 80);;
			l2.setVisible(true);
			l2.setOpaque(true);
			this.add(l2);

			l3.setIcon(marron);
			l3.setText("12");
			l3.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l3.setBounds((width-width/3)+240, 10, 80, 80);;
			l3.setVisible(true);
			l3.setOpaque(true);
			this.add(l3);


			break;
		case 5 :
			l.setText("Gemme Feu + Gemme Vert + Gemme Ciel + 300° --> Gemme Argentée");
			l.setBounds(5, height - 90, width - 30, 60);
			l.setVisible(true);
			l.setOpaque(true);
			this.add(l);


			l1.setIcon(blanc);
			l1.setText("20");
			l1.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l1.setBounds(width-width/3, 10, 80, 80);;
			l1.setVisible(true);
			l1.setOpaque(true);
			this.add(l1);

			l2.setIcon(verdatre);
			l2.setText("28");
			l2.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l2.setBounds((width-width/3)+120, 10, 80, 80);;
			l2.setVisible(true);
			l2.setOpaque(true);
			this.add(l2);

			l3.setIcon(turquoise);
			l3.setText("15");
			l3.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 27));
			l3.setBounds((width-width/3)+120, 10, 80, 80);;
			l3.setVisible(true);
			l3.setOpaque(true);
			this.add(l3);

			break;

		default : break;
		}


		//Add Quit Button
		JButton quit = new JButton("Quitter");
		quit.setBackground(Color.red);
		quit.setBounds(width * 11 / 12, 30, width /12  - 5, 40);
		quit.addActionListener((event) -> {
			int a = JOptionPane.showConfirmDialog(quit, "Voulez vous sauvegarder vos modifications ?");
			if (a==JOptionPane.YES_OPTION) {
					modele.retour(true);
			}
			if (a==JOptionPane.NO_OPTION) {
				modele.retour(false);
			}
		});
		this.add(quit);
		actualiser();
	}

	public JPanel affichagePlateau(Plateau p) {
		jplat = new JPanel();
		jplat.setLayout(new GridLayout(p.hauteur, p.largeur));//à remplacer avec les valeurs de plateau
		jplat.setOpaque(true);
		for (int i = 0; i < p.hauteur; i++) 
			for (int j = 0; j < p.largeur; j++) {
				Bouton bouton = new Bouton(i,j);
				bouton.setIcon(null);
				bouton.addActionListener((event) -> {modele.selectionner(bouton.x, bouton.y);actualiser();});
				jplat.add(bouton);
			}
		jplat.setBounds(0, 80, width / 3, height - 180);
		actualiser();
		return jplat;
	}



	public void actualiser() {
		// Plateau Actualisation
		Component [] component = jplat.getComponents();
		Plateau plato = modele.currentPlat;
		//Current Machine variables
		int x = this.modele.currentx;
		int y = this.modele.currenty;
		this.machineCurrent = plato.grid[x][y];
		for (int i=0; i<component.length; i++) { // Actualiser chaque bouton
			Bouton bouton = (Bouton)component[i];
			Dimension d = new Dimension(140,140);
			bouton.setMinimumSize(d);
			bouton.setMaximumSize(d);
			bouton.setPreferredSize(d);
			if (plato.grid[bouton.x][bouton.y] == null) // Si il n'y a pas une machine à cet endroit
			{
				bouton.setIcon(null);
				bouton.setOpaque(true);
				bouton.setContentAreaFilled(false);
				bouton.setBorderPainted(true);
				if (bouton.x == x && bouton.y == y)  // Bouton séléctionné
					bouton.setBorder(new LineBorder(Color.GREEN));
				else
					bouton.setBorder(new LineBorder(Color.BLACK));
				bouton.setText(null);
			}
			else // Si il y a une machine à cet endroit
			{
				bouton.setOpaque(false);
				bouton.setContentAreaFilled(false);
				if (bouton.x == x && bouton.y == y) { // Bouton séléctionné
					bouton.setBorderPainted(true);
					bouton.setBorder(new LineBorder(Color.green, 3));
			} else
					bouton.setBorderPainted(false);
				Machine m = plato.grid[bouton.x][bouton.y];
				bouton.setIcon(new ImageIcon(m.getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT)));
				if (m instanceof Furnace) // Selon le type de la machine change l'affichage du bouton
					bouton.setText("Four");
				else if (m instanceof NightGemImport)
					bouton.setText("Mine de gemmes nocturnes");
				else if (m instanceof SunGemImport)
					bouton.setText("Mine de gemmes solaires");
				else if (m instanceof MoonGemImport)
					bouton.setText("Mine de gemmes lunaires");
				else if (m instanceof Conveyer) 
					bouton.setText("Convoyeur");
			}
		}
		// Actualisation depending on the Machine
		if (this.machineCurrent == null) {
			// Si il n'y a pas de machine, ne pas afficher de script, proposer l'ajout d'une machine
			((CardLayout) mid.getLayout()).last(mid);
			this.creeBlocks.setVisible(false);
		} else {
			// Si il y a une machine, afficher le script, proposer d'ajouter des blocks, ne pas proposer l'ajout de machine
			((CardLayout) mid.getLayout()).first(mid);
			script.actualiser(this.machineCurrent);
			this.creeBlocks.setVisible(true);
			//Change les boutons pour ajouter les blocks :
			for (int t = 0; t < this.creeBlocks.getTabCount(); t++) {
				JPanel inside = (JPanel)this.creeBlocks.getComponentAt(t);
				inside.removeAll();
				switch (t) {
				case 0 : // Rajouter différents blocks selon leur type
					if (this.machineCurrent instanceof HeatMachine)
						inside.add(new AddBlockButton("SetHeat # °C", width / 3, 0, BlockType.SetHeat, machineCurrent));
					if (this.machineCurrent instanceof Conveyer)
						inside.add(new AddBlockButton("Move from (#,#) to (#,#)", width / 3, 1, BlockType.Move, machineCurrent));
					if (this.machineCurrent instanceof Generators)
						inside.add(new AddBlockButton("Generate", width / 3, 0, BlockType.Generate, machineCurrent));
					break;
				case 1 :
					inside.add(new AddBlockButton("Assign Double : # = # ", width / 3, 0, BlockType.AssignD, machineCurrent));
					inside.add(new AddBlockButton("Assign Matter : # = # ", width / 3, 0, BlockType.AssignM, machineCurrent));
					break;
				case 2 :
					inside.add(new AddBlockButton("If # :", width / 3, 0, BlockType.If, machineCurrent));
					inside.add(new AddBlockButton("From # to # with step # :", width / 3, 0, BlockType.For, machineCurrent));
					inside.add(new AddBlockButton("While # :", width / 3, 0, BlockType.While, machineCurrent));
					break;
				default :
					break;
				}
				inside.updateUI();
			}
		}
	}

	class Bouton extends JButton {
		private static final long serialVersionUID = 1L;
		public final int x;
		public final int y;

		public Bouton (int i, int j) {
			super();
			x = i;
			y=j;
		}
		
	}
}
