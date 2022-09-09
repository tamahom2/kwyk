package machines;

import kwyk.Material;
import kwyk.Matter;
import kwyk.exceptions.UnauthorisedMaterialsException;

public abstract class Generators extends Machine {
	Matter type;

	public void generate() throws UnauthorisedMaterialsException {
		if (this.materials.size() < this.stock_size)
			this.materials.add(new Material(getType()));
		else
			throw new UnauthorisedMaterialsException("Machine full");
	}
	
	@Override
	public void addMaterial(Material m) throws UnauthorisedMaterialsException {
		throw new UnauthorisedMaterialsException("Generators does not accept an input of materials.");		
	}

	public Matter getType() {
		return type;
	}
	
}
