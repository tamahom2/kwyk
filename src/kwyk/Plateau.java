package kwyk;

import java.util.Iterator;

import machines.Machine;

import java.io.Serializable;

public class Plateau implements Serializable,Cloneable,Iterable<Machine>{
	private static final long serialVersionUID = 1L;
	public Machine[][] grid;
	public int hauteur;
	public int largeur;

	public Plateau (int h, int l) {
		hauteur=h;
		largeur=l;
		grid = new Machine[hauteur][largeur];
		delete();
	}
	
	public void delete() {
		for (int i = 0; i < hauteur; i++)
			for (int j = 0; j < largeur; j++)
				grid[i][j] = null;
	}
	
	public boolean isWithinBounds(int a, int b) {
		return (a >= 0 && b >= 0 && a < hauteur && b < largeur);
	}
	
	public boolean suppMachine(int a, int b) {
		grid[a][b] = null;
		return true;
	}
	
	public Machine[][] getGrid(){
		return grid;
	}
	@Override
	public String toString() {
		String s = "Size : " + hauteur + " " + largeur + "\n";
		for (Machine[] line : grid) {
			for (Machine m : line) {
				if (m == null) {
					s += "x";
				} else {
					s += m.getClass().toString().charAt(0);
				}
			}
			s += "\n";
		}
		return s;
	}
	
	public Plateau clone() throws CloneNotSupportedException {
		Plateau copy = (Plateau)super.clone();
		copy.grid = this.grid.clone();
		return copy;
		
	}

	@Override
	public Iterator<Machine> iterator() {
		return new MachineIter();
	}
	
	private class MachineIter implements Iterator<Machine> {
		int x;
		int y;
		boolean prems;
		
		public MachineIter() {
			x = 0;
			y = 0;
			prems = true;
		}
		
		@Override
		public boolean hasNext() {
			int i = x;
			int j = y;
			if (prems && grid[x][y] != null)
				return true;
			if (++j >= largeur) {
				j = 0;
				i++;
			}
			while (hauteur > i && grid[i][j] == null)
			{
				if (++j >= largeur) {
					j = 0;
					i++;
				}
			}
			return (hauteur > i);
		}

		@Override
		public Machine next() {
			if (prems && grid[x][y] != null) {
				prems = false;
				return grid[x][y];
			}
			if (++y >= largeur) {
				y = 0;
				x++;
			}
			while (hauteur > x && grid[x][y] == null)
			{
				if (++y >= largeur) {
					y = 0;
					x++;
				}
			}
			if (x >= hauteur)
				return null;
			return grid[x][y];
		}
		
		
	}
	
}
