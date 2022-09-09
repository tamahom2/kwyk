package kwyk;

import java.io.Serializable;

import core.operations.collections.Block;
import kwyk.exceptions.ExpressionException;

public class Script implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Block<Boolean> root;

	public Script() {
		root = null;
	}
	
	public Script(Block<Boolean> root) {
		this.root = root;
	}
	
	public void run() {
		try {
			root.execute();
		} catch (ExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void setRoot(Block<Boolean> root) {
		this.root = root;
	}
	
	public Block<Boolean> getRoot()
	{
		return root;
	}
	
	public String toString() {
		String s = "{";
		if (root != null)
			s += root.description();
		else
			s += "EmptyScript";
		s += "}";
		return (s);
	}
}
