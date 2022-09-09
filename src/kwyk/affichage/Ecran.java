package kwyk.affichage;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ecran extends JFrame{
	private static final long serialVersionUID = 1L;
	Environnement envi;
	AffichageSelect selection = null;
	HomeMenu menu = null;
	public int width=1300;
	public int height=750;
	
	
	public Ecran (Environnement e) {
		super();
		this.setTitle("KWYK");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		envi = e;
		this.setSize(width, height);
		envi.setScreen(this);
		menu();
	}
	
	public void select() {
		if (selection == null)
			selection = new AffichageSelect(envi);
		selection.actualiser();
		this.setContentPane(selection);
		this.setVisible(true);
	}
	
	public void menu() {
		if (menu == null)
			menu = new HomeMenu();
		this.setContentPane(menu);
		this.setVisible(true);
	}
	
	private class HomeMenu extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image bgImage = null;
		
		public HomeMenu() {
			this.setLayout(null);//new GridLayout(6, 1));
			File f = new File("images//pageDacceuil.jpg");
			if (f.exists()) {
				try {
					bgImage = ImageIO.read(f);
					bgImage = new ImageIcon("images/pageDacceuil.jpg").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
				} catch (IOException except) {
					except.printStackTrace();
				}
			} else {
				System.out.println("BackgroundImg, bad link.");
			}
			JButton play = new JButton();
			play.setOpaque(false);
			play.setContentAreaFilled(false);
			play.setIcon(new ImageIcon("images/right.png"));
			play.setBounds(615, 340, 70, 70);
			play.setBorderPainted(false);
			play.addActionListener((event) -> select());
			this.add(play);
		}
		

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			if (bgImage != null) {
				g2.drawImage(bgImage, 0, 0, null);
				g2.dispose();
			}
			else
				System.out.println("no backgroundimage");
		}
	}
}