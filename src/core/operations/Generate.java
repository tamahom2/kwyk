package core.operations;

import core.Operation;
import core.ReturnValue;
import kwyk.exceptions.UnauthorisedMaterialsException;
import machines.Generators;

public class Generate<T> implements Operation<T>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Generators generateur;
	
	public Generate(Generators generateur) {
		this.generateur=generateur;
	}
	
	@Override
	public ReturnValue<T> execute(ReturnValue<?>... returnValues)  {
		// TODO Auto-generated method stub
			try {
				generateur.generate();
			} catch (UnauthorisedMaterialsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
		
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Generate("+generateur.getType()+")" ;
	}

}
