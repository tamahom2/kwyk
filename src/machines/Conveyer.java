package machines;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import kwyk.Material;
import kwyk.exceptions.MoveMachineException;
import kwyk.exceptions.UnauthorisedMaterialsException;

public class Conveyer extends Machine {
	public final double MAX_DIST = 2.0;
	
	public Conveyer() {
		super();
		this.stock_size = 1;
	}

	@Override
	public void addMaterial(Material m) throws UnauthorisedMaterialsException {
		if (this.materials.size() < this.stock_size)
			this.materials.add(m);
		else
			throw new UnauthorisedMaterialsException("Machine full");
	}
	
	public void move(int x1,int y1,int x2,int y2) throws MoveMachineException {
		if(!(this.p.isWithinBounds(x1,y1) && this.p.isWithinBounds(x2,y2))) {
			throw new MoveMachineException("Move out of bounds");
		}
		//We should fix the maximum distance let's say for now it's 2
		if(!(this.distance(x1, y1)<=MAX_DIST && this.distance(x2, y2)<=MAX_DIST)) {
			throw new MoveMachineException("Move too far for this machine");
		}
		
		Material m = this.p.getGrid()[x1][y1].materials.pop();
		try {
			this.p.getGrid()[x2][y2].addMaterial(m);
		} catch (UnauthorisedMaterialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Image getImage() {
		Image image = null;
		//TODO : add the handle
		try {
			image = ImageIO.read(new File("images/machine.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image.getScaledInstance(145, 200, Image.SCALE_DEFAULT);
	}
	
}
