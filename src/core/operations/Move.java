package core.operations;

import core.Operation;
import core.ReturnValue;
import kwyk.exceptions.MoveMachineException;
import machines.Conveyer;

public class Move<T> implements Operation<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2290259217761787687L;
	private Conveyer machine;
	private int x1, y1, x2, y2;
	private String sx1, sy1, sx2, sy2;

	public Move(Conveyer machine, String x1,String y1, String x2, String y2) {
		this.machine = machine;
		sx1 = x1;
		sx2 = x2;
		sy1 = y1;
		sy2 = y2;
	}

	@Override
	public ReturnValue<T> execute(ReturnValue<?>... returnValues) throws MoveMachineException {
		
		Eval pos = new Eval(sx1);
		this.x1 = (int) Math.round(pos.execute().value());
		pos.setContent(sy1);
		this.y1 = (int) Math.round(pos.execute().value());
		pos.setContent(sx2);
		this.x2 = (int) Math.round(pos.execute().value());
		pos.setContent(sy2);
		this.y2 = (int) Math.round(pos.execute().value());
		this.machine.move(x1, y1, x2, y2);
		return null;

	}

	@Override
	public String description() {
		return "Move("+sx1+","+sy1+","+sx2+","+sy2+")";
	}

}
