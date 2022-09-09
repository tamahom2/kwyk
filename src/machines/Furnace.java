package machines;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import kwyk.Material;
import kwyk.exceptions.UnauthorisedMaterialsException;

public class Furnace extends HeatMachine{
	
	public Furnace() {
		super();
		this.stock_size = 5;
	}
	
	public void heat_inside() {
		for (Material mat : materials)
			mat.heated(heat);
	}
	
	@Override
	public void waiting() {
		this.heat_inside();
		for (Material m : materials)
			m.waiting();
	}

	@Override
	public void addMaterial(Material m) throws UnauthorisedMaterialsException {
		if (this.materials.size() < this.stock_size)
			this.materials.add(m);
		else
			throw new UnauthorisedMaterialsException("Machine full");
	}

	public void move(int x1, int y1, int x2, int y2) {
		// TODO Throw error to make it easier for us
		
	}
	
	@Override
	public Image getImage() {
		Image image = null;
		try {
			image = (this.heat>0)?ImageIO.read(new File("images/fourA.jpg")):
					ImageIO.read(new File("images/fourE.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image.getScaledInstance(145, 200, Image.SCALE_DEFAULT);
	}
}
