package kwyk.affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class AffichageSelect extends JPanel {
	private static final long serialVersionUID = 1L;
	Environnement modele;
	ArrayList<Bouton> niveaux = new ArrayList<>();
	JButton play, home, save, music;
	private Image bgImage = null;
	boolean noSound= true;
	Clip clip;
	
	public AffichageSelect (Environnement e) {
		modele = e;
		this.setLayout(null);//new GridLayout(6, 1));
		File f = new File("images/pageSelection.jpg");
		if (f.exists()) {
			try {
				bgImage = ImageIO.read(f);
				bgImage = new ImageIcon("images/pageSelection.jpg").getImage().getScaledInstance(1290, 740, Image.SCALE_DEFAULT);
			} catch (IOException except) {
				except.printStackTrace();
			}
		} else {
			System.out.println("BackgroundImg, bad link.");
		}
		for (int i = 1; i < 6; i++)
		{
			int x =  325;
			int y =  i * (1300 / 6)-50 ;
			Bouton niv = new Bouton(i);
			niv.setText(String.valueOf(i));
			niv.setOpaque(true);
			niv.setContentAreaFilled(false);
			niv.setFont(new Font("Arial", Font.BOLD, 60));
			niv.setBounds(y, x, 100, 100);
			if ( i > e.maxNiv) {
				niv.setEnabled(false);
				niv.setBorderPainted(false);
			}
			if (i == e.cursorNiv) {
				niv.setEnabled(false);
			}
			if ( i <= e.maxNiv)
				niv.setBorder(new LineBorder(Color.green, 3, true));;
				niv.addActionListener((event) -> changeNiv(niv.num));niv.setOpaque(false);
				niveaux.add(niv);
				this.add(niv);
		}
		play = new JButton("Jouer au niveau 1 !");
		play.setContentAreaFilled(false);
		play.setOpaque(true);
		play.setFont(new Font("Arial", Font.BOLD, 40));
		play.setBounds(400, 550, 480, 50);
		play.setBorderPainted(false);
		Image g = null;
		if (g != null)
			play.setIcon(new ImageIcon(g));
		else
			play.setBackground(Color.green);
		play.addActionListener((event) -> modele.chargerNiveau());
		this.add(play);
		home = new JButton();
		home.setContentAreaFilled(false);
		home.setBorderPainted(false);
		home.setIcon(new ImageIcon("images/home.png"));
		home.setBounds(0, 0, 70, 70);
		home.addActionListener((event)-> modele.screen.menu());
		this.add(home);
		save = new JButton();
		save.setContentAreaFilled(false);
		save.setOpaque(false);
		save.setBorderPainted(false);
		save.setIcon(new ImageIcon("images/save.png"));
		save.setBounds(1230, 0, 70, 70);
		save.addActionListener((event)-> {modele.save();save.setIcon(new ImageIcon("images/checkmark.png"));save.setEnabled(false);});
		this.add(save);
		music = new JButton();
		music.setContentAreaFilled(false);
		music.setOpaque(false);
		music.setBorderPainted(false);
		music.setBounds(0, 600, 70, 70);;
		switchMusic();
		music.addActionListener((event)-> {switchMusic();});
		this.add(music);

	}
	public void switchMusic() {
		File f= new File("music/sb-indreams.wav");
		if(f.exists()){
			try {
				if (clip == null)
					clip = AudioSystem.getClip();
				if (noSound) {
					AudioInputStream audioInput= AudioSystem.getAudioInputStream(f);
					clip.open(audioInput);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					music.setIcon(new ImageIcon("images/musicOn.png"));
					noSound = false;
				}
				else {
					clip.stop();
					clip.close();
					noSound = true;
					music.setIcon(new ImageIcon("images/musicOff.png"));
				}
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			} else {
				System.out.println("Music File Not Found");
			}
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

	// Le niveau a été quitté donc le score ou nombre de niveau disponibles ont peut être changer
	public void actualiser() {
		for (int i = 1; i < 6; i++)
		{
			if ( i <= modele.maxNiv && i != modele.cursorNiv)
				this.niveaux.get(i - 1).setEnabled(true);
			if ( i <= modele.maxNiv) {
				this.niveaux.get(i - 1).setBorderPainted(true);
				this.niveaux.get(i - 1).setBorder(BorderFactory.createLineBorder(Color.green));
			}
		}
		save.setIcon(new ImageIcon("images/save.png"));
		save.setEnabled(true);
	}

	// Click sur un bouton de séléction
	private void changeNiv(int cursor) {
		this.niveaux.get(modele.cursorNiv - 1).setEnabled(true);
		modele.cursorNiv = cursor;
		this.niveaux.get(cursor - 1).setEnabled(false);
		this.play.setText("Jouer au niveau " + cursor + " !");
	}

	private BufferedImage loadImage(String src) {
		BufferedImage img;
		File f = new File(src);
		if (!f.exists()) {
			System.out.println("L'image n'existe pas.");
			return null;
		}
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return img;
	}

	// Pour pouvoir numéroter les boutons 
	private class Bouton extends JButton {
		private static final long serialVersionUID = 1L;
		public final int num;

		public Bouton (int i) {
			super();
			num = i;
		}
	}

}