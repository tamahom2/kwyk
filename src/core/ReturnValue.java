package core;

/**
 *
 * Représente une valeur renvoyée par une opération (voir {@link Operation}).
 *
 * @author Skander Jeddi
 *
 */
public class ReturnValue<T> {
	private final T value;

	public ReturnValue(T val) {
		this.value = val;
	}

	public T value() {
		return this.value;
	}
}
