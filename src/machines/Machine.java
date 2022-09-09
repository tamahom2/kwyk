package machines;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;


import core.operations.collections.Block;
import javax.imageio.ImageIO;

import kwyk.Material;
import kwyk.Matter;
import kwyk.Plateau;
import kwyk.Script;
import kwyk.exceptions.UnauthorisedMaterialsException;

public abstract class Machine implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Script script;
	protected LinkedList<Material> materials = new LinkedList<>();
	protected int stock_size;
	public boolean isExecutable = true;
	protected int i, j;
	protected Plateau p;
	
	public Machine ()
	{
		this.script = new Script(new Block<Boolean>());
		this.isExecutable = false;
	}

	public Machine(int i, int j, Plateau p) {
		this();
		this.i = i;
		this.j = j;
		this.p = p;
	}

	public Machine(Script s) { // Machine with an already made script
		this.script = s;
	}

	public Script getScript() {
		return script;
	}

	public void addScript(Script s) {
		this.isExecutable = true;
		this.script = s;
	}

	public abstract void addMaterial (Material m) throws UnauthorisedMaterialsException;
	
	public int countMaterial(Matter m)
	{
		int i = 0;
		for (Material mat : materials)
			if (mat.name == m)
				i++;
		return i;
	}

	public void waiting() {
		for (Material m : materials)
			m.waiting();
	}

	public void run() {
		script.run();
	}

	public void setScript(Script script) {
		this.script = script;
	}

	public int getPosX() {
		return this.j;
	}

	public int getPosY() {
		return this.i;
	}

	public Material getTopMaterial() {
		return this.materials.getLast();
	}

	public Material getLastMaterial() {
		return this.materials.getFirst();
	}

	public LinkedList<Material> getListMaterial() {
		return this.materials;
	}

	public double distance(int x, int y) {
		return Math.sqrt((i - x) * (i - x) + (j - y) * (j - y));
	}

	public String toString() {
		String s = this.getClass().getName() + "\n";
		if (s.contains("."))
			s = s.substring(s.lastIndexOf('.') + 1);
		if (this.script != null)
			s += "Script :\n" + script.toString();
		else
			s += "No Script";
		s += "\nMaterials : {";
		for (Material m : materials)
			s += m.name.name() + ", ";
		s += "}";
		return s;
	}

	public Image getImage() {
		Image image = null;
		try {
			image = ImageIO.read(new File("images/caseVide.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image.getScaledInstance(145, 200, Image.SCALE_DEFAULT);
	}

	public int getStock_size() {
		return stock_size;
	}
}
