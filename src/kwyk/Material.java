package kwyk;

public class Material {
	public Matter name;
	public int heat = 0;
	private int tick = 0;
	
	public Material (Matter m) {
		name = m;
	}

	public Material (Matter m, int h) {
		heat = h;
	}

	public void heated(int h) {
		heat = (h * 2 + heat) / 3;
	}

	public void waiting() {
		switch (name.getName()) {
		case "Rouge" :
			if (this.heat > 1300)
				tick++;
			else
				tick = 0;
			if (tick >= 3)
				this.name = Matter.NOIR;
			break;
		default :
			break;
		}
			
	}

	public int getHeat() {
		return heat;
	}

}