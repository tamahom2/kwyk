package core.operations.collections;

import core.Condition;
import core.Operation;
import core.ReturnValue;
import kwyk.exceptions.ExpressionException;

/**
 *
 * Bloc if.
 *
 * @author Skander Jeddi
 *
 */

public class If<T> extends Block<T> {
	private static final long serialVersionUID = -4789930092847867381L;

	private final Condition condition;
	private final Block<T> ifContents;
	private final Block<T> elseContents;
	
	public If(Block<T> ifContents, Block<T> elseContents, Condition condition) {
		super();
		this.condition = condition;
		this.ifContents = ifContents;
		this.elseContents = elseContents;
	}

	@Override
	public ReturnValue<T> execute(final ReturnValue<?>... returnValues) throws ExpressionException {

		if (this.condition.check()) {
			try {
				return this.ifContents.execute(returnValues);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				return this.elseContents.execute(returnValues);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}

	

	@Override
	public String description() {
		String s = "[If :, then :";
		s += this.ifContents.description();
		s += ", else : " + this.elseContents.description();
		s += "]";
		return s;
	}
	
	public String description(String condition) {
		String s = "[If : "+condition+" , then : ";
		s += this.ifContents.description();
		s += ", else : " + this.elseContents.description();
		s += "]";
		return s;
	}
	
	public Condition getCondition() {
		return condition;
	}
	
	public Block<T> getElseContents() {
		return elseContents;
	}
	
	public Block<T> getIfContents() {
		return ifContents;
	}
}
