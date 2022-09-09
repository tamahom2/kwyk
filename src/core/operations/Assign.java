package core.operations;

import core.Kwyk;
import core.Operation;
import core.ReturnValue;

/**
 *
 * Sert � d�clarer une nouvelle variable et � lui assigner une variable.
 *
 * @author Skander Jeddi
 *
 */

@SuppressWarnings("unchecked")
public class Assign<T> implements Operation<T> {
	private static final long serialVersionUID = -4260134291743686686L;

	// Nom de la variable
	private String name;
	// Valeur typ�e
	private T value;
	// Si cette variable d�pend d'une ou plusieurs autre(s) variable(s)
	private boolean isDynamic;
	// Le nom de(s) variable(s) li�e(s)
	private String dynamicValueName;

	/**
	 * D�clare une variable <i>name</i> et lui assigne la valeur de retour de
	 * l'op�ration pr�cedente.
	 *
	 * @param name le nom de la variable � d�clarer
	 */
	public Assign(String name) {
		this(name, (T) null);
	}

	/**
	 * D�clare une variable <i>name</i> et lui assigne la valeur <i>value</i>.
	 *
	 * @param name  le nom de la variable � d�clarer
	 * @param value la valeur de la variable
	 */
	public Assign(String name, T value) {
		this.name = name;
		this.value = value;
		this.isDynamic = false;
	}

	/**
	 * D�clare une variable <i>name</i> et lui assigne la valeur de la variable
	 * pr�-d�clar�e <i>value</i>.
	 *
	 * @param name  le nom de la variable � d�clarer
	 * @param value la valeur d'une variable d�j� existante
	 */
	public Assign(String name, String value) {
		this.name = name;
		this.dynamicValueName = value;
		this.value = null;
		this.isDynamic = true;
	}

	@Override
	public ReturnValue<T> execute(final ReturnValue<?>... returnValues) {
		if (this.isDynamic) {
			if (this.dynamicValueName.contains("{") && this.dynamicValueName.contains("}")) {
				String var = this.dynamicValueName.substring(this.dynamicValueName.indexOf("{") + 1,
						this.dynamicValueName.indexOf("}", this.dynamicValueName.indexOf("{") + 1));
				this.value = (T) Kwyk.engine().get(var);
			} else {
				this.value = null;
			}
		}
		if (this.value == null) {
			try {
				this.value = (T) returnValues[0].value();
			} catch (NullPointerException nullPointerException) {
				System.err.println(
						"A critical error occurred, a null ReturnValue has been passed to a variable assignment statement ("
								+ this.name + ")");
				System.exit(-1);
			}
		}
		if (this.value instanceof Double) {
			Kwyk.engine().setNumeric(this.name, (double) this.value);
		} else if (this.value instanceof String) {
			Kwyk.engine().setTextual(this.name, (String) this.value);
		} 
		return new ReturnValue<T>(this.value);
	}

	@Override
	public String description() {
		String s;
		s = this.name + "=";
		if (this.isDynamic)
			s += this.dynamicValueName;
		else if (value != null)
			s += this.value.toString();
		else
			s += "last value";
		return s;
	}
}
