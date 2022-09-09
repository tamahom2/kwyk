package core.operations.collections;

import core.Condition;
import core.Operation;
import core.ReturnValue;
import kwyk.exceptions.ExpressionException;

/**
 *
 * Boucle while.
 *
 * @author Skander Jeddi
 *
 */

@SuppressWarnings("unchecked")
public class While<T> extends Block<T> {
	private static final long serialVersionUID = -8517784587757403450L;

	private final Block<T> contents;
	private final Condition condition;

	public While(Block<T> contents, Condition condition) {
		super();
		this.contents = contents;
		this.condition = condition;
	}

	@Override
	public ReturnValue<T> execute(final ReturnValue<?>... returnValues) throws ExpressionException {
		ReturnValue<?> lastReturn = null;
		while (this.condition.check()) {
			try {
				lastReturn = this.contents.execute(lastReturn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (ReturnValue<T>) lastReturn;
	}
	
	public Condition getCondition() {
		return condition;
	}
	
	public Block<T> getContents() {
		return contents;
	}
}
