package core.operations;

import core.Operation;
import core.ReturnValue;
import kwyk.exceptions.HeatSettingException;
import machines.HeatMachine;

public class SetHeat<T> implements Operation<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2290259217761787687L;
	private HeatMachine machine;
	private String sheat;
	private int heat;

	public SetHeat(HeatMachine machine, String heat) {
		this.machine = machine;
		this.sheat = heat;
	}

	@Override
	public ReturnValue<T> execute(ReturnValue<?>... returnValues) throws HeatSettingException{
				
		try {
			Eval h = new Eval(sheat);
			this.heat = (int) Math.round(h.execute().value());
			this.machine.setHeat(heat);;
			return null;
		}
		catch(Exception exception) { 
			throw new HeatSettingException("",exception);
		}
	}

	@Override
	public String description() {
		return "SetHeat("+sheat+")";
	}

}
