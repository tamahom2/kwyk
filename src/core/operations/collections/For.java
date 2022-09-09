package core.operations.collections;

import core.Kwyk;
import core.Operation;
import core.ReturnValue;

/**
 *
 * Boucle for.
 *
 * @author Skander Jeddi
 *
 */

@SuppressWarnings("unchecked")
public class For<T> extends Block<T> {
	private static final long serialVersionUID = 236954271233926230L;

	private int min, max, step;
	private int currentLoopsCount;
	private final Block<T> contents;

	public For() {
		this(0, 0, 0);
	}

	public For(int min, int max, int step) {
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
		this.step = step;
		this.currentLoopsCount = 0;
		this.contents = new Block<T>();
	}


	@Override
	public ReturnValue<T> execute(final ReturnValue<?>... returnValues) {
		ReturnValue<?> lastReturnValue = null;
		for (int i = this.min; i < this.max; i += this.step) {

			try {
				lastReturnValue = this.contents.execute(lastReturnValue, new ReturnValue<Integer>(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.currentLoopsCount += 1;
			if (this.currentLoopsCount > Kwyk.MAX_LOOP_COUNT) {
				System.err.println("ForLoop aborted");
				return null;
			}
		}
		return (ReturnValue<T>) lastReturnValue;
	}
	
	public String getCondition() {
		return this.min + " to " + this.max + " with step of " + this.step;
	}
	
	public Block<T> getContents() {
		return contents;
	}
}