package machines;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import kwyk.Material;
import kwyk.Matter;

public class NightGemImport extends Generators {

	public NightGemImport() {
		super();
		type = Matter.NOIR;
		this.stock_size = 10;
	}

	@Override
	public Image getImage() {
		Image image = null;
		// TODO : add all pictures
		try {
			image = (this.materials.size() == 0) ? ImageIO.read(new File("images/caseVide.jpg"))
					: ImageIO.read(new File("images/casenoir.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image.getScaledInstance(145, 200, Image.SCALE_DEFAULT);
	}
}
