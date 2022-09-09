package kwyk.affichage;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.Operation;
import core.operations.*;
import core.operations.collections.*;
import kwyk.Niveau;
import machines.Machine;

public class AffichageScript extends JPanel {
	private static final long serialVersionUID = 1L;
	Niveau modele;
	Machine machineCurrent;
	LinkedList<OpVisuel> operations = new LinkedList<>();
	private int pos;
	private int min;
	int width, height;
	static Block<?> selected;
	static JSlider selector;
	static boolean opAdded = false;

	public AffichageScript(Niveau n, int width, int height) {
		modele=n;
		Machine current = n.currentPlat.grid[n.currentx][n.currenty];
		min = 0;
		this.setLayout(null);
		this.width = width;
		this.height = height;
		if (current != null && current.getScript() != null) {
			selected = current.getScript().getRoot();
			selector = new JSlider(JSlider.VERTICAL, 0, 17, 0);
			selector.setMajorTickSpacing(1);
			selector.setPaintTicks(true);
			selector.setInverted(true);
			selector.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent evt) {
			          JSlider slider = (JSlider) evt.getSource();
			          if (!slider.getValueIsAdjusting()) {
			            select();
			          }
			        }
			      });
			actualiser(current);
		}
	}
	
	public static void newOp() {
		opAdded = true;
		System.out.println("New Op to Add");
	}
	
	private void select() {
		int pos = AffichageScript.selector.getValue();
		Block<?> b = null;
		if (pos == 0 && min > 0) {
			min--;
			replacinglabels();
			selector.setValue(1);
		}
		else if (pos == 17 && min + 17 < this.operations.size()) {
			min++;
			replacinglabels();
			selector.setValue(16);
		}
		if (pos + min < this.operations.size())
			b = this.operations.get(pos + min).getParentBlock();
		if (b != null) {
			selected = b;
		}
	}
	
	public void actualiser(Machine current) {
		//TODO
		if (current == null)
			return;
		if (current != this.machineCurrent || opAdded) {
			this.machineCurrent = current;
			Block<Boolean> root = machineCurrent.getScript().getRoot();
			operations = new LinkedList<>();
			pos = -1;
			addop(root, 0, root);
			min = 0;
			select();
		} else return;
		replacinglabels();
	}
	
	public void replacinglabels() {
		Component savesupp = null;
		if (this.getComponentCount() > 0)
			savesupp = this.getComponent(this.getComponentCount() - 1);
		this.removeAll();
		for (int i = 0 ; i + min < operations.size() && i < 18; i++) {
			OpVisuel visu = operations.get(i + min);
			visu.setBounds(30, i* height / 20, width , height / 20 - 2);
			this.add(visu);
		}
		selector.setBounds(0, 17, 30, height / 20 * 18);
		this.add(selector);
		if (savesupp != null) {
			this.add(savesupp);
		}
		this.repaint();
	}
	
	private void addop(Operation<?> op, int depth, Block<?> parentop) {
		if (pos == -1 || pos >= operations.size())
			pos = operations.size();
		if (op instanceof If<?>) {
			If<?> realop = (If<?>) op;
			Block<?> ifcont = realop.getIfContents();
			Block<?> elsecont = realop.getElseContents();			
			operations.add(pos, new OpVisuel("If " + realop.getCondition() + " then {", depth, ifcont));
			pos++;
			for (Operation<?> ope : ifcont.getOpsList()) {
				addop(ope, depth + 1, ifcont);
				pos++;
			}
			operations.add(pos, new OpVisuel("} else {", depth, elsecont));
			pos++;
			for (Operation<?> ope : elsecont.getOpsList()) {
				addop(ope, depth + 1, elsecont);
				pos++;
			}
			operations.add(pos, new OpVisuel("}", depth, parentop));
		} else if (op instanceof For<?>) {
			For<?> realop = (For<?>) op;
			Block<?> cont = realop.getContents();
			operations.add(pos, new OpVisuel("For " + realop.getCondition() + " {", depth, cont));
			pos++;
			for (Operation<?> ope : cont.getOpsList()) {
				addop(ope, depth + 1, cont);
				pos++;
			}
			operations.add(new OpVisuel("}", depth, cont));
		} else if (op instanceof While<?>) {
			While<?> realop = (While<?>) op;	
			Block<?> cont = realop.getContents();		
			operations.add(pos, new OpVisuel("While " + realop.getCondition().toString() + " {", depth, cont));
			pos++;
			for (Operation<?> ope : cont.getOpsList()) {
				addop(ope, depth + 1, realop);
				pos++;
			}
			operations.add(new OpVisuel("}", depth, parentop));
		} else if (op instanceof Block<?>) {
			Block<?> realop = (Block<?>) op;
			operations.add(pos, new OpVisuel("{", depth, parentop));
			pos++;
			for (Operation<?> ope : realop.getOpsList()) {
				addop(ope, depth + 1, realop);
				pos++;
			}
			operations.add(pos, new OpVisuel("}", depth, realop));
		} else {
			// Any other operations do not have other operations inside of them
			// and are thus easier to deal with, we just have to print their description
			operations.add(pos, new OpVisuel(op.description(), depth, parentop));
		}
	}
	
	
	
	private class OpVisuel extends JLabel {
		/**
		 *  Visuel de chaque opération dans le script
		 */
		private static final long serialVersionUID = 1L;
		private Block<?> parent;
		public int depth;

		public OpVisuel(String s, int d, Block<?> p) {
			super();
			depth = d;
			String tab = "";
			while (d-- > 0)
				tab += "  ";
			this.setText(tab + s);
			parent = p;
		}
		
		public Block<?> getParentBlock() {
			return parent;
		}
		
	}
}
