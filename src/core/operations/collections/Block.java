package core.operations.collections;

import java.util.LinkedList;

import core.Operation;
import core.ReturnValue;
import kwyk.exceptions.ExpressionException;

/**
 *
 * A block contains a sequence of statements (which can also be blocks) and runs
 * these statements sequentially.
 *
 * @author Skander Jeddi
 *
 */
@SuppressWarnings("unchecked")
public class Block<T> implements Operation<T> {
	private static final long serialVersionUID = 4894651110232442206L;

	protected LinkedList<Operation<?>> opsList;

	public Block() {
		this.opsList = new LinkedList<Operation<?>>();
	}

	@Override
	public ReturnValue<T> execute(final ReturnValue<?>... returnValues) throws ExpressionException {
		ReturnValue<?> previousReturnValue = null;
		for (Operation<?> statement : this.opsList) {
			try {
				previousReturnValue = statement.execute(previousReturnValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (ReturnValue<T>) previousReturnValue;
	}

	public void append(Operation<?> statement) {
		this.opsList.add(statement);
	}

	public void append(Operation<?>... statements) {
		for (Operation<?> statement : statements) {
			this.append(statement);
		}
	}

	public Operation<?> getPrevious(Operation<?> op) {
		int index = 0;
		for (Operation<?> op1 : this.opsList) {
			if (op1 == op) {
				if (index == 0) {
					return null;
				} else {
					return this.opsList.get(index - 1);
				}
			}
			index += 1;
		}
		return null;
	}

	public Operation<?> getNext(Operation<?> op) {
		int index = 0;
		for (Operation<?> op1 : this.opsList) {
			if (op1 == op) {
				if (index == (this.opsList.size() - 1)) {
					return null;
				} else {
					return this.opsList.get(index + 1);
				}
			}
			index += 1;
		}
		return null;
	}

	public LinkedList<Operation<?>> getOpsList() {
		return opsList;
	}
	
	@Override
	public String description() {
		String s = "[StartBlock";
		for (Operation<?> o : this.opsList)
			s += "\n" + o.description();
		s += "]";
		return s;
	}
	
}
