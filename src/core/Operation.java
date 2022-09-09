package core;

import java.io.Serializable;

/**
 *
 * Une operation correspond grossi�rement � une ligne de code usuel.
 *
 * @author Skander Jeddi
 *
 */
public interface Operation<T> extends Serializable {
	ReturnValue<T>	execute(final ReturnValue<?>... returnValues) throws Exception;
	String			description();
}
