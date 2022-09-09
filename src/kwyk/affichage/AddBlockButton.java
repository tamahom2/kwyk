package kwyk.affichage;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import core.Condition;
import core.operations.*;
import core.operations.collections.*;
import kwyk.BlockType;
import kwyk.Matter;
import kwyk.Script;
import kwyk.exceptions.ExpressionException;
import machines.Conveyer;
import machines.Generators;
import machines.HeatMachine;
import machines.Machine;

public class AddBlockButton extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AffichageNiv affichage;
	private static Block<?> placeToAdd;
	private int fieldn;
	private String[] texts;
	private Font uniforme = new Font("Arial", Font.PLAIN, 11);
	private JTextField[] fields;
	Machine	machine;
	
	public AddBlockButton(String s, int width, int numero, BlockType block, Machine m) {
		texts = s.split("#");
		this.machine = m;
		fieldn = texts.length - 1;
		fields = new JTextField[fieldn];
		this.setBackground(new Color(200,0,200));
		this.setFont(uniforme);
		//this.setLayout(new GridLayout(1, texts.length + fieldn + 1));
		JLabel label;
		int sizefield = 4;
		if (block == BlockType.Move || block == BlockType.Move)
			sizefield = 2;
		if (block == BlockType.If || block == BlockType.While)
			sizefield = 12;
		if (block == BlockType.AssignD || block == BlockType.AssignM)
			sizefield = 6;
		for (int i = 0; i < fieldn; i++) {
			label = new JLabel(texts[i]);
			label.setFont(uniforme);
			this.add(label);
			fields[i] = new JTextField(sizefield);
			this.add(fields[i]);
		}
		label = new JLabel(texts[fieldn]);
		label.setFont(uniforme);
		this.add(label);
		
		// BOUTON DE CONFIRMATION
		JButton validate = new JButton("+");
		validate.setBackground(Color.green);
		validate.addActionListener((event) -> {addBlock(block);affichage.actualiser();});
		this.add(validate);
		
		// SIZES
		this.setBounds(5, 5 + numero * 40, width - 10, 30);
	}

	public void	addBlock(BlockType block)
	{
		for (JTextField f : fields)
			if (f.getText().equals("")) {
				System.out.println("Empty field in block");
				return;
			}
		Script script = this.machine.getScript();
		if (script == null) {
			script = new Script();
			script.setRoot(new Block<Boolean>());
			this.machine.addScript(script);
			placeToAdd = script.getRoot();
		} else {
			placeToAdd = AffichageScript.selected;
		}
		switch (block) {
		case Move :
			if (fields.length == 4) {
				placeToAdd.append(new Move<Boolean>((Conveyer)machine, fields[0].getText(), fields[1].getText(), fields[2].getText(), fields[3].getText()));
				AffichageScript.newOp();
			}
			break;
		case SetHeat :
			if (fields.length == 1 && machine instanceof HeatMachine) {
				placeToAdd.append(new SetHeat<Boolean>((HeatMachine) machine, fields[0].getText()));
				AffichageScript.newOp();
			}
			break;
		case Generate :
			if (fields.length==0 && machine instanceof Generators) {
				placeToAdd.append(new Generate<Boolean>((Generators) machine));
			AffichageScript.newOp();
			}
			break;
				
		case AssignD :
			if (fields.length == 1) {
				placeToAdd.append(new Assign<Double>(fields[0].getText()));
				AffichageScript.newOp();
			}
			else if (fields.length == 2) {
				placeToAdd.append(new Assign<Double>(fields[0].getText(), Double.valueOf(fields[1].getText())));
				AffichageScript.newOp();
			}
			break;
		case AssignM :
			if (fields.length == 1) {
				placeToAdd.append(new Assign<Matter>(fields[0].getText()));
				AffichageScript.newOp();
			}
			else if (fields.length == 2) {
				placeToAdd.append(new Assign<Matter>(fields[0].getText(), Matter.valueOf(fields[1].getText())));
				AffichageScript.newOp();
			}
			break;
		case PrintL :
			if (fields.length == 1) {
				placeToAdd.append(new PrintLine(fields[0].getText()));
				AffichageScript.newOp();
			}
			break;
		case If :
			if (fields.length == 1) {
				try {
					Condition cond = new Condition(machine, fields[0].getText());
					placeToAdd.append(new If<Boolean>(new Block<>(), new Block<>(), cond));
					AffichageScript.newOp();
				} catch (ExpressionException e) {
					afficheraide(e.getMessage());
				}
			}
			break;
		case While :
			if (fields.length == 1) {
				try {
					Condition cond = new Condition(machine, fields[0].getText());
					placeToAdd.append(new While<Boolean>(new Block<>(), cond));
					AffichageScript.newOp();
				} catch (ExpressionException e) {
					afficheraide(e.getMessage());
				}
			}
			break;
		case For :
			if (fields.length == 3) {
				int a = Integer.valueOf(fields[0].getText());
				int b = Integer.valueOf(fields[1].getText());
				int c = Integer.valueOf(fields[2].getText());
				placeToAdd.append(new For<Boolean>(a, b , c));
			}
			break;
		}
	}

	private void afficheraide(String error) {
		int a = JOptionPane.showConfirmDialog(this, error
				+ "\nVariables locales :"
				+ "\nTOP (Dernière matière ajoutée)"
				+ "\nNBR (Nombre de matières dans la machine)"
				+ "\nHEAT (chaleur sur les machines qui chauffent)"
				+ "\nSIZE (capacitée en matériaux de la machine)"
				+ "\nVoulez vous un exemple ?");
		if (a==JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(this, "Exemple : '( NBR > 0 ) && ( TOP == Rouge )");
		}
	}
	
	public int getFieldn() {
		return fieldn;
	}

	public static void setAffichage(AffichageNiv affichage) {
		AddBlockButton.affichage = affichage;
	}
}
