package kwyk;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Matter {
	ROUGE("Rouge"), NOIR("Noir"), ARGENT("Argent"), JAUNE("Jaune"), BLEU("Bleu");

	private final String name;
	
	Matter(String string) {
		this.name = string;
	}

	public String getName() {
		return name;
	}
	
	
}
