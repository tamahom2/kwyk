package machines;

public abstract class HeatMachine extends Machine{
	int heat = 0;
	
	public void	setHeat(int h) {
		heat = h;
	}
	
	public int	getHeat() {
		return heat;
	}
	
}
