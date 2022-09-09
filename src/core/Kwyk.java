package core;

import java.util.HashMap;
import java.util.Map;

import kwyk.Matter;

/**
 *
 * Cette classe repr�sente le moteur d'ex�cution s�quentielle, elle sert �
 * garder un registre des variables et de leur valeur.
 *
 * @author Skander Jeddi
 *
 */
public final class Kwyk {
	// Si un assignement de variable �choue, arr�ter ou non l'ex�cution
	public static final boolean ASSIGN_KERNEL_PANIC = true;
	// Nombre maximal d'it�rations dans une boucle
	public static final int MAX_LOOP_COUNT = 1000;

	private static Kwyk engine;

	// private final Map<String, Object> variables;
	private final Map<String, Double> numericVariables;
	private final Map<String, String> textualVariables;
	private final Map<String, Matter> materialVariables;

	public static Kwyk engine() {
		return Kwyk.engine == null ? Kwyk.engine = new Kwyk() : Kwyk.engine;
	}

	private Kwyk() {
		// this.variables = new HashMap<String, Object>();
		this.numericVariables = new HashMap<String, Double>();
		this.textualVariables = new HashMap<String, String>();
		this.materialVariables = new HashMap<String, Matter>();
	}

	// Executer une seule operation
	public void execute(Operation<?> op) {
		try {
			op.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// Retrouver la valeur d'une variable assign�e au pr�alable
	public Object get(String name) {
		return this.numericVariables.containsKey(name) ? this.numericVariables.get(name) : this.getMatter(name);
	}

	public double getNumeric(String name) {
		return this.numericVariables.getOrDefault(name, Double.NaN);
	}

	public String getTextual(String name) {
		return this.textualVariables.get(name);
	}
	
	public Matter getMatter(String name) {
		return this.materialVariables.get(name);
	}

	// Retrouver la valeur d'une variable assignée au préalable
	/** public Object get(String name) {
		if ((this.variables.get(name) != null) && (this.variables.get(name) instanceof ReturnValue<?>)) {
			return ((ReturnValue<?>) this.variables.get(name)).value();
		}
		return this.variables.get(name);
	}**/

	public void setNumeric(String name, double value) {
		this.numericVariables.put(name, value);
	}

	public void setTextual(String name, String value) {
		this.textualVariables.put(name, value);
	}

	// Ajouter une variable au registre
	/** public void set(String name, Object value) {
		if (value == null) {
			System.out.println("A null value has been assigned to variable " + name + "!");
			if (Kwyk.ASSIGN_KERNEL_PANIC) {
				System.err.println("Kernel panic mode is enabled, exiting...");
				System.exit(-1);
			}
		}
		this.variables.put(name, value);
	} **/
}
