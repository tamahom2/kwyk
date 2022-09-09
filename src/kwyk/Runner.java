package kwyk;

import machines.Machine;

public class Runner {
	private	Plateau plat;
	
	public Runner (Plateau p)
	{
		plat = p;
	}
	
	public void run() {
		for(Machine[] lines : plat.grid)
			for (Machine m : lines)
				if (m != null && m.isExecutable)
					System.out.println(m + " : " + m.getScript());
				else
					System.out.println("Unexecutable machine");
	}

}
