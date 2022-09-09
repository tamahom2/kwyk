package core.operations;

import java.util.Scanner;

import core.Operation;
import core.ReturnValue;

/**
 *
 * Cette classe est utilis�e pour demander une valeur � l'utilisateur.
 *
 * @author Skander Jeddi
 *
 */

@SuppressWarnings("unchecked")
public class Prompt<T> implements Operation<T> {
	private static final long serialVersionUID = -7930508108741524255L;

	private static final Scanner scanner = new Scanner(System.in);

	private String message;
	private Class<T> tClass;

	public Prompt(String message, Class<T> clazz) {
		this.message = message;
		this.tClass = clazz;
	}

	@Override
	public ReturnValue<T> execute(final ReturnValue<?>... returnValues) {
		System.out.print("-> " + this.message);
		String ans = Prompt.scanner.nextLine();
		T ansT = null;
		if (this.tClass.equals(Integer.class)) {
			ansT = (T) Integer.valueOf(ans);
		} else if (this.tClass.equals(Double.class)) {
			ansT = (T) Double.valueOf(ans);
		} else if (this.tClass.equals(String.class)) {
			ansT = (T) ans;
		}
		// TODO: other classes?
		return new ReturnValue<T>(ansT);
	}
	

	@Override
	public String description() {
		return "Prompt(" + this.message + ")";
	}
}
